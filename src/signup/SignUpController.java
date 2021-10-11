package signup;

import event.manager.EventManagerFacade;
import event.manager.EventNotFoundException;
import system.console.ConsoleInputController;
import user.UserType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * The SignUpController class is responsible for a user's registration and cancelling registration for
 * various events. This class also allows a user to be able to filter an Event by it's Title, Speaker, or Start Time
 */
public class SignUpController extends ConsoleInputController<SignUpPrompts> {
    private final EventManagerFacade eventManager;
    private final SignUpPresenter signUpPresenter;

    /**
     * Creates a new <code>SignUpController</code> object.
     *
     * @param in           a Scanner used by this controller to get user input
     * @param eventManager an <code>EventManager</code> that is used by this class to interact with events
     */
    public SignUpController(Scanner in, EventManagerFacade eventManager) {
        super(in, new SignUpPresenter());
        this.eventManager = eventManager;
        this.signUpPresenter = new SignUpPresenter();
    }

    /**
     * Searches for <code>Events</code> by either the Title, Speaker, or StartTime of an <code>event</code>.
     * Prompts the user to choose a specified parameter to search by, and displays an error message if the input
     * is invalid.
     */
    public void searchForEvents() {
        String eventParameter;
        boolean quit = false;
        while (!quit) {
            eventParameter = promptString(SignUpPrompts.CHOOSE_FILTER);
            switch (eventParameter) {
                case "1":
                    searchByTitleOrSpeaker(SignUpPrompts.ENTER_TITLE_PROMPT);
                    quit = true;
                    break;
                case "2":
                    searchByTitleOrSpeaker(SignUpPrompts.ENTER_SPEAKER_PROMPT);
                    quit = true;
                    break;
                case "3":
                    searchByTime();
                    quit = true;
                    break;
                case "4":
                    quit = true;
                    break;
                default:
                    signUpPresenter.display(SignUpPrompts.INVALID_INPUT);
            }
        }
    }

    /**
     * Displays a list of <code>events</code> that contain the given title or speaker, or gives an error message if
     * no <code>events</code> have the specified title or speaker.
     *
     * @param message The message prompt to be displayed before taking in an input
     */
    private void searchByTitleOrSpeaker(SignUpPrompts message) {
        List<String> tempEventList = new ArrayList<>();
        String eventParameter = promptString(message);
        for (String tempEventTitle : eventManager.getEventList()) {
            if (eventParameter.equals(tempEventTitle) ||
                    eventManager.getSpeakers(tempEventTitle).contains(eventParameter)) {
                tempEventList.add(tempEventTitle);
            }
        }
        if (tempEventList.isEmpty()) {
            signUpPresenter.display(SignUpPrompts.EVENT_NOT_FOUND_ERROR);
        } else {
            signUpPresenter.showFilteredEvents(eventManager.getStringRepresentations(tempEventList));
        }
    }

    /**
     * Displays a list of <code>Events</code>that contain the specified start time (and optionally end time),
     * or an error message if no <code>Events</code> have the specified start time and/or end time.
     * If the input is not an <code>int</code>, repeats until a valid input is given.
     */
    private void searchByTime() {
        LocalTime startTime;
        LocalTime endTime = null;
        List<String> tempEventList = new ArrayList<>();
        startTime = promptTime(SignUpPrompts.ENTER_START_TIME_PROMPT, SignUpPrompts.INVALID_START_TIME);
        if (promptYesNo(SignUpPrompts.INCLUDE_END_TIME_YN, SignUpPrompts.INVALID_INPUT)) {
            endTime = promptTime(SignUpPrompts.ENTER_END_TIME_PROMPT, SignUpPrompts.INVALID_END_TIME);
        }
        for (String tempEventTitle : eventManager.getEventList()) {
            LocalTime otherStartTime = eventManager.getStartTime(tempEventTitle).toLocalTime();
            LocalTime otherEndTime = otherStartTime.plusMinutes(eventManager.getDuration(tempEventTitle));
            if (startTime.equals(otherStartTime) &&
                    (endTime == null || endTime.equals(otherEndTime))) {
                tempEventList.add(tempEventTitle);
            }
        }
        if (tempEventList.isEmpty()) {
            signUpPresenter.display(SignUpPrompts.EVENT_NOT_FOUND_ERROR);
        } else {
            signUpPresenter.showFilteredEvents(eventManager.getStringRepresentations(tempEventList));
        }
    }


    /**
     * Processes a user's request to sign up for an event.
     *
     * @param username The username of the currently logged in user
     */
    public void signUpForEvent(String username, UserType user) {
        String title = promptString(SignUpPrompts.SIGNUP_PROMPT);
        try {
            if (eventManager.isVIP(title) && user != UserType.VIP) {
                signUpPresenter.display(SignUpPrompts.VIP_EVENT);
                return;
            } else if (!eventManager.isVIP(title) && user == UserType.VIP) {
                signUpPresenter.display(SignUpPrompts.NOT_VIP_EVENT);
                return;
            } else if (eventManager.getSignedUpEvents(username).contains(title)) {
                signUpPresenter.display(SignUpPrompts.ALREADY_SIGNED_UP);
                return;
            } else if (eventManager.getSpeakers(title).contains(username)) {
                signUpPresenter.display(SignUpPrompts.SPEAKER_SIGN_UP_ERROR);
                return;
            }
            boolean successfulSignup = eventManager.addAttendee(title, username);
            if (successfulSignup) {
                signUpPresenter.display(SignUpPrompts.SUCCESSFUL_SIGNUP_PROMPT);
            } else if (eventManager.getAttendees(title).size() == eventManager.getCapacity(title)) {
                signUpPresenter.display(SignUpPrompts.EVENT_FULL_ERROR);
            }
        } catch (EventNotFoundException e) {
            signUpPresenter.display(SignUpPrompts.EVENT_NOT_FOUND_ERROR);
        }
    }

    /**
     * Processes a user's request to cancel their attendance in an event.
     *
     * @param username The username of the currently logged in user
     */
    public void cancelSpotInEvent(String username, UserType user) {
        String title = promptString(SignUpPrompts.CANCEL_SIGNUP_PROMPT);
        try {
            if (eventManager.isVIP(title) && user != UserType.VIP) {
                signUpPresenter.display(SignUpPrompts.VIP_EVENT);
                return;
            } else if (!eventManager.isVIP(title) && user == UserType.VIP) {
                signUpPresenter.display(SignUpPrompts.NOT_VIP_EVENT);
                return;
            }
            boolean successfulCancellation = eventManager.removeAttendee(title, username);
            if (successfulCancellation) {
                signUpPresenter.display(SignUpPrompts.SUCCESSFUL_CANCELLATION_PROMPT);
            } else if (!eventManager.getAttendees(title).contains(username)) {
                signUpPresenter.display(SignUpPrompts.NOT_REGISTERED);
            }
        } catch (EventNotFoundException e) {
            signUpPresenter.display(SignUpPrompts.EVENT_NOT_FOUND_ERROR);
        }
    }
}