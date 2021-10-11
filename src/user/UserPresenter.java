package user;

import java.util.EnumMap;
import java.util.List;

/**
 * The UserPresenter class is responsible for displaying messages related to actions involving user accounts
 * on the screen for the client. These messages include prompts, errors, success confirmations, and menus.
 * This responsibility implies that the class currently acts as a Presenter and a UI.
 */
public class UserPresenter extends UserTypeConsolePresenter<UserPrompts> {

    /**
     * Returns an <code>EnumMap</code> that maps each enum key of type <code>T</code> to its respective console message
     *
     * @return an <code>EnumMap</code> that maps each enum key of type <code>T</code> to its respective console message
     */
    @Override
    protected EnumMap<UserPrompts, String> initializeMessages() {
        EnumMap<UserPrompts, String> m = new EnumMap<>(UserPrompts.class);
        initializePrompts(m);
        initializeErrors(m);
        initializeSuccessMessages(m);
        return m;
    }

    private void initializePrompts(EnumMap<UserPrompts, String> m) {
        m.put(UserPrompts.USER_TYPE_PROMPT, "Please select a user type:");
        m.put(UserPrompts.ACTION_PROMPT, "Please select an action.");
        m.put(UserPrompts.ADD_FRIEND_PROMPT, "Please enter the username of the user you would like to add:");
        m.put(UserPrompts.REMOVE_FRIEND_PROMPT, "Please enter the username of the user you would like to remove:");
        m.put(UserPrompts.BAN_NAME_PROMPT, "Please enter the name of the user you would like to ban:");
        m.put(UserPrompts.UNBAN_NAME_PROMPT, "Please enter the name of the user you would like to unban:");
        m.put(UserPrompts.BAN_REASON_PROMPT, "Please enter the reason for why you are banning this user:");
        m.put(UserPrompts.DELETE_NAME_PROMPT, "Please enter the name of the user you wish to delete:");
        m.put(UserPrompts.CHANGE_TYPE_PROMPT, "Please enter the name of the user who will have their type changed:");
        m.put(UserPrompts.TYPE_CHANGE_PROMPT, "Please enter the type you would like to change this user to:");
    }

    private void initializeErrors(EnumMap<UserPrompts, String> m) {
        m.put(UserPrompts.INVALID_INPUT_ERROR, "Sorry, the input you entered was not valid.");
        m.put(UserPrompts.USER_NOT_FOUND_ERROR, "Sorry, that user does not exist.");
        m.put(UserPrompts.NO_USERS_OF_TYPE_ERROR, "Sorry, no users of this type were found.");
        m.put(UserPrompts.ALREADY_FRIENDS_ERROR, "Sorry, you are already friends with this person.");
        m.put(UserPrompts.NOT_FRIENDS_ERROR, "Sorry, this person is not in your friends list.");
        m.put(UserPrompts.EMPTY_FRIENDS_LIST_ERROR,
                "Sorry, you currently do not have any friends in your friends list.");
        m.put(UserPrompts.ADD_YOURSELF_ERROR, "Sorry, you cannot add yourself to your own friends list.");
        m.put(UserPrompts.ATTENDEE_ADD_ORGANIZER_ERROR, "Sorry, you cannot add an Organizer to your friends list.");
        m.put(UserPrompts.CUR_TYPE_ERROR, "Sorry, you cannot change the types of Organizers or other Admins.");
        m.put(UserPrompts.NEW_TYPE_ERROR, "Sorry, you cannot change users to Organizers or Admins.");
        m.put(UserPrompts.DELETE_UNSUCCESSFUL, "Sorry, the user does not exist or is an Admin/Organizer.");
        m.put(UserPrompts.CANNOT_BAN_ERROR, "Sorry, you cannot ban Organizers or other Admins.");
        m.put(UserPrompts.ALREADY_TYPE_ERROR, "Sorry, this user is already that type.");
    }

    private void initializeSuccessMessages(EnumMap<UserPrompts, String> m) {
        m.put(UserPrompts.ADD_FRIEND_SUCCESS, "You have successfully added this user to your friends list.");
        m.put(UserPrompts.REMOVE_FRIEND_SUCCESS, "You have successfully removed this user from your friends list.");
        m.put(UserPrompts.BAN_SUCCESS, "User has been banned.");
        m.put(UserPrompts.UNBAN_SUCCESS, "User has been unbanned.");
        m.put(UserPrompts.DELETE_SUCCESS, "User successfully deleted.");
        m.put(UserPrompts.TYPE_CHANGE_SUCCESS, "Type change successful.");
    }

    /**
     * Prints the menu of options related to managing a user's friends list to the screen.
     */
    public void displayFriendsListMenu() {
        System.out.println("===== FRIENDS LIST ACTIONS ======");
        System.out.println("1 - Return to the main menu");
        System.out.println("2 - View your friends list");
        System.out.println("3 - Add a user to your friends list");
        System.out.println("4 - Remove a user from your friends list");
    }

    /**
     * Display list of banned users
     */
    public void displayBanned(String users) {
        String temp = "List of Banned Users: " + "\n";
        temp = temp + users;
        System.out.println(temp);
    }

    /**
     * Prints all the usernames of accounts in the user's friends list to the screen.
     * This method gives a specific error message if the user's friends list is empty.
     *
     * @param friendsList the list of usernames to be printed to the screen
     */
    public void displayFriendsList(List<String> friendsList) {
        System.out.println("===== YOUR FRIENDS LIST =====");
        if (friendsList.isEmpty()) {
            display(UserPrompts.EMPTY_FRIENDS_LIST_ERROR);
        } else {
            displayContents(friendsList);
        }
    }

    /**
     * Displays a menu of all user types
     */
    public void displayUserTypes() {
        System.out.println("1 - ATTENDEE");
        System.out.println("2 - ORGANIZER");
        System.out.println("3 - SPEAKER");
        System.out.println("4 - VIP");
        System.out.println("5 - ADMIN");
    }

    /**
     * Displays a username and user type
     *
     * @param user a username
     * @param type the user type of <code>user</code>
     */
    public void displayUsernameAndType(String user, UserType type) {
        System.out.println(user + " is currently a " + type);
    }
}