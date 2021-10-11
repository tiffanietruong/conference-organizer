package messaging.presenter;

import messaging.controller.MessageOption;
import system.console.ConsolePresenter;

import java.util.*;

/**
 * This class presents messaging information to the user and formats messages properly.
 */
public class MessagePresenter extends ConsolePresenter<MessagePrompts> {
    private final Map<MessageOption, String> optionPrompts;

    /**
     * Constructs a MessagePresenter object to display messaging information to the user and format messages properly.
     */
    public MessagePresenter() {
        optionPrompts = initializeOptions();
    }

    private Map<MessageOption, String> initializeOptions() {
        Map<MessageOption, String> options = new HashMap<>();
        options.put(MessageOption.REPLY, "Reply");
        options.put(MessageOption.DELETE, "Delete");
        options.put(MessageOption.ARCHIVE, "Archive");
        options.put(MessageOption.MARK_UNREAD, "Mark as unread");
        options.put(MessageOption.UNMARK_UNREAD, "Unmark unread");
        options.put(MessageOption.CANCEL, "Cancel");
        return options;
    }

    /**
     * Returns an <code>EnumMap</code> that maps each enum key of type <code>T</code> to its respective console message
     *
     * @return an <code>EnumMap</code> that maps each enum key of type <code>T</code> to its respective console message
     */
    @Override
    protected EnumMap<MessagePrompts, String> initializeMessages() {
        EnumMap<MessagePrompts, String> m = new EnumMap<>(MessagePrompts.class);
        initializeConfirmations(m);
        initializeErrors(m);
        m.put(MessagePrompts.INBOX_TITLE, "Here is your inbox:\n" +
                "==========================================================");
        m.put(MessagePrompts.ARCHIVED_TITLE, "Here are your archived messages.\n" +
                "==========================================================");
        m.put(MessagePrompts.END_OF_INBOX, "==========================================================\n" +
                "End of inbox.\n");
        m.put(MessagePrompts.END_OF_ARCHIVES, "==========================================================\n" +
                "End of archives.\n");
        m.put(MessagePrompts.INBOX_MENU_TITLE, "Please enter a command:");
        m.put(MessagePrompts.INBOX_QUIT_PROMPT, "1 - Return to main menu");
        m.put(MessagePrompts.MESSAGE_INTERACTION_PROMPT, "2 - Interact with a message");
        m.put(MessagePrompts.MESSAGE_INTERACTION_TITLE, "Please enter a command to interact with the message:");
        m.put(MessagePrompts.MESSAGE_REPLY_PROMPT, "Reply to the message");
        m.put(MessagePrompts.MESSAGE_DELETE_PROMPT, "Delete the message");
        m.put(MessagePrompts.MESSAGE_ARCHIVE_PROMPT, "Archive the message");
        m.put(MessagePrompts.MESSAGE_MARK_UNREAD_PROMPT, "Mark message as unread");
        m.put(MessagePrompts.MESSAGE_REMOVE_UNREAD_PROMPT, "Unmark message as unread");
        m.put(MessagePrompts.RECIPIENTS_INPUT_PROMPT, "Please enter the recipients' usernames (separated by commas) " +
                "and then hit Enter.");
        m.put(MessagePrompts.SINGLE_RECIPIENT_INPUT_PROMPT, "Please enter a recipient and then hit Enter.");
        m.put(MessagePrompts.TEXT_INPUT_PROMPT, "Please enter the text for your message.");
        m.put(MessagePrompts.MESSAGE_SELECTION_PROMPT, "Please enter the number of the message you wish to select.");
        m.put(MessagePrompts.MESSAGE_SELECTION_CANCELLED, "Cancelled message selection.");

        return m;
    }

