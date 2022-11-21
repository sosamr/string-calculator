package com.sosamr.calculator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringCalculator {

    private final static String DELIMITER = ",";
    private final static String NEW_LINE_DELIMITER = "\n";

    /**
     *
     * @param numbers - comma or new line separated list of valid integer numbers.
     *                Assumptions: Must be not null, all the values are valid integer numbers.
     * @return the sum of the numbers or 0 for an empty string
     */
    public long add(String numbers) {
        if(numbers.isEmpty()) {
            return 0;
        }

        checkForConsecutiveDelimiters(numbers);

        List<String> allNumbers = parseNumbers(numbers);

        return allNumbers.stream()
                .map(Integer::parseInt)
                .reduce(0, Integer::sum);

    }

    private void checkForConsecutiveDelimiters(String input) throws IllegalArgumentException {
        if (input.contains(DELIMITER + NEW_LINE_DELIMITER) || input.contains(NEW_LINE_DELIMITER + DELIMITER)) {
            throw new IllegalArgumentException("Two consecutive delimiters is not allowed");
        }
    }

    private List<String> parseNumbers(String input) {
        final String[] lines = input.split(NEW_LINE_DELIMITER);

        return Stream.of(lines)
                .map(line -> line.split(DELIMITER))
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }
}
