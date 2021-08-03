package com.example.b07project.user_information;

import com.example.b07project.appointment_activities.Appointment;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Doctor extends Person{

    public ArrayList<Appointment> timeSlots;
    private int employeeID;
    String specialty;



    public Doctor(){}

    public Doctor(String name){
        super(name);
    }

    public Doctor(String name, String email, String password, ArrayList<Appointment> slots){
        super(name, email, password);
        //employeeID = validateEmployeeID(EmployeeID);
        this.timeSlots = slots;
    }

    public Doctor(String name, String email, String password){
        super(name, email, password);
        //employeeID = validateEmployeeID(EmployeeID);
        this.timeSlots = new ArrayList<Appointment>();
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
//    public void addAppointment(Appointment appointment, String patientName) {
//       for(Appointment t : timeSlots){
//           if(t.getAppointmentID() == appointment.getAppointmentID())
//               t.bookAppointment(patientName);
//       }
//
//    }

    //---------------- For Firebase --------------------//
    // setter function for a doctor
    public void setAppointments(ArrayList<Appointment> appointments){this.timeSlots=appointments;}

    // getter function for a doctor
    public ArrayList<Appointment> getAppointments(){return this.timeSlots;}

//    public int getEmployeeID() {
//        return employeeID;
//    }
//
//    public void setEmployeeID(int employeeID) {
//        this.employeeID = validateEmployeeID(employeeID);
//    }

    public String getSpecialty(){return specialty;}
    public void setSpecialty(String specialty){this.specialty = specialty;}

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
