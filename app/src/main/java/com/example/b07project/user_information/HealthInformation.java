package com.example.b07project.user_information;

import java.io.Serializable;

public class HealthInformation implements Serializable {
    /**
     * Class that stores basic Patient information that can later by viewed by doctors.
     * @param age an int storing age of the patient
     * @param weight an int storing the weight of the patient in pounds
     * @param gender a string storing the gender of the patient - either "Male" or "Female"
     */
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
