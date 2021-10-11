package user.manager;

import user.User;
import user.UserType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The <code>UserManager</code> class is responsible for storing the data of all existing users during program execution
 * and processes requests involving user accounts.
 */
public class UserManagerFacade implements Serializable {

    private final UserInformationManager userInformationManager;
    private final UserManipulationManager userManipulationManager;

    /**
     * Constructs a manager responsible for user account data that is initialized storing an empty list of users.
     */
    public UserManagerFacade() {
        List<User> users = new ArrayList<>();
        List<User> banned = new ArrayList<>();
        IUserRepository userRepository = new UserRepository(users, banned);
        userInformationManager = new UserInformationManager(userRepository);
        userManipulationManager = new UserManipulationManager(userRepository);
    }

    //<editor-fold desc="Getting Information">

    /**
     * Gets reason for ban
     *
     * @param user the username of user that has been banned
     * @return reason for ban
     */
    public String getReason(String user) {
        return userInformationManager.getReason(user);
    }

    /**
     * Gets admin who issued ban
     *
     * @param user the username of user that has been banned
     * @return admin who issued ban's username
     */
    public String getAdminForBan(String user) {
        return userInformationManager.getAdminForBan(user);
    }

    /**
     * Returns the list of banned users' usernames separated by a line
     *
     * @return usernames of banned users in a string separated by new lines
     */
    public String getBannedUsernames() {
        return userInformationManager.getBannedUsernames();
    }

    /**
     * Returns true if there exists a <code>User</code> with the specified username.
     *
     * @param username the username of a potential existing <code>User</code>
     * @return true if there exists a <code>User</code> with the specified username
     */
    public boolean userExists(String username) {
        return userInformationManager.userExists(username);
    }

    /**
     * Returns true if there exists a banned <code>User</code> with the specified username.
     *
     * @param username the username of a potential existing banned <code>User</code>
     * @return true if there exists a banned <code>User</code> with the specified username
     */
    public boolean bannedUserExists(String username) {
        return userInformationManager.bannedUserExists(username);
    }

    /**
     * Returns true if there exists a <code>User</code> that has type organizer
     *
     * @return true if there exists a <code>User</code> with type organizer, false otherwise
     */
    public boolean organizerExists() {
        return userInformationManager.organizerExists();
    }

    /**
     * Returns true if there exists a <code>User</code> whose login credentials match the given username and password.
     *
     * @param username the username of a potential existing <code>User</code>
     * @param password the password of a potential existing <code>User</code>
     * @return true if there exists a <code>User</code> whose credentials match the given username and password
     */
    public boolean passwordMatches(String username, String password) {
        return userInformationManager.passwordMatches(username, password);
    }

    /**
     * Returns the <code>UserType</code> of a <code>User</code> who has the given username.
     * Precondition: username is the username of an existing <code>User</code>
     *
     * @param username the username of the desired <code>User</code>
     * @return the <code>UserType</code> of a <code>User</code> who has the given username
     */
    public UserType getUserType(String username) {
        return userInformationManager.getUserType(username);
    }

    /**
     * Returns true if there exists a <code>User</code> whose username is <code>user</code> and
     * is friends with a <code>User</code> whose username is <code>potentialFriend</code>.
     *
     * @param user            the username of the user's whose friends list is being checked
     * @param potentialFriend the username of the potential friend in the first user's friends list
     * @return true if there exists a <code>User</code> whose username is <code>user</code> and is friends with a <code>User</code>
     * whose username is <code>potentialFriend</code>
     */
    public boolean areFriends(String user, String potentialFriend) {
        return userInformationManager.areFriends(user, potentialFriend);
    }

    /**
     * Returns a list of usernames corresponding to accounts in the given user's friends list.
     *
     * @param curUser the username of the <code>User</code> whose friends list is being accessed
     * @return a list of usernames corresponding to accounts in the user's friends list.
     */
    public List<String> getFriendsList(String curUser) {
        return userInformationManager.getFriendsList(curUser);
    }

    /**
     * Returns a list of usernames corresponding to users that are of the specified <code>UserType</code> .
     *
     * @param userType the type of <code>User</code> such that usernames of users of that type will be returned
     * @return a list of usernames corresponding to <code>Users</code> that are of userType
     */
    public List<String> getAllUsernamesOfType(UserType userType) {
        return userInformationManager.getAllUsernamesOfType(userType);
    }
    //</editor-fold>

    //<editor-fold desc="Manipulating Users">

    /**
     * Changes the given user's associated <code>UserType</code> to be <code>type</code>.
     *
     * @param user    the username of the user whose <code>UserType</code> is being modified
     * @param newType the new <code>UserType</code>> to be assigned to the given user
     */
    public void changeUserType(String user, UserType newType) {
        userManipulationManager.changeUserType(user, newType);
    }

    /**
     * Bans a user
     *
     * @param username the username of the user to be banned
     * @param admin    the username of the admin who issued the ban
     * @param reason   the reason the admin banned this user
     */
    public void banUser(String username, String admin, String reason) {
        userManipulationManager.banUser(username, admin, reason);
    }

    /**
     * Unbans a user
     *
     * @param username the username of user to be unbanned
     */
    public void unBanUser(String username) {
        userManipulationManager.unBanUser(username);
    }

    /**
     * Delete a user
     *
     * @param username the username of the user being deleted
     */
    public boolean deleteUser(String username) {
        return userManipulationManager.deleteUser(username);
    }

    /**
     * Adds a new <code>User</code> with the given <code>username</code>, <code>password</code>, and
     * <code>UserType</code> to the list of existing <code>Users</code>.
     *
     * @param username the username of the <code>User</code> to be added to the list of existing users
     * @param password the password of the <code>User</code> to be added to the list of existing users
     * @param userType the <code>UserType</code> of the <code>User</code> to be added to the list of existing users
     */
    public void addUser(String username, String password, UserType userType) {
        userManipulationManager.addUser(username, password, userType);
    }


    /**
     * Adds the <code>User</code> with username <code>addedUser</code> to the friends list of the user with
     * username <username>requestingUser</username>.
     * <p>
     * Precondition: <code>requestingUser</code> and <code>addedUser</code> are the usernames of existing
     * <code>Users</code>, and <code>addedUser</code> is not in <code>requestingUser</code>'s friends list
     *
     * @param requestingUser the username of the user whose friends list is being modified
     * @param addedUser      the username of the user to be added to the first user's friends list
     */
    public void addFriend(String requestingUser, String addedUser) {
        userManipulationManager.addFriend(requestingUser, addedUser);
    }

    /**
     * Removes the <code>User</code> with username <code>removedUser</code> from the friends list of the
     * <code>User</code> with username <code>requestingUser</code>.
     * <p>
     * Precondition: <code>requestingUser</code> and <code>removedUser</code> are the usernames of existing
     * <code>Users</code>, and <code>removedUser</code> is exists in  <code>requestingUser</code>'s friends list
     *
     * @param requestingUser the username of the user whose friends list is being modified
     * @param removedUser    the username of the user to be added to the first user's friends list
     */
    public void removeFriend(String requestingUser, String removedUser) {
        userManipulationManager.removeFriend(requestingUser, removedUser);
    }
    //</editor-fold>
}
