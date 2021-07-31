package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
    }

    public void navigateToBookAppointmentActivity(View view){
        Intent navigateToPatientIntent = new Intent(this, BookAppointmentActivity.class);
        startActivity(navigateToPatientIntent);
    }

    public void navigateToViewAppointmentsActivity(View view){
        Intent navigateToPatientIntent = new Intent(this, ViewAppointmentActivity.class);
        startActivity(navigateToPatientIntent);
    }


}