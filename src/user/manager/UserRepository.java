package user.manager;

import user.User;

import java.io.Serializable;
import java.util.List;

/**
 * The <code>UserRepository</code> class is responsible for storing the <code>User</code>s in the conference.
 */
public class UserRepository implements IUserRepository, Serializable {
    private final List<User> users;
    private final List<User> banned;

    /**
     * Constructs a repository responsible for storing the <code>User</code>s in the conference.
     *
     * @param users  a list of <code>User</code>s to be stored
     * @param banned a list of banned <code>User</code>s to be stored
     */
    public UserRepository(List<User> users, List<User> banned) {
        this.users = users;
        this.banned = banned;
    }

    /**
     * Returns the <code>User</code> whose username matches the given username.
     * Precondition: username is the username of an existing <code>User</code>
     *
     * @param username the username of the desired User
     * @return the instance of <code>User</code> associated with username
     * @throws UserNotFoundException if the given username is not the username of an existing user
     */
    @Override
    public User getUserWithUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        throw new UserNotFoundException(String.format("User with username %s does not exist.", username));
    }

    /**
     * Returns the <code>User</code> whose username matches the given banned username.
     * Precondition: username is the username of an existing banned <code>User</code>
     *
     * @param username the username of the desired banned User
     * @return the instance of <code>User</code> associated with username
     * @throws UserNotFoundException if the given username is not the username of an existing user
     */
    @Override
    public User getBannedUserWithUsername(String username) {
        for (User user : banned) {
            if (user.getUsername().equals(username))
                return user;
        }
        throw new UserNotFoundException(String.format("User with username %s does not exist.", username));
    }

    /**
     * Returns a list of the <code>User</code>s stored in this repository.
     *
     * @return a list of the <code>User</code>s stored in this repository
     */
    @Override
    public List<User> getUsers() {
        return users;
    }

    /**
     * Returns a list of the <code>User</code>s in this repository who are banned.
     *
     * @return a list of the <code>User</code>s in this repository who are banned
     */
    @Override
    public List<User> getBanned() {
        return banned;
    }

    /**
     * Adds a <code>User</code> to this repository.
     *
     * @param user the user to add
     */
    @Override
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Registers a <code>User</code> as banned.
     *
     * @param user the user to register as banned
     */
    @Override
    public void addBanned(User user) {
        banned.add(user);
    }

    /**
     * Removes a <code>User</code> from this repository.
     *
     * @param username the username of the user to remove
     */
    @Override
    public void removeUser(String username) {
        User user = getUserWithUsername(username);
        users.remove(user);
    }

    /**
     * Removes a <code>User</code> from the list of banned <code>Users</code>.
     *
     * @param username the username of the user to unban
     */
    @Override
    public void removeBanned(String username) {
        User user = getBannedUserWithUsername(username);
        banned.remove(user);
    }

    /**
     * Returns true if there exists a <code>User</code> with the specified username.
     *
     * @param username the username of a potential existing <code>User</code>
     * @return true if there exists a <code>User</code> with the specified username
     */
    @Override
    public boolean userExists(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return true;
        }
        return false;
    }

    /**
     * Returns true if there exists a banned <code>User</code> with the specified username.
     *
     * @param username the username of a potential existing banned <code>User</code>
     * @return true if there exists a banned <code>User</code> with the specified username
     */
    @Override
    public boolean bannedUserExists(String username) {
        for (User user : banned) {
            if (user.getUsername().equals(username))
                return true;
        }
        return false;
    }
}
