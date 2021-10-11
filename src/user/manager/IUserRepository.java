package user.manager;

import user.User;

import java.util.List;

public interface IUserRepository {

    /**
     * Returns the <code>User</code> whose username matches the given username.
     * Precondition: username is the username of an existing <code>User</code>
     *
     * @param username the username of the desired User
     * @return the instance of <code>User</code> associated with username
     * @throws UserNotFoundException if the given username is not the username of an existing user
     */
    User getUserWithUsername(String username);

    /**
     * Returns the <code>User</code> whose username matches the given banned username.
     * Precondition: username is the username of an existing banned <code>User</code>
     *
     * @param username the username of the desired banned User
     * @return the instance of <code>User</code> associated with username
     * @throws UserNotFoundException if the given username is not the username of an existing user
     */
    User getBannedUserWithUsername(String username);

    /**
     * Returns a list of the <code>User</code>s stored in this repository.
     *
     * @return a list of the <code>User</code>s stored in this repository
     */
    List<User> getUsers();

    /**
     * Returns a list of the <code>User</code>s in this repository who are banned.
     *
     * @return a list of the <code>User</code>s in this repository who are banned
     */
    List<User> getBanned();

    /**
     * Adds a <code>User</code> to this repository.
     *
     * @param user the user to add
     */
    void addUser(User user);

    /**
     * Registers a <code>User</code> as banned.
     *
     * @param user the user to register as banned
     */
    void addBanned(User user);

    /**
     * Removes a <code>User</code> from this repository.
     *
     * @param username the username of the user to remove
     */
    void removeUser(String username);

    /**
     * Removes a <code>User</code> from the list of banned <code>Users</code>.
     *
     * @param username the username of the user to unban
     */
    void removeBanned(String username);

    /**
     * Returns true if there exists a <code>User</code> with the specified username.
     *
     * @param username the username of a potential existing <code>User</code>
     * @return true if there exists a <code>User</code> with the specified username
     */
    boolean userExists(String username);

    /**
     * Returns true if there exists a banned <code>User</code> with the specified username.
     *
     * @param username the username of a potential existing banned <code>User</code>
     * @return true if there exists a banned <code>User</code> with the specified username
     */
    boolean bannedUserExists(String username);
}
