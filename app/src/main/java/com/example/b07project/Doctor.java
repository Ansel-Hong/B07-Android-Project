package com.example.b07project;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.temporal.Temporal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Doctor extends Person{

    private Set<Appointment> appointments;
    // This field stores the availability of the doctor.
    private Set<Availability> availability;

    public Doctor(){}

    public Doctor(String name){
        super(name);
    }

    public Doctor(String name, String username, int EmployeeID, String password){
        super(name, username, validateEmployeeID(EmployeeID), password);
//      this.appointments = new HashSet<Appointment>();
        this.availability = new HashSet<Availability>();
        storeInDB();
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
            // Assuming that the appointment must be booked because of an available spot from the doctor
            //ie: There must be an element in availability that matches the start and end Date of this new appointment
            Availability t = new Availability(appointment.getStartTime(), appointment.getEndTime());
            for(Availability each: availability){
                if (t.equals(each) == true)
                    each.booking();
            }
        }
        else {
            // Throws an exception if the doctor is not available at the given time slot.
            throw new IllegalArgumentException("This doctor is not available at this time slot, please book another time slot or check another doctor's availability.");
        }

    }

    //---------------- For Firebase --------------------//
    // setter function for a doctor
    public void setAppointments(Set<Appointment> appointments){this.appointments=appointments;}
    public void setAvailability(Set<Availability> availability){this.availability=availability;}

    // getter function for a doctor
    public Set<Appointment> getAppointments(){return this.appointments;}
    public Set<Availability> getAvailability(){return this.availability;}





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
