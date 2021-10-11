package event.manager;

import event.Event;

import java.io.Serializable;
import java.util.List;

/**
 * The <code>EventManipulationManager</code> is responsible for modifying event entities, including adding and deleting
 * events, and managing the participants of an existing event.
 */
public class EventManipulationManager implements Serializable {
    private final IEventRepository eventRepository;
    private final EventFactory eventFactory;

    /**
     * Constructs a new <code>EventManipulationManager</code> object.
     *
     * @param eventRepository the repository containing all events
     */
    public EventManipulationManager(IEventRepository eventRepository) {
        this.eventRepository = eventRepository;
        this.eventFactory = new EventFactory();
    }

    /**
     * Creates a new event in the conference with the given information.
     *
     * @param eventParameterObject a parameter object containing all the information for the event to create
     */
    public void createEvent(EventParameterObject eventParameterObject) {
        eventRepository.addEvent(eventFactory.getEvent(eventParameterObject));
    }

    /**
     * Returns true if there exists an event with the given title and removes that event from the list.
     *
     * @param title the title of the event that is being deleted
     * @return true if the title exists in the list of all events, false otherwise
     */
    public boolean deleteEvent(String title) {
        if (eventRepository.eventExists(title)) {
            eventRepository.removeEvent(title);
            return true;
        }
        return false;
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
        Event event = eventRepository.getEventWithTitle(eventTitle);
        if (event.getSpeakers().size() < event.getSpeakerCapacity()) {
            event.addSpeaker(speakerName);
            return true;
        }
        return false;
    }


    /**
     * Removes user from all events.
     *
     * @param username       the username of the user
     * @param signedUpEvents a list of titles of the events that <code>username</code> is signed up for
     */
    public void removeUserFromEvents(String username, List<String> signedUpEvents) {
        for (String event : signedUpEvents) {
            eventRepository.getEventWithTitle(event).removeAttendee(username);
        }
    }

    /**
     * Removes speaker from all events.
     *
     * @param username        the username of the speaker
     * @param eventsBySpeaker a list of titles of the events at which the speaker is speaking
     */
    public void removeSpeakerFromEvents(String username, List<String> eventsBySpeaker) {
        for (String event : eventsBySpeaker) {
            eventRepository.getEventWithTitle(event).removeSpeaker(username);
        }
    }

    /**
     * Adds the user with the given username to the attendee list of the event with the given title if the
     * event is not at maximum capacity.
     *
     * @param eventTitle the title of the event an attendee wants to sign up for
     * @param username   the username of the attendee
     * @param isFull     <code>true</code> iff the event is already full
     * @return true if the attendee was successfully added to the list
     */
    public boolean addAttendee(String eventTitle, String username, boolean isFull) {
        if (!isFull) {
            eventRepository.getEventWithTitle(eventTitle).addAttendee(username);
            return true;
        }
        return false;
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
        Event event = eventRepository.getEventWithTitle(eventTitle);
        if (event.getAttendees().contains(username)) {
            event.removeAttendee(username);
            return true;
        }
        return false;
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
        Event event = eventRepository.getEventWithTitle(eventTitle);
        if (event.getSpeakers().contains(username)) {
            event.getSpeakers().remove(username);
            return true;
        }
        return false;
    }


    /**
     * Removes user from all VIP events.
     *
     * @param username       the username of the user
     * @param signedUpEvents a list of titles of the events that <code>username</code> is signed up for
     */
    public void removeUserFromVIPEvents(String username, List<String> signedUpEvents) {
        for (String event : signedUpEvents) {
            if (eventRepository.getEventWithTitle(event).getVip()) {
                eventRepository.getEventWithTitle(event).removeAttendee(username);
            }
        }
    }

    /**
     * Adds a review to an event with a given title.
     *
     * @param title  the title of the event
     * @param review the review as a string
     */
    public void addReview(String title, String review) {
        eventRepository.getEventWithTitle(title).addReview(review);
    }
}
