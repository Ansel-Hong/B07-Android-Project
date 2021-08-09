package com.example.b07project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.b07project.user_information.DoctorActivity;
import com.example.b07project.user_information.HealthInformation;
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
public class MockitoPresenterTests {
//    @Mock
//    MainActivity view;
//
//    @Mock
//    LoginPage loginView;

//    @Test
//    public void testMainNavigation(){
//        when(loginView.navigateToPatientSignup()).getMock();
//    }

//    @Mock
//    PatientActivity patView;
//    @Mock
//    FirebaseUser patient;
//    @Mock
//    DatabaseReference ref;
//    @Mock
//    private String userID;


    @Mock
    LoginPage loginView;

    @Mock
    Model model;

    @Test
    public void presenterTest(){
        when(loginView.getEmail()).thenReturn("abc@mail.com");
        when(loginView.getPassword()).thenReturn("abc");
        when(model.userIsFound("abc@mail.com", "abc")).thenReturn(true);
        Presenter presenter = new Presenter(model, loginView);

        presenter.login();
        verify(loginView).displayMessage("trying to login");
    }

//    @Mock
//    DoctorActivity docView;


//    @Test
//    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
//    }
}