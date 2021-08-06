package com.example.b07project.appointment_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b07project.LoginPage;
import com.example.b07project.R;
import com.example.b07project.user_information.DoctorActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ScheduleShowAvailability extends AppCompatActivity {

    TextView timeSlot;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        auth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        Context pageContext = this;

        Intent intent = getIntent();
        String doctorID = auth.getUid();
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("doctors").child(doctorID);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();



        //Creating blank schedule
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                int i = 0;

                TimeZone.setDefault(TimeZone.getTimeZone("EST"));

                Calendar today = Calendar.getInstance();
                today.add(Calendar.DAY_OF_MONTH, 1);
                today.set(Calendar.HOUR, -15);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);


                Calendar holder = new GregorianCalendar();
                holder = today;


                while(i<7){ //this week
                    int j = 0;
                    while (j<9){
                        Calendar slot = new GregorianCalendar();
                        slot = holder;

                        Date date = slot.getTime();

                        Calendar now = Calendar.getInstance();


                        SimpleDateFormat test = new SimpleDateFormat("yyyyMMddhhmm");
                        String dateCode = test.format(date)+doctorID;



                        if(!snapshot.child("Appointments").child(dateCode).exists() && now.before(slot))
                            addTimeSlot(date); //should be in if statement once all features are added

                        slot.add(Calendar.HOUR, 1);
                        holder = slot;
                        j++;
                    }
                    holder.add(Calendar.HOUR, -9);
                    holder.add(Calendar.DAY_OF_MONTH,1);
                    i++;
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void addTimeSlot(Date date){
        LinearLayout layout = (LinearLayout) findViewById(R.id.time_slots);
        timeSlot = new TextView(this);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d 'at' h:mm a");
        String time = dateFormat.format(date);
        timeSlot.setText(time + " (available) ");

        layout.addView(timeSlot);

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