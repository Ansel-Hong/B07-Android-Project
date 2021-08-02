package com.example.b07project;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Doctor extends Person{

    private ArrayList<Appointment> timeSlots;
    private int employeeID;


    public Doctor(){}

    public Doctor(String name){
        super(name);
    }

    public Doctor(String name, String email, int EmployeeID, String password, ArrayList<Appointment> slots){
        super(name, email, password);
        employeeID = validateEmployeeID(EmployeeID);
        this.timeSlots = slots;
        storeInDB();
    }

    public Doctor(String name, String email, int EmployeeID, String password){
        super(name, email, password);
        employeeID = validateEmployeeID(EmployeeID);
        this.timeSlots = new ArrayList<Appointment>();
        storeInDB();
    }

    public ArrayList<Appointment> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(ArrayList<Appointment> timeSlots) {
        this.timeSlots = timeSlots;
    }

    /*
    ** This method validate the format of the doctor's EmployeeID.
    **  Return the ID if it is valid, throw an exception otherwise.
     */
    private static int validateEmployeeID(int EmployeeID){
        String s = Integer.toString(EmployeeID);
        Pattern pattern = Pattern.compile("\\d{6}");
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches() == false){
            throw new IllegalArgumentException("The employee ID is invalid!");
        }
        return EmployeeID;
    }

    public void storeInDB(){
        int loginID = this.employeeID;
        FirebaseDatabase.getInstance().getReference().child("doctors")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot doctors : dataSnapshot.getChildren()) {
                            Doctor doctor = doctors.getValue(Doctor.class);
//                            if (doctor.loginID == loginID) {
//                                throw new IllegalArgumentException("An account has already been created with the ID provided");
//                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w("info", "Failed to read value.", error.toException());
                    }
                });

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("doctors");
        db.child("" + this.employeeID).setValue(this);
    }

    @Override
    public void setLoginInformation(String username, int loginID, String password) {
        super.setLoginInformation(username, loginID, password);
    }

    /* Not sure if this class needs this.
    *  If this class needs this method, then it should display the appointments
    *  that the doctor currently have.
    */
//    @Override
//    public void getAppointments(){
//        for (Appointment appointment:this.appointments){
//            // Not sure how to display the appointment on the screen.
//        }
//    }

    /* A proposed way to implement addAppointment for the class Doctor.
    *  It takes one parameter appointment of class Appointment and add it to the doctor's
    *  appointment list appointments if it's not in the list.
    *  The availability of the doctor will be updated accordingly.
    */
    public void addAppointment(Appointment appointment, String patientName) {
       for(Appointment t : timeSlots){
           if(t.getAppointmentID() == appointment.getAppointmentID())
               t.bookAppointment(patientName);
       }

    }

    //---------------- For Firebase --------------------//
    // setter function for a doctor
    public void setAppointments(ArrayList<Appointment> appointments){this.timeSlots=appointments;}

    // getter function for a doctor
    public ArrayList<Appointment> getAppointments(){return this.timeSlots;}

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = validateEmployeeID(employeeID);
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null){
            return false;
        }
        if (this.getClass() != obj.getClass()){
            return false;
        }
        Doctor doctor = (Doctor) obj;
        if (doctor.hashCode() == this.hashCode()){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "{Doctor name: Dr. " + name +
                ", ID: " + employeeID +"}";
    }
}
