package com.example.kingpins;

import android.widget.Button;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    MainActivity mainActivity = null;

    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void testViews()
    {
        TextInputEditText etEmail , etPassword;
        TextInputLayout ilEmail , ilPassword;
        Button btnLogin, btnRegister, Btnlogin;

        ilEmail = mainActivity.findViewById(R.id.layout_loginEmail);
        ilPassword = mainActivity.findViewById(R.id.layout_loginPassword);
        etEmail = mainActivity.findViewById(R.id.loginEmail);
        etPassword = mainActivity.findViewById(R.id.loginPassword);
        btnLogin = mainActivity.findViewById(R.id.btnLogin);
        btnRegister = mainActivity.findViewById(R.id.btnRegister);
        Btnlogin = mainActivity.findViewById(R.id.btnGoogleLogin);

        assertNotNull(ilEmail);
        assertNotNull(ilPassword);
        assertNotNull(etPassword);
        assertNotNull(etEmail);
        assertNotNull(btnLogin);
        assertNotNull(Btnlogin);
        assertNotNull(btnRegister);
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
}