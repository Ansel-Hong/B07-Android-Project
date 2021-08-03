package com.example.b07project.appointment_activities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment {

    private String patientID;
    private String doctorID;
    private Date startTime;
    private String appointmentID;

    public Appointment (String doctorID, String patientID, Date startTime) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.startTime = startTime;
        SimpleDateFormat st = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        SimpleDateFormat et = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        SimpleDateFormat test = new SimpleDateFormat("yyyyMMddhhmm");
        //this.appointmentID = (int)startTime.getTime();
        this.appointmentID = test.format(startTime)+doctorID;
    }

    public Appointment() { }

//    public void bookAppointment(String patientID){
//        this.patientID = patientID;
//        this.booked = true;
//    }

//    public void cancelAppointment(){
//        this.patientID = null; //optional
//        this.booked = false;
//    }

    @Override
    public String toString() {
        return "" + patientID + " has a appointment with Dr." + doctorID + " at " + startTime;
    }

//    @Override
//    public int hashCode() { return appointmentID; }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj.getClass() != getClass())
            return false;
        Appointment appointment = (Appointment) obj;
        if(this.hashCode() != appointment.hashCode())
            return false;
        return true;
    }
//    public boolean isBooked() {
//        return booked;
//    }

//========================== Necessary ===============================
    public String getPatientID() {
        return patientID;
    }

    public void setPatientName(String patientID) {
        this.patientID = patientID;
    }

    public void setDoctor(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setAppointmentID(String appointmentID){this.appointmentID = appointmentID;}

    public String getAppointmentID() {
        return appointmentID;
    }

//    public void setBooked(boolean booked){this.booked = booked;}
//    public boolean getBooked(){return this.booked};



    //========================== Necessary ===============================
}


