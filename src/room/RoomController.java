package room;

import event.manager.EventManagerFacade;
import system.console.ConsoleInputController;

import java.util.Map;
import java.util.Scanner;

/**
 * The RoomController class is responsible for processing requests involving creating or deleting rooms
 * from the conference.
 */
public class RoomController extends ConsoleInputController<RoomPrompts> {

    private final EventManagerFacade eventManager;
    private final RoomManager roomManager;
    private final RoomPresenter roomPresenter;

    /**
     * Creates a new RoomController with the given <code>RoomManager</code>.
     *
     * @param roomManager a manager that manages <code>Room</code> entities
     * @param in          the instance of Scanner currently looking at the Console
     */
    public RoomController(EventManagerFacade eventManager, RoomManager roomManager, Scanner in) {
        super(in, new RoomPresenter());
        this.eventManager = eventManager;
        this.roomManager = roomManager;
        this.roomPresenter = new RoomPresenter();
    }

    /**
     * Processes an organizer's request to enter a new room into the system.
     * This method accepts information regarding the new room, checks if this information is valid,
     * and calls for the creation of the new room if so.
     */
    public void addNewRoom() {
        // Process room ID
        int roomID = promptInt(RoomPrompts.ROOM_ID_PROMPT, RoomPrompts.INVALID_NUM_ERROR);
        if (invalidRoomToAdd(roomID)) {
            return;
        }
        // Process room capacity
        int roomCapacity = promptInt(RoomPrompts.ROOM_CAPACITY_PROMPT, RoomPrompts.INVALID_NUM_ERROR);
        if (invalidRoomCapacity(roomCapacity)) {
            return;
        }
        roomManager.createNewRoom(roomID, roomCapacity);
        roomPresenter.display(RoomPrompts.ROOM_ADDED_SUCCESS);
    }

    private boolean invalidRoomToAdd(int roomID) {
        if (roomID <= 0) {
            roomPresenter.display(RoomPrompts.INVALID_NUM_ERROR);
            return true;
        } else if (roomManager.roomExists(roomID)) {
            roomPresenter.display(RoomPrompts.ROOM_EXISTS_ERROR);
            return true;
        }
        return false;
    }

    private boolean invalidRoomCapacity(int roomCapacity) {
        if (roomCapacity <= 0) {
            roomPresenter.display(RoomPrompts.INVALID_NUM_ERROR);
            return true;
        }
        return false;
    }

    /**
     * Processes the user's request to view all rooms entered in the system.
     * This method results in the output of the room numbers and capacity of all rooms.
     */
    public void viewRooms() {
        Map<Integer, Integer> roomInformation = roomManager.getRoomInformation();
        roomPresenter.displayRoomInformation(roomInformation);
    }

    /**
     * Deletes a room that has the given ID if the room has no events scheduled in it.
     * Precondition: roomID is the ID of an existing room in the conference.
     */
    public void deleteRoom() {
        int roomID = readRoomID();
        if (eventManager.roomHasAnEvent(roomID)) {
            roomPresenter.display(RoomPrompts.ROOM_HAS_EVENTS);
            return;
        }
        boolean check = roomManager.deleteRoom(roomID);
        if (check) {
            roomPresenter.display(RoomPrompts.ROOM_DELETE_SUCCESS);
        } else {
            roomPresenter.display(RoomPrompts.ROOM_DELETE_FAIL);
        }
    }

    /**
     * Reads and returns a roomID that the user enters.
     *
     * @return the roomID entered by the user
     */
    public int readRoomID() {
        return promptInt(RoomPrompts.DELETE_ROOM_PROMPT, RoomPrompts.INVALID_NUM_ERROR);
    }
}
