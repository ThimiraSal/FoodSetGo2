package com.example.foodsetgo;

public class NonVeg {
    String nonvegId;
    String nonvegName;
    String nonvegDescription;

    public NonVeg(){

    }

    public NonVeg(String nonvegId, String nonvegName, String nonvegDescription) {
        this.nonvegId = nonvegId;
        this.nonvegName = nonvegName;
        this.nonvegDescription = nonvegDescription;
    }

    public String getNonvegId() {
        return nonvegId;
    }

    public String getNonvegName() {
        return nonvegName;
    }

    public String getNonvegDescription() {
        return nonvegDescription;
    }
}
