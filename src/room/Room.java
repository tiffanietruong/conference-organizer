package room;

import java.io.Serializable;

/**
 * This class represents a Room in the conference.
 */
class Room implements Serializable {

    private final int roomID;
    private final int capacity;

    /**
     * Constructs a new Room that stores an initially empty list of eventIDs for each Event in this room, as well
     * as another list for each Event's corresponding startTime.
     *
     * @param roomID   the unique room number of this room.
     * @param capacity the maximum number of attendees this room can hold.
     */
    public Room(int roomID, int capacity) {
        this.roomID = roomID;
        this.capacity = capacity;
    }

    /**
     * Gets the unique ID (the room number) for this Room, which is used to refer to this Room.
     *
     * @return the roomID for this Message
     */
    public int getRoomID() {
        return this.roomID;
    }

    /**
     * Gets the capacity (the maximum number of attendees) of this Room.
     *
     * @return the capacity for this Room
     */
    public int getCapacity() {
        return this.capacity;
    }

}

