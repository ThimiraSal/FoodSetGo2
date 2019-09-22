package com.example.foodsetgo;

public class DessertDetails {

    private String desDetailId;
    private String desPrice;
    private String desQuantity;
    private int desDetailRating;


    public DessertDetails(){

    }

    public DessertDetails(String desDetailId, String desPrice, String desQuantity, int desDetailRating) {

        this.desDetailId = desDetailId;
        this.desPrice = desPrice;
        this.desQuantity = desQuantity;
        this.desDetailRating = desDetailRating;
    }

    public String getDesDetailId() {
        return desDetailId;
    }

    public String getDesPrice() {
        return desPrice;
    }

    public String getDesQuantity() {
        return desQuantity;
    }

    public int getDesDetailRating() {
        return desDetailRating;
    }
}
