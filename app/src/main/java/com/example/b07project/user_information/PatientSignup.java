package com.example.b07project.user_information;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.b07project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class PatientSignup extends AppCompatActivity {

    private FirebaseAuth auth;
    EditText editTextName, editTextEmail, editTextPassword, editTextAge, editTextWeight, editTextBloodType;
    RadioGroup genderRadioGroup;
    RadioButton genderRadioChoice;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_signup);
        getSupportActionBar().setTitle("Create An Account");



        auth = FirebaseAuth.getInstance();


        Button update_info = findViewById(R.id.add_patient_info);
        update_info.setAllCaps(false);
        update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                editTextPassword = (EditText) findViewById(R.id.patient_password);

                editTextName = (EditText) findViewById(R.id.patient_name);

                editTextEmail = (EditText) findViewById(R.id.patient_email);

                editTextAge = (EditText) findViewById(R.id.age);

                editTextWeight = (EditText) findViewById(R.id.weight);

                genderRadioGroup = (RadioGroup)findViewById(R.id.gender_patient);

                registerUser();

            }
        });
    }

    public void navigateToPatientActivity(Patient patient){
        Intent navigateToPatientIntent = new Intent(this, PatientActivity.class);
        //navigateToPatientIntent.putExtra("patient", patient);

        startActivity(navigateToPatientIntent);
    }


    private void registerUser() {
        Context pageContext = this;


        String newPassword = editTextPassword.getText().toString().trim();

        String newName = editTextName.getText().toString().trim();

        String newEmail = editTextEmail.getText().toString().trim();

        String newAge = editTextAge.getText().toString();

        String newWeight = editTextWeight.getText().toString();

        String gender = ((RadioButton)findViewById(genderRadioGroup.getCheckedRadioButtonId()))
                .getText().toString();

        if (newName.isEmpty()) {
            editTextName.setError("Full name is required!");
            editTextName.requestFocus();
            return;
        }

        if (newAge.isEmpty()) {
            editTextAge.setError("Age is required!");
            editTextAge.requestFocus();
            return;
        }

        if (newEmail.isEmpty()) {
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (newPassword.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (newPassword.length() < 6) {
            editTextPassword.setError("Password should be at least 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }



        Log.i("USERCREATION", "Creating user with new email and password");
        auth.createUserWithEmailAndPassword(newEmail, newPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            HealthInformation newHealthInformation = new HealthInformation(Integer.parseInt(newAge), Integer.parseInt(newWeight), gender);
                            Patient newPatient = new Patient(newName, newEmail, newPassword, newHealthInformation);

                            FirebaseDatabase.getInstance().getReference().child("patients").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(newPatient).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
//                                        Toast toast = Toast.makeText(PatientInformation.this, "User successfully registered!", Toast.LENGTH_LONG);
//                                        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
//                                        toast.show();

                                        navigateToPatientActivity();

                                    } else{
                                        new AlertDialog.Builder(PatientSignup.this)
                                                .setTitle("User failed to register, please try again")
                                                .setMessage("Email may have already been used to create an account")

                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                }).show();
                                    }
                                }
                            });

                        } else {
                            new AlertDialog.Builder(PatientSignup.this)
                                    .setTitle("User failed to register, please try again")
                                    .setMessage("Email may have already been used to create an account")

                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }).show();
                        }
                    }
                });

    }

    public void navigateToPatientActivity(){
        Intent navigateToPatientIntent = new Intent(PatientSignup.this, PatientActivity.class);
        //navigateToPatientIntent.putExtra("patient", patient);

        startActivity(navigateToPatientIntent);
    }

}