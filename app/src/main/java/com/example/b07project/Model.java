package com.example.b07project;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class Model {
    private FirebaseAuth auth;

    public Model(){
        
    }

    public boolean userIsFound(String email, String password){

        auth = FirebaseAuth.getInstance();
        boolean [] checker = {false};

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    checker[0] = true;
            }
        });
        return checker[0];
    }


}
