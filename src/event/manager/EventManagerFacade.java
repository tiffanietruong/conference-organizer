package event.manager;

import event.EventType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The <code>EventManager</code> class is responsible for storing event data during program execution. It modifies and
 * accesses information regarding both individual events and the complete list of events as directed.
 */
public class EventManagerFacade implements Serializable {

    private final EventInformationManager eventInformationManager;
    private final EventManipulationManager eventManipulationManager;

    /**
     * Constructs a manager responsible for event data that is initialized storing an empty list of events.
     */
    public EventManagerFacade() {
        IEventRepository eventRepository = new EventRepository();
        this.eventInformationManager = new EventInformationManager(eventRepository);
        this.eventManipulationManager = new EventManipulationManager(eventRepository);
    }

    //<editor-fold desc="Get Event Information Methods">

    public boolean isVIP(String title) {
        return eventInformationManager.isVIP(title);
    }

    /**
     * Returns the duration for the given event.
     * Precondition: an <code>Event</code> object with the title <code>eventTitle</code> already exists.
     *
     * @param eventTitle the title of the event
     * @return the duration (in minutes) of the event
     */
    public int getDuration(String eventTitle) {
        return eventInformationManager.getDuration(eventTitle);
    }

    /**
     * Returns the start time for the given event.
     * Precondition: an <code>Event</code> object with the title <code>eventTitle</code> already exists.
     *
     * @param eventTitle the title of the event
     * @return the start time of the event
     */
    public LocalDateTime getStartTime(String eventTitle) {
        return eventInformationManager.getStartTime(eventTitle);
    }

    /**
     * Returns the capacity for the given event.
     * Precondition: an <code>Event</code> object with the title <code>eventTitle</code> already exists.
     *
     * @param eventTitle the title of the event
     * @return the capacity of the event
     */
    public int getCapacity(String eventTitle) {
        return eventInformationManager.getCapacity(eventTitle);
    }

    /**
     * Returns the usernames of the speakers for the given event.
     * Precondition: an <code>Event</code> object with the title <code>eventTitle</code> already exists.
     *
     * @param eventTitle the title of the event
     * @return the usernames of the speakers for the event
     */
    public List<String> getSpeakers(String eventTitle) {
        return eventInformationManager.getSpeakers(eventTitle);
    }

    /**
     * Returns true if the event with the given title has less users signed up than the maximum number of users
     * that can sign up.
     *
     * @param eventTitle the title of the event whose attendance is being checked
     * @return true if the event currently has less users signed up than the maximum number of users that can sign up
     */
    public boolean isNotFull(String eventTitle) {
        return eventInformationManager.isNotFull(eventTitle);
    }

    /**
     * Returns a list of the titles of all events in the conference.
     *
     * @return a list of the titles of all events
     */
    public List<String> getEventList() {
        return eventInformationManager.getEventList();
    }

    /**
     * Gets a list of usernames corresponding to attendees that are signed up for the event with the given title.
     *
     * @param eventTitle the title of the event whose list of attendees is being checked
     * @return a list of usernames corresponding to attendees that are signed up for the event with the given title
     */
    public List<String> getAttendees(String eventTitle) {
        return eventInformationManager.getAttendees(eventTitle);
    }

    /**
     * Returns true if there exists an event with the given title.
     *
     * @param title the title of the event whose existence is being checked
     * @return true if the title exists in the list of all events, false otherwise
     */
    public boolean eventExists(String title) {
        return eventInformationManager.eventExists(title);
    }

    /**
     * Returns a list of string representations for the given events. The information in the returned list
     * corresponds to the same order of events in the list that is passed in.
     *
     * @param eventTitles the list of events to get string representations for
     * @return a list of string representations for the given events
     */
    public List<String> getStringRepresentations(List<String> eventTitles) {
        return eventInformationManager.getStringRepresentations(eventTitles);
    }

    /**
     * Returns a list of string representations for all events in the conference
     *
     * @return a list of string representations for all events in the conference
     */
    public List<String> getStringRepresentations() {
        return eventInformationManager.getStringRepresentations();
    }

    /**
     * Returns a list of string representations for vip events in the conference
     *
     * @return a list of string representations for vip events in the conference
     */
    public List<String> getVipStringRepresentations() {
        return eventInformationManager.getVipStringRepresentations();
    }

    /**
     * Returns a list of events that the given speaker is the scheduled to speak at.
     *
     * @param speakerName the username of the speaker
     * @return a list of events that the given speaker is the scheduled to speak at
     */
    public List<String> getEventsBySpeaker(String speakerName) {
        return eventInformationManager.getEventsBySpeaker(speakerName);
    }

    /**
     * Returns a list of the events that the given user is signed up for.
     *
     * @param username the username of the user whose event schedule is being checked
     * @return a list of the events that the given user is signed up for
     */
    public List<String> getSignedUpEvents(String username) {
        return eventInformationManager.getSignedUpEvents(username);
    }

