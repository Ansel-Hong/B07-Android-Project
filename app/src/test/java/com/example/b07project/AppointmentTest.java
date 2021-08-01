package com.example.b07project;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;

public class AppointmentTest {


    @Test
    public void CreateAppointment(){
        Date start = new Date(2020,8, 20, 13, 30);
        Date end = new Date(2020,8, 20, 14, 30);


        Appointment a = new Appointment(12345, 4040, start, end);

        assertEquals(a.getPatientID(),12345);
    }

}
