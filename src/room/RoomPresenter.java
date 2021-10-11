package room;

import system.console.ConsolePresenter;

import java.util.EnumMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * The RoomPresenter class is responsible for displaying messages during user requests involving rooms.
 * This responsibility implies that the class currently acts as a Presenter and a UI.
 */
class RoomPresenter extends ConsolePresenter<RoomPrompts> {

    /**
     * Returns an <code>EnumMap</code> that maps each enum key of type <code>T</code> to its respective console message
     *
     * @return an <code>EnumMap</code> that maps each enum key of type <code>T</code> to its respective console message
     */
    @Override
    protected EnumMap<RoomPrompts, String> initializeMessages() {
        EnumMap<RoomPrompts, String> m = new EnumMap<>(RoomPrompts.class);
        m.put(RoomPrompts.ROOM_ID_PROMPT, "Please enter the ID of the new room:");
        m.put(RoomPrompts.ROOM_CAPACITY_PROMPT, "Please enter the occupant capacity of the new room:");
        m.put(RoomPrompts.INVALID_NUM_ERROR, "Sorry, please enter an integer greater than 0.");
        m.put(RoomPrompts.ROOM_EXISTS_ERROR, "Sorry, that room already exists.");
        m.put(RoomPrompts.NO_ROOMS_ERROR, "Sorry, there are currently no rooms entered in the system.");
        m.put(RoomPrompts.ROOM_ADDED_SUCCESS, "The room was successfully added.");
        m.put(RoomPrompts.ROOM_DELETE_PROMPT, "Please enter the room ID of the room you want to delete.");
        m.put(RoomPrompts.ROOM_DELETE_SUCCESS, "The room was successfully deleted.");
        m.put(RoomPrompts.ROOM_DELETE_FAIL, "Sorry, the room doesn't exist.");
        m.put(RoomPrompts.ROOM_HAS_EVENTS, "Sorry, this room has events scheduled, so it cannot be deleted.");
        m.put(RoomPrompts.DELETE_ROOM_PROMPT, "Please enter the ID of the room you want to delete:");
        return m;
    }

    /**
     * Prints the mappings of room number to room capacity stored in <code>roomInformation</code> to the screen.
     *
     * @param roomInformation a map of room numbers to room capacities
     */
    public void displayRoomInformation(Map<Integer, Integer> roomInformation) {
        System.out.println("===== ROOMS IN THE SYSTEM =====");
        if (roomInformation.isEmpty()) {
            display(RoomPrompts.NO_ROOMS_ERROR);
        } else {
            SortedSet<Integer> roomNumbers = new TreeSet<>(roomInformation.keySet());
            for (Integer roomNumber : roomNumbers) {
                Integer capacity = roomInformation.get(roomNumber);
                System.out.println("Room " + roomNumber + " has an occupant capacity of " + capacity + ".");
            }
        }
    }
}