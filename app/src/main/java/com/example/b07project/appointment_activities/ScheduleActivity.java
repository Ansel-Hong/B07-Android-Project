package com.example.b07project.appointment_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b07project.R;
import com.example.b07project.user_information.DoctorActivity;
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
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ScheduleActivity extends AppCompatActivity {

    TextView timeSlot;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

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

                        else
                            getAppointment(date, dateCode);



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


    public void addTimeSlot(Date date){
        LinearLayout layout = (LinearLayout) findViewById(R.id.time_slots2);
        timeSlot = new TextView(this);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d 'at' h:mm a");
        String time = dateFormat.format(date);
        timeSlot.setText(time + " (available) ");

        layout.addView(timeSlot);

    }


    public void getAppointment(Date date, String dateCode){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Appointments");
        LinearLayout layout = (LinearLayout) findViewById(R.id.time_slots2);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot apt: snapshot.getChildren()){
                    Appointment aptm = apt.getValue(Appointment.class);


                    if(dateCode.equals(apt.getKey())){ //not sure if this is correct
                        timeSlot = new TextView(ScheduleActivity.this);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d 'at' h:mm a");
                        String time = dateFormat.format(date);
                        timeSlot.setText(time+" with patient "+aptm.getPatientID());

                        layout.addView(timeSlot);
                        break;
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


    /** Called when the user taps the back button */
    public void goBack(View view) {
        Intent intent = new Intent(this, DoctorActivity.class);
        startActivity(intent);
    }

}
