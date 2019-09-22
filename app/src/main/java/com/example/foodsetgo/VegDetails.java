package com.example.foodsetgo;

public class VegDetails {
    private String vegdetailsId;
    private String vegdetailsPrice;
    private String vegdetailsQuantity;
    private int vegdetailsRating;

    public VegDetails(){

    }

    public VegDetails(String vegdetailsId, String vegdetailsPrice, String vegdetailsQuantity, int vegdetailsRating) {
        this.vegdetailsId = vegdetailsId;
        this.vegdetailsPrice = vegdetailsPrice;
        this.vegdetailsQuantity = vegdetailsQuantity;
        this.vegdetailsRating = vegdetailsRating;
    }

    public String getVegdetailsId() {
        return vegdetailsId;
    }

    public String getVegdetailsPrice() {
        return vegdetailsPrice;
    }

    public String getVegdetailsQuantity() {
        return vegdetailsQuantity;
    }

    public int getVegdetailsRating() {
        return vegdetailsRating;
    }
}
