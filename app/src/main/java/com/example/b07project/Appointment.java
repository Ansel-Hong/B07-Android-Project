package com.example.b07project;

import java.util.Date;

public class Appointment {

    private Patient patient;
    private Doctor doctor;
    private Date date;

    public Appointment (Patient patient, Doctor doctor, Date date) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
    }

    public Appointment() {
        this.patient = null;
        this.doctor = null;
        this.date = null;
    }

    @Override
    public String toString() {
        return patient.name + " has a appointment with Dr." + doctor.name + " on " + date;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
//========================== not sure if these are necessary ===============================
}


