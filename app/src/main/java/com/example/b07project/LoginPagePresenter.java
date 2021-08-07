package com.example.b07project;

import android.util.Patterns;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class LoginPagePresenter {

    private Model model;
    private LoginPage view;

    public LoginPagePresenter(Model model, LoginPage view){
        this.model = model;
        this.view = view;
    }

    public static String[] checkLoginDetails(String email, String password){

        String[] retVal = new String[2];

        if (email.isEmpty()) {
            retVal[0] = "Email is required!";
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            retVal[0] = "Please enter a valid email!";
        }

        if (password.isEmpty()) {
            retVal[1] = "Password is required!";
        }

        if (password.length() < 6) {
            retVal[1] = "The minimum password length is 6 characters";
        }

        return retVal;
    }
}
