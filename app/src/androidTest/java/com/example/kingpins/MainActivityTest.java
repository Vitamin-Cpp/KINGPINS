package com.example.kingpins;

import android.text.TextUtils;
import android.widget.Button;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    MainActivity mainActivity = null;

    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
        assertNotNull(mainActivity);
    }

//    @Test
//    public void testViews()
//    {
//        TextInputEditText etEmail , etPassword;
//        TextInputLayout ilEmail , ilPassword;
//        Button btnLogin, btnRegister, Btnlogin;
//
//        ilEmail = mainActivity.findViewById(R.id.layout_loginEmail);
//        ilPassword = mainActivity.findViewById(R.id.layout_loginPassword);
//        etEmail = mainActivity.findViewById(R.id.loginEmail);
//        etPassword = mainActivity.findViewById(R.id.loginPassword);
//        btnLogin = mainActivity.findViewById(R.id.btnLogin);
//        btnRegister = mainActivity.findViewById(R.id.btnRegister);
//        Btnlogin = mainActivity.findViewById(R.id.btnGoogleLogin);
//
//        assertNotNull(ilEmail);
//        assertNotNull(ilPassword);
//        assertNotNull(etPassword);
//        assertNotNull(etEmail);
//        assertNotNull(btnLogin);
//        assertNotNull(Btnlogin);
//        assertNotNull(btnRegister);
//    }
    @Test
    public void testValidationErrors()
    {
        TextInputEditText etEmail , etPassword;
        etEmail = mainActivity.findViewById(R.id.loginEmail);
        etPassword = mainActivity.findViewById(R.id.layout_loginPassword);

        if(!TextUtils.isEmpty(etEmail.getText()) && !TextUtils.isEmpty(etPassword.getText()))
        {
            // both non empty
            assertEquals(0,mainActivity.validationErrors());
        }
        else{
            // at least on empty
            assertNotEquals(0,mainActivity.validationErrors());
        }
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
}