    private void initializeConfirmations(EnumMap<MessagePrompts, String> m) {
        m.put(MessagePrompts.MESSAGE_CONFIRMATION, "Your message has been sent to all valid recipients:");
        m.put(MessagePrompts.REPLY_CONFIRMATION, "Your reply has been sent.");
        m.put(MessagePrompts.DELETION_CONFIRMATION, "Your message has been deleted.");
        m.put(MessagePrompts.ARCHIVED_CONFIRMATION, "Your message has been archived.");
        m.put(MessagePrompts.MARK_UNREAD_CONFIRMATION, "Message has been marked as unread.");
        m.put(MessagePrompts.REMOVE_UNREAD_CONFIRMATION, "Message has been re-marked as read.");
    }

    private void initializeErrors(EnumMap<MessagePrompts, String> m) {
        m.put(MessagePrompts.INVALID_COMMAND_ERROR, "Sorry, that is not a valid command. Please try again.");
        m.put(MessagePrompts.EMPTY_INBOX_ERROR, "Sorry, you do not have any messages in your inbox.");
        m.put(MessagePrompts.EMPTY_ARCHIVES_ERROR, "Sorry, you do not have any archived messages.");
        m.put(MessagePrompts.NO_RECIPIENTS_ERROR, "Sorry, there were no valid recipients.");
        m.put(MessagePrompts.INVALID_MESSAGE_SELECTION_ERROR, "Sorry, that is not a valid message number. " +
                "Please try again.");
    }

    /**
     * Displays the inbox menu options for actions that can be performed on messages in a user's inbox.
     */
    public void displayInboxMenu() {
        display(MessagePrompts.INBOX_MENU_TITLE);
        display(MessagePrompts.INBOX_QUIT_PROMPT);
        display(MessagePrompts.MESSAGE_INTERACTION_PROMPT);
    }

    /**
     * Displays message to get user input for how they want to interact with a message in their inbox.
     *
     * @param messageOptions list of actions that could be performed on the selected message
     */
    // Assumption: messageOptions will always be nonempty
    public void displayMessageInteractionMenu(List<MessageOption> messageOptions) {
        System.out.println("Please enter a command to interact with the message:");
        int curNum = 1;
        for (MessageOption option : messageOptions) {
            System.out.printf("%d - %s%n", curNum, optionPrompts.get(option));
            curNum++;
        }
    }

    /**
     * Displays a message with the given information to the user.
     * <ul>
     *     <li>The message will be indented according to its nesting level in the reply hierarchy.</li>
     *     <li>If the user should be able to reply to this message, the message will be numbered,
     *     with number <code>curNum</code>.</li>
     * </ul>
     *
     * @param message the message (as a string) to display to the user
     * @param nesting the nesting level of this message in the reply hierarchy
     * @param curNum  the current message number, to be displayed alongside the message
     */
    public void displayMessage(String message, int nesting, int curNum, boolean unread) {
        String formattedMessage = formatUnread(message, unread);
        formattedMessage = indentString(formattedMessage, nesting);
        System.out.printf("%d: %s", curNum, formattedMessage);
    }

    private String formatUnread(String message, boolean unread) {
        if (unread) {
            return message + "[UNREAD!]\n";
        }
        return message;
    }

    /**
     * Indents a string to a specified indent level.
     *
     * @param toIndent    the string to be indented
     * @param indentLevel the indent level to be applied
     * @return <code>toIndent</code>, indented <code>indentLevel</code> times
     */
    private String indentString(String toIndent, int indentLevel) {
        String indent = String.join("", Collections.nCopies(indentLevel, "\t"));
        // Match the start of every line. ?m indicates multiline mode - all lines will be matched.
        return toIndent.replaceAll("(?m)^", indent);
    }

    /**
     * Displays that a message was sent, along with the names of the valid recipients to which the message was sent.
     *
     * @param recipients the usernames of the recipients to which the message was sent
     */
    public void displayMessageConfirmation(List<String> recipients) {
        display(MessagePrompts.MESSAGE_CONFIRMATION);
        System.out.println(String.join(", ", recipients));
    }


}
