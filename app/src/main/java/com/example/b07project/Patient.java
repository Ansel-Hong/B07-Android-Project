package com.example.b07project;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Patient extends Person{

    private ArrayList<Appointment> appointments;
    private HealthInformation healthInformation;

    public Patient(){

    }


    public Patient(String name, String username, int patientID, String password) {
        //first maybe check if the ID is valid (ie: in a correct format)
//        String t = Integer.toString(PatientID);
//        Pattern pattern = Pattern.compile("\\d{10}");
//        Matcher matcher = pattern.matcher(t);
//        if (matcher.matches() == false) {
//            throw new IllegalArgumentException("ID does not match proper format");}
        //else{
        super(name, username, patientID, password);
        appointments = new ArrayList<Appointment>();
        storeInDB();
//      }

        }

        public void storeInDB(){
            int loginID = this.loginID;
            FirebaseDatabase.getInstance().getReference().child("patients")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot patients : dataSnapshot.getChildren()) {
                                Patient patient = patients.getValue(Patient.class);
//                                if (patient.loginID == loginID) {
//                                    throw new IllegalArgumentException("An account has already been created with the ID provided");
//                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.w("info", "Failed to read value.", error.toException());
                        }
                    });

            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("patients");
            db.child("" + this.loginID).setValue(this);
        }



//    @Override
//    //keep it so no error
//    public Set<Appointment> getAppointments() {
//
//    }


    //assume Date is a class including the time of the appointment
    //I think abstract method getAppointments() would maybe need some arguments
    //Or maybe we don't declare the abstract method getAppointments() in Person class
    //because I think only Patient would demand appointments and not doctor
//    public void getAppointments(Doctor doctor, Date date) {
//        //assume to create an Appointment with 3 fields: Patient, Doctor, Date
////        Appointment a = new Appointment(this, doctor, date);
////        if(appointments.contains(a) == false){
////            appointments.add(a);
////
////            //assume doctor will also get this appointment
////            doctor.addAppointment(a);
////
////        }
//    }

    public void addHealthInformation(HealthInformation healthInformation){ this.healthInformation = healthInformation; }

    public void clearHealthInformation(){ this.healthInformation = null; }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }
    public HealthInformation getHealthInformation() {
        return healthInformation;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null)
            return false;
        if(obj.getClass() != getClass())
            return false;
        Patient p = (Patient)obj;
        if(this.hashCode() != p.hashCode())
            return false;
        return true;
    }



    @Override
    public String toString() {
        return "{Patient name: " + name +
                ", ID: " + loginID +"}";
    }



}
