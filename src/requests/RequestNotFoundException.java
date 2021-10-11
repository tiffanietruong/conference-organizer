package requests;

/**
 * An exception which indicates that there is no <code>Request</code> with the given ID.
 * This is a runtime exception since its occurrence predictable and should be avoided.
 */
class RequestNotFoundException extends RuntimeException {
    /**
     * Creates a new <code>RequestNotFoundException</code> with the given message.
     *
     * @param message a message to be displayed alongside the exception
     */
    public RequestNotFoundException(String message) {
        super(message);
    }
}