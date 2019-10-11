/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.Channel;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.dxc.bankia.model.functions.DateUtils.toDate;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/**
 *
 * @author esteban
 */
public class SparkRulesTest {

    /**
     * Tests customer-classification-simple.drt template using the configuration
     * present in kmodule.xml.
     */
    @Test
    public void testSimpleTemplateWithSpreadsheet1(){

        KieSession ksession = this.createSession("sparkKSession");
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

    /**
     * For performance purposes, we keep a cached container. Please note that
     * this is not Thread Safe at all!.
     */
    private static KieContainer cachedKieContainer;

    protected KieSession createDefaultSession() {
        return this.createContainer().newKieSession();
    }

    protected KieBase createKnowledgeBase(String name) {
        KieContainer kContainer = this.createContainer();
        KieBase kbase = kContainer.getKieBase(name);

        if (kbase == null){
            throw new IllegalArgumentException("Unknown Kie Base with name '"+name+"'");
        }

        return kbase;
    }

    protected KieSession createSession(String name) {


        String userDir = System.getProperty("user.dir");
        Path customPath = Paths.get(userDir, "settings.xml");
        //System.setProperty("kie.maven.settings.custom", customPath.toString());

        KieContainer kContainer = this.createContainer();
        KieSession ksession = kContainer.newKieSession(name);

        if (ksession == null){
            throw new IllegalArgumentException("Unknown Session with name '"+name+"'");
        }

        return ksession;
    }

    protected <T> Collection<T> getFactsFromKieSession(KieSession ksession, Class<T> classType) {
        return (Collection<T>) ksession.getObjects(new ClassObjectFilter(classType));
    }

    private KieContainer createContainer(){
        if (cachedKieContainer != null){
            return cachedKieContainer;
        }

        KieServices ks = KieServices.Factory.get();


        //GAV.
        String groupId = "com.dxc.bankia";
        String artifactId = "spark_rules_kjar";
        String version = "1.0";

        //KieServices ks = KieServices.Factory.get();
        ReleaseId releaseId = ks.newReleaseId(groupId, artifactId, version);


        KieContainer kContainer = ks.newKieContainer(releaseId);

        //The KieContainer is wrapped by a KieScanner.
        //Note that we are never starting the KieScanner because we want to control
        //when the upgrade process kicks in.
        //KieScanner scanner = ks.newKieScanner(kContainer);

        Results results = kContainer.verify();

        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.printf("[%s] - %s[%s,%s]: %s", message.getLevel(), message.getPath(), message.getLine(), message.getColumn(), message.getText());
            }

            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }

        cachedKieContainer = kContainer;
        return cachedKieContainer;
    }

}
