package messaging.controller;

import messaging.manager.MessageManager;
import messaging.presenter.MessagePresenter;
import messaging.presenter.MessagePrompts;
import system.console.ConsoleInputController;

import java.util.*;
import java.util.function.Predicate;

/**
 * This class allows the user to interact with existing messages in their inbox.
 */
public class MessageInteractionController extends ConsoleInputController<MessagePrompts> {
    /**
     * Representation invariants:
     * <ul>
     *     <li><code>curUser</code> is either empty or the name of an existing user in the system whose message
     *     interaction is to be processed.</li>
     *     <li><code>curMessage</code> is either empty or the UUID of an existing message in <code>curUser</code>'s
     *     inbox who the user is interacting with.</li>
     * </ul>
     */
    private final MessageManager messageManager;
    private final MessagePresenter messagePresenter;
    private final Scanner in;
    private final Map<MessageOption, Runnable> commands;
    private String curUser;
    private UUID curMessage;

    /**
     * Constructs a new <code>MessageInteractionController</code> object.
     *
     * @param in               a scanner used by this controller to get user input
     * @param messageManager   a <code>MessageManager</code> used by this controller to interact with messages
     * @param messagePresenter a <code>MessagePresenter</code> used by this controller to display information to the user
     */
    public MessageInteractionController(Scanner in, MessageManager messageManager, MessagePresenter messagePresenter) {
        super(in, messagePresenter);
        this.messageManager = messageManager;
        this.messagePresenter = messagePresenter;
        this.in = in;
        this.commands = initializeCommands();
    }

    /**
     * Returns a list of commands that the user can execute on a message. Each <code>MessageOption</code> value
     * is associated to a <code>Runnable</code> that can be run to execute the command.
     *
     * @return a map associating to each <code>MessageOption</code> value a <code>Runnable</code> corresponding to
     * the given command
     */
    private Map<MessageOption, Runnable> initializeCommands() {
        Map<MessageOption, Runnable> commands = new HashMap<>();
        commands.put(MessageOption.REPLY, this::processReply);
        commands.put(MessageOption.DELETE, this::processDeletion);
        commands.put(MessageOption.ARCHIVE, this::processArchiving);
        commands.put(MessageOption.MARK_UNREAD, this::processMarkingAsUnread);
        commands.put(MessageOption.UNMARK_UNREAD, this::processUnmarkingAsUnread);
        commands.put(MessageOption.CANCEL, () -> {
        });
        return commands;
    }

    /**
     * Prompts the user to enter a request to interact with a message after viewing the inbox, if the user has any
     * messages in their inbox. Processes the user's request.
     * <p></p>
     * The user can choose to either exit to the main menu or select and interact with a message in their inbox.
     *
     * @param curUser the user whose inbox messages are available for interaction
     */
    public void processInboxRequest(String curUser, List<UUID> inbox) {
        this.curUser = curUser;
        if (!inbox.isEmpty()) {
            messagePresenter.displayInboxMenu();
            String input = in.nextLine();
            if (input.equals("2")) {
                processMessageInteraction(inbox);
            } else if (!input.equals("1")) {
                messagePresenter.display(MessagePrompts.INVALID_COMMAND_ERROR);
                processInboxRequest(curUser, inbox);
            }
        }
    }

    /**
     * Processes the user's interaction with a message.
     *
     * @param inbox the list IDs of the messages in the user's inbox
     */
    private void processMessageInteraction(List<UUID> inbox) {
        updateCurrentMessage(inbox);
        MessageOption selectedOption = readMessageInteractionInput();
        commands.get(selectedOption).run();
    }

    /**
     * Returns a list of options for how the current user can interact with the current selected message.
     *
     * @return a list of options for how the current user can interact with the current selected message.
     */
    private List<MessageOption> determineMessageOptions() {
        List<MessageOption> messageOptions = new ArrayList<>();
        if (messageManager.isRecipient(curUser, curMessage)) {
            messageOptions.add(MessageOption.REPLY);
        }
        if (messageManager.isAuthor(curUser, curMessage) && !messageManager.isDeleted(curMessage)) {
            messageOptions.add(MessageOption.DELETE);
        }
        messageOptions.add(MessageOption.ARCHIVE);
        if (!messageManager.didUserMarkUnread(curUser, curMessage)) {
            messageOptions.add(MessageOption.MARK_UNREAD);
        } else {
            messageOptions.add(MessageOption.UNMARK_UNREAD);
        }
        messageOptions.add(MessageOption.CANCEL);
        return messageOptions;
    }

    /**
     * Reads and returns the user's choice of interaction with the current message.
     *
     * @return a <code>MessageOption</code> value corresponding to the selected interaction
     */
    private MessageOption readMessageInteractionInput() {
        List<MessageOption> messageOptions = determineMessageOptions();
        messagePresenter.displayMessageInteractionMenu(messageOptions);
        Predicate<Integer> isValid = n -> (n >= 1 && n <= messageOptions.size());
        int optionSelection = readInt(MessagePrompts.INVALID_COMMAND_ERROR, isValid);
        return messageOptions.get(optionSelection - 1);
    }

    /**
     * Updates <code>curMessage</code> to be an ID corresponding to a message that the user has selected,
     * if the selection was valid. The message is guaranteed be in the current user's inbox.
     *
     * @param inbox the list of IDs of messages in the current user's inbox
     */
    private void updateCurrentMessage(List<UUID> inbox) {
        Predicate<Integer> isValid = n -> (n >= 1 && n <= inbox.size());
        int selection = promptInt(MessagePrompts.MESSAGE_SELECTION_PROMPT,
                MessagePrompts.INVALID_MESSAGE_SELECTION_ERROR, isValid);
        this.curMessage = inbox.get(selection - 1);
    }

    //<editor-fold desc="Processing the Commands">
    private void processReply() {
        String text = promptString(MessagePrompts.TEXT_INPUT_PROMPT);
        messageManager.replyToMessage(text, curUser, curMessage);
        messagePresenter.display(MessagePrompts.REPLY_CONFIRMATION);
    }

    private void processDeletion() {
        messageManager.markAsDeleted(curMessage);
        messagePresenter.display(MessagePrompts.DELETION_CONFIRMATION);
    }

    private void processArchiving() {
        messageManager.addToArchive(curMessage, curUser);
        messagePresenter.display(MessagePrompts.ARCHIVED_CONFIRMATION);
    }

    private void processMarkingAsUnread() {
        messageManager.markAsUnread(curMessage, curUser);
        messagePresenter.display(MessagePrompts.MARK_UNREAD_CONFIRMATION);
    }

    private void processUnmarkingAsUnread() {
        messageManager.unmarkAsUnread(curMessage, curUser);
        messagePresenter.display(MessagePrompts.REMOVE_UNREAD_CONFIRMATION);
    }
    //</editor-fold>
}

