package com.example.b07project;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Doctor extends Person{

    private Set<Appointment> appointments;
    // This field stores the availability of the doctor.
    private Set<Date> availability;

    public Doctor(String name){
        super(name);
    }

    public Doctor(String name, String username, int EmployeeID, String password){
        super(name, username, EmployeeID, password);
        this.appointments = new HashSet<Appointment>();
        this.availability = new HashSet<Date>();
    }

    @Override
    public void setLoginInformation(String username, int loginID, String password) {
        super.setLoginInformation(username, loginID, password);
    }

    /* Not sure if this class needs this.
    *  If this class needs this method, then it should display the appointments
    *  that the doctor currently have.
    */
    @Override
    public void getAppointments(){
        for (Appointment appointment:this.appointments){
            // Not sure how to display the appointment on the screen.
        }
    }

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
}
