package com.dxc.bankia.services;

import com.dxc.bankia.model.Driver;
import com.dxc.bankia.model.Vehicle;

public interface FinderService {
    Vehicle getVehicleByRegistrationNumber(String registrationNumber);
    Driver getDriverByIdentificationNumber(String identificationNumber);
}
