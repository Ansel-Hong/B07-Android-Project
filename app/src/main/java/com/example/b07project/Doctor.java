package com.example.b07project;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Doctor extends Person{

    private Set<Appointment> appointments;
    // This field stores the availability of the doctor.
    private Set<Date> availability;

    public Doctor(){}


    public Doctor(String name){
        super(name);
    }

    public Doctor(String name, String username, int EmployeeID, String password){
        super(name, username, EmployeeID, password);
//        this.appointments = new HashSet<Appointment>();
        this.availability = new HashSet<Date>();

        storeInDB();
    }

    public void storeInDB(){
        int loginID = this.loginID;
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
        db.child("" + this.loginID).setValue(this);
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
    public void addAppointment(Appointment appointment) {
        // Add the appointment to the doctor's appointment list if it's not in the list.
        if (this.appointments.contains(appointment) == false){
            appointments.add(appointment);
            // Update the doctor's availability accordingly.
            // Assuming the class Appointment has the method getDate
            // that returns the date (and the specific time) of the appointment;
            Date date = appointment.getDate();
            this.availability.remove(date);
        }
        else {
            // Throws an exception if the doctor is not available at the given time slot.
            throw new IllegalArgumentException("This doctor is not available at this time slot, please book another time slot or check another doctor's availability.");
        }

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
                ", ID: " + loginID +"}";
    }
}
