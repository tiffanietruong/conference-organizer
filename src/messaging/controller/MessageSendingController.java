package messaging.controller;

import messaging.manager.MessageManager;
import messaging.presenter.MessagePresenter;
import messaging.presenter.MessagePrompts;
import system.console.ConsoleInputController;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * The <code>MessageSendingController</code> class is responsible for processing requests to send messages between
 * users. In doing so, it also parses through lists of recipients.
 */
public class MessageSendingController extends ConsoleInputController<MessagePrompts> {

    private final MessageManager messageManager;
    private final MessagePresenter messagePresenter;

    /**
     * Creates a new MessageSendingController object to control messages users send.
     *
     * @param in               a scanner used by this controller to get user input
     * @param messageManager   a MessageManager object used by this controller to interact with messages
     * @param messagePresenter a MessagePresenter object used by this controller to display messaging information to the user
     */

    public MessageSendingController(Scanner in, MessageManager messageManager, MessagePresenter messagePresenter) {
        super(in, messagePresenter);
        this.messageManager = messageManager;
        this.messagePresenter = messagePresenter;
    }

    /**
     * Prompts the user to enter text for the message to be sent. The message is authored by <code>author</code>
     * and is sent to <code>recipients</code>, if this list is nonempty. Otherwise an error message is displayed.
     *
     * @param author     the author of the message
     * @param recipients the list of recipients to whom the message will be sent
     */
    public void sendMessage(String author, List<String> recipients) {
        if (recipients.isEmpty()) {
            messagePresenter.display(MessagePrompts.NO_RECIPIENTS_ERROR);
        } else {
            String text = promptString(MessagePrompts.TEXT_INPUT_PROMPT);
            messageManager.addMessage(text, author, recipients, 0);
            messagePresenter.displayMessageConfirmation(recipients);
        }
    }

    /**
     * Prompts the user to enter a comma-separated list of recipients for a message, and returns a list of the
     * recipient usernames entered. These usernames need not represent existing users in the system.
     *
     * @return a list of recipient usernames entered by the user
     */
    public List<String> readRecipients() {
        String recipientInput = promptString(MessagePrompts.RECIPIENTS_INPUT_PROMPT);
        // Split the string at points where the regex matches: 0 or more whitespaces, a comma, and 0 or more whitespaces
        return Arrays.asList(recipientInput.split("\\s*,\\s*"));
    }
    //</editor-fold>
}
