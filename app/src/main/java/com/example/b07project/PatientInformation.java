package com.example.b07project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_information);

        Context pageContext = this;

        Button update_info = findViewById(R.id.add_patient_info);
        update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editText = (EditText) findViewById(R.id.patient_loginID);
                int newLoginID = Integer.parseInt(editText.getText().toString());

                editText = (EditText) findViewById(R.id.patient_password);
                String newPassword = editText.getText().toString();

                editText = (EditText) findViewById(R.id.patient_name);
                String newName = editText.getText().toString();

                editText = (EditText) findViewById(R.id.patient_email);
                String newEmail = editText.getText().toString();

                editText = (EditText) findViewById(R.id.age);
                int newAge = Integer.parseInt(editText.getText().toString());

                editText = (EditText) findViewById(R.id.weight);
                int newWeight = Integer.parseInt(editText.getText().toString());

                editText = (EditText) findViewById(R.id.blood_type);
                String newBloodType = editText.getText().toString();

                try{
                    Patient newPatient = new Patient(newName, newEmail, newLoginID, newPassword);
                    HealthInformation newHealthInformation = new HealthInformation(newAge, newWeight, newBloodType);
                    newPatient.addHealthInformation(newHealthInformation);
                } catch (IllegalArgumentException e){
                    new AlertDialog.Builder(pageContext)
                            .setTitle("Invalid Input")
                            .setMessage("This user ID has already been used to create another account")

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }catch (InputException ex){
                    new AlertDialog.Builder(pageContext)
                            .setTitle("Invalid Input")
                            .setMessage("The ID or email inputted do not match proper format.\nPlease insure the ID is a 5 digit number")

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }



            }
        });
    }
}