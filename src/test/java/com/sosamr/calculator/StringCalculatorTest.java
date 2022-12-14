package com.sosamr.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringCalculatorTest {

    @Test
    public void givenEmptyString_whenAdded_thenReturnValueIs0() {
        final StringCalculator stringCalculator = new StringCalculator();
        final String emptyString = "";
        final long expectedValue = 0;
        final long actualValue = stringCalculator.add(emptyString);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void givenStringWithOneNumber_whenAdded_thenReturnValueIsThatNumber() {
        final StringCalculator stringCalculator = new StringCalculator();
        final String oneNumberString = "5";
        final long expectedValue = 5;
        final long actualValue = stringCalculator.add(oneNumberString);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void givenStringWithTwoNumbers_whenAdded_thenReturnValueIsOK() {
        final StringCalculator stringCalculator = new StringCalculator();
        final String twoNumbersString = "1,2";
        final long expectedValue = 3;
        final long actualValue = stringCalculator.add(twoNumbersString);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void givenStringWithManyNumbers_whenAdded_thenReturnValueIsOK() {
        final StringCalculator stringCalculator = new StringCalculator();
        final String manyNumbersString = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15";
        final long expectedValue = 120;
        final long actualValue = stringCalculator.add(manyNumbersString);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void givenStringWithNumbersIncludingNewLines_whenAdded_thenReturnValueIsOK() {
        final StringCalculator stringCalculator = new StringCalculator();
        final String numbersString = "1\n2,3,4,5\n6,7,8,9\n10";
        final long expectedValue = 55;
        final long actualValue = stringCalculator.add(numbersString);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void givenStringWithNewLineAndCommaSeparatorTogether_whenAdded_thenExceptionIsThrown() {
        final StringCalculator stringCalculator = new StringCalculator();
        final String numbersString = "1\n2,3,4,5,\n6,7";
        assertThrows(IllegalArgumentException.class, () -> stringCalculator.add(numbersString));
    }

    @Test
    public void givenStringWithNumbersAndCustomDelimiter_whenAdded_thenReturnValueIsOK() {
        final StringCalculator stringCalculator = new StringCalculator();
        final String numbersWithCustomDelimiter = "//;\n1;2";
        final long expectedValue = 3;
        final long actualValue = stringCalculator.add(numbersWithCustomDelimiter);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void givenStringWithCustomDelimiterButNoNumbers_whenAdded_thenReturnValueIs0() {
        final StringCalculator stringCalculator = new StringCalculator();
        final String noNumbersWithCustomDelimiter = "//;\n";
        final long expectedValue = 0;
        final long actualValue = stringCalculator.add(noNumbersWithCustomDelimiter);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void givenStringWithNegativeNumbers_whenAdded_thenExceptionIsThrown() {
        final StringCalculator stringCalculator = new StringCalculator();
        final String numbersString = "1\n-2,3,4,-5,\n6,7";
        final String expectedMessage = "Negative numbers not allowed: -2,-5";
        assertThrows(IllegalArgumentException.class, () -> stringCalculator.add(numbersString), expectedMessage);
    }

    @Test
    public void givenStringWithValuesGreaterThan1000_whenAdded_thenReturnValueExcludedThem() {
        final StringCalculator stringCalculator = new StringCalculator();
        final String manyNumbersString = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1000,5000,999999999";
        final long expectedValue = 1120;
        final long actualValue = stringCalculator.add(manyNumbersString);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void givenStringWithNumbersAndCustomDelimiterWithMoreThanOneChar_whenAdded_thenReturnValueIsOK() {
        final StringCalculator stringCalculator = new StringCalculator();
        final String numbersWithCustomDelimiter = "//[***]\n1***2***3";
        final long expectedValue = 6;
        final long actualValue = stringCalculator.add(numbersWithCustomDelimiter);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void givenStringWithNumbersAndDifferentCustomDelimitersWithOneChar_whenAdded_thenReturnValueIsOK() {
        final StringCalculator stringCalculator = new StringCalculator();
        final String numbersWithCustomDelimiter = "//[-][!][*]\n1!2!3-4*5";
        final long expectedValue = 15;
        final long actualValue = stringCalculator.add(numbersWithCustomDelimiter);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void givenStringWithNumbersAndDifferentCustomDelimitersWithMoreThanOneChar_whenAdded_thenReturnValueIsOK() {
        final StringCalculator stringCalculator = new StringCalculator();
        final String numbersWithCustomDelimiter = "//[---][!!][****]\n1!!2!!3---4****5";
        final long expectedValue = 15;
        final long actualValue = stringCalculator.add(numbersWithCustomDelimiter);
        assertEquals(expectedValue, actualValue);
    }
}
