package com.dxc.bankia.util;


import com.dxc.bankia.model.Country;
import com.dxc.bankia.model.Driver;
import com.dxc.bankia.model.Vehicle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class VehicleBuilder {



    private final Vehicle instance;

    private Optional<DriverBuilder> driverBuilder = Optional.empty();
    private Optional<VehicleLicenseBuilder> vehicleLicenseBuilder = Optional.empty();

    private static Long iddGenerator = 0L;

    public VehicleBuilder() {
        instance = new Vehicle();
        instance.setId(iddGenerator++);
    }

    public VehicleBuilder(Driver driver) {
        instance = new Vehicle();
        instance.setId(iddGenerator++);
        instance.setOwner(driver);
    }

    public VehicleBuilder withId(Long id){
        instance.setId(id);
        return this;
    }


    public VehicleBuilder withRegistrationNumber(String registrationNumber){
        instance.setRegistrationNumber(registrationNumber);
        return this;
    }

    public VehicleBuilder withCategory(Vehicle.Category category){
        instance.setCategory(category);
        return this;
    }

    public VehicleBuilder withColor(Vehicle.Color color){
        instance.setColor(color);
        return this;
    }

    public VehicleBuilder withBrand(Vehicle.Brand brand){
        instance.setBrand(brand);
        return this;
    }
    public VehicleBuilder withModel(String model){
        instance.setModel(model);
        return this;
    }

    public VehicleBuilder withRegistrationDate(Date registrationDate){
        instance.setRegistrationDate(registrationDate);
        return this;
    }

    public VehicleBuilder withCountry(Country country){
        instance.setCountry(country);
        return this;
    }

    public VehicleBuilder withLastItvDate(Date lastItvDate){
        instance.setLastItvDate(lastItvDate);
        return this;
    }

    public VehicleBuilder withNextItvDate(Date nextItvDate){
        instance.setLastItvDate(nextItvDate);
        return this;
    }
    public Vehicle build(){
        return instance;
    }

    public VehicleBuilder end() {
        return this;
    }
}
