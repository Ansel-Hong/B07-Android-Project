package com.example.b07project;

import androidx.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;

public abstract class Person implements Login{
    public String name;;
    static Set<Person> persons = new HashSet<Person>(); //static set to store Persons until we get DB, just for testing

    // I figure having the 3 fields is no harm when logging in
    public String username;
    public int loginID; //OHIB for Patients and Employee ID for Doctors, unique for each person
    private String password;

    public Person(String name){
        this.name = name;
    }

    public Person(String name, String username, int loginID, String password){
        this.name = name;

        for (Person p: persons){ //eventually this will check DB
            if(loginID == p.loginID)
                throw new IllegalArgumentException("An account has already been created with the ID provided");
        }
        this.username = username;
        this.loginID = loginID;
        this.password = password;
    }


    public void setLoginInformation(String username, int loginID, String password){
        this.username = username;
        this.loginID = loginID;
        this.password = password;
    }

    //Function to pull appointments from DB. Abstract since it will be implemented differently for Doctors/Patients
    public abstract void getAppointments(); //return type might not be void eventually, depends on later implementation


    @Override
    public int hashCode() {
        return loginID;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null)
            return false;
        if(obj.getClass() != getClass())
            return false;
        Person p = (Person)obj;
        if(this.hashCode() != p.hashCode())
            return false;
        return true;
    }
}
