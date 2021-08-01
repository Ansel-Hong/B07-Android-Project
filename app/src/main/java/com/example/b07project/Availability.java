package com.example.b07project;

import java.util.Date;

public class Availability {

    private Date start;
    private Date end;
    private boolean available;

    public Availability() {
    }

    public Availability(Date start, Date end) {
        this.start = start;
        this.end = end;
        available = true;
    }

    public void booking(){
        available = false;
    }

    public void cancel(){
        available = true;
    }

    public boolean isAvailable() {
        return available;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public int hashCode() {
        return start.hashCode() * 31 + end.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null)
            return false;
        if(obj.getClass() != getClass())
            return false;
        Availability p = (Availability) obj;
        if(this.hashCode() != p.hashCode())
            return false;
        return true;
    }

    public Appointment bookTime(int patientID, int doctorID){
        Appointment appt = new Appointment(patientID, doctorID, start, end);
        available = false;
        return appt;
    }




    @Override
    public String toString() {
        if(this.available = true){
            return "Doctor is available from " + start.toString() + " to" + end.toString();
        }
        else{
            return "Doctor is unavailable from " + start.toString() + " to" + end.toString();
        }
    }
}
