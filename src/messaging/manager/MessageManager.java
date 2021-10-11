package messaging.manager;

import messaging.Message;

import java.io.Serializable;
import java.util.*;

/**
 * This class stores and manipulates <code>Message</code> objects in the program.
 */
public class MessageManager implements Serializable {
    /**
     * Representation invariant: the <code>Message</code> objects stored in <code>messages</code> are stored in
     * the order in which they were added to the list.
     */
    private final List<Message> messages;
    private final Map<String, List<UUID>> archivedMessages;
    private final Map<String, List<UUID>> unreadMessages;
    private final List<UUID> deletedMessages;
    private final MessageFactory factory;

    /**
     * Creates a new <code>MessageManager</code> object with no messages.
     */
    public MessageManager() {
        this.messages = new ArrayList<>();
        this.archivedMessages = new HashMap<>();
        this.unreadMessages = new HashMap<>();
        this.deletedMessages = new ArrayList<>();
        this.factory = new MessageFactory();
    }

    /**
     * Gets the <code>Message</code> object corresponding to the ID <code>messageId</code>.
     * <p></p>
     * Precondition: a <code>Message</code> with ID <code>messageId</code> should already exist.
     *
     * @param messageId the ID for the <code>Message</code> object to retrieve
     * @return the unique <code>Message</code> object with ID <code>messageId</code>
     * @throws MessageNotFoundException if a <code>Message</code> with ID <code>messageId</code> does not exist.
     */
    private Message getMessageWithId(UUID messageId) {
        for (Message message : messages) {
            if (message.getId() == messageId) {
                return message;
            }
        }
        throw new MessageNotFoundException(String.format("Message with ID %s does not exist.", messageId.toString()));
    }

    //<editor-fold desc="Adding Messages and Replies">

    /**
     * Add a new message to the list of messages.
     * <p></p>
     * Precondition: <code>recipients</code> is not empty.
     *
     * @param text       the text for the message
     * @param author     the author for the message
     * @param recipients a nonempty list of recipient usernames for the message
     * @param nesting    an integer that indicates how deeply nested this message is in the reply hierarchy.
     *                   More formally, this is 0 if this message is not a reply, or n + 1 if this is a reply to
     *                   a message with nesting level equal to n.
     */
    public void addMessage(String text, String author, List<String> recipients, int nesting) {
        Message newMessage = factory.getMessage(text, author, recipients, nesting);
        messages.add(newMessage);
    }

    /**
     * Adds a reply with a given text and author to a message with ID <code>messageId</code>.
     * <p></p>
     * Precondition: a <code>Message</code> with ID <code>messageId</code> should already exist.
     *
     * @param text      the text for the reply
     * @param author    the author for the reply
     * @param messageId the ID for the message that is being replied to
     */
    public void replyToMessage(String text, String author, UUID messageId) {
        Message message = getMessageWithId(messageId);
        String originalAuthor = message.getAuthor();
        List<String> replyRecipient = Collections.singletonList(originalAuthor);
        Message reply = factory.getMessage(text, author, replyRecipient, message.getNesting() + 1);
        messages.add(reply);
        message.addReply(reply.getId());
    }
    //</editor-fold>

    //<editor-fold desc="Getting Lists of Message IDs">

    /**
     * Returns a list of IDs corresponding to replies of the message whose ID is <code>messageId</code>.
     * <p></p>
     * The list of reply IDs is ordered according to when these replies were added to the manager;
     * earlier replies appear earlier in the list.
     * <p></p>
     * Precondition: a <code>Message</code> with ID <code>messageId</code> should already exist.
     *
     * @param messageId the ID corresponding to the message whose replies are returned
     * @return a list of IDs corresponding to replies of the message whose ID is <code>messageId</code>
     */
    public List<UUID> getReplies(UUID messageId) {
        Message message = getMessageWithId(messageId);
        return message.getReplies();
    }

    /**
     * Returns a list of IDs corresponding to messages that pertain to user <code>username</code>. More precisely,
     * the IDs returned are those of all messages with <code>username</code> as an author or a recipient.
     * <p></p>
     * The list of IDs is ordered according to when the corresponding messages were added to the manager;
     * earlier messages appear earlier in the list.
     *
     * @param username the user whose incoming and outgoing messages are returned
     * @return a list of IDs of all messages with <code>username</code> as an author or a recipient
     */
    public List<UUID> getInboxMessages(String username) {
        List<UUID> messagesToOrFromUser = new ArrayList<>();
        for (Message message : messages) {
            if ((message.getRecipients().contains(username) || message.getAuthor().equals(username))
                    && !messageInArchives(username, message.getId())) {
                messagesToOrFromUser.add(message.getId());
            }
        }
        return messagesToOrFromUser;
    }

    private boolean messageInArchives(String username, UUID messageID) {
        if (archivedMessages.containsKey(username)) {
            return archivedMessages.get(username).contains(messageID);
        }
        return false;
    }

    //</editor-fold>

