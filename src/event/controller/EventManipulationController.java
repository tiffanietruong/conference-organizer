package event.controller;

import event.manager.EventManagerFacade;
import event.manager.EventNotFoundException;
import event.manager.EventParameterObject;
import event.presenter.EventPresenter;
import event.presenter.EventPrompts;
import room.RoomManager;
import system.console.ConsoleInputController;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * The <code>EventManipulatorController</code> class is responsible for changing information about events in the
 * conference. This responsibility includes adding and deleting events, changing the users associated with events
 * through sign up or scheduling, and the reviews of various events.
 */
public class EventManipulationController extends ConsoleInputController<EventPrompts> {

    private final EventManagerFacade eventManager;
    private final EventPresenter eventPresenter;
    private final EventValidationController validator;

    /**
     * Constructs a <code>EventManipulationController</code> to change event information.
     *
     * @param in             the instance of the Scanner currently looking at the Console for input
     * @param eventManager   the manager responsible for event data during program execution
     * @param roomManager    the manager responsible for room data during program execution
     * @param eventPresenter the presenter responsible for displaying messages when interacting with event data
     */
    public EventManipulationController(Scanner in, EventManagerFacade eventManager, RoomManager roomManager,
                                       EventPresenter eventPresenter) {
        super(in, eventPresenter);
        this.eventManager = eventManager;
        this.eventPresenter = eventPresenter;
        this.validator = new EventValidationController(in, eventManager, roomManager, eventPresenter);
    }

    /**
     * Returns true iff the event with the given title exists in the conference.
     *
     * @param title title of the event
     * @return true if the event exists, false otherwise
     */
    public boolean eventExists(String title) {
        return eventManager.eventExists(title);
    }

    /**
     * Processes an organizer's request to add a new event to the conference.
     * This method accepts user input describing the new event, checks if all the information is valid,
     * and calls for the creation of the new event if so.
     */
    public void addNewEvent() {
        Optional<EventParameterObject> parameters = validator.validate();
        parameters.ifPresent(this::processEventAddition);
    }

    /**
     * Gets a title, start time and roomID for an event and deletes it from the conference if it exists
     */
    public void deleteEvent() {
        String title = promptString(EventPrompts.EVENT_DELETE_PROMPT1);
        boolean deleted = eventManager.deleteEvent(title);
        if (deleted) {
            eventPresenter.display(EventPrompts.EVENT_SUCCESSFULLY_DELETED);
        } else {
            eventPresenter.display(EventPrompts.EVENT_DELETE_FAIL);
        }
    }

    private void processEventAddition(EventParameterObject eventParameterObject) {
        eventManager.createEvent(eventParameterObject);
        eventPresenter.display(EventPrompts.EVENT_CREATION_SUCCESS);
    }

    //<editor-fold desc="Setting Speakers">

    /**
     * Schedules the speaker <code>speaker</code> to an event entered by the user, if possible. Displays an appropriate
     * error message if this operation cannot be completed, which is the case if
     * <ul>
     *     <li>The event that the user has entered does not exist, or</li>
     *     <li>The event that the user has entered already has a speaker, or</li>
     *     <li>The speaker with username <code>speaker</code> is already booked at the time of the event.</li>
     * </ul>
     *
     * @param speaker   the speaker's username
     * @param isSpeaker <code>true</code> if and only if <code>speaker</code> is the username of a speaker
     */
    public void scheduleSpeakerToEvent(String speaker, boolean isSpeaker) {
        if (!isSpeaker) {
            eventPresenter.display(EventPrompts.NO_SUCH_SPEAKER_USERNAME);
            return;
        }
        String eventTitle = promptString(EventPrompts.EVENT_TITLE_PROMPT);
        EventPrompts result = validateScheduling(speaker, eventTitle);
        if (result == EventPrompts.SPEAKER_SUCCESSFULLY_ASSIGNED) {
            boolean speakerAdded = setSpeaker(eventTitle, speaker);
            if (speakerAdded) {
                result = EventPrompts.SPEAKER_ADD_SUCCESS;
            } else {
                result = EventPrompts.SPEAKER_ADD_ERROR;
            }
        }
        eventPresenter.display(result);
    }

