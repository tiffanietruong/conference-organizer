package messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class represents a message between users of the program.
 */
public class Message extends AbstractMessage {
    private final List<String> recipients;
    private final List<UUID> replies;
    private final int nesting;

    /**
     * Creates a new message object using the given information.
     * Precondition: <code>recipients</code> must be nonempty.
     *
     * @param text       the text to be included in the message
     * @param author     the author of the message
     * @param recipients a nonempty list of usernames representing the recipients of this message
     * @param nesting    an integer that indicates how deeply nested this message is in the reply hierarchy.
     *                   More formally, this is 0 if this message is not a reply, or n + 1 if this is a reply to
     *                   a message with <code>nesting</code> equal to n.
     */
    public Message(String text, String author, List<String> recipients, int nesting) {
        super(text, author);
        this.recipients = recipients;
        this.replies = new ArrayList<>();
        this.nesting = nesting;
    }

    /**
     * Gets the nonempty list of usernames representing the recipients for this message.
     *
     * @return the nonempty list of recipient usernames
     */
    public List<String> getRecipients() {
        return recipients;
    }

    /**
     * Gets an integer that indicates how deeply nested this message is in the reply hierarchy.
     * More formally, this is 0 if this message is not a reply, or n + 1 if this is a reply to a message with
     * <code>nesting</code> equal to n.
     *
     * @return an integer that indicates how deeply nested this message is in the reply hierarchy
     */
    public int getNesting() {
        return nesting;
    }

    /**
     * Returns whether or not the user corresponding to username is a recipient of this message.
     *
     * @param username the username to check for being a recipient of this message
     * @return <code>true</code> if and only if this message has <code>username</code> as a recipient
     */
    public boolean hasRecipient(String username) {
        return recipients.contains(username);
    }

    /**
     * Gets the list of IDs corresponding to message that are replies to this message.
     *
     * @return the list of IDs corresponding to message that are replies to this message.
     */
    public List<UUID> getReplies() {
        return replies;
    }

    /**
     * Adds the ID for an existing message to the list of replies to this message.
     *
     * @param replyId the unique ID corresponding to a message to be added as a reply to this message
     */
    public void addReply(UUID replyId) {
        replies.add(replyId);
    }

    /**
     * Changes the text of the message to "[DELETED]".
     */
    public void markAsDeleted() {
        text = "[DELETED]";
    }
}
