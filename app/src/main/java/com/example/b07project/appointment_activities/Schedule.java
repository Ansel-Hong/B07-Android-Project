package com.example.b07project.appointment_activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Schedule {

    ArrayList<ArrayList<Calendar>> schedule;

    public Schedule(){
        schedule = new ArrayList<ArrayList<Calendar>>();

        createSchedule();
    }


    // DO THIS **IN** the display appointment page... that way you can check the db right away.
    // Once appointment is verified, addButton to book it.

    public void createSchedule(){

        int i = 0;

        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_MONTH, 1);
        today.set(Calendar.HOUR, 9);

        Calendar holder = new GregorianCalendar();
        holder = today;


        while(i<30);

        ArrayList<Calendar> day = new ArrayList<Calendar>();

        int j = 0;
        while (j<9){
            Calendar slot = new GregorianCalendar();
            slot = holder;

            day.add(slot);

            slot.add(Calendar.HOUR, 1);
            holder = slot;
            j++;
        }
        System.out.println("\n-----------" + holder.toString() + "------------\n");


        schedule.add(day);
        i++;
    }


}
