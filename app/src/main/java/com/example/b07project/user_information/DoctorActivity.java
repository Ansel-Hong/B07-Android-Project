package com.example.b07project.user_information;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.b07project.MainActivity;
import com.example.b07project.R;
import com.example.b07project.appointment_activities.DoctorViewAppointmentActivity;
import com.example.b07project.appointment_activities.ViewAppointmentActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorActivity extends AppCompatActivity {

    private FirebaseUser doctor;
    private DatabaseReference ref;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        doctor = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference().child("doctors");
        userID = doctor.getUid();

        final TextView welcome = (TextView) findViewById(R.id.doctor_welcome);

        ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Doctor doctorProfile = snapshot.getValue(Doctor.class);

                if (doctorProfile != null){
                    String name = doctorProfile.name;
                    String email = doctorProfile.email;
                    String gender = doctorProfile.gender;
                    String specialty = doctorProfile.specialty;

                    welcome.setText("Welcome Dr." + name + "!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                new AlertDialog.Builder(DoctorActivity.this)
                        .setTitle("Something went wrong, please restart the application")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation.
                            }
                        });
            }
        });

        Button logout = (Button) findViewById(R.id.doctorSignOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent navigateToDoctorIntent = new Intent(DoctorActivity.this, MainActivity.class);
                startActivity(navigateToDoctorIntent);
            }
        });

        }

        public void navigateToDoctorViewAppointmentsActivity(View view) {
            Intent navigateToDoctorIntent = new Intent(this, DoctorViewAppointmentActivity.class);
            startActivity(navigateToDoctorIntent);
        }

        public void navigateToDoctorViewPatientsActivity(View view) {
            Intent navigateToDoctorIntent = new Intent(this, DoctorPatientCheckupActivity.class);
            startActivity(navigateToDoctorIntent);
        }

}