package event.controller;

import event.manager.EventManagerFacade;
import event.presenter.EventPresenter;
import event.presenter.EventPrompts;
import room.RoomManager;
import system.console.ConsoleInputController;

import java.util.List;
import java.util.Scanner;

/**
 * The <code>EventController</code> class is responsible for processing requests involving event data.
 * These requests include viewing, adding, and signing up for events. Requests to find information about specific
 * events are also managed by this class.
 */
public class EventControllerFacade extends ConsoleInputController<EventPrompts> {

    private final EventManipulationController eventManipulationController;
    private final EventInformationController eventInformationController;

    /**
     * Constructs a controller to process the current user's requests involving events in the conference.
     *
     * @param eventManager the manager responsible for event data during program execution
     * @param roomManager  the manager responsible for room information during program execution
     * @param in           the instance of Scanner currently taking user input from the Console
     */
    public EventControllerFacade(EventManagerFacade eventManager, RoomManager roomManager, Scanner in) {
        super(in, new EventPresenter());
        EventPresenter eventPresenter = new EventPresenter();
        eventManipulationController = new EventManipulationController(in, eventManager, roomManager, eventPresenter);
        eventInformationController = new EventInformationController(in, eventManager, eventPresenter);
    }

    //<editor-fold desc="Manipulating Existing Events">

    /**
     * Removes user from all events
     *
     * @param username the username of the user
     */
    public void removeUserFromAllEvents(String username) {
        eventManipulationController.removeUserFromAllEvents(username);
    }

    /**
     * Removes user from all VIP events
     *
     * @param username the username of the user
     */
    public void removeUserFromAllVIPEvents(String username) {
        eventManipulationController.removeUserFromAllVIPEvents(username);
    }

    /**
     * Removes speaker from all events
     *
     * @param username the username of the speaker
     */
    public void removeSpeakerFromAllEvents(String username) {
        eventManipulationController.removeSpeakerFromAllEvents(username);
    }

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
        eventManipulationController.scheduleSpeakerToEvent(speaker, isSpeaker);
    }

    /**
     * Removes the speaker from a certain event.
     *
     * @param speaker   speaker that we want to remove
     * @param isSpeaker boolean to check whether the speaker is a speaker at the chosen event
     */
    public void removeSpeakerFromEvent(String speaker, boolean isSpeaker) {
        eventManipulationController.removeSpeakerFromEvent(speaker, isSpeaker);
    }

    /**
     * Uses EventManipulationController to get user's text string review and add it to the list of reviews for an event.
     *
     * @param curUser username of user who is writing the review
     */
    public void writeReview(String curUser) {
        eventManipulationController.writeReview(curUser);
    }
    //</editor-fold>

    //<editor-fold desc="Managing (Adding/Removing) Events">

    /**
     * Processes an organizer's request to add a new event to the conference.
     * This method accepts user input describing the new event, checks if all the information is valid,
     * and calls for the creation of the new event if so.
     */
    public void addNewEvent() {
        eventManipulationController.addNewEvent();
    }

    /**
     * Gets a title, start time and roomID for an event and deletes it from the conference if it exists
     */
    public void deleteEvent() {
        eventManipulationController.deleteEvent();
    }
    //</editor-fold>

    //<editor-fold desc="Displaying Information About Events">

    /**
     * Displays the list of all events scheduled in the conference.
     */
    public void displayCompleteSchedule() {
        eventInformationController.displayCompleteSchedule();
    }

    /**
     * Displays the list of vip events scheduled in the conference.
     */
    public void displayVipSchedule() {
        eventInformationController.displayVipSchedule();
    }

    /**
     * Displays the list of events that given user is signed up for.
     *
     * @param username name of the user
     */
    public void displayUserSchedule(String username) {
        eventInformationController.displayUserSchedule(username);
    }

    /**
     * Displays the list of events that the given speaker is scheduled to speak at.
     *
     * @param speakerName name of the speaker
     */
    public void displaySpeakerEvents(String speakerName) {
        eventInformationController.displaySpeakerEvents(speakerName);
    }

    /**
     * Displays all the reviews for a certain event.
     */
    public void viewReviews() {
        eventInformationController.viewReviews();
    }
    //</editor-fold>

    //<editor-fold desc="Getting Information About Events">

    /**
     * Gets a list of usernames corresponding to attendees signed up for at least one event that the given
     * speaker is speaking at.
     *
     * @param speakerName username of the speaker for the event
     * @return list of attendees for the event based on the speaker
     */
    public List<String> getAttendeesForEventsBySpeaker(String speakerName) {
        return eventInformationController.getAttendeesForEventsBySpeaker(speakerName);
    }

    /**
     * Gets a list of usernames corresponding to attendees signed up for a given event.
     *
     * @param eventTitle the title of the event whose attendees are returned
     * @return a list of usernames corresponding to attendees signed up for the event <code>eventTitle</code>
     */
    public List<String> getAttendeesForEvent(String eventTitle) {
        return eventInformationController.getAttendeesForEvent(eventTitle);
    }

    /**
     * Reads and returns an event title that the user enters.
     *
     * @return the event title entered by the user
     */
    public String readEventTitle() {
        return promptString(EventPrompts.EVENT_TITLE_PROMPT);
    }

    /**
     * Reads and returns a speaker name that the user enters.
     *
     * @return the speaker name entered by the user
     */
    public String readSpeaker() {
        return promptString(EventPrompts.EVENT_SPEAKER_PROMPT);
    }

    /**
     * Returns whether or not the speaker with name <code>speakerName</code> is speaking at the event called
     * <code>eventTitle</code>.
     * <p>
     * If <code>speakerName</code> is not a speaker or <code>eventTitle</code> is not the title
     * for an existing event, the method will return false.
     *
     * @param speakerName the name of the speaker to be checked
     * @param eventTitle  the title of the event to be checked
     * @return <code>true</code> if and only if an event with title <code>eventTitle</code> exists
     */
    public boolean isSpeakerForEvent(String speakerName, String eventTitle) {
        return eventInformationController.isSpeakerForEvent(speakerName, eventTitle);
    }
    //</editor-fold>
}

