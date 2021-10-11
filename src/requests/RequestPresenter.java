package requests;

import system.console.ConsolePresenter;

import java.util.EnumMap;

public class RequestPresenter extends ConsolePresenter<RequestPrompts> {

    /**
     * The list of messages that will be displayed to the user
     */
    protected EnumMap<RequestPrompts, String> initializeMessages() {
        EnumMap<RequestPrompts, String> m = new EnumMap<>(RequestPrompts.class);
        m.put(RequestPrompts.USER_REQUESTS, "Here are your requests:\n" +
                "==========================================================");
        m.put(RequestPrompts.ALL_REQUESTS, "Here are all requests.\n" +
                "==========================================================");
        m.put(RequestPrompts.END_OF_REQUESTS, "==========================================================\n" +
                "End of inbox.\n");
        m.put(RequestPrompts.REQUEST_MENU_USER, "Please enter a command:\n1 - Return to main menu\n2 - View all requests you've sent" +
                "\n3 - Make a request");
        m.put(RequestPrompts.REQUEST_MENU_ORG, "Please enter a command:\n1 - Return to main menu" +
                "\n2 - View all requests you've sent\n3 - Make a request\n4 - Delete a request" +
                "\n5 - View all requests from all users\n6 - Reply to the request");
        m.put(RequestPrompts.INVALID_COMMAND_ERROR, "Sorry, that is not a valid command. Please try again.\n");
        m.put(RequestPrompts.REPLY_CONFIRMATION, "Your reply has been sent.");
        m.put(RequestPrompts.TEXT_INPUT_PROMPT, "Please enter the text for your message.");
        m.put(RequestPrompts.REQUEST_CONFIRMATION, "Your request has been sent to the event organizers.");
        m.put(RequestPrompts.DELETION_CONFIRMATION, "You have successfully deleted the request.");
        m.put(RequestPrompts.REQUEST_SELECTION_PROMPT, "Please enter the number of the request you wish to delete.");
        m.put(RequestPrompts.REQUEST_SELECTION_CANCELLED, "Cancelled request selection.");
        m.put(RequestPrompts.NO_USER_REQUESTS_ERROR, "Sorry, you do not have any requests sent.");
        m.put(RequestPrompts.NO_REQUESTS_ERROR, "Sorry, there are no requests sent.");
        m.put(RequestPrompts.INVALID_REQUEST_SELECTION_ERROR, "Sorry, that is not a valid request number. " +
                "Please try again.");
        m.put(RequestPrompts.ALREADY_REPLIED, "There is already a reply to this request");
        m.put(RequestPrompts.REQUEST_REPLY_PROMPT, "Please enter the text for your reply");
        return m;
    }

    /**
     * Displays a message with the given information to the user.
     * <ul>
     *     <li>The message will be indented according to its nesting level in the reply hierarchy.</li>
     *     <li>If the user should be able to reply to this message, the message will be numbered,
     *     with number <code>curNum</code>.</li>
     * </ul>
     *  @param message  the message (as a string) to display to the user
     *
     * @param curNum the current message number, to be displayed alongside the message
     */
    public void displayRequest(String message, int curNum, boolean status) {
        if (status) {
            System.out.printf("%d: %s [ADDRESSED]\n", curNum, message);
        } else {
            System.out.printf("%d: %s [PENDING]\n", curNum, message);
        }
    }

}
