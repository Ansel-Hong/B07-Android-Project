package com.example.b07project.user_information;

import java.io.Serializable;

public class HealthInformation implements Serializable {
    public int age;
    public int weight;
    public String gender;

    public HealthInformation(){}

    public HealthInformation(int age, int weight, String gender){
        this.age = age;
        this.weight = weight;
        this.gender = gender;

    }



}
