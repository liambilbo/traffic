package com.dxc.bankia.util;


import com.dxc.bankia.model.Driver;
import com.dxc.bankia.model.Sanction;
import com.dxc.bankia.model.Vehicle;
import  com.dxc.bankia.model.Discount;

import java.util.Date;

public class SanctionBuilder {


    private final Sanction instance;

    private static Long sanctionIdGenerator = 0L;

    public SanctionBuilder(Vehicle vehicle) {
        this(vehicle,vehicle.getOwner());
    }
    public SanctionBuilder(Vehicle vehicle, Driver driver) {
        instance = new Sanction();
        instance.setId(sanctionIdGenerator++);
        instance.setVehicle(vehicle);
        instance.setDriver(driver);
        this.instance.setPaymentStatus(Sanction.PaymentStatus.PENDING);
        this.instance.setSanctionStatus(Sanction.SanctionStatus.PENDING);
        this.instance.setInceptionDate(new Date());
    }

    public SanctionBuilder withId(Long id){
        instance.setId(id);
        return this;
    }



    private Long id;
    private Driver driver;
    private Vehicle vehicle;
    private Sanction.Level level;
    private Date inceptionDate;
    private String description;
    private Double fineAmount;
    private Integer points;
    private Sanction.PaymentStatus paymentStatus;
    private Discount discount;
    private Double payment;

    public SanctionBuilder withInceptionDate(Date inceptionDate){
        instance.setInceptionDate(inceptionDate);
        return this;
    }
    public SanctionBuilder withDescription(String description){
        instance.setDescription(description);
        return this;
    }

    public SanctionBuilder withFineAmount(Double fineAmount){
        instance.setFineAmount(fineAmount);
        return this;
    }

    public SanctionBuilder withPoints(Integer points){
        instance.setPoints(points);
        return this;
    }



    public Sanction build(){
        return instance;
    }

    public SanctionBuilder end() {
        return this;
    }
}
