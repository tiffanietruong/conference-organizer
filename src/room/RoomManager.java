package room;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The <code>RoomManager</code> class is responsible for storing the data of all existing rooms during program
 * execution and processes requests involving any <code>Room</code> and/or its properties.
 */
public class RoomManager implements Serializable {

    private final List<Room> rooms;

    /**
     * Constructs a <code>RoomManager</code> initialized with an empty list of rooms.
     */
    public RoomManager() {
        this.rooms = new ArrayList<>();
    }

    /**
     * Creates a new room with the given ID and occupant capacity.
     * Precondition: roomID is not the ID of an existing room in the conference.
     *
     * @param roomID   the room number of the new room
     * @param capacity the capacity of the new room
     */
    public void createNewRoom(int roomID, int capacity) {
        rooms.add(new Room(roomID, capacity));
    }

    /**
     * Deletes a room that has  the given ID.
     * Precondition: roomID is the ID of an existing room in the conference.
     *
     * @param roomID the room number of the new room
     */
    public boolean deleteRoom(int roomID) {
        if (roomExists(roomID)) {
            this.rooms.remove(getRoomWithID(roomID));
            return true;
        }
        return false;
    }

    /**
     * Returns the <code>Room</code> whose room number matches the given <code>roomID</code>.
     *
     * @param roomID the room number of the desired room
     * @return the instance of <code>Room</code> associated with <code>roomID</code>
     * @throws RoomNotFoundException if a room with the given roomID does not exist.
     */
    private Room getRoomWithID(int roomID) {
        for (Room room : rooms) {
            if (room.getRoomID() == (roomID)) {
                return room;
            }
        }
        throw new RoomNotFoundException();
    }

    /**
     * Returns the capacity of the room whose room number matches the given <code>roomID</code>.
     *
     * @param roomID the room number of the desired room
     * @return the capacity of the instance of Room associated with <code>roomID</code>
     * @throws RoomNotFoundException if a room with the given roomID does not exist.
     */
    public int getCapacityOfRoom(int roomID) {
        for (Room room : rooms) {
            if (roomID == room.getRoomID()) {
                return room.getCapacity();
            }
        }
        throw new RoomNotFoundException();
    }

    /**
     * Returns true if there exists a room with the specified room ID.
     *
     * @param roomID the ID of the room being searched for
     * @return true if there exists a room with the specified room ID
     */
    public boolean roomExists(int roomID) {
        for (Room room : rooms) {
            if (room.getRoomID() == (roomID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a <code>Map</code> storing mappings of room numbers to room capacities of all <code>Rooms</code>
     * in the system.
     *
     * @return a <code>Map</code> storing mappings of room numbers to room capacities
     */
    public Map<Integer, Integer> getRoomInformation() {
        Map<Integer, Integer> roomInformation = new TreeMap<>();
        for (Room room : rooms) {
            roomInformation.put(room.getRoomID(), room.getCapacity());
        }
        return roomInformation;
    }

}
