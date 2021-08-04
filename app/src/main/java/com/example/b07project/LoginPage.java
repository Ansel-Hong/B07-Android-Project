package com.example.b07project;

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
import android.widget.TextView;

import com.example.b07project.user_information.Doctor;
import com.example.b07project.user_information.DoctorActivity;
import com.example.b07project.user_information.DoctorSignup;
import com.example.b07project.user_information.Patient;
import com.example.b07project.user_information.PatientActivity;
import com.example.b07project.user_information.PatientInformation;
import com.example.b07project.user_information.confirm_callback.ConfirmCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button logIn;
    private FirebaseAuth auth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Context pageContext = this;




        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPassword = (EditText) findViewById(R.id.login_password);

        progressBar = (ProgressBar) findViewById(R.id.login_progressBar);
        auth =FirebaseAuth.getInstance();


        logIn = (Button) findViewById(R.id.login_button);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userLogin();


            }
        });


        Button signupPatient = findViewById(R.id.signup_patient);
        signupPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToPatientSignup();
            }
        });

        Button signupDoctor = findViewById(R.id.signup_doctor);
        signupDoctor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, DoctorSignup.class));
            }
        });

    }


    public void navigateToPatientActivity(){
        Intent navigateToPatientIntent = new Intent(LoginPage.this, PatientActivity.class);
        //navigateToPatientIntent.putExtra("patient", patient);

        startActivity(navigateToPatientIntent);
    }

    public void navigateToPatientSignup(){
        Intent navigateToPatientSignup = new Intent(this, PatientInformation.class);
        startActivity(navigateToPatientSignup);
    }

    private void userLogin() {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if (email.isEmpty()) {
            loginEmail.setError("Email is required!");
            loginEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEmail.setError("Please enter a valid email!");
            loginEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            loginPassword.setError("Password is required!");
            loginPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            loginPassword.setError("The minimum password length is 6 characters");
            loginPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        Context pageContext = this;

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Get current UID
                    String currentLoggedInUID = auth.getUid();
                    Log.i("UID OBTAINED", currentLoggedInUID);

                    final Boolean[] checkIfDoc = {false};

//                    final ConfirmCallback cb = null;

                    //Match UID to see if user is a doctor... run through doctor UID list
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("doctors");
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot child: snapshot.getChildren()){
                                String curDocUID = child.getKey();
                                if(curDocUID.equals(currentLoggedInUID)){
                                    Log.i("UID MATCHED", curDocUID);
                                    checkIfDoc[0] = true;
//                                    confirmUserPageNavigationCallback(true);
                                }
                            }
                            checkIfDoc[0] = false;
//                            if(checkIfDoc[0] != null && checkIfDoc[0] == false)
//                            confirmUserPageNavigationCallback(false);
//                            cb.confirmCallback(checkIfDoc[0]);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            new AlertDialog.Builder(LoginPage.this)
                                    .setTitle("Something went wrong, please restart the application")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Continue with delete operation
                                        }
                                    });
                        }
                    });

                    //test code
                    checkIfDoc[0] = true;
                    if (checkIfDoc[0]) {
                        startActivity(new Intent(LoginPage.this, DoctorActivity.class));
                    } else {
                        //Else not a doctor and login via patientlogin
                        startActivity(new Intent(LoginPage.this, PatientActivity.class));
                    }


                //If authentication fails
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    new AlertDialog.Builder(pageContext)
                            .setTitle("Login information was incorrect, please try again.")
                            //.setMessage("Are you sure you want to delete this entry?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                }
                            });

                            // A null listener allows the button to dismiss the dialog and take no further action.
//                            .setNegativeButton(android.R.string.no, null)
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show();
                }

            }
        });

    }

    public void confirmUserPageNavigationCallback(boolean isDoc) {
        if (isDoc) {
            startActivity(new Intent(LoginPage.this, DoctorActivity.class));
        } else {
            //Else not a doctor and login via patientlogin
            startActivity(new Intent(LoginPage.this, PatientActivity.class));
        }
    }
}

