package com.muk.helpinghand;

import android.location.Location;

import com.muk.helpinghand.fragment.HandUpFragment;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Created by Zelta on 8/06/16.
 */
public class HandUpUnitTest {
    private HandUpFragment handUpFragment;

    @Before
    public void setUp() {
        handUpFragment = new HandUpFragment();
    }

    //Validate location
    @Test
    public void noLocationTest() throws Exception{
        assertTrue(handUpFragment.validateLocation(0.0));
    }

    @Test
    public void validLocationTest() throws Exception{
        assertFalse(handUpFragment.validateLocation(32.1111));
    }

    //Equivalence class test for user input
    @Test
    public void validNullUserInputTest() throws Exception{
        assertTrue(handUpFragment.validateUserInput(null));
    }

    @Test
    public void emptyUserInputTest() throws Exception{
        assertTrue(handUpFragment.validateUserInput(""));
    }

    @Test
    public void emptySpaceUserInputTest() throws Exception{
        assertTrue(handUpFragment.validateUserInput("   "));
    }

    @Test
    public void validUserInputTest() throws Exception{
        assertFalse(handUpFragment.validateUserInput("Test"));
    }
}
