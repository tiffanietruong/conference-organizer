package requests;


import system.console.ConsoleInputController;
import user.UserType;
import user.manager.UserManagerFacade;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * The <code>RequestController</code> class is responsible for processing requests involving user requests.
 * These requests include interactions with user requests, such as making a request, and viewing requests.
 */
public class RequestController extends ConsoleInputController<RequestPrompts> {
    private final RequestManager requestManager;
    private final RequestPresenter requestPresenter;
    private final UserManagerFacade userManager;

    /**
     * Constructs a controller to process the current user's requests involving events in the conference.
     *
     * @param requestManager the manager responsible for request data during program execution
     * @param userManager    the manager responsible for user information during program execution
     * @param in             the instance of Scanner currently taking user input from the Console
     */
    public RequestController(RequestManager requestManager, UserManagerFacade userManager, Scanner in) {
        super(in, new RequestPresenter());
        this.requestManager = requestManager;
        this.requestPresenter = new RequestPresenter();
        this.userManager = userManager;
    }

    private boolean isOrganizer(String username) {
        return userManager.getUserType(username).equals(UserType.ORGANIZER);
    }

    /**
     * Displays the menu involving all of the user request interactions, with a different menu for
     * <code>ORGANIZER</code> users.
     *
     * @param username the name of the currently logged in user
     */
    public void requestMenu(String username) {
        boolean quit = false;
        if (isOrganizer(username)) {
            organizerRequestMenu(username);
        } else {
            Predicate<Integer> isValid = n -> (n >= 1 && n <= 3);
            int input;
            while (!quit) {
                input = promptInt(RequestPrompts.REQUEST_MENU_USER, RequestPrompts.INVALID_COMMAND_ERROR, isValid);
                switch (input) {
                    case 1:
                        quit = true;
                        break;
                    case 2:
                        displayUserRequests(username);
                        break;
                    case 3:
                        makeRequest(username);
                        break;
                }
            }
        }
    }

    private void organizerRequestMenu(String username) {
        boolean quit = false;
        int input;
        Predicate<Integer> isValid = n -> (n >= 1 && n <= 6);
        while (!quit) {
            input = promptInt(RequestPrompts.REQUEST_MENU_ORG, RequestPrompts.INVALID_COMMAND_ERROR, isValid);
            switch (input) {
                case 1:
                    quit = true;
                    break;
                case 2:
                    displayUserRequests(username);
                    break;
                case 3:
                    makeRequest(username);
                    break;
                case 4:
                    deleteRequest(requestManager.getRequestsIds());
                    break;
                case 5:
                    organizerDisplayRequests();
                    break;
                case 6:
                    replyToRequest(username, requestManager.getRequestsIds());
            }
        }
    }

    /**
     * Displays all requests made by all the users.
     */
    public void organizerDisplayRequests() {
        if (requestManager.getRequestsIds().isEmpty()) {
            requestPresenter.display(RequestPrompts.NO_REQUESTS_ERROR);
        } else {
            requestPresenter.display(RequestPrompts.ALL_REQUESTS);
            int curNum = 1;
            for (UUID requestId : requestManager.getRequestsIds()) {
                String requestString = requestManager.getRequestWithId(requestId).toString();
                boolean status = requestManager.getRequestWithId(requestId).getStatus();
                requestPresenter.displayRequest(requestString, curNum, status);
                curNum++;
            }
            requestPresenter.display(RequestPrompts.END_OF_REQUESTS);
        }
    }

    /**
     * Displays all of a specific user's requests, if there are any.
     *
     * @param username the name of the currently logged in user
     */
    public void displayUserRequests(String username) {
        List<UUID> userRequests = requestManager.userRequests(username);
        if (userRequests.isEmpty()) {
            requestPresenter.display(RequestPrompts.NO_USER_REQUESTS_ERROR);
        } else {
            requestPresenter.display(RequestPrompts.USER_REQUESTS);
            int curNum = 1;
            for (UUID requestId : userRequests) {
                String requestString = requestManager.getRequestWithId(requestId).toString();
                boolean status = requestManager.getRequestWithId(requestId).getStatus();
                requestPresenter.displayRequest(requestString, curNum, status);
                curNum++;
            }
            requestPresenter.display(RequestPrompts.END_OF_REQUESTS);
        }
    }

    /**
     * Allows an organizer user search through a list of requests and choose one to reply to.
     *
     * @param author   the name of the user writing the reply
     * @param requests a list of requests to select from
     */
    public void replyToRequest(String author, List<UUID> requests) {
        Predicate<Integer> isValid = n -> (n >= 1 && n <= requests.size());
        int requestSelection = promptInt(RequestPrompts.REQUEST_SELECTION_PROMPT,
                RequestPrompts.INVALID_REQUEST_SELECTION_ERROR, isValid);
        UUID curMessage = requests.get(requestSelection - 1);
        if (requestManager.hasReply(curMessage)) {
            requestPresenter.display(RequestPrompts.ALREADY_REPLIED);
        } else {
            String reply = promptString(RequestPrompts.REQUEST_REPLY_PROMPT);
            requestManager.addReply(reply, curMessage, author);
            requestManager.updateStatus(curMessage);
        }
    }

    /**
     * Allows a user to make a request to the organizers of the conference.
     *
     * @param username the name of the user making the request
     */
    public void makeRequest(String username) {
        String request = promptString(RequestPrompts.TEXT_INPUT_PROMPT);
        requestManager.addRequest(request, username);
        requestPresenter.display(RequestPrompts.REQUEST_CONFIRMATION);
    }

    /**
     * Allows an organizer to search through the list of requests and select one to delete.
     *
     * @param requests a list of requests to select from
     */
    public void deleteRequest(List<UUID> requests) {
        if (requestManager.getRequestsIds().isEmpty()) {
            requestPresenter.display(RequestPrompts.NO_REQUESTS_ERROR);
        } else {
            Predicate<Integer> isValid = n -> (n >= 1 && n <= requests.size());
            int selection = promptInt(RequestPrompts.REQUEST_SELECTION_PROMPT,
                    RequestPrompts.INVALID_REQUEST_SELECTION_ERROR, isValid);
            UUID curRequest = requests.get(selection - 1);
            requestManager.deleteRequest(curRequest);
            requestPresenter.display(RequestPrompts.DELETION_CONFIRMATION);
        }
    }
}