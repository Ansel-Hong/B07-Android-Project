package com.example.b07project.user_information;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b07project.R;
import com.example.b07project.appointment_activities.Appointment;
import com.example.b07project.appointment_activities.ViewAppointmentActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DoctorPatientCheckupActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextView patientInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient_checkup);

        auth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        LinearLayout layout = (LinearLayout) findViewById(R.id.patient_list);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot patientsSnapshot = snapshot.child("patients");
                for(DataSnapshot patient:patientsSnapshot.getChildren()){
                    String doctorID = auth.getUid();
                    Patient pat = patient.getValue(Patient.class);

                    patientInfo = new TextView(DoctorPatientCheckupActivity.this);

                    String patientName = pat.getName();
                    String patUID = patient.getKey();
                    HealthInformation patientHI = pat.getHealthInformation();
                    String patientGender = patientHI.gender;
                    int patientAge = patientHI.age;
                    int patientWeight = patientHI.weight;
                    patientInfo.setText(patientName+": ");
                    if (patientGender != null)
                        patientInfo.append("\n    Gender - "+patientHI.gender);
                    if (patientAge > 0)
                        patientInfo.append("\n    Age - "+patientHI.age);
                    if (patientWeight > 0)
                        patientInfo.append("\n    weight - "+patientHI.weight);



                    patientInfo.append("\n\n");

                    layout.addView(patientInfo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getPastDoctors(Patient pat, String patUID){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Appointments");
        LinearLayout layout = (LinearLayout) findViewById(R.id.appointment_list);

        ArrayList<Appointment> currPatientAppointments = pat.getAppointments();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot apt: snapshot.getChildren()){
//                    Doctor doc = apt.getValue(Doctor.class);
//
//
//                    if(doctorID.equals(doctor.getKey())){
//                        appointmentInfo = new TextView(ViewAppointmentActivity.this);
//
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d 'at' h:mm a");
//                        String time = dateFormat.format(date);
//                        appointmentInfo.setText(time+" with Dr. "+doc.getName());
//
//                        layout.addView(appointmentInfo);
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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