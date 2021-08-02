package com.example.b07project;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment {

    private String patientName;
    private int doctorID;
    private Date startTime;
    private Date endTime;
    private int appointmentID;
    private boolean booked;

    public Appointment (int doctorID, Date startTime, Date endTime) {
        this.patientName = null;
        this.doctorID = doctorID;
        this.startTime = startTime;
        this.endTime = endTime;
        SimpleDateFormat st = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        SimpleDateFormat et = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        this.appointmentID = doctorID + (int)startTime.getTime() + (int)endTime.getTime();
        this.booked = false;
    }

    public Appointment() { }

    public void bookAppointment(String name){
        this.patientName = name;
        this.booked = true;
    }

    public void cancelAppointment(){
        this.patientName = null; //optional
        this.booked = false;
    }

    @Override
    public String toString() {
        return "" + patientName + " has a appointment with Dr." + doctorID + " from " + startTime
                + " to " + endTime;
    }

    @Override
    public int hashCode() { return appointmentID; }

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
//========================== Necessary ===============================
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setDoctor(int doctorID) {
        this.doctorID = doctorID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public boolean isBooked() {
        return booked;
    }

    //========================== Necessary ===============================
}


