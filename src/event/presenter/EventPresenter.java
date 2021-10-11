package event.presenter;

import system.console.ConsolePresenter;

import java.util.EnumMap;
import java.util.List;

/**
 * The <code>EventPresenter</code> class is responsible for displaying messages related to events and event management.
 * These messages include prompts, errors, success confirmations, and menus.
 * This responsibility implies that the class currently acts as a Presenter and a UI.
 */
public class EventPresenter extends ConsolePresenter<EventPrompts> {

    /**
     * Returns an <code>EnumMap</code> that maps each enum key of type <code>T</code> to its respective console message
     *
     * @return an <code>EnumMap</code> that maps each enum key of type <code>T</code> to its respective console message
     */
    @Override
    protected EnumMap<EventPrompts, String> initializeMessages() {
        EnumMap<EventPrompts, String> m = new EnumMap<>(EventPrompts.class);
        initializePrompts(m);
        initializeErrors(m);
        initializeSuccessMessages(m);
        initializeDisplayMessages(m);
        return m;
    }

    private void initializePrompts(EnumMap<EventPrompts, String> m) {
        m.put(EventPrompts.EVENT_TITLE_PROMPT, "Please enter the title of the event:");
        m.put(EventPrompts.EVENT_SPEAKER_PROMPT, "Please enter the username of the speaker:");
        m.put(EventPrompts.EVENT_DATE_PROMPT, "Please enter the date of the event " +
                "in the format of YYYY-MM-DD (e.g. January 1st, 2020 would be: 2020-01-01):");
        m.put(EventPrompts.EVENT_START_TIME_PROMPT, "Please enter the start time of the event in 24-hour clock " +
                "format (HH:MM):");
        m.put(EventPrompts.EVENT_ROOM_ID_PROMPT, "Please enter the room number of the event room:");
        m.put(EventPrompts.EVENT_CAPACITY_PROMPT, "Please enter the maximum number of people " +
                "able to sign up for the event:");
        m.put(EventPrompts.EVENT_VIP_PROMPT, "Please enter whether this is a VIP exclusive event (yes/no/y/n):");
        m.put(EventPrompts.SPEAKER_CAPACITY_PROMPT, "Please enter the maximum number of speakers " +
                "able to sign up for the event:");
        m.put(EventPrompts.EVENT_DURATION_PROMPT, "Please enter the duration of this event in minutes:");
        m.put(EventPrompts.EVENT_DELETE_PROMPT1, "Please enter the title of the event you wish to delete:");
        m.put(EventPrompts.EVENT_DELETE_PROMPT2, "Please enter the room of the event you wish to delete: ");
        m.put(EventPrompts.EVENT_DELETE_PROMPT3, "Please enter the start time of the event you wish to delete:");
        m.put(EventPrompts.REVIEW_PROMPT, "Please write your review of the event here: ");

    }

