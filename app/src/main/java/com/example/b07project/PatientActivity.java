package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        Intent i = getIntent();
        Patient patient = (Patient)i.getSerializableExtra("patient");

        TextView welcome = (TextView) findViewById(R.id.patient_welcome);
//        String s = "Welcome" + patient.name;
//        welcome.setText(s);

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