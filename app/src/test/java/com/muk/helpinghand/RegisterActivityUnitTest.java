package com.muk.helpinghand;

import android.support.v4.media.MediaMetadataCompat;

import com.muk.helpinghand.activity.RegisterActivity;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Zelta on 9/06/16.
 */
public class RegisterActivityUnitTest {
    private RegisterActivity registerActivity;

    @Before
    public void setUp() {
        registerActivity = new RegisterActivity();
    }

    //Validate whether user input the same password
    @Test
    public void notSamePasswordTest() throws Exception {
        assertFalse(registerActivity.validateSamePwd("123456","1234567"));
    }

    @Test
    public void validSamePasswordTest() throws Exception{
        assertTrue(registerActivity.validateSamePwd("123abc","123abc"));
    }

    //Boundary value test for password length test
    @Test
    public void validMinimumPasswordLengthTest() throws Exception{
        assertFalse(registerActivity.validatePwdLength("123456"));
    }

    @Test
    public void validMoreThanMinimumPasswordLengthTest() throws Exception{
        assertFalse(registerActivity.validatePwdLength("1234567"));
    }

    @Test
    public void validNormalPasswordLengthTest() throws Exception{
        assertFalse(registerActivity.validatePwdLength("1234567abcdef"));
    }

    @Test
    public void validLessThanMaximumPasswordLengthTest() throws Exception{
        assertFalse(registerActivity.validatePwdLength("123456789abcde"));
    }

    @Test
    public void validMaximumPasswordLengthTest() throws Exception{
        assertFalse(registerActivity.validatePwdLength("123456789abcdef"));
    }

    @Test
    public void invalidLessThanMinimumPasswordLengthTest() throws Exception{
        assertTrue(registerActivity.validatePwdLength("12345"));
    }

    @Test
    public void invalidMoreThanMaximumPasswordLengthTest() throws Exception{
        assertTrue(registerActivity.validatePwdLength("123456789abcdefg"));
    }
}
