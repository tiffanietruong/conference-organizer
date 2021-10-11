package system;

import event.manager.EventManagerFacade;
import messaging.manager.MessageManager;
import requests.RequestManager;
import room.RoomManager;
import user.manager.UserManagerFacade;

/**
 * A parameter object responsible for storing manager objects used in the program.
 */
public final class ManagerParameterObject {
    private final UserManagerFacade userManager;
    private final MessageManager messageManager;
    private final RoomManager roomManager;
    private final EventManagerFacade eventManager;
    private final RequestManager requestManager;

    /**
     * Constructs a ManagerParameterObject from the given managers.
     *
     * @param userManager    the manager responsible for user data during program execution
     * @param messageManager the manager responsible for message data during program execution
     * @param roomManager    the manager responsible for room data during program execution
     * @param eventManager   the manager responsible for event data during program execution
     * @param requestManager the manager responsible for request data during program execution
     */
    public ManagerParameterObject(UserManagerFacade userManager, MessageManager messageManager,
                                  RoomManager roomManager, EventManagerFacade eventManager, RequestManager requestManager) {
        this.userManager = userManager;
        this.messageManager = messageManager;
        this.roomManager = roomManager;
        this.eventManager = eventManager;
        this.requestManager = requestManager;
    }

    /**
     * Returns a manager responsible for user data.
     *
     * @return a user manager object
     */
    public UserManagerFacade getUserManager() {
        return userManager;
    }

    /**
     * Returns a manager responsible for message data.
     *
     * @return a message manager object
     */
    public MessageManager getMessageManager() {
        return messageManager;
    }

    /**
     * Returns a manager responsible for room data.
     *
     * @return a room manager object
     */
    public RoomManager getRoomManager() {
        return roomManager;
    }

    /**
     * Returns a manager responsible for event data.
     *
     * @return an event manager object
     */
    public EventManagerFacade getEventManager() {
        return eventManager;
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }
}
