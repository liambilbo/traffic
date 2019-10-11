package com.dxc.bankia.model;

import com.dxc.bankia.model.functions.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String registrationNumber;
    private Date registrationDate;


    private Country country;
    private Category category;
    private Color color;
    private Brand brand;
    private String model;
    private Driver owner;

    private Date lastItvDate;            ;
    private Date nextItvDate;

    public Vehicle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Driver getOwner() {
        return owner;
    }

    public void setOwner(Driver owner) {
        this.owner = owner;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }


    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public enum Category {
        BIKE,CAR,TRUCK
    }
    public enum Brand {
        YAMAHA,BMW,NISSAN,JAGUAR
    }
    public enum Color {
        RED,BLUE,YELLOW
    }

    public Date getLastItvDate() {
        return lastItvDate;
    }

    public void setLastItvDate(Date lastItvDate) {
        this.lastItvDate = lastItvDate;
    }

    public Date getNextItvDate() {
        return nextItvDate;
    }

    public void setNextItvDate(Date nextItvDate) {
        this.nextItvDate = nextItvDate;
    }

    public int getAge(){
        return DateUtils.getAge(getRegistrationDate());
    }

    public Date getBaseDateToCalculateNextItvDate(){
        return getLastItvDate()!=null?this.getLastItvDate():this.getRegistrationDate();
    }

}
