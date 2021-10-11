package user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The <code>User</code> class represents a user attending the conference and consequently a potential client of
 * the program.
 */
public class User implements Serializable {

    private final String username;
    private final String password;
    private final List<String> friends;
    private String banReason;
    private String banBy;
    private UserType userType;

    /**
     * Constructs a new <code>User</code> with an initially empty friends list.
     *
     * @param username the username associated with this user's account
     * @param password the password associated with this user's account
     * @param userType the type of this user, representing their role in the conference
     */
    public User(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.banBy = null;
        this.banReason = null;
        this.friends = new ArrayList<>();
    }

    /**
     * Gets the username associated with this user's account.
     *
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password associated with this user's account.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the list of usernames corresponding to friends in this user's friends list.
     *
     * @return the list of friends in this user's friends list
     */
    public List<String> getFriends() {
        return friends;
    }

    /**
     * Gets the type of this <code>User</code>.
     *
     * @return the type associated with this <code>User</code>
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Sets this user's type.
     *
     * @param type the <code>UserType</code> to be assigned to this User
     */
    public void setUserType(UserType type) {
        userType = type;
    }

    //<editor-fold desc="Friends List Information">

    /**
     * Adds the given friend to this user's friends list.
     *
     * @param friend the username of the friend to add to this user's friends list
     */
    public void addFriend(String friend) {
        friends.add(friend);
    }

    /**
     * Removes the given friend from this user's friends list.
     *
     * @param friend the username of the friend to remove from this user's friends list
     */
    public void removeFriend(String friend) {
        friends.remove(friend);
    }

    //</editor-fold>

    //<editor-fold desc="Ban Information">

    /**
     * Gets the username of the Admin who banned this user.
     *
     * @return the username of the Admin who banned this user
     */
    public String getBanBy() {
        return banBy;
    }

    /**
     * Sets the username of the Admin who banned this user.
     *
     * @param admin the username of the Admin who banned this user
     */
    public void setBanBy(String admin) {
        banBy = admin;
    }

    /**
     * Gets the reason why this user is banned.
     *
     * @return the reason why this user was banned
     */
    public String getBanReason() {
        return banReason;
    }

    /**
     * Sets the reason why this user is banned.
     *
     * @param reason the reason why this user is banned
     */
    public void setBanReason(String reason) {
        banReason = reason;
    }
    //</editor-fold>
}