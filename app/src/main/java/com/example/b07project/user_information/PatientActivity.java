package com.example.b07project.user_information;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.b07project.R;
import com.example.b07project.appointment_activities.ViewAppointmentActivity;
import com.example.b07project.appointment_activities.BookAppointmentActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientActivity extends AppCompatActivity {

    private FirebaseUser patient;
    private DatabaseReference ref;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        patient = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference().child("patients");
        userID = patient.getUid();

        final TextView welcome = (TextView) findViewById(R.id.patient_welcome);

        ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Patient patientProfile = snapshot.getValue(Patient.class);

                if(patientProfile != null){
                    String name = patientProfile.name;
                    String email = patientProfile.email;
                    int age = patientProfile.getHealthInformation().age;
                    int weight = patientProfile.getHealthInformation().weight;
                    String bloodType = patientProfile.getHealthInformation().bloodtype;

                    welcome.setText("Welcome " + name + "!");



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                new AlertDialog.Builder(PatientActivity.this)
                        .setTitle("Something went wrong, please restart the application")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                            }
                        });
            }
        });




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