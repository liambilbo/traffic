package com.dxc.bankia.model;

import org.drools.core.factmodel.traits.Traitable;
import org.omg.CORBA.Object;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

@Traitable
public class Event implements Serializable {

    public enum Type {
        REQUEST_SANCTION_COMPLIANCE,REQUEST_CAR_ITV_COMPLIANCE,REQUEST_DRIVER_ITV_COMPLIANCE
    }


    private Long id;
    private Type type;
    private String registrationNumber;


    private String identificationNumber;


    private Vehicle vehicle;



    private Driver driver;

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }


}
