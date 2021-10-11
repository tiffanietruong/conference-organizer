package user.manager;

/**
 * An exception which indicates that there is no <code>User</code> with a given username.
 * This exception is thrown when such a <code>User</code> should be found but is not found in the program.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a <code>UserNotFoundException</code> with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval)
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
