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

import com.example.b07project.R;
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

public class SelectAvailabilityActivity extends AppCompatActivity {

    Button newSlot;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_availability);

        auth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        Context pageContext = this;

        Intent intent = getIntent();
        String doctorID = intent.getStringExtra("doctorID");
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("doctors").child(doctorID);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        //Creating blank schedule
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                int i = 0;

                Calendar today = Calendar.getInstance();
                today.add(Calendar.DAY_OF_MONTH, 1);
                today.set(Calendar.HOUR, -15);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);


                Calendar holder = new GregorianCalendar();
                holder = today;


                while(i<30){
                    int j = 0;
                    while (j<9){
                        Calendar slot = new GregorianCalendar();
                        slot = holder;

                        Date date = slot.getTime();

                        Calendar now = Calendar.getInstance();


                        SimpleDateFormat test = new SimpleDateFormat("yyyyMMddhhmm");
                        String dateCode = test.format(date)+doctorID;



                        if(!snapshot.child("Appointments").child(dateCode).exists() && now.before(slot))
                            addTimeSlot(date, doctorID); //should be in if statement once all features are added

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


//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot availability:dataSnapshot.getChildren()){
//                    //Availability child = availability.getValue(Availability.class);
//                    //addTimeSlot(child);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                Log.w("info", "Failed to read value.", error.toException());
//            }
//        });
    }

//    public void addTimeSlot(Availability availability){
//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.availability_list);
//        Button newTimeSlot = new Button(this);
//        newTimeSlot.setText(availability.toString());
//        newTimeSlot.setId(availability.hashCode());
//        linearLayout.addView(newTimeSlot);
//        Context pageContext = this;
//        newTimeSlot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(pageContext)
//                        .setTitle("Booking Successful")
//                        .setMessage("You have successfully booked this time slot!")
//                        .setNegativeButton(android.R.string.no, null)
//                        .show();
//            }
//        });
//    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void addTimeSlot(Date date, String doctorID){
        LinearLayout layout = (LinearLayout) findViewById(R.id.time_slots);
        newSlot = new Button(this);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d 'at' h:mm a");
        String time = dateFormat.format(date);
        newSlot.setText(time);
        newSlot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                bookAppointment(date, doctorID);
            }
        });

        layout.addView(newSlot);

    }

    public void bookAppointment(Date date, String doctorID){


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Appointments");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String patientID = auth.getCurrentUser().getUid();

                Appointment apt = new Appointment(doctorID, patientID, date);

                String aptID = apt.getAppointmentID();

                ref.child(aptID).setValue(apt);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }



}