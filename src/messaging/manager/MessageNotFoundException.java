package messaging.manager;

/**
 * An exception which indicates that there is no <code>Message</code> with the given ID.
 * This is a runtime exception since its occurrence predictable and should be avoided.
 */
class MessageNotFoundException extends RuntimeException {
    /**
     * Creates a new <code>MessageNotFoundException</code> with the given message.
     *
     * @param message a message to be displayed alongside the exception
     */
    public MessageNotFoundException(String message) {
        super(message);
    }
}
