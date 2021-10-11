package system.console;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An abstract class that provides methods for taking user input from console.
 *
 * @param <K> the Enum type of the prompts that this class will contain a presenter for
 */
abstract public class ConsoleInputController<K extends Enum<K>> {

    private final Scanner in;
    private final ConsolePresenter<K> presenter;

    /**
     * Constructs ConsoleInputController.
     *
     * @param in        a Scanner object to be used for taking input
     * @param presenter a presenter to be used for displaying prompts
     */
    protected ConsoleInputController(Scanner in, ConsolePresenter<K> presenter) {
        this.in = in;
        this.presenter = presenter;
    }

    /**
     * Displays a prompt to the user and takes user input.
     * Each time the user gives input, its validity is checked,
     * and if it is invalid then an error message is displayed and the user must enter the input again.
     *
     * @param inputPrompt        the enum key of the prompt asking for input; if null, no prompt will be displayed
     * @param invalidInputPrompt the enum key of the prompt reporting invalid input
     * @param isValid            a function that returns true iff its argument is a valid input
     * @param convert            a function that converts a String from user input into an object of the desired type;
     *                           this function must throw an exception or return null if no valid object can be made
     *                           from the input
     * @param <T>                the desired type of the object to be parsed from user input
     * @return the object parsed from user input
     */
    protected <T> T promptInput(K inputPrompt, K invalidInputPrompt,
                                Predicate<T> isValid, Function<String, T> convert) {
        if (inputPrompt != null) {
            presenter.display(inputPrompt);
        }
        T input;
        do {
            try {
                input = convert.apply(in.nextLine());
            } catch (RuntimeException e) {
                input = null;
            }
            if (input == null || !isValid.test(input)) {
                presenter.display(invalidInputPrompt);
            }
        } while (input == null || !isValid.test(input));
        return input;
    }

    /**
     * Prompts the user for an integer subject to certain validity conditions and returns the result.
     * Repeats the prompt until a valid input is given.
     *
     * @param inputPrompt        the enum key of the presenter prompt asking for input
     * @param invalidInputPrompt the enum key of the presenter prompt reporting invalid input
     * @param isValid            a function that returns true iff its argument is a valid input
     * @return an integer parsed from user input that satisfies isValid
     */
    protected int promptInt(K inputPrompt, K invalidInputPrompt, Predicate<Integer> isValid) {
        return promptInput(inputPrompt, invalidInputPrompt, isValid, Integer::parseInt);
    }

    /**
     * Prompts the user for an integer and returns the result.
     * Does not repeat the prompt unless the input is not an integer.
     *
     * @param inputPrompt        the enum key of the presenter prompt asking for input
     * @param invalidInputPrompt the enum key of the presenter prompt reporting invalid input
     * @return an integer parsed from user input
     */
    protected int promptInt(K inputPrompt, K invalidInputPrompt) {
        return promptInt(inputPrompt, invalidInputPrompt, n -> true);
    }

    /**
     * Reads the user's input until a valid integer input is given and returns the result.
     *
     * @param invalidInputPrompt the enum key of the presenter prompt reporting invalid input
     * @param isValid            a function that returns true iff its argument is a valid input
     * @return an integer parsed from user input that satisfies isValid
     */
    protected int readInt(K invalidInputPrompt, Predicate<Integer> isValid) {
        return promptInt(null, invalidInputPrompt, isValid);
    }

    /**
     * Prompts the user for a String subject to certain validity conditions and returns the result.
     * Repeats the prompt until a valid input is given.
     *
     * @param inputPrompt        the enum key of the presenter prompt asking for input
     * @param invalidInputPrompt the enum key of the presenter prompt reporting invalid input
     * @param isValid            a function that returns true iff its argument is a valid input
     * @return a String parsed from user input that satisfies isValid
     */
    protected String promptString(K inputPrompt, K invalidInputPrompt, Predicate<String> isValid) {
        return promptInput(inputPrompt, invalidInputPrompt, isValid, Function.identity());
    }

