package com.muk.helpinghand;

import com.muk.helpinghand.fragment.ChangePwdFragment;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Zelta on 9/06/16.
 */
public class ChangePwdUnitTest {
    private ChangePwdFragment changePwdFragment;

    @Before
    public void setUp() {
        changePwdFragment = new ChangePwdFragment();
    }

    //Validate whether user input the same password
    @Test
    public void notSamePasswordTest() throws Exception {
        assertFalse(changePwdFragment.validateSamePwd("123456","1234567"));
    }

    @Test
    public void validSamePasswordTest() throws Exception{
        assertTrue(changePwdFragment.validateSamePwd("123abc","123abc"));
    }

    //Boundary value test for password length test
    @Test
    public void validMinimumPasswordLengthTest() throws Exception{
        assertFalse(changePwdFragment.validatePwdLength("123456"));
    }

    @Test
    public void validMoreThanMinimumPasswordLengthTest() throws Exception{
        assertFalse(changePwdFragment.validatePwdLength("1234567"));
    }

    @Test
    public void validNormalPasswordLengthTest() throws Exception{
        assertFalse(changePwdFragment.validatePwdLength("1234567abcdef"));
    }

    @Test
    public void validLessThanMaximumPasswordLengthTest() throws Exception{
        assertFalse(changePwdFragment.validatePwdLength("123456789abcde"));
    }

    @Test
    public void validMaximumPasswordLengthTest() throws Exception{
        assertFalse(changePwdFragment.validatePwdLength("123456789abcdef"));
    }

    @Test
    public void invalidLessThanMinimumPasswordLengthTest() throws Exception{
        assertTrue(changePwdFragment.validatePwdLength("12345"));
    }

    @Test
    public void invalidMoreThanMaximumPasswordLengthTest() throws Exception{
        assertTrue(changePwdFragment.validatePwdLength("123456789abcdefg"));
    }
}
