package com.example.b07project.appointment_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b07project.R;
import com.example.b07project.user_information.Patient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleShowAppointment extends AppCompatActivity {

    FirebaseAuth auth;
    TextView appointmentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_appointment);

        auth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot appointmentsSnapshot = snapshot.child("Appointments");
                for(DataSnapshot appointment: appointmentsSnapshot.getChildren()){
                    String doctorID = auth.getUid();
                    Appointment apt = appointment.getValue(Appointment.class);
                    Calendar thisWeek = Calendar.getInstance();
                    int t = thisWeek.get(thisWeek.HOUR_OF_DAY);
                    t = 24 - t + 144;
                    thisWeek.add(Calendar.HOUR, t);
                    thisWeek.set(Calendar.MINUTE, 0);
                    thisWeek.set(Calendar.SECOND, 0);
                    if(doctorID.equals(apt.getDoctorID()) && apt.getStartTime().before(thisWeek.getTime()) == true){
                        getAppointment(apt.getStartTime(), apt.getPatientID());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void getAppointment(Date date, String patientID){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("patients");
        LinearLayout layout = (LinearLayout) findViewById(R.id.appointment_list);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot patient: snapshot.getChildren()){
                    Patient pat = patient.getValue(Patient.class);


                    if(patientID.equals(patient.getKey())){
                        appointmentInfo = new TextView(ScheduleShowAppointment.this);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d 'at' h:mm a");
                        String time = dateFormat.format(date);
                        appointmentInfo.setText(time+" with patient "+pat.getName());

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