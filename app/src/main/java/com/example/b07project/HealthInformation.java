package com.example.b07project;

import java.io.Serializable;

public class HealthInformation implements Serializable {
    public int age;
    public int weight;
    public String bloodtype;

    public HealthInformation(){}

    public HealthInformation(int age, int weight, String bloodtype){
        this.age = age;
        this.weight = weight;
        this.bloodtype = bloodtype;

    }



}
