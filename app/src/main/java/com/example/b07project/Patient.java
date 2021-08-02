package com.example.b07project;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Patient extends Person{

    private ArrayList<Appointment> appointments;
    private HealthInformation healthInformation;

    public Patient(){}

    public Patient(String name){
        super(name);
    }

    public Patient(String name, String email, int patientID, String password) throws InputException {
        super(name, validatePatientEmail(email), validatePatientID(patientID), password);
        appointments = new ArrayList<Appointment>();
        storeInDB();
    }

    /*
    ** This method validates the format of the patient's email.
    ** Return the email if it is valid, throw an exception otherwise.
     */
    public static String validatePatientEmail(String email) throws InputException {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)\\.[a-z]+$");
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches() == false){
            throw new InputException("The email address is invalid!");
        }
        return email;
    }

    /*
    ** This method validates the format of the patient's patientID.
    ** Return the ID if it is valid, throw an exception otherwise.
    */
    public static int validatePatientID(int patientID) throws InputException {
        String s = Integer.toString(patientID);
        Pattern pattern = Pattern.compile("\\d{5}");
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches() == false){
            throw new InputException("The patient ID is invalid! ID must be a 5 digit number.");
        }
        return patientID;
    }

    public void storeInDB(){

        int loginID = this.loginID;


        FirebaseDatabase.getInstance().getReference().child("patients")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot patients:dataSnapshot.getChildren()){
                            Patient patient = patients.getValue(Patient.class);
                            if (patient.loginID == loginID){

                                throw new IllegalArgumentException("An account has already been created with the ID provided");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w("info","Failed to read value", error.toException());
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
//    public void getAppointments(Doctor doctor, Date start, Date end) {
//        //assume to create an Appointment with 3 fields: Patient, Doctor, Date
////        Appointment a = new Appointment(this, doctor, Date start, Date end);
////        if(appointments.contains(a) == false){
////            appointments.add(a);
////
////            //assume doctor will also get this appointment
////            doctor.addAppointment(a);
////
////        }
//    }

    public void addHealthInformation(HealthInformation healthInformation){
        Map<String, Object> patientUpdates = new HashMap<>();
        patientUpdates.put("healthInformation", healthInformation);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("patients").child(""+ this.loginID);

        ref.updateChildren(patientUpdates);
    }

    public void clearHealthInformation(){ this.healthInformation = null; }


    public void setHealthInformation(HealthInformation healthInformation){this.healthInformation = healthInformation;}
    public ArrayList<Appointment> getAppointments() { return appointments; }
    public HealthInformation getHealthInformation() { return healthInformation; }

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
