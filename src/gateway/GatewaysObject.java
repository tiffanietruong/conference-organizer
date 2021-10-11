package gateway;

import event.manager.EventManagerFacade;
import messaging.manager.MessageManager;
import requests.RequestManager;
import room.RoomManager;
import system.ManagerParameterObject;
import user.manager.UserManagerFacade;

/**
 * A class which encapsulates the various gateways used in the program, and consolidates the saving and loading of
 * manager objects into one class.
 */
public class GatewaysObject {
    private final UserManagerGateway userGate;
    private final EventManagerGateway eventGate;
    private final MessageManagerGateway messageGate;
    private final RoomManagerGateway roomGate;
    private final RequestManagerGateway requestGate;

    /**
     * Creates a new <code>GatewaysObject</code>.
     */
    public GatewaysObject() {
        this.userGate = new UserManagerGateway();
        this.eventGate = new EventManagerGateway();
        this.messageGate = new MessageManagerGateway();
        this.roomGate = new RoomManagerGateway();
        this.requestGate = new RequestManagerGateway();
    }

    /**
     * Saves <code>userManager</code> to a .ser file.
     *
     * @param userManager the instance of <code>UserManager</code> to be saved
     */
    private void writeUsers(UserManagerFacade userManager) {
        userGate.saveManager(userManager);
    }

    /**
     * Saves <code>messageManager</code> to a .ser file.
     *
     * @param messageManager the instance of <code>MessageManager</code> to be saved
     */
    private void writeMessages(MessageManager messageManager) {
        messageGate.saveManager(messageManager);
    }

    /**
     * Saves <code>eventManager</code> to a .ser file.
     *
     * @param eventManager the instance of <code>EventManager</code> to be saved
     */
    private void writeEvents(EventManagerFacade eventManager) {
        eventGate.saveManager(eventManager);
    }

    /**
     * Saves <code>roomManager</code> to a .ser file.
     *
     * @param roomManager the instance of <code>RoomManager</code> to be saved
     */
    private void writeRooms(RoomManager roomManager) {
        roomGate.saveManager(roomManager);
    }

    /**
     * Saves <code>requestManager</code> to a .ser file.
     *
     * @param requestManager the instance of <code>RequestManager</code> to be saved
     */
    private void writeRequests(RequestManager requestManager) {
        requestGate.saveManager(requestManager);
    }

    /**
     * Saves all managers in <code>managers</code> to .ser files.
     * <p></p>
     * The saved managers consist of a UserManager, MessageManager, EventManager, and a RoomManager.
     *
     * @param managers the <code>ManagerParameterObject</code> whose stored managers are saved
     */
    public void writeManagers(ManagerParameterObject managers) {
        writeUsers(managers.getUserManager());
        writeMessages(managers.getMessageManager());
        writeEvents(managers.getEventManager());
        writeRooms(managers.getRoomManager());
        writeRequests(managers.getRequestManager());
    }

    /**
     * Gets a <code>ManagerParameterObject</code> consisting of managers loaded from .ser files.
     * <p></p>
     * The loaded managers consist of a UserManager, MessageManager, EventManager, and a RoomManager.
     *
     * @return an object consisting of managers loaded from .ser files
     */
    public ManagerParameterObject getManagers() {
        UserManagerFacade userManager = userGate.getSavedManager();
        MessageManager messageManager = messageGate.getSavedManager();
        EventManagerFacade eventManager = eventGate.getSavedManager();
        RoomManager roomManager = roomGate.getSavedManager();
        RequestManager requestManager = requestGate.getSavedManager();
        return new ManagerParameterObject(userManager, messageManager, roomManager, eventManager, requestManager);
    }
}