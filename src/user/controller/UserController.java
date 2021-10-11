package user.controller;

import system.console.ConsoleInputController;
import user.UserPresenter;
import user.UserPrompts;
import user.UserType;
import user.manager.UserManagerFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The <code>UserController</code> class is responsible for processing interactions involving user accounts,
 * particularly the request of a client to manage their friends list. Requests to find information about specific
 * user accounts are also managed by this class.
 */
public class UserController extends ConsoleInputController<UserPrompts> {

    private final UserManagerFacade userManager;
    private final UserPresenter userPresenter;
    private final Scanner in;

    /**
     * Constructs a controller to process requests involving interactions with other users.
     *
     * @param userManager the manager responsible for user data during program execution
     * @param in          the instance of Scanner currently looking at the Console
     */
    public UserController(UserManagerFacade userManager, Scanner in) {
        super(in, new UserPresenter());
        this.userManager = userManager;
        this.userPresenter = new UserPresenter();
        this.in = in;
    }

    /**
     * Processes the user's request to manage their friends list.
     * This method accepts user input that requests to
     * (1) view their friends list,
     * (2) add a friend to their friends list, or
     * (3) remove a friend from their friends list.
     * It accepts user input until the user requests to return to the main menu.
     *
     * @param curUser the username of the currently logged in user
     */
    public void manageFriendsList(String curUser) {
        boolean quit = false;
        String input;
        while (!quit) {
            userPresenter.display(UserPrompts.ACTION_PROMPT);
            userPresenter.displayFriendsListMenu();
            input = in.nextLine();
            switch (input) {
                case "1":
                    quit = true;
                    break;
                case "2":
                    viewFriendsList(curUser);
                    break;
                case "3":
                    addFriend(curUser);
                    break;
                case "4":
                    removeFriend(curUser);
                    break;
                default:
                    userPresenter.display(UserPrompts.INVALID_INPUT_ERROR);
            }
        }
    }

    /**
     * Processes the user's request to display all usernames in their friends list.
     *
     * @param username the username of the currently logged in user
     */
    public void viewFriendsList(String username) {
        List<String> friendsList = userManager.getFriendsList(username);
        userPresenter.displayFriendsList(friendsList);
    }

    /**
     * Processes the user's request to add a user to their friends list.
     *
     * @param username the username of the currently logged in user
     */
    public void addFriend(String username) {
        String friendUsername = promptString(UserPrompts.ADD_FRIEND_PROMPT);
        if (validUserToAdd(username, friendUsername)) {
            userPresenter.display(UserPrompts.ADD_FRIEND_SUCCESS);
            userManager.addFriend(username, friendUsername);
        }
    }

    /**
     * Returns true if the potential friend can be added to the user's friends list.
     * This occurs when the username of the potential friend belongs to an existing user other than the current user
     * that is not already in the user's friends list.
     *
     * @param username        the username of the user whose friends list is being modified
     * @param potentialFriend the username of the potential friend to be added to the user's friends list
     * @return true if the potential friend can be added to the user's friends list
     */
    private boolean validUserToAdd(String username, String potentialFriend) {
        if (username.equals(potentialFriend)) {
            userPresenter.display(UserPrompts.ADD_YOURSELF_ERROR);
            return false;
        } else if (!userManager.userExists(potentialFriend)) {
            userPresenter.display(UserPrompts.USER_NOT_FOUND_ERROR);
            return false;
        } else if (userManager.areFriends(username, potentialFriend)) {
            userPresenter.display(UserPrompts.ALREADY_FRIENDS_ERROR);
            return false;
        } else if (userManager.getUserType(username) == UserType.ATTENDEE &&
                userManager.getUserType(potentialFriend) == UserType.ORGANIZER) {
            userPresenter.display(UserPrompts.ATTENDEE_ADD_ORGANIZER_ERROR);
            return false;
        }
        return true;
    }


    /**
     * Processes the user's request to remove a friend from their friends list.
     * This method immediately returns if the user does not have any friends in their friends list.
     *
     * @param username the username of the currently logged in user
     */
    public void removeFriend(String username) {
        if (userManager.getFriendsList(username).isEmpty()) {
            userPresenter.display(UserPrompts.EMPTY_FRIENDS_LIST_ERROR);
            return;
        }
        String friendUsername = promptString(UserPrompts.REMOVE_FRIEND_PROMPT);
        if (validUserToRemove(username, friendUsername)) {
            userManager.removeFriend(username, friendUsername);
            userPresenter.display(UserPrompts.REMOVE_FRIEND_SUCCESS);
        }
    }

    /**
     * Returns true if the given friend can be removed from the user's friends list.
     * This occurs when a friend with the given username exists in the user's friends list.
     *
     * @param username         the username of the user whose friends list is being modified
     * @param usernameOfFriend the username of the friend to remove from the user's friends list
     * @return true if the given friend can be removed from the user's friends list
     */
    private boolean validUserToRemove(String username, String usernameOfFriend) {
        if (!userManager.userExists(usernameOfFriend) || !userManager.areFriends(username, usernameOfFriend)) {
            userPresenter.display(UserPrompts.NOT_FRIENDS_ERROR);
            return false;
        }
        return true;
    }

    /**
     * Returns a list of usernames such that a username is in the list if and only if it matches the
     * username of an existing account.
     *
     * @param usernames the list of usernames to be filtered
     * @return a list of usernames corresponding to existing accounts
     */
    public List<String> filterForExistingUsernames(List<String> usernames) {
        List<String> validUsernames = new ArrayList<>();
        for (String username : usernames) {
            if (userManager.userExists(username)) {
                validUsernames.add(username);
            }
        }
        return validUsernames;
    }

    /**
     * Returns a list of usernames such that a username is in the list if and only if it matches the
     * username of an account in the given user's friends list.
     *
     * @param usernames the list of usernames to be filtered
     * @param user      the user whose friend's list is being referenced for a match
     * @return a list of usernames such that a username is in the list if and only if it matches the
     * username of an account in the given user's friends list
     */
    public List<String> filterForFriendsUsernames(List<String> usernames, String user) {
        List<String> friendsNames = new ArrayList<>();
        for (String username : usernames) {
            if (userManager.areFriends(user, username)) {
                friendsNames.add(username);
            }
        }
        return friendsNames;
    }

    /**
     * Gets a list of usernames such that a username is in the list if and only if it corresponds to a
     * <code>User</code> of the specified <code>UserType</code>.
     *
     * @param userType the <code>UserType</code> of the users that are searched for
     * @return a list of usernames such that a username is in the list if and only if it corresponds to a
     * <code>User</code> of the specified <code>UserType</code>
     */
    public List<String> getAllUsernamesOfType(UserType userType) {
        return userManager.getAllUsernamesOfType(userType);
    }

    /**
     * Returns true if the given <code>username</code> corresponds to a user of the specified <code>UserType</code>.
     *
     * @param username the username of the user whose <code>UserType</code> is being checked
     * @param userType the <code>UserType</code> being compared to that of the specified user's
     * @return true if the given <code>username</code> corresponds to a user of the specified <code>UserType</code>
     */
    public boolean isUserOfType(String username, UserType userType) {
        if (userManager.userExists(username)) {
            return userManager.getUserType(username) == userType;
        }
        return false;
    }


    /**
     * Returns whether or not the user with name <code>username</code> has at least one friend.
     *
     * @param username the name of the user for whom the existence of friends is checked
     * @return <code>true</code> if and only if <code>username</code> has at least one friend
     */
    public boolean userHasFriends(String username) {
        return !userManager.getFriendsList(username).isEmpty();
    }

}
