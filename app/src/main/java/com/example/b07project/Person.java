package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public abstract class Person implements Login, Serializable {
    public String name;
    static int personCount;

    // I figure having the 3 fields is no harm when logging in
    public String email;
    private String password;


    public Person(){};

    public Person(String name){
        this.name = name;
    }

    public Person(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }


    public void setLoginInformation(String email, int loginID, String password){
        this.email = email;
        this.password = password;
    }

    //Function to pull appointments from DB. Abstract since it will be implemented differently for Doctors/Patients
    //public abstract Set<Appointment> getAppointments(); //return type might not be void eventually, depends on later implementation


    //--------------------- for Firebase -----------------------//
    public void setName(String name){this.name = name;}
    public void setEmail(String email){this.email = email;}
    public void setPassword(String password){this.name = password;}

    public String getName(){return name;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}


    @Override
    public int hashCode() {
        return 3;
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
