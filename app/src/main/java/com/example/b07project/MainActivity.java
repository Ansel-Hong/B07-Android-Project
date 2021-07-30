package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.b07project.ui.login.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActivityBackgroundColor(0xff42e9f5);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//        Patient p = new Patient("John Doe", "johndoe", 10000, "password");
//        ref.child("p1").setValue(p);

        //reads patients in database and displays users in log --
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                DataSnapshot patients = dataSnapshot.child("patients");
                for (DataSnapshot child: patients.getChildren()){
                    Patient p = child.getValue(Patient.class);
//                    if(p.loginID == 202023){
//                        HealthInformation h = new HealthInformation(33, 170, "O-");
//                        Map<String, Object> patientUpdates = new HashMap<>();
//                        patientUpdates.put("healthInformation", h);
//                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("patients").child(""+ p.loginID);
//                        ref.updateChildren(patientUpdates);
//
//
//                    }
                    Log.i("info", p.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("info", "Failed to read value.", error.toException());
            }
        });





    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

    /** Called when the user taps the login button */
    public void goToLoginPage(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}