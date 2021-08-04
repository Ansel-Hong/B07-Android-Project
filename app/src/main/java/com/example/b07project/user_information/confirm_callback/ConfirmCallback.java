package com.example.b07project.user_information.confirm_callback;

import android.content.Intent;

import com.example.b07project.LoginPage;
import com.example.b07project.user_information.DoctorActivity;
import com.example.b07project.user_information.PatientActivity;

public interface ConfirmCallback {
    public void confirmUserPageNavigationCallback(boolean isDoc);
//        if (isDoc) {
//            startActivity(new Intent(LoginPage.this, DoctorActivity.class));
//        } else {
//            //Else not a doctor and login via patientlogin
//            startActivity(new Intent(LoginPage.this, PatientActivity.class));
//        }
}
