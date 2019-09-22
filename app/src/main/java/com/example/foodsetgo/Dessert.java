package com.example.foodsetgo;

public class Dessert {

    String desid;
    String desname;
    String desdiscription;


    public Dessert(){

    }

    public Dessert(String desid, String desname, String desdiscription) {
        this.desid = desid;
        this.desname = desname;
        this.desdiscription = desdiscription;
    }

    public String getDesid() {
        return desid;
    }

    public String getDesname() {
        return desname;
    }

    public String getDesdiscription() {
        return desdiscription;
    }
}
