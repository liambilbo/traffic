package com.dxc.bankia.util;



import com.dxc.bankia.model.Country;
import com.dxc.bankia.model.Driver;
import com.dxc.bankia.model.Vehicle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DriverBuilder {


    private final Driver instance;
    private Optional<VehicleLicenseBuilder> vehicleLicenseBuilder = Optional.empty();
    private Optional<VehicleBuilder> vehicleBuilder = Optional.empty();

    private static Long idGenerator = 0L;

    public DriverBuilder() {
        instance = new Driver();
        instance.setId(idGenerator++);
    }

    public DriverBuilder withId(Long id){
        instance.setId(id);
        return this;
    }


    public DriverBuilder withIdentificationNumber(String name){
        instance.setIdentificationNumber(name);
        return this;
    }

    public DriverBuilder withName(String firstName,String lastName){
        instance.setFirstName(firstName);
        instance.setLastName(lastName);
        return this;
    }


    public DriverBuilder withDateOfBirth(Date dateOfBirth){
        instance.setDateOfBirth(dateOfBirth);
        return this;
    }

    public DriverBuilder withNationality(Country nationality){
        instance.setNationality(nationality);
        return this;
    }

    public DriverBuilder withLicenseNumber(String licenseNumber){
        instance.setLicenseNumber(licenseNumber);
        return this;
    }

    public DriverBuilder newVehicleLicense(){
        if (vehicleLicenseBuilder.isPresent()) {
            instance.getLicenses().add(vehicleLicenseBuilder.get().build());
        }
        vehicleLicenseBuilder = Optional.of(new VehicleLicenseBuilder(instance));
        return this;
    }

    public VehicleBuilder newVehicle(){
        if (vehicleBuilder.isPresent()) {
            instance.getVehicles().add(vehicleBuilder.get().build());
        }
        vehicleBuilder = Optional.of(new VehicleBuilder(instance));
        return vehicleBuilder.get();
    }

    public DriverBuilder withVehicle(Vehicle vehicle){
        vehicle.setOwner(instance);
        instance.getVehicles().add(vehicle);
        return this;
    }


    public Driver build(){
        if (vehicleLicenseBuilder.isPresent()) {
            instance.getLicenses().add(vehicleLicenseBuilder.get().build());
        }
        return instance;
    }

    public DriverBuilder end() {
        return this;
    }
}
