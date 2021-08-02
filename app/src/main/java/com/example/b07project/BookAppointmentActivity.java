package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookAppointmentActivity extends AppCompatActivity {
    Button newDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("doctors");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Doctor d = child.getValue(Doctor.class);
                    addDoctor(d);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("info", "Failed to read value.", error.toException());
            }
        });

    }



    public void addDoctor(Doctor doctor){
        int loginID = doctor.getEmployeeID();
        LinearLayout layout = (LinearLayout) findViewById(R.id.doctor_list);
        newDoctor = new Button(this);
        newDoctor.setText(doctor.name);

        newDoctor.setId(doctor.getEmployeeID());
        newDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigateToAvailabilityIntent = new Intent(BookAppointmentActivity.this, SelectAvailabilityActivity.class);
                navigateToAvailabilityIntent.putExtra("loginID", Integer.toString(loginID));
                startActivity(navigateToAvailabilityIntent);
            }
        });
        layout.addView(newDoctor);
    }

}