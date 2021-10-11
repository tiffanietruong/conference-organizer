package event.manager;

import event.Event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The <code>EventRepository</code> class is responsible for storing Event entities during program execution,
 * retrieving events by their title or getting basic information about events, and adding or removing events to the
 * list of events as directed.
 */
public class EventRepository implements IEventRepository, Serializable {
    private final List<Event> events;

    /**
     * Constructs a new <code>EventRepository</code> object with no events.
     */
    public EventRepository() {
        events = new ArrayList<>();
    }

    /**
     * Gets the <code>Event</code> object corresponding to the given title.
     * Precondition: a <code>Event</code> object with title <code>title</code> already exists.
     *
     * @param title the unique title of the <code>Event</code> object to retrieve
     * @return the unique <code>Event</code> object with title <code>title</code>
     * @throws EventNotFoundException if an <code>Event</code> object with title <code>title</code> does not exist
     */
    @Override
    public Event getEventWithTitle(String title) {
        for (Event event : events) {
            if (event.getTitle().equals(title)) {
                return event;
            }
        }
        throw new EventNotFoundException();
    }

    /**
     * Returns a list of all events in the program.
     *
     * @return a list of all events in the program
     */
    @Override
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Adds the given event to the program.
     *
     * @param event the event to be added
     */
    @Override
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Removes the event with the given title from the program.
     * Precondition: an event with title <code>eventTitle</code> must already exist.
     *
     * @param eventTitle the title of the event to be removed
     */
    @Override
    public void removeEvent(String eventTitle) {
        if (eventExists(eventTitle)) {
            events.remove(getEventWithTitle(eventTitle));
        }
    }

    /**
     * Returns whether or not the event with the given title exists in the program.
     *
     * @param eventTitle the title of the event whose existence is to be checked
     * @return true iff the event with title <code>eventTitle</code> exists
     */
    @Override
    public boolean eventExists(String eventTitle) {
        for (Event event : events) {
            if (event.getTitle().equals(eventTitle)) {
                return true;
            }
        }
        return false;
    }
}
