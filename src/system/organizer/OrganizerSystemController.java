package system.organizer;

import startup.AccountCreationController;
import system.ManagerParameterObject;
import system.general.SystemController;
import user.UserType;
import view.ViewController;

import java.util.*;

/**
 * The <code>OrganizerSystemController</code> class defines the specialized actions that an Organizer can do.
 * This class determines which additional menu options an Organizer sees once they log in and directs the requests
 * to complete those actions. These actions are generally related to input of new data into the program
 * and advanced capabilities for common actions such as viewing information and messaging.
 */
public class OrganizerSystemController extends SystemController {

    /**
     * Commands available to the user related to account creation-specific actions
     */
    protected final Map<String, Runnable> accountCreationCommands = new HashMap<>();
    /**
     * Commands available to the user related to conference organizing-specific actions
     */
    protected final Map<String, Runnable> conferenceOrganizingCommands = new HashMap<>();
    private final AccountCreationController accountCreationController;
    private final ViewController viewController;
    private final OrganizerSystemPresenter presenter;

    /**
     * Constructs a <code>OrganizerSystemController</code> object to process the organizer's requests.
     *
     * @param presenter an <code>OrganizerSystemPresenter</code> enabling the display of messages related to the menu
     * @param managers  the managers responsible for data during program execution
     * @param curUser   the username of the currently logged in user
     * @param in        the instance of Scanner currently looking at the Console
     */
    public OrganizerSystemController(OrganizerSystemPresenter presenter,
                                     ManagerParameterObject managers, String curUser, Scanner in) {
        super(managers, curUser, in);
        this.accountCreationController = new AccountCreationController(managers.getUserManager(), in);
        this.viewController = new ViewController(managers.getEventManager(), roomController, managers.getUserManager(),
                in);
        this.presenter = presenter;
        initializeAccountCreationCommands();
        initializeConferenceOrganizingCommands();
    }

    //<editor-fold desc="Initializing Menu Commands">

    /**
     * Initializes the commands available to the organizer in the main menu.
     */
    @Override
    protected void initializeMainCommands() {
        super.initializeMainCommands();
        mainCommands.put("5", this::processViewMenu);
        mainCommands.put("6", this::processConferenceOrganizingMenu);
        mainCommands.put("7", this::processAccountCreationMenu);
    }

    /**
     * Initializes the commands available to the organizer in the messaging menu.
     */
    @Override
    protected void initializeMessagingCommands() {
        super.initializeMessagingCommands();
        messagingCommands.put("6", () -> messageAllUsersOfType(UserType.ATTENDEE));
        messagingCommands.put("7", () -> messageAllUsersOfType(UserType.SPEAKER));
        messagingCommands.put("8", () -> messageUserOfType(UserType.ATTENDEE));
        messagingCommands.put("9", () -> messageUserOfType(UserType.SPEAKER));
    }

    /**
     * Initializes the commands available to the organizer in the account creation menu.
     */
    protected void initializeAccountCreationCommands() {
        accountCreationCommands.put("2", () -> accountCreationController.createMenuAccount(UserType.ATTENDEE));
        accountCreationCommands.put("3", () -> accountCreationController.createMenuAccount(UserType.SPEAKER));
        accountCreationCommands.put("4", () -> accountCreationController.createMenuAccount(UserType.ORGANIZER));
        accountCreationCommands.put("5", () -> accountCreationController.createMenuAccount(UserType.VIP));
        accountCreationCommands.put("6", () -> accountCreationController.createMenuAccount(UserType.ADMIN));
    }

    /**
     * Initializes the commands available to the organizer in the conference organization menu.
     */
    protected void initializeConferenceOrganizingCommands() {
        conferenceOrganizingCommands.put("2", eventController::addNewEvent);
        conferenceOrganizingCommands.put("3", roomController::addNewRoom);
        conferenceOrganizingCommands.put("4", this::addSpeakerToEvent);
        conferenceOrganizingCommands.put("5", eventController::deleteEvent);
        conferenceOrganizingCommands.put("6", roomController::deleteRoom);
        conferenceOrganizingCommands.put("7", this::deleteSpeakerFromEvent);
    }
    //</editor-fold>

    /**
     * Returns the presenter for this controller.
     *
     * @return an OrganizerSystemPresenter for this controller
     */
    @Override
    protected OrganizerSystemPresenter getPresenter() {
        return presenter;
    }

    //<editor-fold desc="Processing Organizer Menus">
    private void processViewMenu() {
        viewController.processViewMenu();
    }

    private void processAccountCreationMenu() {
        processMenu(() -> getPresenter().displayAccountMenu(), accountCreationCommands);
    }

    private void processConferenceOrganizingMenu() {
        processMenu(() -> getPresenter().displayConferenceOrganizingMenu(), conferenceOrganizingCommands);
    }
    //</editor-fold>

    //<editor-fold desc="Processing Message-Related Actions">

    /**
     * Messages a specific user of type <code>userType</code>.
     *
     * @param userType the type of user that can be messaged
     */
    private void messageUserOfType(UserType userType) {
        viewController.viewUsernamesOfType(userType);
        String recipient = messageController.readSingleRecipient();
        List<String> validRecipients = new ArrayList<>();
        if (userController.isUserOfType(recipient, userType)) {
            validRecipients.add(recipient);
        }
        messageController.sendMessage(curUser, validRecipients);
    }

    /**
     * Messages all users of type <code>userType</code>.
     *
     * @param userType the type of user to message
     */
    private void messageAllUsersOfType(UserType userType) {
        List<String> recipients = userController.getAllUsernamesOfType(userType);
        messageController.sendMessage(curUser, recipients);
    }
    //</editor-fold>

    //<editor-fold desc="Processing Event-Related Actions">

    /**
     * Schedules a speaker to an existing event.
     */
    private void addSpeakerToEvent() {
        viewController.viewUsernamesOfType(UserType.SPEAKER);
        String speaker = eventController.readSpeaker();
        boolean isSpeaker = userController.isUserOfType(speaker, UserType.SPEAKER);
        eventController.scheduleSpeakerToEvent(speaker, isSpeaker);
    }

    /**
     * Deletes a speaker from an existing event.
     */
    private void deleteSpeakerFromEvent() {
        viewController.viewUsernamesOfType(UserType.SPEAKER);
        String speaker = eventController.readSpeaker();
        boolean isSpeaker = userController.isUserOfType(speaker, UserType.SPEAKER);
        eventController.removeSpeakerFromEvent(speaker, isSpeaker);
    }
    //</editor-fold>
}
