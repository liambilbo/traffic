package com.dxc.bankia.util;


import com.dxc.bankia.model.Driver;
import com.dxc.bankia.model.VehicleLicense;

import java.util.Date;

public class VehicleLicenseBuilder {


    private final VehicleLicense instance;

    private static Long idGenerator = 0L;

    public VehicleLicenseBuilder(Driver driver) {
        instance = new VehicleLicense();
        instance.setId(idGenerator++);
        instance.setDriver(driver);
    }

    public VehicleLicenseBuilder withId(Long id){
        instance.setId(id);
        return this;
    }


    private Long id;
    private Driver driver;
    private VehicleLicense.Type type;
    private Date inceptionDate;


    public VehicleLicenseBuilder withInceptionDate(Date inceptionDate){
        instance.setInceptionDate(inceptionDate);
        return this;
    }
    public VehicleLicenseBuilder withType(VehicleLicense.Type type){
        instance.setType(type);
        return this;
    }


    public VehicleLicense build(){
        return instance;
    }

    public VehicleLicenseBuilder end() {
        return this;
    }
}
