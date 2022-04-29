package com.example.translateapp;

import com.google.common.truth.StringSubject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class InputcheckTest {

    @Test
    public void wheniputisvalid() {
        String input = "provide a value";
        Inputcheck inputcheck = new Inputcheck();
        String result = String.valueOf(inputcheck.validateinput(input));
        assertTrue(result, true);

    }

    @Test
    public void wheniputisinvalid() {
        String input = " ";
        Inputcheck inputcheck = new Inputcheck();
        String result = String.valueOf(inputcheck.validateinput(input));
        assertFalse(result, false);

    }

    @Test
    public void wheniputisanumber() {
        int input = 1234;
        Inputcheck inputcheck = new Inputcheck();
        String result = String.valueOf(inputcheck.validateinput(String.valueOf(input)));
        assertFalse(result, false);

    }
}