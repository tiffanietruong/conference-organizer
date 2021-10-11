package event;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The <code>Event</code> class represents a event in the conference regulated by the program.
 */
public class Event implements Serializable {

    private final String title;
    private final LocalDateTime startTime;
    private final int duration;
    private final int capacity;
    private final int roomID;
    private final boolean vip;
    private final List<String> attendees;
    private final List<String> speakers;
    private final int speakerCapacity;
    private final EventType eventType;
    private final List<String> reviews;

    /**
     * Constructs an <code>Event</code> object associated with the given information and an initially empty list of
     * attendees and speakers.
     *
     * @param title           the name of the event. Event titles are unique.
     * @param startTime       the time that this event starts at
     * @param duration        the duration of this event in minutes
     * @param capacity        the maximum number of attendees that can sign up for this event
     * @param roomID          the ID of the room in which this event is held in
     * @param vip             an indicator of whether this event is exclusive to VIP users
     * @param speakerCapacity the maximum number of speakers that can be scheduled to speak at this event
     * @param eventType       the type of Event
     */
    public Event(String title, LocalDateTime startTime, int duration, int capacity, int roomID, boolean vip,
                 int speakerCapacity, EventType eventType) {
        this.title = title;
        this.startTime = startTime;
        this.duration = duration;
        this.capacity = capacity;
        this.roomID = roomID;
        this.vip = vip;
        this.attendees = new ArrayList<>();
        this.speakers = new ArrayList<>();
        this.speakerCapacity = speakerCapacity;
        this.eventType = eventType;
        this.reviews = new ArrayList<>();
    }

    /**
     * Gets the start time of this event.
     *
     * @return a LocalDateTime representing the time that the event starts at and on what day
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Gets the RoomID of this event.
     *
     * @return an integer representing the roomID
     */
    public int getRoomID() {
        return roomID;
    }

    /**
     * Gets the name of this event.
     *
     * @return the name of this event
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the duration of this event.
     *
     * @return the duration of this event
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Gets the list of usernames corresponding to attendees signed up for this event.
     *
     * @return the list of attendees that have signed up for this event
     */
    public List<String> getAttendees() {
        return attendees;
    }

    /**
     * Adds the given attendee to this event's list of attendees.
     *
     * @param username the username of the attendee to add to this event's list of attendees
     */
    public void addAttendee(String username) {
        attendees.add(username);
    }

    /**
     * Removes the given attendee from this event's list of attendees.
     *
     * @param username the username of the attendee being removed from this events list of attendees
     */
    public void removeAttendee(String username) {
        attendees.remove(username);
    }

    /**
     * Returns true if this event is exclusive to VIP users.
     *
     * @return true if this event is exclusive to VIP users
     */
    public boolean getVip() {
        return vip;
    }

    /**
     * Gets the capacity of this event.
     *
     * @return the maximum number of attendees that can sign up for this event
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Gets the maximum number of speakers that can be scheduled to speak at this event
     *
     * @return the maximum number of speakers that can be scheduled to speak at this event
     */
    public int getSpeakerCapacity() {
        return speakerCapacity;
    }

    /**
     * Gets the usernames of the speakers for this event.
     *
     * @return a list of usernames corresponding to speakers for this event
     */
    public List<String> getSpeakers() {
        return speakers;
    }

    /**
     * Adds speaker to event
     *
     * @param username speaker to be added
     */
    public void addSpeaker(String username) {
        speakers.add(username);
    }

    /**
     * Removes speaker from event
     *
     * @param username speaker to be removed
     */
    public void removeSpeaker(String username) {
        speakers.remove(username);
    }

    /**
     * Returns event type
     *
     * @return the event type
     */
    public EventType getEventType() {
        return this.eventType;
    }

    /**
     * Adds a review to this event
     *
     * @param review the review to be added
     */
    public void addReview(String review) {
        reviews.add(review);
    }

    /**
     * Return a list of all reviews for this event
     *
     * @return the list of reviews
     */
    public List<String> getReviews() {
        return reviews;
    }

    /**
     * Returns a string representation of this event. This representation indicates this event's title, start time,
     * duration, room number, and event type, each on a new line.
     *
     * @return the string representation of this event
     */
    @Override
    public String toString() {
        String date = startTime.getMonth().name() + " " + startTime.getDayOfMonth() + ", " + startTime.getYear();
        String time = startTime.getMinute() < 10 ? startTime.getHour() + ":0" + startTime.getMinute() :
                startTime.getHour() + ":" + startTime.getMinute();
        StringBuilder speakerUsernames = new StringBuilder();
        for (String speaker : speakers) {
            speakerUsernames.append(speaker).append(", ");
        }
        speakerUsernames = new StringBuilder(speakerUsernames.toString().replaceAll(", $", ""));
        return "Event: " + title + "\n" +
                "Start Time: " + date + " @ " + time + "\n" +
                "Duration: " + duration + " minutes" + "\n" +
                "Room: " + roomID + "\n" +
                "Space: " + attendees.size() + "/" + capacity + "\n" +
                "Event type: " + eventType.toString() + ", " + (vip ? "VIP Exclusive" : "General Admission") + "\n" +
                "Speaker: " + speakerUsernames + "\n";

    }

}
