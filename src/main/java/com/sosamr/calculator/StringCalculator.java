package com.sosamr.calculator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringCalculator {

    private final static String DEFAULT_DELIMITER = ",";
    private final static String NEW_LINE = "\n";
    private final static String DELIMITER_INDICATOR = "//";
    private final static int EXCLUDE_VALUES_GREATER_THAN = 1000;

    /**
     * @param input - comma (default) or custom or new line separated list of valid integer numbers.
     *              It can contain a custom delimiter:
     *              "//[delimiter]\n[numbers...]” for example “//;\n1;2” should return 3 where the default delimiter is ‘;’
     *              Numbers greater than 1000 excluded from calculation.
     *              Assumptions: Must be not null, all the values are valid integer numbers.
     * @return the sum of the numbers or 0 for an empty string
     */
    public long add(final String input) {
        if (input.isEmpty()) {
            return 0;
        }

        List<String> allStringNumbers = getParsedNumbers(input);

        List<Integer> allValidNumbers = allStringNumbers.stream()
                .map(Integer::parseInt)
                .filter(value -> value <= EXCLUDE_VALUES_GREATER_THAN)
                .collect(Collectors.toList());

        validateNoNegatives(allValidNumbers);

        return allValidNumbers.stream().reduce(0, Integer::sum);
    }

    public List<String> getParsedNumbers(final String input) {
        final Optional<String> customDelimiter = Optional.ofNullable(determineCustomDelimiter(input));
        final String inputNumbers;
        if (customDelimiter.isPresent()) {
            inputNumbers = input.substring(input.indexOf("\n") + 1);
        } else {
            inputNumbers = input;
        }
        checkForConsecutiveDelimiters(inputNumbers, customDelimiter.orElse(DEFAULT_DELIMITER));

        final String[] lines = inputNumbers.isEmpty() ? new String[0]: inputNumbers.split(NEW_LINE);

        return Stream.of(lines)
                .map(line -> line.split(customDelimiter.orElse(DEFAULT_DELIMITER)))
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }

    private String determineCustomDelimiter(final String input) {
        if (input.startsWith(DELIMITER_INDICATOR)) {
            int delimiterEndPos = input.indexOf(NEW_LINE);
            if (delimiterEndPos < 0) {
                throw new IllegalArgumentException("Customer delimiter must end with end of line char.");
            }
            return input.substring(2, delimiterEndPos);
        }
        return null;
    }

    private void checkForConsecutiveDelimiters(final String input, final String delimiter) throws IllegalArgumentException {
        if (input.contains(delimiter + NEW_LINE) || input.contains(NEW_LINE + delimiter)) {
            throw new IllegalArgumentException("Two consecutive delimiters is not allowed");
        }
    }

    private void validateNoNegatives(final List<Integer> numbers) throws IllegalArgumentException {
        String negatives = numbers.stream()
                .filter(number -> number < 0)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException("Negative numbers not allowed: " + negatives);
        }
    }

}
