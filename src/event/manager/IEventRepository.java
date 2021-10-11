package event.manager;

import event.Event;

import java.util.List;

/**
 * The <code>IEventRepository</code> specifies methods that any class serving as a repository of Event entities must
 * implement. These include methods for accessing events in the program, and adding and removing events.
 */
public interface IEventRepository {
    /**
     * Gets the <code>Event</code> object corresponding to the given title.
     * Precondition: a <code>Event</code> object with title <code>title</code> already exists.
     *
     * @param title the unique title of the <code>Event</code> object to retrieve
     * @return the unique <code>Event</code> object with title <code>title</code>
     * @throws EventNotFoundException if an <code>Event</code> object with title <code>title</code> does not exist
     */
    Event getEventWithTitle(String title);

    /**
     * Returns a list of all events in the program.
     *
     * @return a list of all events in the program
     */
    List<Event> getEvents();

    /**
     * Adds the given event to the program.
     *
     * @param event the event to be added
     */
    void addEvent(Event event);

    /**
     * Removes the event with the given title from the program.
     * Precondition: an event with title <code>eventTitle</code> must already exist.
     *
     * @param eventTitle the title of the event to be removed
     */
    void removeEvent(String eventTitle);

    /**
     * Returns whether or not the event with the given title exists in the program.
     *
     * @param eventTitle the title of the event whose existence is to be checked
     * @return true iff the event with title <code>eventTitle</code> exists
     */
    boolean eventExists(String eventTitle);
}
