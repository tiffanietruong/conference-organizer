package messaging.manager;

import messaging.Message;

import java.io.Serializable;
import java.util.List;

/**
 * This is a Factory class that calls the <code>Message</code> constructor. It is used to:
 * <ul>
 *     <li>Remove hard dependencies on <code>Message</code> from <code>MessageManager</code></li>
 *     <li>Make <code>Message</code> more extensible</li>
 *     <li>Encapsulate the <code>Message</code> constructor calls</li>
 * </ul>
 *
 * @see Message
 */
class MessageFactory implements Serializable {
    /**
     * Returns a new <code>Message</code> object with the specified text, author, recipients, and nesting level.
     *
     * @param text       the text of the new message to be created
     * @param author     the author of the new message to be created
     * @param recipients the list of recipients for the new message to be created
     * @param nesting    the nesting level of this message in the reply hierarchy. More formally, this is 0 if the new
     *                   message is not a reply, or n + 1 if the new message is a reply to a message with
     *                   <code>nesting</code> equal to n.
     * @return a new <code>Message</code> object with the specified text, author, recipients, and nesting level
     */
    public Message getMessage(String text, String author, List<String> recipients, int nesting) {
        return new Message(text, author, recipients, nesting);
    }
}
