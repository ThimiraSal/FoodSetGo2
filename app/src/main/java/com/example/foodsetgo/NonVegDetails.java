package com.example.foodsetgo;

public class NonVegDetails {
    private String nonvegdetailsId;
    private String nonvegdetailsPrice;
    private String nonvegdetailsQuantity;
    private int nonvegdetailsrating;

    public NonVegDetails(){

    }

    public NonVegDetails(String nonvegdetailsId, String nonvegdetailsPrice, String nonvegdetailsQuantity, int nonvegdetailsrating) {
        this.nonvegdetailsId = nonvegdetailsId;
        this.nonvegdetailsPrice = nonvegdetailsPrice;
        this.nonvegdetailsQuantity = nonvegdetailsQuantity;
        this.nonvegdetailsrating = nonvegdetailsrating;
    }

    public String getNonvegdetailsId() {
        return nonvegdetailsId;
    }

    public String getNonvegdetailsPrice() {
        return nonvegdetailsPrice;
    }

    public String getNonvegdetailsQuantity() {
        return nonvegdetailsQuantity;
    }

    public int getNonvegdetailsrating() {
        return nonvegdetailsrating;
    }
}
