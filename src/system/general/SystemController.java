package system.general;

import event.controller.EventControllerFacade;
import messaging.controller.MessageControllerFacade;
import requests.RequestController;
import room.RoomController;
import signup.SignUpController;
import system.ManagerParameterObject;
import user.UserType;
import user.controller.UserController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The SystemController class defines the common actions that all users can do. This class determines which menu
 * options the user sees once they log in and directs the requests to complete those actions.
 */
public abstract class SystemController {

    /**
     * Username of the current user
     */
    protected final String curUser;
    /**
     * Scanner for reading input
     */
    protected final Scanner in;
    /**
     * Controller responsible for handling events
     */
    protected final EventControllerFacade eventController;
    /**
     * Controller responsible for handling users
     */
    protected final UserController userController;
    /**
     * Controller responsible for handling messaging
     */
    protected final MessageControllerFacade messageController;
    /**
     * Controller responsible for handling rooms
     */
    protected final RoomController roomController;
    /**
     * Controller responsible for handling signup for events
     */
    protected final SignUpController signUpController;
    /**
     * Controller responsible for handling special requests
     */
    protected final RequestController requestController;
    /**
     * Commands available to the user related to the main menu actions
     */
    protected final Map<String, Runnable> mainCommands = new HashMap<>();
    /**
     * Commands available to the user related to events
     */
    protected final Map<String, Runnable> eventCommands = new HashMap<>();
    /**
     * Commands available to the user related to messaging
     */
    protected final Map<String, Runnable> messagingCommands = new HashMap<>();

    /**
     * Constructs a controller to process the user's requests given a menu of options.
     *
     * @param managers the managers responsible for data during program execution
     * @param curUser  the username of the currently logged in user
     * @param in       the instance of Scanner currently looking at the Console
     */
    public SystemController(ManagerParameterObject managers, String curUser, Scanner in) {
        this.eventController = new EventControllerFacade(managers.getEventManager(), managers.getRoomManager(), in);
        this.userController = new UserController(managers.getUserManager(), in);
        this.roomController = new RoomController(managers.getEventManager(), managers.getRoomManager(), in);
        this.messageController = new MessageControllerFacade(in, managers.getMessageManager());
        this.curUser = curUser;
        this.in = in;
        this.signUpController = new SignUpController(in, managers.getEventManager());
        this.requestController = new RequestController(managers.getRequestManager(), managers.getUserManager(), in);
        initializeMainCommands();
        initializeEventCommands();
        initializeMessagingCommands();
    }

    //<editor-fold desc="Initializing Menu Commands">

    /**
     * Initializes the commands available to the user in the main menu.
     */
    protected void initializeMainCommands() {
        mainCommands.put("2", this::processEventMenu);
        mainCommands.put("3", () -> userController.manageFriendsList(curUser));
        mainCommands.put("4", this::processMessagingMenu);
    }

    /**
     * Initializes the commands available to the user in the event menu.
     */
    protected void initializeEventCommands() {
        eventCommands.put("2", eventController::displayCompleteSchedule);
        eventCommands.put("3", eventController::viewReviews);
        eventCommands.put("4", signUpController::searchForEvents);
        eventCommands.put("5", () -> signUpController.signUpForEvent(curUser, UserType.ATTENDEE));
        eventCommands.put("6", () -> signUpController.cancelSpotInEvent(curUser, UserType.ATTENDEE));
        eventCommands.put("7", () -> eventController.displayUserSchedule(curUser));
    }

    /**
     * Initializes the commands available to the user in the messaging menu.
     */
    protected void initializeMessagingCommands() {
        messagingCommands.put("2", this::processInboxViewing);
        messagingCommands.put("3", () -> messageController.viewArchives(curUser));
        messagingCommands.put("4", this::messageFriend);
        messagingCommands.put("5", () -> requestController.requestMenu(curUser));
    }
    //</editor-fold>

    /**
     * Returns the presenter for this controller.
     *
     * @return a SystemPresenter for this controller
     */
    abstract protected SystemPresenter getPresenter();

    /**
     * Reads in and processes a user's request to complete an action until they choose to log out.
     */
    public void run() {
        processMenu(() -> getPresenter().displayMenus(), mainCommands);
    }

    //<editor-fold desc="Action Menus">
    private void processEventMenu() {
        processMenu(() -> getPresenter().displayEventMenu(), eventCommands);
    }

    private void processMessagingMenu() {
        processMenu(() -> getPresenter().displayMessagingMenu(), messagingCommands);
    }
    //</editor-fold>

    //<editor-fold desc="General Menu Processing">

    /**
     * Processes the user's interaction with a menu until the user chooses to quit.
     *
     * @param displayMenu a method that displays the menu to the user
     * @param commands    the commands available to the user through the menu
     */
    protected void processMenu(Runnable displayMenu, Map<String, Runnable> commands) {
        boolean quit = false;
        String input;
        while (!quit) {
            getPresenter().promptForAction();
            displayMenu.run();
            input = in.nextLine();
            quit = processMenuInput(input, commands);
        }
    }

    /**
     * Processes a given input provided by the user in response to a menu.
     *
     * @param input    the user's input
     * @param commands the commands available to the user through the menu
     * @return <code>true</code> iff the user chose to quit the menu
     */
    private boolean processMenuInput(String input, Map<String, Runnable> commands) {
        if (input.equals("1")) {
            return true;
        } else if (commands.containsKey(input)) {
            commands.get(input).run();
        } else {
            getPresenter().displayInvalidInputError();
        }
        return false;
    }
    //</editor-fold>

    //<editor-fold desc="Processing Message-Related Actions">
    private void processInboxViewing() {
        messageController.viewInbox(curUser);
        messageController.processInboxRequest(curUser);
    }

    private void messageFriend() {
        userController.viewFriendsList(curUser);
        if (userController.userHasFriends(curUser)) {
            List<String> recipients = messageController.readRecipients();
            recipients = userController.filterForExistingUsernames(recipients);
            recipients = userController.filterForFriendsUsernames(recipients, curUser);
            messageController.sendMessage(curUser, recipients);
        }
    }
    //</editor-fold>

}
