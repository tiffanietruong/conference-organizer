package event.manager;

import event.Event;
import event.EventType;

import java.io.Serializable;

/**
 * This is a Factory class that calls the <code>Event</code> constructor. It is used to:
 * <ul>
 *     <li>Remove hard dependencies on <code>Event</code> from <code>EventManager</code></li>
 *     <li>Make <code>Event</code> more extensible when creating different types of Events</li>
 *     <li>Encapsulate the <code>Event</code> constructor calls</li>
 * </ul>
 *
 * @see Event
 */
class EventFactory implements Serializable {

    /**
     * Returns a new <code>Event</code> object with the information given by <code>eventInfo</code>.
     *
     * @param eventInfo an object storing the title, start time, duration, attendee capacity, roomID, speaker capacity,
     *                  and whether or not an event is VIP exclusive
     * @return a new <code>Event</code> object with the information given by <code>eventInfo</code>
     */
    public Event getEvent(EventParameterObject eventInfo) {
        EventType chosenType;
        if (eventInfo.getSpeakerCapacity() == 0) {
            chosenType = EventType.PARTY;
        } else if (eventInfo.getSpeakerCapacity() == 1) {
            chosenType = EventType.TALK;
        } else {
            chosenType = EventType.PANEL;
        }
        return new Event(eventInfo.getTitle(), eventInfo.getStartTime(), eventInfo.getDuration(),
                eventInfo.getEventCapacity(), eventInfo.getRoomID(), eventInfo.isVip(), eventInfo.getSpeakerCapacity(),
                chosenType);
    }
}