package com.example.b07project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import com.example.b07project.user_information.DoctorActivity;
import com.example.b07project.user_information.Patient;
import com.example.b07project.user_information.PatientActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    @Mock
    MainActivity view;

    @Mock
    LoginPage loginView;

//    @Test
//    public void testMainNavigation(){
//        when(loginView.navigateToPatientSignup()).getMock();
//    }

    @Mock
    PatientActivity patView;
    @Mock
    FirebaseUser patient;
    @Mock
    DatabaseReference ref;
    @Mock
    private String userID;

    @Test
    public void testMainNavigation(){
//        when(patView.navigateToPatientSignup()).getMock();
        Patient pat = new Patient("Patient1");
        pat.getHealthInformation();
    }

    @Mock
    DoctorActivity docView;


//    @Test
//    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
//    }
}