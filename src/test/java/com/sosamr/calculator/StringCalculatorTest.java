package com.sosamr.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringCalculatorTest {

    @Test
    public void givenEmptyString_whenAdded_thenReturnValueIs0() {
        final StringCalculator stringCalculator = new StringCalculator();
        final String emptyString = "";
        final int expectedValue = 0;
        final int actualValue = stringCalculator.add(emptyString);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void givenStringWithOneNumber_whenAdded_thenReturnValueIsThatNumber() {
        final StringCalculator stringCalculator = new StringCalculator();
        final String oneNumberString = "5";
        final int expectedValue = 5;
        final int actualValue = stringCalculator.add(oneNumberString);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void givenStringWithTwoNumbers_whenAdded_thenReturnValueIsOK() {
        final StringCalculator stringCalculator = new StringCalculator();
        final String twoNumbersString = "1,2";
        final int expectedValue = 3;
        final int actualValue = stringCalculator.add(twoNumbersString);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void givenStringWith3Numbers_whenAdded_thenExceptionIsThrown() {
        final StringCalculator stringCalculator = new StringCalculator();
        final String tooManyNumbersString = "1,2,3";
        assertThrows(IllegalArgumentException.class, () -> stringCalculator.add(tooManyNumbersString));
    }
}
