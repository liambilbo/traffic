/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dxc.bankia.event;


import com.dxc.bankia.BaseTest;
import com.dxc.bankia.model.Country;
import com.dxc.bankia.model.Driver;
import com.dxc.bankia.model.Event;
import com.dxc.bankia.model.Vehicle;
import com.dxc.bankia.services.FinderService;
import com.dxc.bankia.util.DriverBuilder;
import com.dxc.bankia.util.EventBuilder;
import com.dxc.bankia.util.VehicleBuilder;
import org.drools.compiler.compiler.DrlParser;
import org.junit.Assert;
import org.junit.Test;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.Channel;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;

import static com.dxc.bankia.model.functions.DateUtils.toDate;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;


/**
 *
 * @author esteban
 */
public class RuleEventEnrichmentTest extends BaseTest {

    /**
     * Tests customer-classification-simple.drt template using the configuration
     * present in kmodule.xml.
     */
    @Test
    public void testSimpleTemplateWithSpreadsheet1(){

        KieSession ksession = this.createSession("enrichmentKSession");
        FinderService finderService=getFinderSerrvice();


        //Implement a Channel that notifies AuditService when new instances of
        //SuspiciousOperation are available.
        final Set<Event> results = new HashSet<>();
        Channel errorServiceChannel = new Channel(){

            @Override
            public void send(Object object) {
                //notify AuditService here. For testing purposes, we are just
                //going to store the received object in a Set.
                results.add((Event) object);
            }

        };

        ksession.setGlobal("finderService", finderService);
        ksession.registerChannel("error-channel",errorServiceChannel);

        this.doTest(ksession,results);
    }

    private void doTest(KieSession ksession,Set<Event> results){

        Event event1 = new EventBuilder()
                .withId(1L)
                .withType(Event.Type.REQUEST_CAR_ITV_COMPLIANCE)
                .withRegistrationNumber("XSC 1234")
                .build();

        Event event2 = new EventBuilder()
                .withId(2L)
                .withType(Event.Type.REQUEST_CAR_ITV_COMPLIANCE)
                .withRegistrationNumber("XSC 66666")
                .build();

        Event event3 = new EventBuilder()
                .withId(3L)
                .withType(Event.Type.REQUEST_DRIVER_ITV_COMPLIANCE)
                .withIdentificationNumber("A3456737X")
                .build();

        Event event4 = new EventBuilder()
                .withId(4L)
                .withType(Event.Type.REQUEST_DRIVER_ITV_COMPLIANCE)
                .withIdentificationNumber("VD345737X")
                .build();


        ksession.insert(event1);
        ksession.insert(event2);
        ksession.insert(event3);
        ksession.insert(event4);

        ksession.fireAllRules();

        assertThat(event1.getVehicle(), not(nullValue()));
        assertThat(event2.getVehicle(), nullValue());
        assertThat(event3.getDriver(), not(nullValue()));
        assertThat(event4.getDriver(), nullValue());


        Assert.assertThat(results, hasSize(2));
        Assert.assertThat(
                results.stream().map(so -> so.getId()).collect(toList())
                , containsInAnyOrder( 2L,4L)
        );


    }

    private KieSession createKieSessionFromDRL(String drl){
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);

        Results results = kieHelper.verify();

        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: "+message.getText());
            }

            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }

        return kieHelper.build().newKieSession();
    }

    /**
     * Tests customer-classification-simple.drt template by manually creating
     * the corresponding DRL using a spreadsheet as the data source.
     */
    public void testSimpleTemplateWithSpreadsheet2() throws Exception{

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        File dslrFile = new File(classLoader.getResource("com.dxc.bankia/event/event-enrichment.drl").getFile());

        String dslrContent = new String(Files.readAllBytes(dslrFile.toPath()));
        System.out.println(dslrContent);


        File dslFile = new File(classLoader.getResource("com.dxc.bankia/event/event-enrichment.dsl").getFile());

        String dslContent = new String(Files.readAllBytes(dslFile.toPath()));
        System.out.println(dslContent);

        InputStream dsl = RuleEventEnrichmentTest.class.getResourceAsStream("/com.dxc.bankia/event/event-enrichment.dsl");

        DrlParser parser = new DrlParser();
        String drl = parser.getExpandedDRL(  dslrContent, new StringReader(dslContent) );

        System.out.println(drl);

        KieSession ksession = this.createKieSessionFromDRL(drl);

        this.doTest(ksession,null);
    }


     private FinderService getFinderSerrvice(){

        return  new FinderService() {

            @Override
            public Vehicle getVehicleByRegistrationNumber(String registrationNumber) {
                switch (registrationNumber){
                    case "XSC 1234":
                        return new VehicleBuilder()
                                .withId(1L)
                                .withModel("320 d")
                                .withCategory(Vehicle.Category.CAR)
                                .withColor(Vehicle.Color.BLUE)
                                .withBrand(Vehicle.Brand.BMW)
                                .withRegistrationNumber("XSC 1234")
                                .withCountry(Country.ES)
                                .withLastItvDate(toDate(LocalDate.of(2005,7,23)))
                                .withRegistrationDate(toDate(LocalDate.of(1999,7,23)))
                                .build();
                    case "BBB 324":
                        return new VehicleBuilder()
                                .withId(2L)
                                .withModel("320 d")
                                .withCategory(Vehicle.Category.CAR)
                                .withColor(Vehicle.Color.BLUE)
                                .withBrand(Vehicle.Brand.BMW)
                                .withRegistrationNumber("BBB 324")
                                .withCountry(Country.ES)
                                .withLastItvDate(toDate(LocalDate.of(2017,7,23)))
                                .withRegistrationDate(toDate(LocalDate.of(1999,7,23)))
                                .build();
                    case "LL 231":
                        return new VehicleBuilder()
                                .withId(3L)
                                .withModel("125 city")
                                .withCategory(Vehicle.Category.BIKE)
                                .withColor(Vehicle.Color.RED)
                                .withBrand(Vehicle.Brand.BMW)
                                .withRegistrationNumber("LL 231")
                                .withCountry(Country.ES)
                                .withRegistrationDate(toDate(LocalDate.of(2018,7,23)))
                                .build();
                    default:
                        return null;
                }
            }



            @Override
            public Driver getDriverByIdentificationNumber(String identificationNumber) {
                switch (identificationNumber){
                    case "A3456737X":
                        return new DriverBuilder()
                                .withId(1L)
                                .withIdentificationNumber("A3456737X")
                                .withLicenseNumber("LT 1234")
                                .withName("Jose","Smith")
                                .withDateOfBirth(toDate(LocalDate.of(1968,4,10)))
                                .withNationality(Country.ES)
                                .withVehicle(getVehicleByRegistrationNumber("XSC 1234"))
                                .withVehicle(getVehicleByRegistrationNumber("LL 231"))
                                .build();
                    default:
                        return null;
                }
            }
        };

    }


    private Channel getChannel(){

        final Set<Event> results = new HashSet<>();
        return  new Channel(){

            @Override
            public void send(Object object) {
                //notify AuditService here. For testing purposes, we are just
                //going to store the received object in a Set.
                results.add((Event) object);
            }

        };

    }



}
