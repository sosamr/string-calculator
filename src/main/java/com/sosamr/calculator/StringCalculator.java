package com.sosamr.calculator;

public class StringCalculator {

    private final static String DELIMITER = ",";

    /**
     *
     * @param numbers - comma separated list of valid integer numbers.
     *                Assumptions: Must be not null, all the values are valid integer numbers.
     * @return the sum of the numbers or 0 for an empty string
     */
    public long add(String numbers) {
        if(numbers.isEmpty()) {
            return 0;
        }

        String[] allNumbers = numbers.split(DELIMITER);

        int sum = 0;
        for (String number: allNumbers) {
            sum = sum + Integer.parseInt(number);
        }
        return sum;
    }

}
