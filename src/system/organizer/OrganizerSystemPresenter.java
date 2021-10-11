package system.organizer;

import system.general.SystemPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>OrganizerSystemPresenter</code> class is responsible for displaying menu options specific to an Organizer.
 * This responsibility implies that the class currently acts as a Presenter and a UI.
 */
public class OrganizerSystemPresenter extends SystemPresenter {
    /**
     * Prompts for Conference Organizing related actions
     */
    protected final List<String> conferenceOrganizingPrompts = new ArrayList<>();
    /**
     * Prompts for Account Creation related actions
     */
    protected final List<String> accountPrompts = new ArrayList<>();

    /**
     * Constructs a new <code>OrganizerSystemPresenter</code> object.
     */
    public OrganizerSystemPresenter() {
        initializeConferenceOrganizingPrompts();
        initializeAccountPrompts();
    }

    /**
     * Initializes the prompts corresponding to options in the main menu.
     */
    @Override
    protected void initializeMainPrompts() {
        super.initializeMainPrompts();
        mainPrompts.add("5 - View information menu");
        mainPrompts.add("6 - Organize conference menu");
        mainPrompts.add("7 - Create Accounts menu");
    }

    /**
     * Initializes the prompts corresponding to options in the messaging menu.
     */
    @Override
    protected void initializeMessagingPrompts() {
        super.initializeMessagingPrompts();
        messagingPrompts.add("6 - Message all attendees");
        messagingPrompts.add("7 - Message all speakers");
        messagingPrompts.add("8 - Message a specific attendee");
        messagingPrompts.add("9 - Message a specific speaker");
    }

    /**
     * Initializes the prompts corresponding to options in the conference organization menu.
     */
    protected void initializeConferenceOrganizingPrompts() {
        conferenceOrganizingPrompts.add("1 - Return to the main menu");
        conferenceOrganizingPrompts.add("2 - Add an event to the conference");
        conferenceOrganizingPrompts.add("3 - Add a room to the system");
        conferenceOrganizingPrompts.add("4 - Add a speaker to an event");
        conferenceOrganizingPrompts.add("5 - Delete an event from the conference");
        conferenceOrganizingPrompts.add("6 - Delete a room from the system");
        conferenceOrganizingPrompts.add("7 - Delete a speaker from an event");
    }

    /**
     * Initializes the prompts corresponding to options in the account creation menu.
     */
    protected void initializeAccountPrompts() {
        accountPrompts.add("1 - Return to main menu");
        accountPrompts.add("2 - Create attendee account");
        accountPrompts.add("3 - Create speaker account");
        accountPrompts.add("4 - Create organizer account");
        accountPrompts.add("5 - Create VIP account");
        accountPrompts.add("6 - Create Admin account");
    }

    /**
     * Displays the conference organization menu to the user.
     */
    public void displayConferenceOrganizingMenu() {
        System.out.println("===== ORGANIZE THE CONFERENCE =====");
        for (String prompt : conferenceOrganizingPrompts) {
            System.out.println(prompt);
        }
    }

    /**
     * Displays the account creation menu to the user.
     */
    public void displayAccountMenu() {
        System.out.println("===== CREATE ACCOUNTS =====");
        for (String prompt : accountPrompts) {
            System.out.println(prompt);
        }
    }
}