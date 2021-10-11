package system.general;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>SystemPresenter</code> class is responsible for displaying the default menu options that any type of
 * user can access. This responsibility implies that the class currently acts as a Presenter and a UI.
 */
public abstract class SystemPresenter {
    /**
     * Prompts for Main menu related actions
     */
    protected final List<String> mainPrompts = new ArrayList<>();
    /**
     * Prompts for event related actions
     */
    protected final List<String> eventPrompts = new ArrayList<>();
    /**
     * Prompts for messaging related actions
     */
    protected final List<String> messagingPrompts = new ArrayList<>();

    /**
     * Creates a new <code>SystemPresenter</code> object.
     */
    public SystemPresenter() {
        initializeMainPrompts();
        initializeEventPrompts();
        initializeMessagingPrompts();
    }

    /**
     * Initializes the prompts corresponding to options in the main menu.
     */
    protected void initializeMainPrompts() {
        mainPrompts.add("2 - Event menu");
        mainPrompts.add("3 - Friends list menu");
        mainPrompts.add("4 - Messaging menu");
    }

    /**
     * Initializes the prompts corresponding to options in the events menu.
     */
    protected void initializeEventPrompts() {
        eventPrompts.add("1 - Return to the main menu");
        eventPrompts.add("2 - View all events scheduled in the conference");
        eventPrompts.add("3 - View reviews of a specific event");
        eventPrompts.add("4 - Filter the events by title, speaker, or start time");
        eventPrompts.add("5 - Sign up for a general admission event");
        eventPrompts.add("6 - Cancel your spot in an event");
        eventPrompts.add("7 - View your event schedule");
    }


    /**
     * Initializes the prompts corresponding to options in the messaging menu.
     */
    protected void initializeMessagingPrompts() {
        messagingPrompts.add("1 - Return to the main menu");
        messagingPrompts.add("2 - View your inbox");
        messagingPrompts.add("3 - View your archives");
        messagingPrompts.add("4 - Message a friend");
        messagingPrompts.add("5 - Go to the requests menu");
    }

    /**
     * Prints message to get user input for an action.
     */
    public void promptForAction() {
        System.out.println("\nPlease select an action.");
    }

    /**
     * Displays message if the user input is invalid.
     */
    public void displayInvalidInputError() {
        System.out.println("Sorry, the input you entered was not valid. Please try again.");
    }

    /**
     * Displays all menus that the user can choose actions related to.
     */
    public void displayMenus() {
        System.out.println("===== SYSTEM =====");
        System.out.println("1 - Log out");
        System.out.println("===== ACTION MENUS =====");
        for (String prompt : mainPrompts) {
            System.out.println(prompt);
        }
    }

    /**
     * Displays the events menu to the user.
     */
    public void displayEventMenu() {
        System.out.println("===== EVENT ACTIONS =====");
        for (String prompt : eventPrompts) {
            System.out.println(prompt);
        }
    }

    /**
     * Displays the messaging menu to the user.
     */
    public void displayMessagingMenu() {
        System.out.println("===== MESSAGING ACTIONS =====");
        for (String prompt : messagingPrompts) {
            System.out.println(prompt);
        }
    }
}
