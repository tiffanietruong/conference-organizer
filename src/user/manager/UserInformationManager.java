package user.manager;

import user.User;
import user.UserType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The <code>UserInformationManager</code> class is responsible for retrieving and providing user information.
 */
public class UserInformationManager implements Serializable {
    private final IUserRepository userRepository;

    /**
     * Constructs a manager responsible for providing data about users.
     *
     * @param userRepository a <code>UserRepository</code> object containing the users in the conference
     */
    public UserInformationManager(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Returns true if there exists a <code>User</code> that has type organizer
     *
     * @return true if there exists a <code>User</code> with type organizer, false otherwise
     */
    public boolean organizerExists() {
        for (User user : userRepository.getUsers()) {
            if (user.getUserType().equals(UserType.ORGANIZER))
                return true;
        }
        return false;
    }


    /**
     * Returns true if there exists a <code>User</code> with the specified username.
     *
     * @param username the username of a potential existing <code>User</code>
     * @return true if there exists a <code>User</code> with the specified username
     */
    public boolean userExists(String username) {
        return userRepository.userExists(username);
    }

    /**
     * Returns true if there exists a banned <code>User</code> with the specified username.
     *
     * @param username the username of a potential existing banned <code>User</code>
     * @return true if there exists a banned <code>User</code> with the specified username
     */
    public boolean bannedUserExists(String username) {
        return userRepository.bannedUserExists(username);
    }

    /**
     * Returns true if there exists a <code>User</code> whose login credentials match the given username and password.
     *
     * @param username the username of a potential existing <code>User</code>
     * @param password the password of a potential existing <code>User</code>
     * @return true if there exists a <code>User</code> whose credentials match the given username and password
     */
    public boolean passwordMatches(String username, String password) {
        if (!userExists(username)) {
            return false;
        }
        User user = userRepository.getUserWithUsername(username);
        return user.getPassword().equals(password);
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
        if (!userExists(user) || !userExists(potentialFriend)) {
            return false;
        }
        return userRepository.getUserWithUsername(user).getFriends().contains(potentialFriend);
    }

    /**
     * Returns a list of usernames corresponding to accounts in the given user's friends list.
     *
     * @param curUser the username of the <code>User</code> whose friends list is being accessed
     * @return a list of usernames corresponding to accounts in the user's friends list.
     */
    public List<String> getFriendsList(String curUser) {
        return userRepository.getUserWithUsername(curUser).getFriends();
    }

    /**
     * Returns a list of usernames corresponding to users that are of the specified <code>UserType</code> .
     *
     * @param userType the type of <code>User</code> such that usernames of users of that type will be returned
     * @return a list of usernames corresponding to <code>Users</code> that are of userType
     */
    public List<String> getAllUsernamesOfType(UserType userType) {
        List<String> usernames = new ArrayList<>();
        for (User user : userRepository.getUsers()) {
            if (user.getUserType() == userType) {
                usernames.add(user.getUsername());
            }
        }
        return usernames;
    }

    /**
     * Returns the <code>UserType</code> of a <code>User</code> who has the given username.
     * Precondition: username is the username of an existing <code>User</code>
     *
     * @param username the username of the desired <code>User</code>
     * @return the <code>UserType</code> of a <code>User</code> who has the given username
     */
    public UserType getUserType(String username) {
        return userRepository.getUserWithUsername(username).getUserType();
    }

    /**
     * Returns the list of banned users' usernames separated by a line
     *
     * @return usernames of banned users in a string separated by new lines
     */
    public String getBannedUsernames() {
        StringBuilder users = new StringBuilder();
        for (User user : userRepository.getBanned()) {
            users.append(user.getUsername()).append("\n");
        }
        return users.toString();
    }

    /**
     * Gets reason for ban
     *
     * @param user the username of user that has been banned
     * @return reason for ban
     */
    public String getReason(String user) {
        return userRepository.getBannedUserWithUsername(user).getBanReason();
    }

    /**
     * Gets admin who issued ban
     *
     * @param user the username of user that has been banned
     * @return admin who issued ban's username
     */
    public String getAdminForBan(String user) {
        return userRepository.getBannedUserWithUsername(user).getBanBy();
    }


}
