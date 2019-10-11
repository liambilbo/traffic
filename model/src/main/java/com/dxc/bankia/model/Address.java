package com.dxc.bankia.model;

import java.io.Serializable;

public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    private Country country;
    private String town;
    private String street;
    private String portal;
    private String number;
    private String postalCode;

    public Address(Country country, String town, String street, String portal, String number, String postalCode) {
        this.country = country;
        this.town = town;
        this.street = street;
        this.portal = portal;
        this.number = number;
        this.postalCode = postalCode;
    }




}
