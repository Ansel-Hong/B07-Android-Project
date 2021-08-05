package com.example.b07project.appointment_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b07project.R;
import com.example.b07project.user_information.Doctor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class ViewPastAppointmentActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextView appointmentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_past_appointment);

        auth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot appointmentSnapshot = snapshot.child("Appointments");
                String patientID = auth.getUid();

                ArrayList<Date> pastAppointmentsDates = new ArrayList<Date>();
                ArrayList<Appointment> pastAppointments = new ArrayList<Appointment>();
                for (DataSnapshot appointment:appointmentSnapshot.getChildren()){
                    Appointment apt = appointment.getValue(Appointment.class);
                    Date appointmentDate = apt.getStartTime();
                    pastAppointmentsDates.add(appointmentDate);
                }
                Collections.sort(pastAppointmentsDates);

                for (Date date:pastAppointmentsDates){
                    for (DataSnapshot appointment:appointmentSnapshot.getChildren()){
                        Appointment apt = appointment.getValue(Appointment.class);
                        if (date.equals(apt.getStartTime())
                                && patientID.equals(apt.getPatientID())
                                && !pastAppointments.contains(apt)){
                            pastAppointments.add(apt);
                        }
                    }
                }

                for (Appointment apt:pastAppointments){
                    getAppointment(apt.getStartTime(), apt.getDoctorID());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getAppointment(Date date, String doctorID){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("doctors");
        LinearLayout layout = (LinearLayout) findViewById(R.id.past_appointment_list);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot doctor:snapshot.getChildren()){
                    Doctor doc = doctor.getValue(Doctor.class);
                    if (doctorID.equals(doctor.getKey())){
                        Calendar currentTime = Calendar.getInstance();
                        Date currentDate = currentTime.getTime();
                        if (date.compareTo(currentDate) < 0){
                            appointmentInfo = new TextView(ViewPastAppointmentActivity.this);
                            SimpleDateFormat formattedDate = new SimpleDateFormat("EEE MMM d 'at' h:mm a");
                            String time = formattedDate.format(date);
                            appointmentInfo.setText(time + " with Dr." + doc.getName() + "\n");
                            layout.addView(appointmentInfo);
                        }
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