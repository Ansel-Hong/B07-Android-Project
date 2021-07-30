package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_information);



        Button update_info = findViewById(R.id.add_patient_info);
        update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editText = (EditText) findViewById(R.id.patient_loginID);
                int newLoginID = Integer.parseInt(editText.getText().toString());

                editText = (EditText) findViewById(R.id.patient_password);
                String newPassword = editText.getText().toString();

                editText = (EditText) findViewById(R.id.patient_name);
                String newName = editText.getText().toString();

                editText = (EditText) findViewById(R.id.patient_password);
                String newEmail = editText.getText().toString();

                editText = (EditText) findViewById(R.id.age);
                int newAge = Integer.parseInt(editText.getText().toString());

                editText = (EditText) findViewById(R.id.weight);
                int newWeight = Integer.parseInt(editText.getText().toString());

                editText = (EditText) findViewById(R.id.blood_type);
                String newBloodType = editText.getText().toString();


                Patient newPatient = new Patient(newName, newEmail, newLoginID, newPassword);
                HealthInformation newHealthInformation = new HealthInformation(newAge, newWeight, newBloodType);
                newPatient.addHealthInformation(newHealthInformation);


            }
        });
    }
}