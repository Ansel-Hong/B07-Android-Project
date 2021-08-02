package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

public class LoginPage extends AppCompatActivity {

    public boolean verify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Context pageContext = this;

        Button switchToPatientPage = findViewById(R.id.login_button);
        switchToPatientPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) findViewById(R.id.login_ID);
                int loginID = Integer.parseInt(editText.getText().toString());

                editText = (EditText) findViewById(R.id.login_password);
                String password = editText.getText().toString();


                final Patient[] patient = new Patient[1];


                int length = String.valueOf(loginID).length();
                if (length == 5){
                    DatabaseReference patientsDb = FirebaseDatabase.getInstance().getReference().child("patients");

                    Log.i("Check", "len correct");

                    if (patientsDb.child(""+ loginID) != null){ //check if user exists

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("patients");
                        ref.child(""+loginID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Patient p = dataSnapshot.getValue(Patient.class);
                                verifyPassword(p.getPassword(), password);

                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                Log.w("info", "Failed to read value.", error.toException());
                            }
                        });

                        //Log.i("ONDATACHANGE", "loginid matched with " + patientsDb.child(""+ loginID));

                        //Login ID verified
                        //------------Verify password here --------

//                        Log.i("ONDATACHANGE", "pass is " + patientsDb.child(""+ loginID).child("password"));
//                        if (patientsDb.child(""+ loginID).child("password").toString().equals(password)){
//                            verifyPassword = true;
//                        }

//                        addListenerForSingleValueEvent
//                        patientsDb.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                Log.i("ONDATACHANGE", "abc");
//                                //System.out.println(password);
//                                String retrievePass = dataSnapshot.child(""+loginID).child("password").getValue(String.class);
//                                Log.i("ONDATACHANGE", retrievePass);
//                                //System.out.println(retrievePass);
//                                if(retrievePass.equals(password)){
//                                    Log.i("PASS", retrievePass);
//                                }
//
//
//                                //Patient patient = dataSnapshot.child
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                                // ...
//                            }
//                        });


                        //------------------------------------------
                        if (verify){
                            navigateToPatientActivity(patient[0]);
                        } else{ //if password incorrect
                            new AlertDialog.Builder(pageContext)
                                    .setTitle("Incorrect Password")
                                    .setMessage("Password provided does not match the login ID")

                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setNegativeButton(android.R.string.no, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    } else {
                        new AlertDialog.Builder(pageContext)
                                .setTitle("Invalid Login ID")
                                .setMessage("No account has been created with this login ID, would you like to sign up for a new account?")

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }

                }


                if (length == 6){
                    DatabaseReference doctorsDb = FirebaseDatabase.getInstance().getReference().child("doctors");

                }




//                if (db.child("" + this.loginID) != null) {
//                    throw new IllegalArgumentException("ID has been used to create an account already");
//                }else{
//                    db.child("" + this.loginID).setValue(this);
//                }



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


    public void navigateToPatientActivity(Patient patient){
        Intent navigateToPatientIntent = new Intent(this, PatientActivity.class);
        navigateToPatientIntent.putExtra("patient", patient);

        startActivity(navigateToPatientIntent);
    }

    public void navigateToPatientSignup(){
        Intent navigateToPatientSignup = new Intent(this, PatientInformation.class);
        startActivity(navigateToPatientSignup);
    }

    public void verifyPassword(String dbPassword, String inputPassword){
        if(dbPassword.equals(inputPassword))
            verify = true;
    }


}