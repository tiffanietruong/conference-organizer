package event.controller;

import event.manager.EventManagerFacade;
import event.presenter.EventPresenter;
import event.presenter.EventPrompts;
import system.console.ConsoleInputController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The <code>EventInformationController</code> class is responsible for processing requests involving getting
 * information about events. This information includes getting the participants for a given talk and displaying
 * schedules of events.
 */
public class EventInformationController extends ConsoleInputController<EventPrompts> {
    private final EventManagerFacade eventManager;
    private final EventPresenter eventPresenter;

    /**
     * Constructs ConsoleInputController.
     *
     * @param in             a Scanner object to be used for taking input
     * @param eventPresenter a presenter to be used for displaying prompts
     */
    protected EventInformationController(Scanner in, EventManagerFacade eventManager, EventPresenter eventPresenter) {
        super(in, eventPresenter);
        this.eventManager = eventManager;
        this.eventPresenter = eventPresenter;
    }

    /**
     * Processes the request to display the list of all events scheduled in the conference.
     */
    public void displayCompleteSchedule() {
        List<String> eventStrings = eventManager.getStringRepresentations();
        if (eventStrings.isEmpty()) {
            eventPresenter.display(EventPrompts.EVENTS_NOT_FOUND);
        } else {
            eventPresenter.display(EventPrompts.EVENT_SCHEDULE_DISPLAYED);
            eventPresenter.displaySchedule(eventStrings);
        }
    }

    /**
     * Processes the request to display the list of vip events scheduled in the conference.
     */
    public void displayVipSchedule() {
        List<String> eventStrings = eventManager.getVipStringRepresentations();
        if (eventStrings.isEmpty()) {
            eventPresenter.display(EventPrompts.EVENTS_NOT_FOUND);
        } else {
            eventPresenter.display(EventPrompts.EVENT_VIP_SCHEDULE_DISPLAYED);
            eventPresenter.displaySchedule(eventStrings);
        }
    }

    /**
     * Processes the request to display the list of events that given user is signed up for.
     *
     * @param username name of the user
     */
    public void displayUserSchedule(String username) {
        List<String> eventTitles = eventManager.getSignedUpEvents(username);
        List<String> eventStrings = eventManager.getStringRepresentations(eventTitles);
        if (eventTitles.isEmpty()) {
            eventPresenter.display(EventPrompts.EVENTS_NOT_FOUND);
        } else {
            eventPresenter.display(EventPrompts.EVENT_LIST_DISPLAYED);
            eventPresenter.displaySchedule(eventStrings);
        }
    }

    /**
     * Processes the request to display the list of events that the given speaker is scheduled to speak at.
     *
     * @param speakerName name of the speaker
     */
    public void displaySpeakerEvents(String speakerName) {
        List<String> eventTitles = eventManager.getEventsBySpeaker(speakerName);
        List<String> eventStrings = eventManager.getStringRepresentations(eventTitles);
        if (eventTitles.isEmpty()) {
            eventPresenter.display(EventPrompts.EVENTS_NOT_FOUND);
        } else {
            eventPresenter.display(EventPrompts.SPEAKER_SCHEDULE_DISPLAYED);
            eventPresenter.displaySchedule(eventStrings);
        }
    }

    /**
     * Gets a list of usernames corresponding to attendees signed up for at least one event that the given
     * speaker is speaking at.
     *
     * @param speakerName username of the speaker for the event
     * @return list of attendees for the event based on the speaker
     */
    public List<String> getAttendeesForEventsBySpeaker(String speakerName) {
        List<String> attendees = new ArrayList<>();
        List<String> eventsBySpeaker = eventManager.getEventsBySpeaker(speakerName);
        for (String event : eventsBySpeaker) {
            attendees.addAll(eventManager.getAttendees(event));
        }
        return attendees;
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
        return eventManager.getEventsBySpeaker(speakerName).contains(eventTitle);
    }

    /**
     * Gets a list of usernames corresponding to attendees signed up for a given event.
     *
     * @param eventTitle the title of the event whose attendees are returned
     * @return a list of usernames corresponding to attendees signed up for the event <code>eventTitle</code>
     */
    public List<String> getAttendeesForEvent(String eventTitle) {
        return eventManager.getAttendees(eventTitle);
    }

    /**
     * Processes the user's request to display reviews for an event.
     * This method accepts input for an event title and displays its reviews if an event is found.
     */
    public void viewReviews() {
        String title = promptString(EventPrompts.EVENT_TITLE_PROMPT);
        if (eventManager.eventExists(title)) {
            eventPresenter.displayReviews(title, eventManager.getReviews(title));
        } else {
            eventPresenter.display(EventPrompts.EVENT_NOT_FOUND);
        }
    }
}
