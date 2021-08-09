package com.example.b07project;

import android.util.Patterns;

public class Presenter {

    private Model model;
    private LoginPage view;

    public Presenter(Model model, LoginPage view){
        this.model = model;
        this.view = view;
    }

    public void login(){
       String email = view.getEmail();
       String password = view.getPassword();

        if(email.equals(""))
            view.displayMessage("email cannot be empty");
        else if(password.equals(""))
            view.displayMessage("password cannot be empty");
        else if(password.length() < 6)
            view.displayMessage("The minimum password length is 6 characters");
//         else if(model.userIsFound(email, password) == true) {
//             view.displayMessage("trying to login");
//             view.userLogin(email, password);
//         }
//         else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
//             view.displayMessage("Please enter a valid email!");
        else{
            int checker = model.userIsFound(email, password);
            if(checker==0)
                view.displayMessage("invalid login");
            else{
                view.displayMessage("trying to login");
                view.userLogin(email, password);
            }

        }

    }
}
