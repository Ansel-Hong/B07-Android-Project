package com.example.b07project.user_information;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07project.LoginPage;
import com.example.b07project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class DoctorSignup extends AppCompatActivity {

    private FirebaseAuth auth;
    EditText editTextName, editTextEmail, editTextPassword, editTextSpecialty;
    RadioGroup genderRadioGroup;
    Spinner specialtySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String[] specialties = new String[] {"Specialty", "Cardiology", "Dermatology", "Family Medicine", "Neurology"
                , "Gynecology", "Pediatrics", "Physiotherapy", "Psychiatry"};
        Spinner specialtySpinner = (Spinner)findViewById(R.id.specialty_doctor);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, specialties);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialtySpinner.setAdapter(adapter);

        auth = FirebaseAuth.getInstance();
        Button update_info = findViewById(R.id.add_doctor_info);
        update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                editTextPassword = (EditText) findViewById(R.id.doctor_password);

                editTextName = (EditText) findViewById(R.id.doctor_name);

                editTextEmail = (EditText) findViewById(R.id.doctor_email);


                genderRadioGroup = (RadioGroup)findViewById(R.id.gender_doctor);

                registerUser();

            }
        });



    }

    private void registerUser() {


        String newPassword = editTextPassword.getText().toString().trim();

        String newName = editTextName.getText().toString().trim();

        String newEmail = editTextEmail.getText().toString().trim();

        Spinner specialtySpinner = (Spinner)findViewById(R.id.specialty_doctor);
        String newSpecialty = specialtySpinner.getSelectedItem().toString().trim();

        String gender = ((RadioButton)findViewById(genderRadioGroup.getCheckedRadioButtonId()))
                .getText().toString();



        if (newName.isEmpty()) {
            editTextName.setError("Full name is required!");
            editTextName.requestFocus();
            return;
        }


        if (newEmail.isEmpty()) {
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (newPassword.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (newPassword.length() < 6) {
            editTextPassword.setError("Password should be at least 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        if (newSpecialty.equals("Specialty")) {
            new AlertDialog.Builder(DoctorSignup.this)
                    .setTitle("Please select your specialty!")

                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }

        auth.createUserWithEmailAndPassword(newEmail, newPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Doctor newDoctor = new Doctor(newName, newEmail, newPassword, gender, newSpecialty);

                            FirebaseDatabase.getInstance().getReference().child("doctors").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(newDoctor).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast toast = Toast.makeText(DoctorSignup.this, "User successfully registered!", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                                        toast.show();



                                    } else{
                                        new AlertDialog.Builder(DoctorSignup.this)
                                                .setTitle("User failed to register, please try again")
                                                .setMessage("Email may have already been used to create an account")

                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                }).show();
                                    }
                                }
                            });

                        } else {
                            new AlertDialog.Builder(DoctorSignup.this)
                                    .setTitle("User failed to register, please try again")
                                    .setMessage("Email may have already been used to create an account")

                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }).show();
                        }
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