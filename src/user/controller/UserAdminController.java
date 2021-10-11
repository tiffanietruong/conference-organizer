package user.controller;

import javafx.util.Pair;
import user.UserPresenter;
import user.UserPrompts;
import user.UserType;
import user.manager.UserManagerFacade;

import java.util.Scanner;

/**
 * The <code>UserAdminController</code> class is responsible for processing admin interactions with user accounts,
 * particularly the banning/unbanning and deleting accounts.
 */
public class UserAdminController extends UserTypeInputController<UserPrompts> {

    private final UserManagerFacade userManager;
    private final UserPresenter userPresenter;

    /**
     * Constructs a controller to process requests involving interactions with other users.
     *
     * @param userManager the manager responsible for user data during program execution
     * @param in          the instance of Scanner currently looking at the Console
     */
    public UserAdminController(UserManagerFacade userManager, Scanner in) {
        super(in, new UserPresenter());
        this.userManager = userManager;
        this.userPresenter = new UserPresenter();
    }


    /**
     * Changes a User's type. This method accepts input to determine which user is being modified and what their new
     * <code>UserType </code> is.
     *
     * @return a pair storing the username of the <code>User</code> being modified and their new <code>UserType</code>
     */
    public Pair<String, UserType> changeUserType() {
        String user = promptString(UserPrompts.CHANGE_TYPE_PROMPT);
        if (invalidUserToChange(user)) {
            return null;
        }
        UserType oldType = userManager.getUserType(user);
        userPresenter.displayUsernameAndType(user, oldType);
        UserType newType = promptUserType(UserPrompts.TYPE_CHANGE_PROMPT, UserPrompts.INVALID_INPUT_ERROR);
        if (invalidTypeToChangeTo(oldType, newType)) {
            return null;
        }
        userManager.changeUserType(user, newType);
        userPresenter.display(UserPrompts.TYPE_CHANGE_SUCCESS);
        return new Pair<>(user, oldType);
    }

    private boolean invalidUserToChange(String user) {
        if (!userManager.userExists(user)) {
            userPresenter.display(UserPrompts.USER_NOT_FOUND_ERROR);
            return true;
        }
        UserType curType = userManager.getUserType(user);
        if (curType == UserType.ADMIN || curType == UserType.ORGANIZER) {
            userPresenter.display(UserPrompts.CUR_TYPE_ERROR);
            return true;
        }
        return false;
    }

    private boolean invalidTypeToChangeTo(UserType oldType, UserType newType) {
        if (newType == UserType.ORGANIZER || newType == UserType.ADMIN) {
            userPresenter.display(UserPrompts.NEW_TYPE_ERROR);
            return true;
        } else if (oldType == newType) {
            userPresenter.display(UserPrompts.ALREADY_TYPE_ERROR);
            return true;
        }
        return false;
    }

    /**
     * Delete a user
     *
     * @return string of user deleted
     */
    public String deleteUser() {
        String user = promptString(UserPrompts.DELETE_NAME_PROMPT);
        boolean temp = userManager.deleteUser(user);
        if (temp) {
            userPresenter.display(UserPrompts.DELETE_SUCCESS);
            return user;
        } else {
            userPresenter.display(UserPrompts.DELETE_UNSUCCESSFUL);
            return null;
        }
    }

    /**
     * Display list of usernames of banned users
     */
    public void displayBannedUsers() {
        userPresenter.displayBanned(userManager.getBannedUsernames());
    }

    /**
     * Ban a user
     *
     * @param admin the username of the currently logged in admin
     * @return string of user banned
     */
    public String banUser(String admin) {
        String ban = promptString(UserPrompts.BAN_NAME_PROMPT);
        if (!userManager.userExists(ban)) {
            userPresenter.display(UserPrompts.USER_NOT_FOUND_ERROR);
            return null;
        }
        if (userManager.getUserType(ban) == UserType.ADMIN || userManager.getUserType(ban) == UserType.ORGANIZER) {
            userPresenter.display(UserPrompts.CANNOT_BAN_ERROR);
            return null;
        }
        String reason = promptString(UserPrompts.BAN_REASON_PROMPT);
        userManager.banUser(ban, admin, reason);
        userPresenter.display(UserPrompts.BAN_SUCCESS);
        return ban;
    }

    /**
     * Unban a user
     */
    public void unbanUser() {
        String unban = promptString(UserPrompts.UNBAN_NAME_PROMPT);
        if (!userManager.bannedUserExists(unban)) {
            userPresenter.display(UserPrompts.USER_NOT_FOUND_ERROR);
            return;
        }
        userManager.unBanUser(unban);
        userPresenter.display(UserPrompts.UNBAN_SUCCESS);
    }
}