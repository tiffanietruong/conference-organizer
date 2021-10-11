package user.manager;

import user.User;
import user.UserType;

import java.io.Serializable;

/**
 * The <code>UserManipulationController</code> class is responsible for manipulating and changing user data.
 */
public class UserManipulationManager implements Serializable {
    private final IUserRepository userRepository;

    /**
     * Constructs a manager responsible for manipulating and changing user data.
     *
     * @param userRepository a <code>UserRepository</code> object that contains the users in the conference
     */
    public UserManipulationManager(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Changes the given user's associated <code>UserType</code> to be <code>type</code>.
     *
     * @param user    the username of the user whose <code>UserType</code> is being modified
     * @param newType the new <code>UserType</code>> to be assigned to the given user
     */
    public void changeUserType(String user, UserType newType) {
        userRepository.getUserWithUsername(user).setUserType(newType);
    }

    /**
     * Bans a user
     *
     * @param username the username of the user to be banned
     * @param admin    the username of the admin who issued the ban
     * @param reason   the reason the admin banned this user
     */
    public void banUser(String username, String admin, String reason) {
        User temp = userRepository.getUserWithUsername(username);
        userRepository.removeUser(username);
        temp.setBanBy(admin);
        temp.setBanReason(reason);
        userRepository.addBanned(temp);
    }

    /**
     * Unbans a user
     *
     * @param username the username of user to be unbanned
     */
    public void unBanUser(String username) {
        User temp = userRepository.getBannedUserWithUsername(username);
        userRepository.getBanned().remove(temp);
        temp.setBanBy(null);
        temp.setBanReason(null);
        userRepository.getUsers().add(temp);
    }


    /**
     * Delete a user
     *
     * @param username the username of the user being deleted
     */
    public boolean deleteUser(String username) {
        UserType userType = userRepository.getUserWithUsername(username).getUserType();
        if (userRepository.userExists(username) && userType != UserType.ADMIN && userType != UserType.ORGANIZER) {
            userRepository.removeUser(username);
            return true;
        }
        if (userRepository.bannedUserExists(username)) {
            userRepository.removeBanned(username);
            return true;
        }
        return false;
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
        userRepository.addUser(new User(username, password, userType));
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
        userRepository.getUserWithUsername(requestingUser).addFriend(addedUser);
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
        userRepository.getUserWithUsername(requestingUser).removeFriend(removedUser);
    }
}
