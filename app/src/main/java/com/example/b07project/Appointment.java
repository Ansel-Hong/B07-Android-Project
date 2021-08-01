package com.example.b07project;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment {

    private int patientID;
    private int doctorID;
    private Date startTime;
    private Date endTime;
    private int appointmentID;

    public Appointment (int patientID, int doctorID, Date startTime, Date endTime) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.startTime = startTime;
        this.endTime = endTime;
        SimpleDateFormat st = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        SimpleDateFormat et = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        this.appointmentID = patientID + doctorID + (int)startTime.getTime() + (int)endTime.getTime();


    }

    public Appointment() { }

    @Override
    public String toString() {
        return "" + patientID + " has a appointment with Dr." + doctorID + " from " + startTime
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
    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getPatientID() { return patientID; }

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

//========================== Necessary ===============================
}


