package com.dxc.bankia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;



    private String identificationNumber;
    private String firstName;
    private String lastName;

    private Date dateOfBirth;
    private Country nationality;
    private String licenseNumber;
    private List<VehicleLicense> licenses = new ArrayList<>();
    private Address address;
    private List<Vehicle> vehicles = new ArrayList<>();

    public Driver() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public List<VehicleLicense> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<VehicleLicense> licenses) {
        this.licenses = licenses;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Driver)) return false;
        Driver driver = (Driver) o;
        return getId().equals(driver.getId()) &&
                getIdentificationNumber().equals(driver.getIdentificationNumber()) &&
                getFirstName().equals(driver.getFirstName()) &&
                getLastName().equals(driver.getLastName()) &&
                getDateOfBirth().equals(driver.getDateOfBirth()) &&
                getNationality() == driver.getNationality() &&
                Objects.equals(getLicenseNumber(), driver.getLicenseNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getIdentificationNumber(), getFirstName(), getLastName(), getDateOfBirth(), getNationality(), getLicenseNumber());
    }

    @Override
    public String toString() {
        return "Driver [identificationNumber = " + identificationNumber + ", firstName=" + firstName + ", lastName=" + lastName + ", licenseNumber=" + licenseNumber  + "]";
    }



}
