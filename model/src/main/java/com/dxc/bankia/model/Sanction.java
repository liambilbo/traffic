package com.dxc.bankia.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Sanction implements Serializable {

    private Long id;
    private Driver driver;
    private Vehicle vehicle;
    private Level level;
    private Date inceptionDate;
    private String description;
    private Double fineAmount;
    private Integer points;



    private SanctionStatus sanctionStatus;
    private PaymentStatus paymentStatus;
    private Discount discount;
    private Double payment;




    public enum Level {
        SOFT,MEDIUM,STRONG
    }
    public enum PaymentStatus {
        PENDING,CANCELED,PAYED
    }

    public enum SanctionStatus {
        PENDING,CANCELED,CONFIRMED
    }

    public Sanction() {
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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle car) {
        this.vehicle = car;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Date getInceptionDate() {
        return inceptionDate;
    }

    public void setInceptionDate(Date inceptionDate) {
        this.inceptionDate = inceptionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(Double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public SanctionStatus getSanctionStatus() {
        return sanctionStatus;
    }

    public void setSanctionStatus(SanctionStatus sanctionStatus) {
        this.sanctionStatus = sanctionStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sanction)) return false;
        Sanction sanction = (Sanction) o;
        return getId().equals(sanction.getId()) &&
                getDriver().equals(sanction.getDriver()) &&
                getVehicle().equals(sanction.getVehicle()) &&
                getInceptionDate().equals(sanction.getInceptionDate()) &&
                getFineAmount().equals(sanction.getFineAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDriver(), getVehicle(), getInceptionDate(), getFineAmount());
    }

    @Override
    public String toString() {
        return "Sanction [id = " + id + ", driver=" + driver + ", vehicle=" + vehicle + ", description=" + description  + "]";
    }





}
