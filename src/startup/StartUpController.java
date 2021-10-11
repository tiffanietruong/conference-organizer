package startup;

import gateway.GatewaysObject;
import javafx.util.Pair;
import system.ManagerParameterObject;
import system.SystemControllerFactory;
import system.general.SystemController;
import user.UserType;

import java.util.Scanner;

/**
 * This class is responsible for coordinating the controllers and gateways that are used on program startup.
 */
public class StartUpController {
    private final Scanner in;
    private final SystemControllerFactory factory;
    private final StartUpPresenter startUpPresenter;
    private final AccountCreationController accountCreationController;
    private final GatewaysObject gateways;
    private final ManagerParameterObject managers;

    /**
     * Creates a new <code>StartUpController</code> object.
     */
    public StartUpController() {
        this.in = new Scanner(System.in);
        gateways = new GatewaysObject();
        managers = gateways.getManagers();
        startUpPresenter = new StartUpPresenter();
        accountCreationController = new AccountCreationController(managers.getUserManager(), in);
        factory = new SystemControllerFactory();
    }

    /**
     * Processes the user's initial input. This method runs the program until the user selects the option to quit
     * the the program entirely. At that point, this method initiates the writing of the program's data to files.
     */
    public void run() {
        boolean quit = processInitialRequest();
        while (!quit) {
            String username = login();
            UserType userType = managers.getUserManager().getUserType(username);
            SystemController system = factory.createSystemController(userType, managers, username, in);
            system.run();
            quit = processInitialRequest();
        }
        gateways.writeManagers(managers);
    }

    /**
     * Processes the user's initial input regarding whether to create an account, log in, or quit.
     *
     * @return <code>true</code> if and only if the user has selected to quit
     */
    private boolean processInitialRequest() {
        startUpPresenter.display(StartUpPrompts.WELCOME_MESSAGE);
        startUpPresenter.displayStartUpMenu();
        String input;
        while (true) {
            input = in.nextLine();
            switch (input) {
                case "1":
                    if (managers.getUserManager().organizerExists()) {
                        accountCreationController.createMenuAccount(UserType.ATTENDEE);
                    } else {
                        startUpPresenter.display(StartUpPrompts.ORGANIZER_CREATION);
                        accountCreationController.createMenuAccount(UserType.ORGANIZER);
                    }
                    // Display the main menu again after account creation
                    return processInitialRequest();
                case "2":
                    return false;
                case "3":
                    return true;
                default:
                    startUpPresenter.display(StartUpPrompts.INPUT_ERROR);
                    startUpPresenter.displayStartUpMenu();
                    break;
            }
        }
    }

    //<editor-fold desc="Log In Functionality">

    /**
     * Returns the username of the account that the user has successfully logged in to.
     * This method reads in the user's inputted login credentials until the input matches an existing account.
     *
     * @return the username of the account successfully logged in to
     */
    private String login() {
        Pair<String, String> credentials = inputCredentials();
        while (usernameIsInvalid(credentials.getKey()) || passwordIsInvalid(credentials)) {
            if (usernameIsBanned(credentials.getKey())) {
                startUpPresenter.displayBan(managers.getUserManager().getAdminForBan(credentials.getKey()),
                        managers.getUserManager().getReason(credentials.getKey()));
            } else if (usernameIsInvalid(credentials.getKey())) {
                startUpPresenter.display(StartUpPrompts.NO_SUCH_USERNAME_ERROR);
            } else {
                startUpPresenter.display(StartUpPrompts.WRONG_PASSWORD_ERROR);
            }
            credentials = inputCredentials();
        }
        startUpPresenter.displaySuccessMessage(credentials.getKey());
        return credentials.getKey();
    }

    private boolean usernameIsBanned(String username) {
        return managers.getUserManager().bannedUserExists(username);
    }

    private Pair<String, String> inputCredentials() {
        startUpPresenter.display(StartUpPrompts.USERNAME_PROMPT);
        String username = in.nextLine();
        if (usernameIsBanned(username)) {
            return new Pair<>(username, "");
        }
        if (usernameIsInvalid(username)) { /* prevent input from continuing if username is already invalid */
            return new Pair<>(username, "");
        }
        startUpPresenter.display(StartUpPrompts.PASSWORD_PROMPT);
        String password = in.nextLine();
        return new Pair<>(username, password);
    }

    private boolean usernameIsInvalid(String username) {
        return username.equals("") || !managers.getUserManager().userExists(username);
    }

    private boolean passwordIsInvalid(Pair<String, String> credentials) {
        return credentials.getValue().equals("") ||
                !managers.getUserManager().passwordMatches(credentials.getKey(), credentials.getValue());
    }

    //</editor-fold>

}
