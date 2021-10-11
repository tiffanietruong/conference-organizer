package requests;


import java.io.Serializable;

/**
 * This is a Factory class that calls the <code>Request</code> constructor. It is used to:
 * <ul>
 *     <li>Remove hard dependencies on <code>Request</code> from <code>RequestManager</code></li>
 *     <li>Make <code>Request</code> more extensible</li>
 *     <li>Encapsulate the <code>Request</code> constructor calls</li>
 * </ul>
 *
 * @see Request
 */
class RequestFactory implements Serializable {
    /**
     * Returns a new <code>Request</code> object with the specified text and author
     *
     * @param text   the text of the new request to be created
     * @param author the author of the new request to be created
     * @return a new <code>Request</code> object with the specified text and author
     */

    public Request getRequest(String text, String author) {
        return new Request(text, author);
    }
}
