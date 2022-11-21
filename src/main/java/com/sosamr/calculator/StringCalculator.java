package com.sosamr.calculator;

public class StringCalculator {

    private final static String DELIMITER = ",";

    /**
     *
     * @param numbers - comma separated list of valid integer numbers.
     *                Assumptions: Must be not null, all the values are valid integer numbers.
     *                Current implementation allows up to 2 numbers.
     * @return the sum of the numbers or 0 for an empty string
     */
    public int add(String numbers) {
        if(numbers.isEmpty()) {
            return 0;
        }

        String[] allNumbers = numbers.split(DELIMITER);

        if (allNumbers.length > 2) {
            throw new IllegalArgumentException("This calculator cannot add more than 2 numbers");
        }

        int sum = 0;
        for (String number: allNumbers) {
            sum = sum + Integer.parseInt(number);
        }
        return sum;
    }

}
