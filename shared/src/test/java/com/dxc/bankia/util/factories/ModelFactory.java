package com.dxc.bankia.util.factories;

import com.dxc.bankia.model.Vehicle;
import com.dxc.bankia.util.VehicleBuilder;

public class ModelFactory {

    public static Vehicle getVehicle(){
        return new VehicleBuilder()
                .withBrand(Vehicle.Brand.BMW)
                .withCategory(Vehicle.Category.CAR)
                .withModel("320 d")
                .withRegistrationNumber("XS  2345")
                .end()
                .build();


    }

}
