package messaging.controller;

import messaging.manager.MessageManager;
import messaging.presenter.MessagePresenter;
import messaging.presenter.MessagePrompts;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The <code>MessageViewingController</code> class is responsible for processing requests to view messages.
 * This involves requests to view messages in both a user's inbox and archives.
 */
public class MessageViewingController {
    private final MessagePresenter messagePresenter;
    private final MessageManager messageManager;

    /**
     * Creates a new MessageViewController object so users can view their messages
     *
     * @param messageManager   a MessageManager object used by this controller to interact with messages
     * @param messagePresenter a MessagePresenter object used by this controller to display messaging information to the user
     */
    public MessageViewingController(MessageManager messageManager, MessagePresenter messagePresenter) {
        this.messagePresenter = messagePresenter;
        this.messageManager = messageManager;
    }

    /**
     * Displays the inbox for the user <code>username</code>.
     * <p></p>
     * This inbox consists of all messages sent to or by
     * the user <code>username</code> (or replies to such messages), where the user's conversations
     * (threads of parent messages and all arbitrarily-nested replies) are ordered by the time when the parent messages
     * were added to the system, from oldest to newest. If the user has no conversations, an appropriate message is displayed.
     *
     * @param username the name of the user whose inbox is displayed
     */
    public void viewInbox(String username) {
        if (userHasInboxMessages(username)) {
            messagePresenter.display(MessagePrompts.INBOX_TITLE);
            displayAllUserConversations(username);
            messagePresenter.display(MessagePrompts.END_OF_INBOX);
        } else {
            messagePresenter.display(MessagePrompts.EMPTY_INBOX_ERROR);
        }
    }

    /**
     * Displays all the conversations in the inbox for the user <code>username</code>.
     * <p></p>
     * The conversations are ordered by the time when the parent messages were added to the system, from oldest to
     * newest. This ordering applies at every level of the reply hierarchy, i.e. sub-conversations of a conversation
     * are also ordered as above. Messages are nested according to their nesting level in the reply hierarchy.
     * Messages that the user <code>username</code> can reply to are numbered in the order that they appear to the user.
     *
     * @param username the name of the user whose inbox is displayed
     */
    private void displayAllUserConversations(String username) {
        List<UUID> allMessages = getInboxMessages(username);
        int curNum = 1;
        for (UUID messageId : allMessages) {
            String messageString = messageManager.getMessageAsString(messageId);
            int nesting = messageManager.getNestingLevel(messageId);
            boolean unread = messageManager.didUserMarkUnread(username, messageId);
            messagePresenter.displayMessage(messageString, nesting, curNum, unread);
            curNum++;
        }
    }

    /**
     * Returns a list of IDs of all the messages in the conversations of the user <code>username</code>. That is, the
     * IDs represent messages sent to or by <code>username</code>, or replies to such messages. The messages appear in
     * the list in the order that they appear to the user, as specified in the
     * {@link #displayAllUserConversations(String) displayAllUserConversations} method.
     *
     * @param username the name of the user whose conversation messages are returned
     * @return a list of IDs of all the messages in the conversations of the user <code>username</code>
     */
    public List<UUID> getInboxMessages(String username) {
        List<UUID> messageIds = messageManager.getInboxMessages(username);
        List<UUID> allMessages = new ArrayList<>();
        for (UUID messageId : messageIds) {
            if (messageManager.getNestingLevel(messageId) == 0) {
                allMessages.addAll(getThreadMessages(messageId));
            }
        }
        return allMessages;
    }

    /**
     * Returns a list of IDs of all the messages in the conversation whose parent message has ID <code>parentId</code>.
     * <code>parentId</code> itself will also be among the IDs returned.
     *
     * @param parentId the ID of the message at the top of the thread
     * @return a list of IDs of all the messages in the conversation whose parent message has ID <code>parentId</code>
     */
    private List<UUID> getThreadMessages(UUID parentId) {
        List<UUID> conversationMessages = new ArrayList<>();
        conversationMessages.add(parentId);
        List<UUID> replies = messageManager.getReplies(parentId);
        for (UUID reply : replies) {
            conversationMessages.addAll(getThreadMessages(reply));
        }
        return conversationMessages;
    }

    /**
     * Returns whether or not the user <code>username</code> has participated in any conversations. That is,
     * whether or not there are any messages sent to or by the user.
     *
     * @param username the user for whom existence of conversations is checked
     * @return <code>true</code> if and only if there are messages having <code>username</code> as author or recipient
     */
    private boolean userHasInboxMessages(String username) {
        return !messageManager.getInboxMessages(username).isEmpty();
    }

    /**
     * Displays all the archived messages for the user <code>username</code>.
     * <p></p>
     *
     * @param username the name of the user whose archives are displayed
     */
    public void viewArchives(String username) {
        if (userHasArchives(username)) {
            messagePresenter.display(MessagePrompts.ARCHIVED_TITLE);
            displayAllUserArchives(username);
            messagePresenter.display(MessagePrompts.END_OF_ARCHIVES);
        } else {
            messagePresenter.display(MessagePrompts.EMPTY_ARCHIVES_ERROR);
        }
    }

    private void displayAllUserArchives(String username) {
        List<UUID> userArchivedMessages = messageManager.getUserArchivedMessages(username);
        int curNum = 1;
        for (UUID messageId : userArchivedMessages) {
            String messageString = messageManager.getMessageAsString(messageId);
            messagePresenter.displayMessage(messageString, 0, curNum, false);
            curNum++;
        }
    }

    private boolean userHasArchives(String username) {
        return !messageManager.getUserArchivedMessages(username).isEmpty();
    }
}
