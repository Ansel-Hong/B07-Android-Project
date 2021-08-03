package com.example.b07project.user_information;

import com.example.b07project.appointment_activities.Appointment;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Doctor extends Person{

    private int employeeID;
    String specialty;
    String gender;



    public Doctor(){}

    public Doctor(String name){
        super(name);
    }

    public Doctor(String name, String email, String password){
        super(name, email, password);
        //employeeID = validateEmployeeID(EmployeeID);
    }

    public Doctor(String name, String email, String password, String gender, String specialty){
        super(name, email, password);
        this.gender = gender;
        this.specialty = specialty;
        //employeeID = validateEmployeeID(EmployeeID);
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


    //---------------- For Firebase --------------------//


    public String getGender(){return gender;}
    public void setGender(String gender){this.gender=gender;}
    public String getSpecialty(){return specialty;}
    public void setSpecialty(String specialty){this.specialty = specialty;}

    //---------------- For Firebase --------------------//


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