    /**
     * Adds the speaker for the given event.
     *
     * @param eventTitle  the title of the event
     * @param speakerName the username of the speaker
     */
    public boolean setSpeaker(String eventTitle, String speakerName) {
        return eventManager.addSpeaker(eventTitle, speakerName);
    }

    private EventPrompts validateScheduling(String speaker, String eventTitle) {
        if (!eventExists(eventTitle)) {
            return EventPrompts.EVENT_NOT_FOUND;
        } else if (speakerIsBookedDuringEvent(speaker, eventTitle)) {
            return EventPrompts.SPEAKER_ALREADY_BOOKED;
        }
        return EventPrompts.SPEAKER_SUCCESSFULLY_ASSIGNED;
    }
    //</editor-fold>

    //<editor-fold desc="Removing Speakers">

    /**
     * Removes a <code>speaker</code> from the event.
     *
     * @param speaker   speaker we want to remove
     * @param isSpeaker boolean to check whether the speaker is a speaker at the event
     */
    public void removeSpeakerFromEvent(String speaker, boolean isSpeaker) {
        if (!isSpeaker) {
            eventPresenter.display(EventPrompts.NO_SUCH_SPEAKER_USERNAME);
            return;
        } else if (eventManager.getEventsBySpeaker(speaker).isEmpty()) {
            eventPresenter.display(EventPrompts.SPEAKER_NOT_SCHEDULED);
            return;
        }
        try {
            eventPresenter.displayEventTitles(eventManager.getEventsBySpeaker(speaker));
            EventPrompts result = EventPrompts.SPEAKER_NOT_FOUND_IN_EVENT;
            String eventTitle = promptString(EventPrompts.EVENT_TITLE_PROMPT);
            if (eventManager.removeSpeaker(eventTitle, speaker)) {
                result = EventPrompts.SPEAKER_SUCCESSFULLY_REMOVED;
            }
            eventPresenter.display(result);
        } catch (EventNotFoundException e) {
            eventPresenter.display(EventPrompts.EVENT_NOT_FOUND);
        }

    }

    /**
     * Removes the given user from all VIP events that they are signed up for.
     *
     * @param username the username of the user
     */
    public void removeUserFromAllVIPEvents(String username) {
        eventManager.removeUserFromVIPEvents(username);
    }

    /**
     * Removes the given speaker from all events they are scheduled to speak at.
     *
     * @param username the username of the speaker
     */
    public void removeSpeakerFromAllEvents(String username) {
        eventManager.removeSpeakerFromEvents(username);
    }
    //</editor-fold>

    /**
     * Removes the given user from all the events they are signed up for.
     *
     * @param username the username of the user
     */
    public void removeUserFromAllEvents(String username) {
        eventManager.removeUserFromEvents(username);
    }

    /**
     * Returns true iff the given speaker is already booked for the start time of the given event.
     *
     * @param speakerUsername the username of the speaker
     * @param eventTitle      the title of the event
     * @return true if the speaker is booked at the start time of the event, false otherwise
     */
    public boolean speakerIsBookedDuringEvent(String speakerUsername, String eventTitle) {
        return eventManager.speakerIsBooked(speakerUsername, eventManager.getStartTime(eventTitle),
                eventManager.getDuration(eventTitle));
    }

    /**
     * Processes the user's request to write a review for an event they are signed up for.
     * This method accepts input for the event and the review being written before then saving the review.
     *
     * @param curUser the username of user who is writing the review
     */
    public void writeReview(String curUser) {
        List<String> signedUpEvents = eventManager.getSignedUpEvents(curUser);
        if (signedUpEvents.isEmpty()) {
            eventPresenter.display(EventPrompts.NOT_REGISTERED_IN_EVENT);
            return;
        }
        eventPresenter.displayEventTitles(signedUpEvents);
        String title = promptString(EventPrompts.EVENT_TITLE_PROMPT);
        if (signedUpEvents.contains(title)) {
            String review = promptString(EventPrompts.REVIEW_PROMPT);
            eventManager.addReview(title, review);
            eventPresenter.display(EventPrompts.REVIEW_SUCCESS);
        } else {
            eventPresenter.display(EventPrompts.EVENT_NOT_FOUND);
        }
    }
}
