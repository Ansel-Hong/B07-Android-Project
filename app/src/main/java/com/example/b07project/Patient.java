package com.example.b07project;

import java.util.Set;
import java.util.HashSet;

public class Patient extends Person{

//    private Set<Appointment> appointments;

    public Patient(String name, String username, int PatientID, String password){
        //first maybe check if the ID is valid (ie: in a correct format)
        //String t = Integer.toString(PatientID);
        //Pattern pattern = Pattern.compile("\\d{10}");
        //Matcher matcher = pattern.matcher(t);
        //if(matcher.matches() == true){
        super(name, username, PatientID, password);
//        appointments = new HashSet<Appointment>();
        //}
    }

    @Override
    //keep it so no error
    public void getAppointments() {

    }


    //assume Date is a class including the time of the appointment
    //I think abstract method getAppointments() would maybe need some arguments
    //Or maybe we don't declare the abstract method getAppointments() in Person class
    //because I think only Patient would demand appointments and not doctor
//    public void getAppointments(Doctor doctor, Data date) {
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
}
