package startup;

import system.console.ConsoleInputController;
import user.UserType;
import user.manager.UserManagerFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * This class interacts with the user to facilitate account creation.
 */
public class AccountCreationController extends ConsoleInputController<AccountCreationPrompts> {

    private final AccountCreationPresenter presenter;
    private final UserManagerFacade userManager;

    /**
     * Creates a new <code>AccountCreationController</code> object.
     *
     * @param userManager a <code>UserManager</code> object containing all users
     * @param in          a <code>Scanner</code> object to be used for taking input
     */
    public AccountCreationController(UserManagerFacade userManager, Scanner in) {
        super(in, new AccountCreationPresenter());
        this.userManager = userManager;
        this.presenter = new AccountCreationPresenter();
    }

    /**
     * Creates a new user account according to user input.
     *
     * @param userType the type of the user to be created
     */
    public void createMenuAccount(UserType userType) {
        String username = solicitUsername();
        String password = solicitPassword();
        userManager.addUser(username, password, userType);
    }

    private String solicitUsername() {
        while (true) {
            String username = promptString(AccountCreationPrompts.USERNAME_PROMPT);
            if (username.contains(" ")) {
                presenter.display(AccountCreationPrompts.USERNAME_HAS_SPACE_CHAR);
            } else if (userManager.userExists(username) || userManager.bannedUserExists(username)) {
                presenter.display(AccountCreationPrompts.USERNAME_IN_USE);
                int i = 0;
                boolean alternate = false;
                String tempName;
                while (!alternate) {
                    i++;
                    tempName = username + i;
                    if (!userManager.userExists(tempName) && !userManager.bannedUserExists(tempName)) {
                        alternate = true;
                        presenter.displayRecommendedUsername(tempName);
                    }
                }
            } else {
                presenter.display(AccountCreationPrompts.USERNAME_SUCCESS);
                return username;
            }
        }
    }

    /**
     * Creates a password using regex recommendations
     */
    private String solicitPassword() {
        String password;
        List<Predicate<String>> validators = getValidators();
        boolean valid;
        do {
            password = promptString(AccountCreationPrompts.PASSWORD_PROMPT);
            valid = true;
            for (Predicate<String> validator : validators) {
                if (!validator.test(password)) {
                    valid = false;
                }
            }
        } while (!valid);
        presenter.display(AccountCreationPrompts.PASSWORD_SUCCESS);
        return password;
    }

    private List<Predicate<String>> getValidators() {
        return new ArrayList<>(
                Arrays.asList(this::validateSpecialCharacter,
                        this::validateLowerCase,
                        this::validateUppercase,
                        this::validateNumber,
                        this::validateLength)
        );
    }

    private boolean validateSpecialCharacter(String password) {
        Pattern specialChar = Pattern.compile("[^a-zA-Z0-9\\s]");
        boolean hasSpecial = specialChar.matcher(password).find();
        if (!hasSpecial) {
            presenter.display(AccountCreationPrompts.PASSWORD_NO_SPECIAL);
            return false;
        }
        return true;
    }

    private boolean validateLowerCase(String password) {
        Pattern lowercase = Pattern.compile("[a-z]");
        boolean hasLowercase = lowercase.matcher(password).find();
        if (!hasLowercase) {
            presenter.display(AccountCreationPrompts.PASSWORD_NO_LOWER);
            return false;
        }
        return true;
    }

    private boolean validateUppercase(String password) {
        Pattern uppercase = Pattern.compile("[A-Z]");
        boolean hasUppercase = uppercase.matcher(password).find();
        if (!hasUppercase) {
            presenter.display(AccountCreationPrompts.PASSWORD_NO_CAPITAL);
            return false;
        }
        return true;
    }

    private boolean validateNumber(String password) {
        Pattern number = Pattern.compile("[0-9]");
        boolean hasNumber = number.matcher(password).find();
        if (!hasNumber) {
            presenter.display(AccountCreationPrompts.PASSWORD_NO_NUMBER);
            return false;
        }
        return true;
    }

    private boolean validateLength(String password) {
        if (password.length() < 8) {
            presenter.display(AccountCreationPrompts.PASSWORD_NO_LENGTH);
            return false;
        }
        return true;
    }

}
