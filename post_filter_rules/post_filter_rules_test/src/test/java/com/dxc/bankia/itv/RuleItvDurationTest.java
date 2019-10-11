/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dxc.bankia.itv;


import com.dxc.bankia.BaseTest;
import com.dxc.bankia.model.Country;
import com.dxc.bankia.model.Vehicle;
import com.dxc.bankia.model.functions.DateUtils.*;
import com.dxc.bankia.util.VehicleBuilder;
import org.drools.decisiontable.ExternalSpreadsheetCompiler;
import org.junit.Test;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.dxc.bankia.model.functions.DateUtils.toDate;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;




/**
 *
 * @author esteban
 */
public class RuleItvDurationTest extends BaseTest {

    /**
     * Tests customer-classification-simple.drt template using the configuration
     * present in kmodule.xml.
     */
    @Test
    public void testSimpleTemplateWithSpreadsheet1(){

        KieSession ksession = this.createSession("itvPeriodKSession");

        this.doTest(ksession);
    }

    /**
     * Tests customer-classification-simple.drt template by manually creating
     * the corresponding DRL using a spreadsheet as the data source.
     */
    @Test
    public void testSimpleTemplateWithSpreadsheet2(){

        InputStream template = RuleItvDurationTest.class.getResourceAsStream("/com.dxc.bankia.itv-template/itv-period.drt");
        InputStream data = RuleItvDurationTest.class.getResourceAsStream("/com.dxc.bankia.itv-template/itv-period-data.xls");

        ExternalSpreadsheetCompiler converter = new ExternalSpreadsheetCompiler();
        String drl = converter.compile(data, template, 3, 2);

        System.out.println(drl);

        KieSession ksession = this.createKieSessionFromDRL(drl);

        this.doTest(ksession);
    }


    private void doTest(KieSession ksession){
        Vehicle vehicle1 = new VehicleBuilder()
                .withId(1L)
                .withModel("320 d")
                .withCategory(Vehicle.Category.CAR)
                .withColor(Vehicle.Color.BLUE)
                .withBrand(Vehicle.Brand.BMW)
                .withRegistrationNumber("XSC 1234")
                .withCountry(Country.ES)
                .withRegistrationDate(toDate(LocalDate.of(1999,7,23)))
                .build();


        Vehicle vehicle2 = new VehicleBuilder()
                .withId(2L)
                .withModel("320 d")
                .withCategory(Vehicle.Category.CAR)
                .withColor(Vehicle.Color.BLUE)
                .withBrand(Vehicle.Brand.BMW)
                .withRegistrationNumber("BBB 324")
                .withCountry(Country.ES)
                .withRegistrationDate(toDate(LocalDate.of(2016,7,23)))
                .build();



        ksession.insert(vehicle1);
        ksession.insert(vehicle2);

        ksession.fireAllRules();

        assertThat(vehicle1.getCategory(), not(nullValue()));

        vehicle1.getCategory();


//        assertThat(vehicle1.getCategory(), is(Customer.Category.NA));
//        assertThat(customer2.getCategory(), is(Customer.Category.BRONZE));
//        assertThat(customer3.getCategory(), is(Customer.Category.SILVER));
//        assertThat(customer4.getCategory(), is(Customer.Category.GOLD));
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

}
