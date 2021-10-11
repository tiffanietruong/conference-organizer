package event.manager;

import event.Event;
import event.EventType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The <code>EventInformationManager</code> is responsible for getting information about events from the event entities.
 */
public class EventInformationManager implements Serializable {
    private final IEventRepository eventRepository;

    /**
     * Constructs a new <code>EventInformationManager</code> object.
     *
     * @param eventRepository the repository containing all events
     */
    public EventInformationManager(IEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Returns a list of the titles of all events in the conference.
     *
     * @return a list of the titles of all events
     */
    public List<String> getEventList() {
        List<String> titles = new ArrayList<>();
        for (Event event : eventRepository.getEvents()) {
            titles.add(event.getTitle());
        }
        return titles;
    }

    /**
     * Gets a list of usernames corresponding to attendees that are signed up for the event with the given title.
     *
     * @param eventTitle the title of the event whose list of attendees is being checked
     * @return a list of usernames corresponding to attendees that are signed up for the event with the given title
     */
    public List<String> getAttendees(String eventTitle) {
        return eventRepository.getEventWithTitle(eventTitle).getAttendees();
    }

    /**
     * Returns true if there exists an event with the given title.
     *
     * @param title the title of the event whose existence is being checked
     * @return true if the title exists in the list of all events, false otherwise
     */
    public boolean eventExists(String title) {
        for (Event event : eventRepository.getEvents()) {
            if (event.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the event with the given title is a VIP-only event.
     *
     * @param title the title of the event to be checked
     * @return <code>true</code> iff event <code>title</code> is VIP-only
     */
    public boolean isVIP(String title) {
        return eventRepository.getEventWithTitle(title).getVip();
    }

    /**
     * Returns the duration for the given event.
     * Precondition: an <code>Event</code> object with the title <code>eventTitle</code> already exists.
     *
     * @param eventTitle the title of the event
     * @return the duration (in minutes) of the event
     */
    public int getDuration(String eventTitle) {
        return eventRepository.getEventWithTitle(eventTitle).getDuration();
    }

    /**
     * Returns the start time for the given event.
     * Precondition: an <code>Event</code> object with the title <code>eventTitle</code> already exists.
     *
     * @param eventTitle the title of the event
     * @return the start time of the event
     */
    public LocalDateTime getStartTime(String eventTitle) {
        return eventRepository.getEventWithTitle(eventTitle).getStartTime();
    }

    /**
     * Returns the capacity for the given event.
     * Precondition: an <code>Event</code> object with the title <code>eventTitle</code> already exists.
     *
     * @param eventTitle the title of the event
     * @return the capacity of the event
     */
    public int getCapacity(String eventTitle) {
        return eventRepository.getEventWithTitle(eventTitle).getCapacity();
    }


    /**
     * Returns the usernames of the speakers for the given event.
     * Precondition: an <code>Event</code> object with the title <code>eventTitle</code> already exists.
     *
     * @param eventTitle the title of the event
     * @return the usernames of the speakers for the event
     */
    public List<String> getSpeakers(String eventTitle) {
        return eventRepository.getEventWithTitle(eventTitle).getSpeakers();
    }

    /**
     * Returns true if the event with the given title has less users signed up than the maximum number of users
     * that can sign up.
     *
     * @param eventTitle the title of the event whose attendance is being checked
     * @return true if the event currently has less users signed up than the maximum number of users that can sign up
     */
    public boolean isNotFull(String eventTitle) {
        Event event = eventRepository.getEventWithTitle(eventTitle);
        return event.getAttendees().size() < event.getCapacity();
    }

    /**
     * Returns a list of string representations for the given events. The information in the returned list
     * corresponds to the same order of events in the list that is passed in.
     *
     * @param eventTitles the list of events to get string representations for
     * @return a list of string representations for the given events
     */
    public List<String> getStringRepresentations(List<String> eventTitles) {
        List<String> strings = new ArrayList<>(eventTitles.size());
        for (Event event : eventRepository.getEvents()) {
            int index = eventTitles.indexOf(event.getTitle());
            if (index != -1) {
                strings.add(index, event.toString());
            }
        }
        return strings;
    }

    /**
     * Returns a list of string representations for all events in the conference
     *
     * @return a list of string representations for all events in the conference
     */
    public List<String> getStringRepresentations() {
        return getStringRepresentations(getEventList());
    }

    /**
     * Returns a list of string representations for vip events in the conference
     *
     * @return a list of string representations for vip events in the conference
     */
    public List<String> getVipStringRepresentations() {
        List<String> eventList = new ArrayList<>();
        for (String event : getEventList()) {
            if (isVIP(event)) {
                eventList.add(event);
            }
        }
        return getStringRepresentations(eventList);
    }

    /**
     * Returns a list of events that the given speaker is the scheduled to speak at.
     *
     * @param speakerName the username of the speaker
     * @return a list of events that the given speaker is the scheduled to speak at
     */
    public List<String> getEventsBySpeaker(String speakerName) {
        List<String> eventsBySpeaker = new ArrayList<>();
        for (Event event : eventRepository.getEvents()) {
            List<String> curSpeakers = event.getSpeakers();
            if (curSpeakers.contains(speakerName)) {
                eventsBySpeaker.add(event.getTitle());
            }
        }
        return eventsBySpeaker;
    }

    /**
     * Returns a list of the events that the given user is signed up for.
     *
     * @param username the username of the user whose event schedule is being checked
     * @return a list of the events that the given user is signed up for
     */
    public List<String> getSignedUpEvents(String username) {
        List<String> eventsByUser = new ArrayList<>();
        for (Event event : eventRepository.getEvents()) {
            if (event.getAttendees().contains(username)) {
                eventsByUser.add(event.getTitle());
            }
        }
        return eventsByUser;
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
        for (Event event : eventRepository.getEvents()) {
            List<String> curSpeakers = event.getSpeakers();
            if (curSpeakers.contains(speaker)) {
                LocalDateTime existingEventTime = event.getStartTime();
                for (int minute = 0; minute <= duration; minute++) {
                    // if any minute in startTime + duration is after an existing starttime and before a corresponding existing endtime, then speaker is booked
                    if (startTime.plusMinutes(minute).isAfter(existingEventTime) && startTime.plusMinutes(minute).isBefore(existingEventTime.plusMinutes(event.getDuration()))) {
                        return true;
                    }
                }
            }
        }
        return false;
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
        for (Event event : eventRepository.getEvents()) {
            if (event.getRoomID() == roomID) {
                LocalDateTime existingEventTime = event.getStartTime();
                for (int minute = 0; minute <= duration; minute++) {
                    // if any minute in startTime + duration is after an existing starttime and before a corresponding existing endtime, then speaker is booked
                    if (startTime.plusMinutes(minute).isAfter(existingEventTime) &&
                            startTime.plusMinutes(minute).isBefore(existingEventTime.plusMinutes(event.getDuration()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns true if an event is occurring in the given room
     *
     * @return true if an event is occurring in the given room
     */
    public boolean roomHasAnEvent(int roomID) {
        for (Event event : eventRepository.getEvents()) {
            if (event.getRoomID() == roomID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a list of events titles corresponding to events that are of the specified <code>EventType</code> .
     *
     * @param eventType the type of <code>Event</code> such that event names of events of that type will be returned
     * @return a list of events titles corresponding to <code>Events</code> that are of userType
     */
    public List<String> getAllEventsOfType(EventType eventType) {
        List<String> eventNames = new ArrayList<>();
        for (Event event : eventRepository.getEvents()) {
            if (event.getEventType() == eventType) {
                eventNames.add(event.getTitle());
            }
        }
        return eventNames;
    }

    /**
     * Returns a list of reviews for the event with the given title.
     *
     * @param title the title of the event whose reviews are returned
     * @return a list of strings representing reviews for the event
     */
    public List<String> getReviews(String title) {
        return eventRepository.getEventWithTitle(title).getReviews();
    }
}