    private void initializeErrors(EnumMap<EventPrompts, String> m) {
        m.put(EventPrompts.EVENT_NOT_FOUND, "Sorry, there is no such event.");
        m.put(EventPrompts.EVENTS_NOT_FOUND, "Sorry, no such events were found.");
        m.put(EventPrompts.EVENT_HAS_SPEAKER, "Sorry, this event already has a speaker.");
        m.put(EventPrompts.SPEAKER_ALREADY_BOOKED, "Sorry, this speaker is already booked at the time of this event.");
        m.put(EventPrompts.NO_SUCH_SPEAKER_USERNAME, "Sorry, there is no speaker with that username.");
        m.put(EventPrompts.SPEAKER_NOT_FOR_EVENT, "Sorry, you are not a speaker for this event.");
        m.put(EventPrompts.SPEAKER_NOT_FOUND_IN_EVENT, "Sorry, this event does not have a speaker with that username.");
        m.put(EventPrompts.SPEAKER_NOT_SCHEDULED, "This speaker is not currently scheduled to speak at any events.");
        m.put(EventPrompts.EVENT_EXISTS_ERROR, "Sorry, an event with this title already exists.");
        m.put(EventPrompts.ROOM_NOT_FOUND_ERROR, "Sorry, this room does not exist in the conference.");
        m.put(EventPrompts.TIME_ERROR, "Sorry, this time is not between 9:00 a.m. to 5:00 p.m. " +
                "Please enter a time between 9:00 and 17:59 inclusive.");
        m.put(EventPrompts.INVALID_ATTENDEE_CAPACITY_ERROR, "Sorry, the attendee capacity must be greater than 0.");
        m.put(EventPrompts.INVALID_SPEAKER_CAPACITY_ERROR, "Sorry, the speaker capacity greater than or equal to 0.");
        m.put(EventPrompts.INVALID_DURATION_ERROR, "Sorry, the duration of an event must be greater than 0 minutes.");
        m.put(EventPrompts.INPUT_NOT_INT_ERROR, "Sorry, the value you entered is not an integer.");
        m.put(EventPrompts.EVENT_CAPACITY_TOO_LARGE_ERROR,
                "Sorry, the requested room cannot support that many occupants.");
        m.put(EventPrompts.ROOM_IS_BOOKED, "Sorry, this room is already booked at the given time.");
        m.put(EventPrompts.INVALID_INPUT_DATE, "Sorry, the date you entered is not valid. " +
                "Please enter a valid date in the format YYYY-MM-DD (e.g. January 1st, 2020 would be: 2020-01-01).");
        m.put(EventPrompts.INVALID_INPUT_TIME, "Sorry, the time you entered is not valid. " +
                "Please enter a valid 24-hour time in the format HH:MM: ");
        m.put(EventPrompts.EVENT_DELETE_FAIL, "Sorry, this event does not exist.");
        m.put(EventPrompts.SPEAKER_ADD_ERROR, "Sorry, this event is already at max speaker capacity.");
        m.put(EventPrompts.EVENT_VIP_ERROR, "Sorry, please enter whether this is a VIP exclusive event as yes/no/y/n:");
        m.put(EventPrompts.NOT_REGISTERED_IN_EVENT, "Sorry, you are not registered in any events.");
        m.put(EventPrompts.NO_REVIEWS_ERROR, "Sorry, there are no reviews.");

    }

    private void initializeSuccessMessages(EnumMap<EventPrompts, String> m) {
        m.put(EventPrompts.SPEAKER_SUCCESSFULLY_ASSIGNED, "The speaker has successfully been assigned to this event.");
        m.put(EventPrompts.SPEAKER_SUCCESSFULLY_REMOVED, "The speaker has successfully been removed from this event");
        m.put(EventPrompts.EVENT_SUCCESSFULLY_DELETED, "The event was successfully deleted from the conference.");
        m.put(EventPrompts.EVENT_CREATION_SUCCESS, "The event was successfully added to the conference.");
        m.put(EventPrompts.SPEAKER_ADD_SUCCESS, "The speaker was successfully added to the event");
        m.put(EventPrompts.REVIEW_SUCCESS, "Your review was successfully recorded.");

    }

    private void initializeDisplayMessages(EnumMap<EventPrompts, String> m) {
        m.put(EventPrompts.SPEAKER_SCHEDULE_DISPLAYED, "This is the schedule of events you are speaking at:");
        m.put(EventPrompts.EVENT_SCHEDULE_DISPLAYED, "This is the schedule of all events:");
        m.put(EventPrompts.EVENT_LIST_DISPLAYED, "This is the schedule of events you are signed up for:");
        m.put(EventPrompts.EVENT_VIP_SCHEDULE_DISPLAYED, "This is the schedule of VIP events.");
    }

    /**
     * Prints the list of all events to the user.
     *
     * @param schedule a list of strings each describing an event in the conference
     */
    public void displaySchedule(List<String> schedule) {
        System.out.println(String.join("\n", schedule));
    }

    /**
     * Prints a list of event titles to the user.
     *
     * @param events a list of strings each indicating an event title
     */
    public void displayEventTitles(List<String> events) {
        System.out.println("===== EVENTS =====");
        if (events.isEmpty()) {
            display(EventPrompts.EVENTS_NOT_FOUND);
        } else {
            displayContents(events);
        }
    }

    /**
     * Prints a list of reviews for a given event to the user.
     *
     * @param title   the title of the event whose reviews are to be printed
     * @param reviews the list of reviews for the event titled <code>title</code>
     */
    public void displayReviews(String title, List<String> reviews) {
        System.out.println("===== REVIEWS FOR " + title + "  =====");
        if (reviews.isEmpty()) {
            display(EventPrompts.NO_REVIEWS_ERROR);
        } else {
            displayContents(reviews);
        }
    }
}
