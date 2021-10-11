package system.attendee;

import system.general.SystemPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>AttendeeSystemPresenter</code> class is responsible for displaying menu options specific to an Attendee.
 * This responsibility implies that the class currently acts as a Presenter and a UI.
 */
public class AttendeeSystemPresenter extends SystemPresenter {
    /**
     * Prompts for Attendee related actions
     */
    protected final List<String> attendeePrompts = new ArrayList<>();

    /**
     * Constructs a new <code>AttendeeSystemPresenter</code> object.
     */
    public AttendeeSystemPresenter() {
        initializeAttendeePrompts();
    }

    /**
     * Initializes the prompts corresponding to options in the main menu.
     */
    @Override
    protected void initializeMainPrompts() {
        super.initializeMainPrompts();
        mainPrompts.add("5 - Attendee menu");
    }

    /**
     * Initializes the prompts corresponding to options in the specialized attendee menu.
     */
    protected void initializeAttendeePrompts() {
        attendeePrompts.add("1 - Return to the main menu");
        attendeePrompts.add("2 - Write a review for an event");
    }

    /**
     * Displays the attendee menu to the user.
     */
    public void displayAttendeeMenu() {
        System.out.println("===== ATTENDEE ACTIONS =====");
        for (String prompt : attendeePrompts) {
            System.out.println(prompt);
        }
    }
}
