package com.example.b07project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.b07project.user_information.DoctorActivity;
import com.example.b07project.user_information.PatientActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Model {
    List<String> emails;
    List<String> passwords;
    private FirebaseAuth auth;
    private int loginSuccess;

    public Model(){
        loginSuccess = 2;
    }

    public int loginSuccess(){return loginSuccess;}


    public void userIsFound(String email, String password) {
        //int k = 0;
        // if(emails.contains(email)){
        //search in database
        //if(password matches with the email)
        //return true;
        //else
        //return false;
        //}

        //else
        //return false;

        auth = FirebaseAuth.getInstance();


        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    loginSuccess = 1;

                    //If authentication fails
                } else {
                    loginSuccess = 0;

                }
            }
        });

    }

}
