package startup;

import system.console.ConsolePresenter;

import java.util.EnumMap;

/**
 * The StartUpPresenter class is responsible for displaying messages that appear when the program first begins.
 * Its primary task is to provide the user with the initial menu of actions.
 */
class StartUpPresenter extends ConsolePresenter<StartUpPrompts> {

    /**
     * Returns an <code>EnumMap</code> that maps each enum key of type <code>T</code> to its respective console
     * message.
     */
    @Override
    protected EnumMap<StartUpPrompts, String> initializeMessages() {
        EnumMap<StartUpPrompts, String> m = new EnumMap<>(StartUpPrompts.class);
        m.put(StartUpPrompts.WELCOME_MESSAGE, "Welcome! Please enter a command.");
        m.put(StartUpPrompts.USERNAME_PROMPT, "Please enter your username:");
        m.put(StartUpPrompts.PASSWORD_PROMPT, "Please enter your password:");
        m.put(StartUpPrompts.ORGANIZER_CREATION, "There are no organizer accounts currently registered." + "\n" +
                "The account created here will be an organizer account.");
        m.put(StartUpPrompts.INPUT_ERROR, "Sorry, your input was invalid. Please enter '1', '2', or '3'.");
        m.put(StartUpPrompts.NO_SUCH_USERNAME_ERROR,
                "Sorry, an account with that username was not found. Please try again.");
        m.put(StartUpPrompts.WRONG_PASSWORD_ERROR,
                "Sorry, that password does not match our records. Please try again.");
        return m;
    }

    /**
     * Displays the menu of initial actions that the user can do upon program start up.
     */
    public void displayStartUpMenu() {
        System.out.println("1 - Create a new account ");
        System.out.println("2 - Login to an existing account");
        System.out.println("3 - Quit Program");
    }

    /**
     * Displays the message that a user was banned
     *
     * @param admin  Name of admin who issued ban
     * @param reason Reason for ban
     */
    public void displayBan(String admin, String reason) {
        System.out.println("Your accounts has been banned.");
        System.out.print("Admin who banned you: ");
        System.out.println(admin);
        System.out.print("Reason for ban: ");
        System.out.println(reason);
    }

    /**
     * Displays a welcome message for a user who has successfully logged in.
     *
     * @param username the username of the currently logged in user
     */
    public void displaySuccessMessage(String username) {
        System.out.println("Welcome back, " + username + "!");
    }

}
