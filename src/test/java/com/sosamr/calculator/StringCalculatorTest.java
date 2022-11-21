package com.sosamr.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringCalculatorTest {

    @Test
    public void givenEmptyString_whenAdded_thenReturnValueIs0() {
        final StringCalculator stringCalculator = new StringCalculator();
        final String emptyString = "";
        final int expectedValue = 0;
        final int actualValue = stringCalculator.add(emptyString);
        assertEquals(expectedValue, actualValue);
    }

}
