package com.example.b07project;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment {

    private Patient patient;
    private Doctor doctor;
    private Date startTime;
    private Date endTime;
    private int appointmentID;

    public Appointment (Patient patient, Doctor doctor, Date startTime, Date endTime) {
        this.patient = patient;
        this.doctor = doctor;
        this.startTime = startTime;
        this.endTime = endTime;
        SimpleDateFormat st = new SimpleDateFormat("yyyy.MM.dd hh:mm");
        SimpleDateFormat et = new SimpleDateFormat("yyyy.MM.dd hh:mm");
        this.appointmentID = Integer.parseInt(patient.loginID + doctor.loginID +
                st.format(startTime) + et.format(endTime));
    }

    public Appointment() {
        this.patient = null;
        this.doctor = null;
        this.startTime = null;
        this.endTime = null;
    }

    @Override
    public String toString() {
        return patient.name + " has a appointment with Dr." + doctor.name + " from " + startTime
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
//========================== not sure if these are necessary ===============================
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return doctor;
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
//========================== not sure if these are necessary ===============================
}