    /**
     * Adds the message with ID <code>messageId</code> to the list of deleted messages and marks it as deleted.
     * <p></p>
     *
     * @param messageId unique UUID of a message
     */
    public void markAsDeleted(UUID messageId) {
        Message message = getMessageWithId(messageId);
        deletedMessages.add(messageId);
        message.markAsDeleted();
    }

    /**
     * Returns true if the message has been deleted, false otherwise.
     * <p></p>
     *
     * @param messageId unique UUID of a message
     * @return true if the message is in the list of deleted messages.
     */
    public boolean isDeleted(UUID messageId) {
        return deletedMessages.contains(messageId);
    }

    /**
     * Adds message with ID <code>messageId</code> to user <code>username</code>'s list of unread messages.
     * <p></p>
     *
     * @param messageId unique UUID of a message
     * @param username  the user who chose to mark a message as unread
     */
    public void markAsUnread(UUID messageId, String username) {
        if (unreadMessages.containsKey(username)) {
            unreadMessages.get(username).add(messageId);
        } else {
            List<UUID> userUnread = new ArrayList<>();
            userUnread.add(messageId);
            unreadMessages.put(username, userUnread);
        }
    }

    /**
     * Removes the message with ID <code>messageId</code> from the user <code>username</code>'s list of unread messages.
     * <p></p>
     *
     * @param messageId unique UUID of a message
     * @param username  the user who chose to unmark a message as unread
     */
    public void unmarkAsUnread(UUID messageId, String username) {
        if (unreadMessages.containsKey(username)) {
            unreadMessages.get(username).remove(messageId);
        }
    }

    /**
     * Returns whether or not the message with ID <code>messageId</code> was marked as unread by the user <code>username</code>.
     * <p></p>
     *
     * @param username  the user who marked the message as unread
     * @param messageId unique UUID of a message
     * @return true if the user marked the message as unread, false otherwise
     */
    public boolean didUserMarkUnread(String username, UUID messageId) {
        if (unreadMessages.containsKey(username)) {
            return unreadMessages.get(username).contains(messageId);
        }
        return false;
    }

    /**
     * Add a new message to the list of archived messages for the user <code>username</code>.
     * <p></p>
     *
     * @param messageId UUID of the message to archive
     * @param username  the user who wants to archive
     */
    public void addToArchive(UUID messageId, String username) {
        List<UUID> userArchive = getUserArchivedMessages(username);
        userArchive.add(messageId);
        archivedMessages.put(username, userArchive);
    }

    /**
     * Retrieves the list of archived messages for user <code>username</code>. If the user does not have archived messages
     * yet, creates a new list.
     *
     * @param username the user whose archives to retrieve
     * @return list of UUID message ids of the all the messages the user has archived
     */
    public List<UUID> getUserArchivedMessages(String username) {
        if (archivedMessages.containsKey(username)) {
            return archivedMessages.get(username);
        }
        return new ArrayList<>();
    }


    //<editor-fold desc="Getting Information about a User's Messages">

    /**
     * Returns whether or not the message with ID <code>messageId</code> has <code>username</code> as a recipient.
     * <p></p>
     * Precondition: a <code>Message</code> with ID <code>messageId</code> should already exist.
     *
     * @param username  username to search for among the recipients of the message
     * @param messageId the ID of the message whose recipients are checked
     * @return <code>true</code> if and only if the message with ID <code>messageId</code> has <code>username</code>
     * as a recipient.
     */
    public boolean isRecipient(String username, UUID messageId) {
        Message message = getMessageWithId(messageId);
        return message.hasRecipient(username);
    }

    /**
     * Returns whether or not the message with ID <code>messageId</code> was written by <code>username</code>.
     * <p></p>
     * Precondition: a <code>Message</code> with ID <code>messageId</code> should already exist.
     *
     * @param username  the name of the user to be checked for authorship
     * @param messageId the ID of the message whose author is to be checked
     * @return <code>true</code> if and only if the message with ID <code>messageId</code> was written by <code>username</code>
     */
    public boolean isAuthor(String username, UUID messageId) {
        Message message = getMessageWithId(messageId);
        return message.getAuthor().equals(username);
    }
    //</editor-fold>

    //<editor-fold desc="Getting Information about Specific Messages">

    /**
     * Returns a string representation of the message with ID <code>messageId</code>.
     * <p></p>
     * Precondition: a <code>Message</code> with ID <code>messageId</code> should already exist.
     *
     * @param messageId the ID of the message whose string representation is returned
     * @return a string representation of the message with ID <code>messageId</code>
     */
    public String getMessageAsString(UUID messageId) {
        Message message = getMessageWithId(messageId);
        return message.toString();
    }

    /**
     * Returns an integer that indicates how deeply nested this message is in the reply hierarchy.
     * More formally, this is 0 if this message is not a reply, or n + 1 if this message is a reply to a message with
     * nesting level equal to n.
     * <p></p>
     * Precondition: a <code>Message</code> with ID <code>messageId</code> should already exist.
     *
     * @param messageId the ID for the message whose nesting level is returned
     * @return an integer that indicates how deeply nested this message is in the reply hierarchy.
     */
    public int getNestingLevel(UUID messageId) {
        return getMessageWithId(messageId).getNesting();
    }
    //</editor-fold>

}
