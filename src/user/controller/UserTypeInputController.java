package user.controller;

import system.console.ConsoleInputController;
import user.UserType;
import user.UserTypeConsolePresenter;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A subclass of <code>ConsoleInputController</code> specialized for displaying and taking user types as input.
 *
 * @param <K> the enum containing the set of possible messages that this presenter can print
 */
public class UserTypeInputController<K extends Enum<K>> extends ConsoleInputController<K> {
    private final UserTypeConsolePresenter<K> presenter;
    private final Map<Integer, UserType> numToUserType = new HashMap<>();

    /**
     * Constructs ConsoleInputController.
     *
     * @param in        a Scanner object to be used for taking input
     * @param presenter a presenter to be used for displaying prompts
     */
    protected UserTypeInputController(Scanner in, UserTypeConsolePresenter<K> presenter) {
        super(in, presenter);
        initializeUserTypes();
        this.presenter = presenter;
    }

    private void initializeUserTypes() {
        numToUserType.put(1, UserType.ATTENDEE);
        numToUserType.put(2, UserType.ORGANIZER);
        numToUserType.put(3, UserType.SPEAKER);
        numToUserType.put(4, UserType.VIP);
        numToUserType.put(5, UserType.ADMIN);
    }

    /**
     * Prompts the user for a user type and returns the result.
     * Repeats the prompt until a valid input is given.
     *
     * @param inputPrompt        the enum key of the presenter prompt asking for input
     * @param invalidInputPrompt the enum key of the presenter prompt reporting invalid input
     * @return an <code>UserType</code> parsed from user input
     */
    public UserType promptUserType(K inputPrompt, K invalidInputPrompt) {
        presenter.displayUserTypes();
        int selectedNum = promptInt(inputPrompt, invalidInputPrompt, n -> (n >= 1 && n <= 5));
        return numToUserType.get(selectedNum);
    }
}
