package com.example.kingpins;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> tLoginScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    private ActivityScenario<MainActivity> loginAct = null;

    @Before
    public void setUp() throws Exception {

        loginAct = tLoginScenarioRule.getScenario();

    }

    @Test
    public void testLaunch(){
        assertNotNull( onView(withId(R.id.txtSlogan)));
    }

    @After
    public void tearDown() throws Exception {

        loginAct = null ;
    }

}