    /**
     * Prompts the user for a String and returns the result.
     *
     * @param inputPrompt the enum key of the presenter prompt asking for input
     * @return a String parsed from user input
     */
    protected String promptString(K inputPrompt) {
        return promptString(inputPrompt, inputPrompt, s -> true);
    }

    /**
     * Prompts the user for a Y(es)/N(o) answer and returns the result.
     * Repeats the prompt until the user gives a valid input.
     *
     * @param inputPrompt        the enum key of the presenter prompt asking for input
     * @param invalidInputPrompt the enum key of the presenter prompt reporting invalid input
     * @return true if the user responded yes; false if the user responded no
     */
    protected boolean promptYesNo(K inputPrompt, K invalidInputPrompt) {
        Predicate<String> isValid = s -> s.toLowerCase(Locale.ROOT).matches("^\\s*(y(es)?)|(no?)\\s*$");
        String result = promptString(inputPrompt, invalidInputPrompt, isValid);
        return result.equalsIgnoreCase("yes") || result.equalsIgnoreCase("y");
    }

    private LocalTime parseTime(String str) {
        Pattern pattern = Pattern.compile("^\\s*(\\d\\d?):(\\d\\d)\\s*$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            int hour = Integer.parseInt(matcher.group(1));
            int minute = Integer.parseInt(matcher.group(2));
            return LocalTime.of(hour, minute);
        }
        return null;
    }

    /**
     * Prompts the user for a time subject to certain validity conditions and returns the result.
     * Repeats the prompt until a valid input is given.
     *
     * @param inputPrompt        the enum key of the presenter prompt asking for input
     * @param invalidInputPrompt the enum key of the presenter prompt reporting invalid input
     * @param isValid            a function that returns true iff its argument is a valid input
     * @return a LocalTime object parsed from user input that satisfies isValid
     */
    protected LocalTime promptTime(K inputPrompt, K invalidInputPrompt, Predicate<LocalTime> isValid) {
        return promptInput(inputPrompt, invalidInputPrompt, isValid, this::parseTime);
    }

    /**
     * Prompts the user for a time and returns the result.
     * Does not repeat the prompt unless the input is not a properly formatted time.
     *
     * @param inputPrompt        the enum key of the presenter prompt asking for input
     * @param invalidInputPrompt the enum key of the presenter prompt reporting invalid input
     * @return a LocalTime object parsed from user input
     */
    protected LocalTime promptTime(K inputPrompt, K invalidInputPrompt) {
        return promptTime(inputPrompt, invalidInputPrompt, time -> true);
    }

    private LocalDate parseDate(String str) {
        Pattern pattern = Pattern.compile("^\\s*(\\d{4})-(\\d{2})-(\\d{2})\\s*$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            int year = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(2));
            int day = Integer.parseInt(matcher.group(3));
            return LocalDate.of(year, month, day);
        }
        return null;
    }

    /**
     * Prompts the user for a date subject to certain validity conditions and returns the result.
     * Repeats the prompt until a valid input is given.
     *
     * @param inputPrompt        the enum key of the presenter prompt asking for input
     * @param invalidInputPrompt the enum key of the presenter prompt reporting invalid input
     * @param isValid            a function that returns true iff its argument is a valid input
     * @return a LocalDate object parsed from user input that satisfies isValid
     */
    protected LocalDate promptDate(K inputPrompt, K invalidInputPrompt, Predicate<LocalDate> isValid) {
        return promptInput(inputPrompt, invalidInputPrompt, isValid, this::parseDate);
    }

    /**
     * Prompts the user for a date and returns the result.
     * Does not repeat the prompt unless the input is not a properly formatted date.
     *
     * @param inputPrompt        the enum key of the presenter prompt asking for input
     * @param invalidInputPrompt the enum key of the presenter prompt reporting invalid input
     * @return a LocalDate object parsed from user input
     */
    protected LocalDate promptDate(K inputPrompt, K invalidInputPrompt) {
        return promptDate(inputPrompt, invalidInputPrompt, date -> true);
    }

}
