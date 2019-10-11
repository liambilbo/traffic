package com.dxc.bankia.model;

import java.io.Serializable;

public class Discount implements Serializable {

    private static final long serialVersionUID = 1L;

    public Discount(Double percentage) {
        this.percentage = percentage;
    }
    private Double percentage;

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }


}
