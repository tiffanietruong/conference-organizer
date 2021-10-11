package signup;

import system.console.ConsolePresenter;

import java.util.EnumMap;
import java.util.List;

/**
 * The SignUpPresenter class is responsible for displaying the messages related to signing up for events, that are to
 * be printed on the screen.
 */
class SignUpPresenter extends ConsolePresenter<SignUpPrompts> {

    /**
     * Returns an <code>EnumMap</code> that maps each enum key of type <code>T</code> to its respective console message
     *
     * @return an <code>EnumMap</code> that maps each enum key of type <code>T</code> to its respective console message
     */
    @Override
    protected EnumMap<SignUpPrompts, String> initializeMessages() {
        EnumMap<SignUpPrompts, String> m = new EnumMap<>(SignUpPrompts.class);
        m.put(SignUpPrompts.EVENT_SEARCH_PROMPT,
                "Enter a Start Time, Title, or Speaker to search for the events by:");
        m.put(SignUpPrompts.SIGNUP_PROMPT, "Please enter an event title to sign up for:");
        m.put(SignUpPrompts.SUCCESSFUL_SIGNUP_PROMPT,
                "You have successfully signed up for the event.");
        m.put(SignUpPrompts.CANCEL_SIGNUP_PROMPT,
                "Please enter an event title to cancel your registration in:");
        m.put(SignUpPrompts.SUCCESSFUL_CANCELLATION_PROMPT,
                "You have successfully cancelled your attendance in the event.");
        m.put(SignUpPrompts.NOT_REGISTERED, "You are not registered in this event. please try again.");
        m.put(SignUpPrompts.EVENT_FULL_ERROR, "You could not sign up for the event because the event is full.");
        m.put(SignUpPrompts.EVENT_NOT_FOUND_ERROR, "No events were found with the given information.");
        m.put(SignUpPrompts.SPEAKER_SIGN_UP_ERROR, "You cannot sign up for an event you are the speaker for!");
        m.put(SignUpPrompts.ALREADY_SIGNED_UP, "You are already signed up for this event!");
        m.put(SignUpPrompts.CHOOSE_FILTER, "Choose a filter to search for the events by: \n1 - Event Title\n" +
                "2 - Event Speaker \n3 - Event Time \n4 - Return to the main menu");
        m.put(SignUpPrompts.ENTER_TITLE_PROMPT, "Enter the Title of an event to search by: ");
        m.put(SignUpPrompts.ENTER_SPEAKER_PROMPT, "Enter the Speaker of an event to search by: ");
        m.put(SignUpPrompts.ENTER_START_TIME_PROMPT, "Enter the start time of the event (in the format HH:MM) to search by:");
        m.put(SignUpPrompts.ENTER_END_TIME_PROMPT, "Enter the end time of the event (in the format HH:MM) to search by.");
        m.put(SignUpPrompts.INCLUDE_END_TIME_YN, "Would you also like to search by end time? Y/N");
        m.put(SignUpPrompts.INVALID_INPUT, "Invalid input, please try again.");
        m.put(SignUpPrompts.INVALID_START_TIME, "Invalid start time entered. Please enter a time in the format HH:MM. ");
        m.put(SignUpPrompts.INVALID_END_TIME, "Invalid end time entered. Please enter a time in the format HH:MM. ");
        m.put(SignUpPrompts.VIP_EVENT, "This is a VIP exclusive event. If you wish to register or cancel for VIP " +
                "exclusive events, please select the VIP menu from the main menu." + "\n" +
                "(If you are not a VIP but wish to register for a VIP event, please contact an organizer)");
        m.put(SignUpPrompts.NOT_VIP_EVENT, "This is not a VIP exclusive event. To register or cancel for general " +
                "admission events, please select the Event menu from the main menu.");
        return m;
    }

    /**
     * Prints all the events in a given list.
     *
     * @param events A list of events
     */
    public void showFilteredEvents(List<String> events) {
        for (String eventString : events) {
            System.out.println(eventString);
        }
    }

}