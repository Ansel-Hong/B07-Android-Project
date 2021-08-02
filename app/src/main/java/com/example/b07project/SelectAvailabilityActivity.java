package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SelectAvailabilityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_availability);

        Context pageContext = this;

        Intent intent = getIntent();
        int loginID = Integer.parseInt(intent.getStringExtra("loginID"));
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("doctors").child("" + loginID).child("Availability");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot availability:dataSnapshot.getChildren()){
                    Availability child = availability.getValue(Availability.class);
                    addTimeSlot(child);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("info", "Failed to read value.", error.toException());
            }
        });
    }

    public void addTimeSlot(Availability availability){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.availability_list);
        Button newTimeSlot = new Button(this);
        newTimeSlot.setText(availability.toString());
        newTimeSlot.setId(availability.hashCode());
        linearLayout.addView(newTimeSlot);
        Context pageContext = this;
        newTimeSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(pageContext)
                        .setTitle("Booking Successful")
                        .setMessage("You have successfully booked this time slot!")
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });
    }

}