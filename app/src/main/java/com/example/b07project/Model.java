package com.example.b07project;

import java.util.ArrayList;
import java.util.List;

public class Model {
    List<String> emails;
    List<String> passwords;

    public Model(){
        emails = new ArrayList<String>();
        passwords = new ArrayList<String>();
        //add emails and passwords through database?
        //both doctors and patients?
    }

    public boolean userIsFound(String email, String password){
        int k = 0;
        if(emails.contains(email)){
            //search in database
            //if(password matches with the email)
                return true;
            //else
                //return false;
        }

        else
            return false;
    }


}
