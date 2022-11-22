package com.sosamr.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    private final static String NEW_LINE = "\n";
    private final static int EXCLUDE_VALUES_GREATER_THAN = 1000;

    /**
     * @param fullInput - comma (default) or custom or new line separated list of valid integer numbers.
     *              It can contain a custom delimiter:
     *              "//[delimiter]\n[numbers...]” for example “//;\n1;2” should return 3 where the default delimiter is ‘;’
     *              Numbers greater than 1000 excluded from calculation.
     *              Assumptions: Must be not null, all the values are valid integer numbers.
     * @return the sum of the numbers or 0 for an empty string
     */
    public long add(final String fullInput) {
        if (fullInput.isEmpty()) {
            return 0;
        }

        final InputStringDelimiters delimiters = new InputStringDelimiters(fullInput);
        final String inputNumbers = getInputNumbers(fullInput, delimiters);

        checkForNewLineNextToDelimiter(inputNumbers, delimiters);

        List<String> allStringNumbers = getParsedNumbers(inputNumbers, delimiters);

        List<Integer> allValidNumbers = allStringNumbers.stream()
                .map(Integer::parseInt)
                .filter(value -> value <= EXCLUDE_VALUES_GREATER_THAN)
                .collect(Collectors.toList());

        validateNoNegatives(allValidNumbers);

        return allValidNumbers.stream().reduce(0, Integer::sum);
    }

    private List<String> getParsedNumbers(final String inputNumbers, final InputStringDelimiters delimiters) {

        final String[] lines = inputNumbers.isEmpty() ? new String[0]: inputNumbers.split(NEW_LINE);
        List<String> baseList = new ArrayList<>(Arrays.asList(lines));

        for (String delimiter : delimiters.getDelimiters()) {
            List<String> processedForCurrentDelimiterList = baseList.stream()
                    .map(text -> text.split(Pattern.quote(delimiter)))
                    .flatMap(Arrays::stream)
                    .collect(Collectors.toList());
            baseList.clear();
            baseList.addAll(processedForCurrentDelimiterList);
        }
        return baseList;
    }

    private String getInputNumbers(final String fullInput, final InputStringDelimiters delimiters) {
        if (delimiters.isCustomDelimiter()) {
            return fullInput.substring(fullInput.indexOf(NEW_LINE) + 1);
        }
            return fullInput;
    }

    private void checkForNewLineNextToDelimiter(final String inputNumbers, final InputStringDelimiters inputDelimiters) throws IllegalArgumentException {
        inputDelimiters.getDelimiters().stream()
            .filter(delimiter -> inputNumbers.contains(delimiter + NEW_LINE) || inputNumbers.contains(NEW_LINE + delimiter))
            .findAny()
            .ifPresent((delimiter) -> {
                throw new IllegalArgumentException(String.format("Delimiter %s is next to new line char which is not allowed.", delimiter));
            });
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

    static class InputStringDelimiters {
        private final static String DEFAULT_DELIMITER = ",";

        private final static Pattern delimiterLinePattern = Pattern.compile("^//(.*?)\n");
        private final static Pattern delimiterPattern = Pattern.compile("\\[(.*?)\\]");

        private final List<String> delimiters = new ArrayList<>();

        private boolean customDelimiter = false;

        InputStringDelimiters(final String fullInput) {
            buildDelimiters(fullInput);
        }

        public List<String> getDelimiters() {
            return this.delimiters;
        }

        public boolean isCustomDelimiter() {
            return customDelimiter;
        }
        private void buildDelimiters(final String input) {
            delimiters.clear();
            Matcher matcherLine = delimiterLinePattern.matcher(input);
            if (matcherLine.find()) {
                customDelimiter = true;
                String delimiterLine = matcherLine.group(1);
                Matcher delimiterMatcher = delimiterPattern.matcher(delimiterLine);
                boolean found = false;
                while (delimiterMatcher.find()) {
                    found = true;
                    delimiters.add(delimiterMatcher.group(1));
                }
                //when ! found the delimiter is just a character not inside the []
                if (!found) {
                    delimiters.add(delimiterLine);
                }
            } else {
                //when the optional delimiters line is not present then default is used
                delimiters.add(DEFAULT_DELIMITER);
                customDelimiter = false;
            }
        }
    }
}
