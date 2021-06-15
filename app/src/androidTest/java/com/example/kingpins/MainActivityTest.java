//package com.example.kingpins;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.filters.LargeTest;
//import androidx.test.rule.ActivityTestRule;
//
//import com.google.android.material.textfield.TextInputEditText;
//import com.google.android.material.textfield.TextInputLayout;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//
//@RunWith(AndroidJUnit4.class)
//@LargeTest
//public class MainActivityTest {
//
//    @Rule
//    public ActivityTestRule<MainActivity> mActivityRule = null;
//    Activity mainActivity = null;
//
//    private TextInputEditText etEmail , etPassword;
//    private TextInputLayout ilEmail , ilPassword;
//    private Button btnLogin , btnRegister;
//
//    @Before
//    public void setUp() throws Exception {
//        try{
//            mActivityRule = new ActivityTestRule<>(MainActivity.class);
//            mainActivity = mActivityRule.getActivity();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testMain(){
//        ilEmail = mainActivity.findViewById(R.id.layout_loginEmail);
//        ilPassword = mainActivity.findViewById(R.id.layout_loginPassword);
//        etEmail = mainActivity.findViewById(R.id.loginEmail);
//        etPassword = mainActivity.findViewById(R.id.loginPassword);
//        btnLogin = mainActivity.findViewById(R.id.btnLogin);
//        btnRegister = mainActivity.findViewById(R.id.btnRegister);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        mActivityRule = null;
//        mainActivity = null;
//    }
//}