package com.dxc.bankia.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class VehicleLicense implements Serializable {

    public enum Type {
        B1, B2
    }



    private Long id;
    private Driver driver;
    private Type type;
    private Date inceptionDate;

    public VehicleLicense(){

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getInceptionDate() {
        return inceptionDate;
    }

    public void setInceptionDate(Date inceptionDate) {
        this.inceptionDate = inceptionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VehicleLicense)) return false;
        VehicleLicense that = (VehicleLicense) o;
        return getId().equals(that.getId()) &&
                getDriver().equals(that.getDriver()) &&
                getType() == that.getType() &&
                getInceptionDate().equals(that.getInceptionDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDriver(), getType(), getInceptionDate());
    }


}
