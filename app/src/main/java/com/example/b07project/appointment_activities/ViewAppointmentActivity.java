package com.example.b07project.appointment_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b07project.*;
import com.example.b07project.R;
import com.example.b07project.user_information.Doctor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewAppointmentActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextView appointmentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);

        auth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot appointmentsSnapshot = snapshot.child("Appointments");
                for(DataSnapshot appointment: appointmentsSnapshot.getChildren()){
                    String patientID = auth.getUid();
                    Appointment apt = appointment.getValue(Appointment.class);

                    if(patientID.equals(apt.getPatientID())){
                        getAppointment(apt.getStartTime(), apt.getDoctorID());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void getAppointment(Date date, String doctorID){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("doctors");
        LinearLayout layout = (LinearLayout) findViewById(R.id.appointment_list);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot doctor: snapshot.getChildren()){
                    Doctor doc = doctor.getValue(Doctor.class);


                    if(doctorID.equals(doctor.getKey())){
                        appointmentInfo = new TextView(ViewAppointmentActivity.this);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d 'at' h:mm a");
                        String time = dateFormat.format(date);
                        appointmentInfo.setText(time+" with Dr. "+doc.getName());

                        layout.addView(appointmentInfo);
                    }
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