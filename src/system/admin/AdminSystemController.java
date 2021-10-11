package system.admin;

import javafx.util.Pair;
import raffle.RaffleController;
import system.ManagerParameterObject;
import system.general.SystemController;
import user.UserType;
import user.controller.UserAdminController;
import view.ViewController;

import java.util.*;

/**
 * The <code>AdminSystemController</code> class defines the specialized actions that an Admin can do.
 * This class determines which additional menu options an Admin sees once they log in and directs the requests
 * to complete those actions. These actions are related to banning users and managing the raffle system.
 */
public class AdminSystemController extends SystemController {
    /**
     * Controller responsible for handling admin related actions
     */
    protected final UserAdminController userAdminController;
    /**
     * Commands available to the user related to admin-specific actions
     */
    protected final Map<String, Runnable> adminCommands = new HashMap<>();
    private final ViewController viewController;
    private final RaffleController raffleController;
    private final AdminSystemPresenter presenter;

    /**
     * Constructs a <code>SpeakerSystemController</code> object to process the speaker's requests.
     *
     * @param presenter an <code>AdminSystemPresenter</code> that enables the display of messages related to the menu
     * @param managers  the managers responsible for data during program execution
     * @param curUser   the username of the currently logged in user
     * @param in        the instance of Scanner currently looking at the Console
     */
    public AdminSystemController(AdminSystemPresenter presenter,
                                 ManagerParameterObject managers, String curUser, Scanner in) {
        super(managers, curUser, in);
        this.userAdminController = new UserAdminController(managers.getUserManager(), in);
        this.viewController = new ViewController(managers.getEventManager(), roomController, managers.getUserManager(),
                in);
        this.raffleController = new RaffleController(managers.getUserManager());
        this.presenter = presenter;
        initializeAdminCommands();
    }

    /**
     * Returns the presenter for this controller.
     *
     * @return an AdminSystemPresenter for this controller
     */
    @Override
    protected AdminSystemPresenter getPresenter() {
        return presenter;
    }

    //<editor-fold desc="Initializing Menu Commands">

    /**
     * Initializes the commands available to the admin in the main menu.
     */
    @Override
    protected void initializeMainCommands() {
        super.initializeMainCommands();
        mainCommands.put("5", this::processAdminMenu);
    }

    /**
     * Initializes the commands available to the admin in the specialized admin menu.
     */
    protected void initializeAdminCommands() {
        adminCommands.put("2", this::banUser);
        adminCommands.put("3", userAdminController::displayBannedUsers);
        adminCommands.put("4", userAdminController::unbanUser);
        adminCommands.put("5", this::unbanUser);
        adminCommands.put("6", this::changeUserType);
        adminCommands.put("7", this::pullRaffleWinner);
    }
    //</editor-fold>

    private void processAdminMenu() {
        processMenu(() -> getPresenter().displayAdminMenu(), adminCommands);
    }

    private void banUser() {
        viewController.viewUsernamesOfType(UserType.ATTENDEE);
        viewController.viewUsernamesOfType(UserType.SPEAKER);
        String userToBan = userAdminController.banUser(curUser);
        if (userToBan != null) {
            eventController.removeUserFromAllEvents(userToBan);
            eventController.removeSpeakerFromAllEvents(userToBan);
        }
    }

    private void unbanUser() {
        viewController.viewUsernamesOfType(UserType.ATTENDEE);
        viewController.viewUsernamesOfType(UserType.SPEAKER);
        String userToUnban = userAdminController.deleteUser();
        if (userToUnban != null) {
            eventController.removeUserFromAllEvents(userToUnban);
            eventController.removeSpeakerFromAllEvents(userToUnban);
        }
    }

    /**
     * Changes the user type of a specific user.
     */
    private void changeUserType() {
        Pair<String, UserType> userAndOldType = userAdminController.changeUserType();
        if (userAndOldType != null) {
            String username = userAndOldType.getKey();
            UserType oldType = userAndOldType.getValue();
            if (oldType == UserType.SPEAKER) {
                eventController.removeSpeakerFromAllEvents(username);
            } else if (oldType == UserType.VIP) {
                eventController.removeUserFromAllVIPEvents(username);
            }
        }
    }

    /**
     * Gets and processes a raffle winner.
     */
    private void pullRaffleWinner() {
        List<String> recipient = new ArrayList<>();
        String winner = raffleController.pullWinner();
        if (!winner.equals("")) {
            recipient.add(winner);
            messageController.sendMessage(curUser, recipient);
        }
    }
}