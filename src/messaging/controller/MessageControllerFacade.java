package messaging.controller;

import messaging.manager.MessageManager;
import messaging.presenter.MessagePresenter;
import messaging.presenter.MessagePrompts;
import system.console.ConsoleInputController;

import java.util.List;
import java.util.Scanner;

/**
 * This class allows the user to interact with the messaging system in the program.
 */
public class MessageControllerFacade extends ConsoleInputController<MessagePrompts> {
    private final MessageSendingController senderController;
    private final MessageInteractionController interactionController;
    private final MessageViewingController viewerController;


    /**
     * Creates a new <code>MessageController</code> object.
     *
     * @param in      a scanner used by this controller to get user input
     * @param manager a <code>MessageManager</code> used by this controller to interact with messages
     */
    public MessageControllerFacade(Scanner in, MessageManager manager) {
        super(in, new MessagePresenter());
        MessagePresenter presenter = new MessagePresenter();
        this.senderController = new MessageSendingController(in, manager, presenter);
        this.interactionController = new MessageInteractionController(in, manager, presenter);
        this.viewerController = new MessageViewingController(manager, presenter);
    }

    //<editor-fold desc="Displaying Messages">

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
        viewerController.viewInbox(username);
    }

    /**
     * Displays the archives for the user <code>username</code>.
     * <p></p>
     * This archives consist of all messages selected by
     * the user <code>username</code>, either messages sent by that user
     * or messages they received in their inbox. Displays in order of when it was archived.
     *
     * @param username the name of the user whose archives are displayed
     */
    public void viewArchives(String username) {
        viewerController.viewArchives(username);
    }

    //</editor-fold>

    //<editor-fold desc="Interacting with Messages">

    /**
     * Prompts the user to enter a request to interact with a message after viewing the inbox, if the user has any
     * messages in their inbox. Processes the user's request.
     * <p></p>
     * The user can choose to either exit to the main menu or select and interact with a message in their inbox.
     *
     * @param curUser the user whose inbox messages are available for interaction
     */
    public void processInboxRequest(String curUser) {
        interactionController.processInboxRequest(curUser, viewerController.getInboxMessages(curUser));
    }

    //</editor-fold>

    //<editor-fold desc="Sending Messages">

    /**
     * Prompts the user to enter text for the message to be sent. The message is authored by <code>author</code>
     * and is sent to <code>recipients</code>, if this list is nonempty. Otherwise an error message is displayed.
     *
     * @param author     the author of the message
     * @param recipients the list of recipients to whom the message will be sent
     */
    public void sendMessage(String author, List<String> recipients) {
        senderController.sendMessage(author, recipients);
    }

    /**
     * Prompts the user to enter a comma-separated list of recipients for a message, and returns a list of the
     * recipient usernames entered. These usernames need not represent existing users in the system.
     *
     * @return a list of recipient usernames entered by the user
     */
    public List<String> readRecipients() {
        return senderController.readRecipients();
    }

    /**
     * Prompts the user to enter a single recipient for a message, and returns the user's input.
     *
     * @return a recipient's name that the user has entered
     */
    public String readSingleRecipient() {
        return promptString(MessagePrompts.SINGLE_RECIPIENT_INPUT_PROMPT);
    }
    //</editor-fold>
}
