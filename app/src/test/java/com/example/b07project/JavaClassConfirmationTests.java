package com.example.b07project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.b07project.appointment_activities.Appointment;
import com.example.b07project.user_information.Doctor;
import com.example.b07project.user_information.DoctorActivity;
import com.example.b07project.user_information.HealthInformation;
import com.example.b07project.user_information.Patient;
import com.example.b07project.user_information.Person;
import com.example.b07project.user_information.PatientActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class JavaClassConfirmationTests {

    @Test
    public void testPatientEmptyConstructor(){
        Patient pat = new Patient();
        assertEquals(pat.name, null);
    }

    @Test
    public void testHealthInformationEmptyConstructor(){
        HealthInformation healthInformation = new HealthInformation();
        assertEquals(healthInformation.gender, null);
    }

    @Test
    public void testPatientGetInfo(){
//        when(patView.navigateToPatientSignup()).getMock();
        Patient pat = new Patient("Patient1");
        HealthInformation healthInformation = new HealthInformation(10,100,"Female");
        pat.setHealthInformation(healthInformation);

//        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        HealthInformation retreiveInfo = pat.getHealthInformation();
//        verify(pat).
        assertEquals(retreiveInfo, healthInformation);
    }

    @Test
    public void testPatientToString(){
        HealthInformation healthInformation = new HealthInformation(10,100,"Female");
        Patient pat = new Patient("Patient1", "pat1@gmail.com", "password1", healthInformation);

        assertEquals(pat.toString(), "{Patient name: Patient1}");
    }

    @Test
    public void testPersonSetGetName(){
        HealthInformation healthInformation = new HealthInformation(10,100,"Female");
        Patient pat = new Patient("P1", "pat1@gmail.com", "password1", healthInformation);
        pat.setName("Kevin");
        assertEquals(pat.getName(), "Kevin");
    }

    @Test
    public void testPersonSetGetEmail(){
        HealthInformation healthInformation = new HealthInformation(10,100,"Female");
        Patient pat = new Patient("P1", "pat1@gmail.com", "password1", healthInformation);
        pat.setEmail("123@docs.com");
        assertEquals(pat.getEmail(), "123@docs.com");
    }

    @Test
    public void testPersonSetGetPassword(){
        HealthInformation healthInformation = new HealthInformation(10,100,"Female");
        Patient pat = new Patient("P1", "pat1@gmail.com", "password1", healthInformation);
        pat.setPassword("dragonSlayer99");
        assertEquals(pat.getPassword(), "dragonSlayer99");
    }

    @Test
    public void testDoctorEmptyConstructor(){
        Doctor doctor = new Doctor();
        assertEquals(doctor.getSpecialty(), null);
    }

    @Test
    public void testDoctorNameConstructor(){
        Doctor doctor = new Doctor("Karen");
        assertEquals(doctor.getName(), "Karen");
    }

    @Test
    public void testDoctorNameEmailPasswordConstructor(){
        Doctor doctor = new Doctor("Karen", "doc1@doctor.com", "password");
        assertEquals(doctor.getEmail(), "doc1@doctor.com");
    }

    @Test
    public void testDoctorToString(){
        Doctor doc = new Doctor("D1", "doc1@doctor.com", "noPassword", "male", "computer science");

        assertEquals(doc.toString(), "{Doctor name: Dr. D1}");
    }

    @Test
    public void testDoctorSetGetGender(){
        Doctor doc = new Doctor("D1", "doc1@doctor.com", "noPassword", "male", "computer science");
        doc.setGender("female");
        assertEquals(doc.getGender(), "female");
    }

    @Test
    public void testDoctorSetGetSpecialty(){
        Doctor doc = new Doctor("D1", "doc1@doctor.com", "noPassword", "male", "computer science");
        doc.setSpecialty("humanities");
        assertEquals(doc.getSpecialty(), "humanities");
    }

    @Test
    public void testAppointmentEmptyConstructor(){
        Appointment apt = new Appointment();
        assertEquals(apt.getAppointmentID(), null);
    }

    @Test
    public void testAppointmentID(){
        Date start = new Date(120,9, 1, 12, 0);
        Appointment apt = new Appointment("Doctor1ID", "Patient1ID", start);
        assertEquals(apt.getAppointmentID(), "202010011200Doctor1ID");
    }

    @Test
    public void testAppointmentToString(){
        Date start = new Date(120,9, 1, 12, 0);
        Appointment apt = new Appointment("Doctor1ID", "Patient1ID", start);
        assertEquals(apt.toString(), "Patient1ID has a appointment with Dr.Doctor1ID at Thu Oct 01 12:00:00 EDT 2020");
    }

    @Test
    public void testAppointmentSetGetPatientID(){
        Date start = new Date(120,9, 1, 12, 0);
        Appointment apt = new Appointment("Doctor1ID", "Patient1ID", start);
        apt.setPatientName("Pat2ID");
        assertEquals(apt.getPatientID(), "Pat2ID");
    }

    @Test
    public void testAppointmentSetGetDoctorID(){
        Date start = new Date(120,9, 1, 12, 0);
        Appointment apt = new Appointment("Doctor1ID", "Patient1ID", start);
        apt.setDoctor("DoctorKID");
        assertEquals(apt.getDoctorID(), "DoctorKID");
    }

    @Test
    public void testAppointmentSetGetAppointmentID(){
        Date start = new Date(120,9, 1, 12, 0);
        Appointment apt = new Appointment("Doctor1ID", "Patient1ID", start);
        apt.setAppointmentID("NewAppointmentID");
        assertEquals(apt.getAppointmentID(), "NewAppointmentID");
    }

    @Test
    public void testAppointmentSetGetDate(){
        Date start = new Date(120,9, 1, 12, 0);
        Appointment apt = new Appointment("Doctor1ID", "Patient1ID", start);
        Date newStart = new Date(120,10, 1, 12, 0);
        apt.setStartTime(newStart);
        assertEquals(apt.getStartTime(), newStart);
    }

}
