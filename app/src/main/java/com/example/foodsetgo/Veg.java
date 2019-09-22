package com.example.foodsetgo;

public class Veg {
    String vegId;
    String vegName;
    String vegDescription;

    public Veg(){

    }

    public Veg(String vegId, String vegName, String vegDescription) {
        this.vegId = vegId;
        this.vegName = vegName;
        this.vegDescription = vegDescription;
    }

    /** git comment  **/

    public String getVegId() {
        return vegId;
    }

    public String getVegName() {
        return vegName;
    }

    public String getVegDescription() {
        return vegDescription;
    }
}
