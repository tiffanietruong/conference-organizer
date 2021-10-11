package startup;

import system.console.ConsolePresenter;

import java.util.EnumMap;

/**
 * This class presents messages to the user and provides the UI during account creation.
 */
class AccountCreationPresenter extends ConsolePresenter<AccountCreationPrompts> {

    /**
     * Returns an <code>EnumMap</code> that maps each enum key of type <code>T</code> to its respective console message
     *
     * @return an <code>EnumMap</code> that maps each enum key of type <code>T</code> to its respective console message
     */
    @Override
    protected EnumMap<AccountCreationPrompts, String> initializeMessages() {
        EnumMap<AccountCreationPrompts, String> m = new EnumMap<>(AccountCreationPrompts.class);
        m.put(AccountCreationPrompts.USERNAME_PROMPT, "Please enter a username:");
        m.put(AccountCreationPrompts.USERNAME_IN_USE,
                "That username is already in use. Please choose a different username.");
        m.put(AccountCreationPrompts.USERNAME_HAS_SPACE_CHAR, "Usernames cannot contain a space character. Please " +
                "choose a different username.");
        m.put(AccountCreationPrompts.PASSWORD_PROMPT, "Please enter a password:");
        m.put(AccountCreationPrompts.PASSWORD_NO_CAPITAL, "Your password did not contain a capital letter.");
        m.put(AccountCreationPrompts.PASSWORD_NO_LOWER, "Your password did not contain a lowercase letter.");
        m.put(AccountCreationPrompts.PASSWORD_NO_SPECIAL, "Your password did not contain a non-alphanumeric " +
                "character.");
        m.put(AccountCreationPrompts.PASSWORD_NO_NUMBER, "Your password did not contain a number.");
        m.put(AccountCreationPrompts.PASSWORD_SUCCESS, "Your password was accepted.");
        m.put(AccountCreationPrompts.USERNAME_SUCCESS, "Your username was accepted.");
        m.put(AccountCreationPrompts.PASSWORD_INVALID, "Your password was invalid. Please remember that your " +
                "password must contain at least one capital letter, one lowercase letter, one number " +
                "password must contain 8 characters and at least one " +
                "non-alphanumeric character.");
        m.put(AccountCreationPrompts.USERTYPE_PROMPT, "Please choose your user type.");
        m.put(AccountCreationPrompts.INVALID_USERTYPE, "Invalid selection. Please enter a valid choice.");
        m.put(AccountCreationPrompts.PASSWORD_NO_LENGTH, "Your password does not have at least 8 characters");
        return m;
    }

    /**
     * Displays a recommended username.
     *
     * @param username the username to recommend
     */
    public void displayRecommendedUsername(String username) {
        System.out.println("We recommend using the username " + username);
    }

}