    /**
     * Returns true if the given speaker is booked to speak at an event at the given time of interest.
     *
     * @param speaker   the username of the speaker whose being checked for being booked/not booked
     * @param startTime an integer representing an hour in 24-hour clock format that an event starts at
     * @param duration  the duration of the event that is being checked
     * @return true if the speaker is booked at the given time
     */
    public boolean speakerIsBooked(String speaker, LocalDateTime startTime, int duration) {
        return eventInformationManager.speakerIsBooked(speaker, startTime, duration);
    }

    /**
     * Returns true if the given room is booked for an event at the given time of interest.
     *
     * @param roomID    the room number of the room that is being checked for being booked/not booked
     * @param startTime an integer representing an hour in 24-hour clock format that an event starts at
     * @param duration  the duration of the event that is being checked
     * @return true if the room is booked at the given time
     */
    public boolean roomIsBooked(int roomID, LocalDateTime startTime, int duration) {
        return eventInformationManager.roomIsBooked(roomID, startTime, duration);
    }

    /**
     * Returns true if an event is occurring in the given room
     *
     * @return true if an event is occurring in the given room
     */
    public boolean roomHasAnEvent(int roomID) {
        return eventInformationManager.roomHasAnEvent(roomID);
    }

    /**
     * Returns a list of events titles corresponding to events that are of the specified <code>EventType</code> .
     *
     * @param eventType the type of <code>Event</code> such that event names of events of that type will be returned
     * @return a list of events titles corresponding to <code>Events</code> that are of userType
     */
    public List<String> getAllEventsOfType(EventType eventType) {
        return eventInformationManager.getAllEventsOfType(eventType);
    }

    public List<String> getReviews(String title) {
        return eventInformationManager.getReviews(title);
    }
    //</editor-fold">

    //<editor-fold desc="Manipulating Events">

    /**
     * Creates a new event in the conference with the given information.
     *
     * @param eventParameterObject a parameter object storing all information about the new event to be created
     */
    public void createEvent(EventParameterObject eventParameterObject) {
        eventManipulationManager.createEvent(eventParameterObject);
    }

    /**
     * Returns true if there exists an event with the given title and removes that event from the list.
     *
     * @param title the title of the event that is being deleted
     * @return true if the title exists in the list of all events, false otherwise
     */
    public boolean deleteEvent(String title) {
        return eventManipulationManager.deleteEvent(title);
    }

    /**
     * Adds the speaker for the given event.
     * Precondition: an <code>Event</code> object with the title <code>eventTitle</code> already exists
     *
     * @param eventTitle  the title of the event
     * @param speakerName the username of the speaker
     * @return a boolean of whether the addition was successful
     */
    public boolean addSpeaker(String eventTitle, String speakerName) {
        return eventManipulationManager.addSpeaker(eventTitle, speakerName);
    }


    /**
     * Removes user from all events
     *
     * @param username the username of the user
     */
    public void removeUserFromEvents(String username) {
        eventManipulationManager.removeUserFromEvents(username, getSignedUpEvents(username));
    }

    /**
     * Removes speaker from all events
     *
     * @param username the username of the speaker
     */
    public void removeSpeakerFromEvents(String username) {
        eventManipulationManager.removeSpeakerFromEvents(username, getEventsBySpeaker(username));
    }


    /**
     * Adds the user with the given username to the attendee list of the event with the given title if the
     * event is not at maximum capacity.
     *
     * @param eventTitle the title of the event an attendee wants to sign up for
     * @param username   the username of the attendee
     * @return true if the attendee was successfully added to the list
     */
    public boolean addAttendee(String eventTitle, String username) {
        return eventManipulationManager.addAttendee(eventTitle, username, !isNotFull(eventTitle));
    }

    /**
     * Removes the user with the given username from the attendee list of the event with the given title if
     * the user is signed up for the event.
     *
     * @param eventTitle the event an attendee will no longer be attending
     * @param username   the username of the attendee
     * @return true if the attendee was successfully removed from the attendee list,
     * false if the attendee was not found in the list
     */
    public boolean removeAttendee(String eventTitle, String username) {
        return eventManipulationManager.removeAttendee(eventTitle, username);
    }

    /**
     * Removes the speaker with the given username from the speaker list of the event with the given title if
     * the speaker is signed up for the event.
     *
     * @param eventTitle the event a speaker will no longer be speaking at
     * @param username   the username of the speaker
     * @return true if the speaker was successfully removed from the speaker list,
     * false if the speaker was not found in the list
     */
    public boolean removeSpeaker(String eventTitle, String username) {
        return eventManipulationManager.removeSpeaker(eventTitle, username);
    }

    /**
     * Removes user from all VIP events
     *
     * @param username the username of the user
     */
    public void removeUserFromVIPEvents(String username) {
        eventManipulationManager.removeUserFromVIPEvents(username, getSignedUpEvents(username));
    }

    /**
     * Adds a review to an event with a given title.
     *
     * @param title  the title of the event
     * @param review the review as a string
     */
    public void addReview(String title, String review) {
        eventManipulationManager.addReview(title, review);
    }
    //</editor-fold>
}
