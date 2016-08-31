package com.muk.helpinghand;

import com.muk.helpinghand.fragment.HandWrittenFragment;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Zelta on 9/06/16.
 */
public class HandWrittenUnitTest {
    private HandWrittenFragment handWrittenFragment;

    @Before
    public void setUp() {
        handWrittenFragment = new HandWrittenFragment();
    }

    //Validate location
    @Test
    public void noLocationTest() throws Exception{
        assertTrue(handWrittenFragment.validateLocation(0.0));
    }

    @Test
    public void validLocationTest() throws Exception{
        assertFalse(handWrittenFragment.validateLocation(32.1111));
    }

    //Equivalence class test for user input
    @Test
    public void validNullUserInputTest() throws Exception{
        assertTrue(handWrittenFragment.validateUserInput(null));
    }

    @Test
    public void emptyUserInputTest() throws Exception{
        assertTrue(handWrittenFragment.validateUserInput(""));
    }

    @Test
    public void emptySpaceUserInputTest() throws Exception{
        assertTrue(handWrittenFragment.validateUserInput("   "));
    }

    @Test
    public void validUserInputTest() throws Exception{
        assertFalse(handWrittenFragment.validateUserInput("Test"));
    }
}
