package event.manager;

import event.Event;

import java.time.LocalDateTime;

/**
 * The <code>EventParameterObject</code> class defines an object that stores all information needed for event creation.
 * Its main responsibility is to prevent long parameter lists and achieves this using setter methods to store event
 * information that can then be unpackaged in the <code>EventFactory</code> class.
 *
 * @see EventFactory
 * @see Event
 */
public class EventParameterObject {

    private String title;
    private boolean vip;
    private LocalDateTime startTime;
    private int roomID;
    private int speakerCapacity;
    private int eventCapacity;
    private int duration;

    /**
     * Constructs an empty EventParameterObject to store <code>Event</code> information.
     */
    public EventParameterObject() {

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
     * Sets the name of this event.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns whether an event is exclusive to VIP users.
     *
     * @return true of the event is exclusive to VIP users, false otherwise
     */
    public boolean isVip() {
        return vip;
    }

    /**
     * Sets an event to be exclusive to VIP users.
     */
    public void setVip(boolean vip) {
        this.vip = vip;
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
     * Sets the start time of this event.
     *
     * @param startTime LocalDateTime representing the time that the event starts at and on what day
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
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
     * Sets the RoomID of this event.
     *
     * @param roomID an integer representing the roomID
     */
    public void setRoomID(int roomID) {
        this.roomID = roomID;
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
     * Sets the maximum number of speakers that can be scheduled to speak at this event
     *
     * @param speakerCapacity the maximum number of speakers that can be scheduled to speak at this event
     */
    public void setSpeakerCapacity(int speakerCapacity) {
        this.speakerCapacity = speakerCapacity;
    }

    /**
     * Gets the capacity of this event.
     *
     * @return the maximum number of attendees that can sign up for this event
     */
    public int getEventCapacity() {
        return eventCapacity;
    }

    /**
     * Sets the capacity of this event.
     *
     * @param eventCapacity the maximum number of attendees that can sign up for this event
     */
    public void setEventCapacity(int eventCapacity) {
        this.eventCapacity = eventCapacity;
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
     * Sets the duration of this event.
     *
     * @param duration the duration of this event
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
}
