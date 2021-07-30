package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        Button switchToPatientPage = findViewById(R.id.login_button);
        switchToPatientPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToPatientActivity();
            }
        });


        Button signupPatient = findViewById(R.id.signup_patient);
        signupPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToPatientSignup();
            }
        });
    }

    public void goToPatientUserPage(View view) {
        Intent intent = new Intent(this, PatientActivity.class);
        startActivity(intent);
    }

    public void navigateToPatientActivity(){
        Intent navigateToPatientIntent = new Intent(this, PatientActivity.class);
        startActivity(navigateToPatientIntent);
    }

    public void navigateToPatientSignup(){
        Intent navigateToPatientSignup = new Intent(this, PatientInformation.class);
        startActivity(navigateToPatientSignup);
    }

}