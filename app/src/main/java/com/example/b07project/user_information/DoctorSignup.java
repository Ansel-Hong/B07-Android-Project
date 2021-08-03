package com.example.b07project.user_information;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.b07project.R;
import com.example.b07project.appointment_activities.Appointment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DoctorSignup extends AppCompatActivity {

    private FirebaseAuth auth;
    EditText editTextName, editTextEmail, editTextPassword, editTextSpecialty;
    RadioGroup genderRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        auth = FirebaseAuth.getInstance();
        Button update_info = findViewById(R.id.add_doctor_info);
        update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                editTextPassword = (EditText) findViewById(R.id.doctor_password);

                editTextName = (EditText) findViewById(R.id.doctor_name);

                editTextEmail = (EditText) findViewById(R.id.doctor_email);

                editTextSpecialty = (EditText) findViewById(R.id.doctor_specialty);

                genderRadioGroup = (RadioGroup)findViewById(R.id.gender_doctor);

                registerUser();

            }
        });



    }

    private void registerUser() {


        String newPassword = editTextPassword.getText().toString().trim();

        String newName = editTextName.getText().toString().trim();

        String newEmail = editTextEmail.getText().toString().trim();


        String newSpecialty = editTextSpecialty.getText().toString().trim();

        String gender = ((RadioButton)findViewById(genderRadioGroup.getCheckedRadioButtonId()))
                .getText().toString();



        if (newName.isEmpty()) {
            editTextName.setError("Full name is required!");
            editTextName.requestFocus();
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

        auth.createUserWithEmailAndPassword(newEmail, newPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Doctor newDoctor = new Doctor(newName, newEmail, newPassword, gender);

                            FirebaseDatabase.getInstance().getReference().child("doctors").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(newDoctor).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast toast = Toast.makeText(DoctorSignup.this, "User successfully registered!", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                                        toast.show();



                                    } else{
                                        Toast toast = Toast.makeText(DoctorSignup.this, "Failed to register patient! Try Again!", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                                        toast.show();
                                    }
                                }
                            });

                        } else {
                            Toast toast = Toast.makeText(DoctorSignup.this, "Failed to register patient! Try Again!", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                            toast.show();
                        }
                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}