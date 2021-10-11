package view;

import event.EventType;
import event.manager.EventManagerFacade;
import room.RoomController;
import user.UserType;
import user.controller.UserTypeInputController;
import user.manager.UserManagerFacade;

import java.util.*;

/**
 * The ViewController class is responsible for processing requests involving the View menu.
 */
public class ViewController extends UserTypeInputController<ViewPrompts> {

    private final EventManagerFacade eventManager;
    private final RoomController roomController;
    private final UserManagerFacade userManager;
    private final ViewPresenter viewPresenter;
    private final Scanner in;

    /**
     * Constructs a controller to process requests involving the View menu.
     *
     * @param eventManager   the manager responsible for event data during program execution
     * @param roomController the controller responsible for interacting with rooms during program execution
     * @param userManager    the manager responsible for user data during program execution
     * @param in             the instance of Scanner currently looking at the Console
     */
    public ViewController(EventManagerFacade eventManager, RoomController roomController, UserManagerFacade userManager,
                          Scanner in) {
        super(in, new ViewPresenter());
        this.eventManager = eventManager;
        this.roomController = roomController;
        this.userManager = userManager;
        this.in = in;
        this.viewPresenter = new ViewPresenter();
    }

    //<editor-fold desc="Menu Selection Methods">

    /**
     * Processes the user's input regarding what they would like to view.
     */
    public void processViewMenu() {
        boolean quit = false;
        String input;
        while (!quit) {
            viewPresenter.displayViewMenu();
            input = in.nextLine();
            switch (input) {
                case "1":
                    quit = true;
                    break;
                case "2":
                    roomController.viewRooms();
                    break;
                case "3":
                    viewEventStatistics();
                    break;
                case "4":
                    viewUserStatistics();
                    break;
                case "5":
                    viewRankingStatistics();
                    break;
                default:
                    viewPresenter.displayInvalidInputError();
            }
        }
    }

    private void viewEventStatistics() {
        boolean quit = false;
        String input;
        while (!quit) {
            viewPresenter.displayEventStatisticsMenu();
            input = in.nextLine();
            switch (input) {
                case "1":
                    quit = true;
                    break;
                case "2":
                    viewEventDistribution();
                    break;
                case "3":
                    viewEventsOfType();
                    break;
                default:
                    viewPresenter.displayInvalidInputError();
            }
        }
    }

    private void viewUserStatistics() {
        boolean quit = false;
        String input;
        while (!quit) {
            viewPresenter.displayUserStatisticsMenu();
            input = in.nextLine();
            switch (input) {
                case "1":
                    quit = true;
                    break;
                case "2":
                    viewUserDistribution();
                    break;
                case "3":
                    viewUsernamesOfType();
                    break;
                default:
                    viewPresenter.displayInvalidInputError();
            }
        }
    }

    private void viewRankingStatistics() {
        boolean quit = false;
        String input;
        while (!quit) {
            viewPresenter.displayRankingMenu();
            input = in.nextLine();
            switch (input) {
                case "1":
                    quit = true;
                    break;
                case "2":
                    viewTopFiveLargest(2);
                    break;
                case "3":
                    viewTopFiveLargest(1);
                    break;
                case "4":
                    viewTopFiveUsers(UserType.SPEAKER);
                    break;
                case "5":
                    viewTopFiveUsers(UserType.ATTENDEE);
                    break;
                default:
                    viewPresenter.displayInvalidInputError();
            }
        }
    }

    //</editor-fold>

    //<editor-fold desc="Event Menu Methods">
    private EventType selectEventType() {
        EventType selectedType = null;
        while (selectedType == null) {
            viewPresenter.display(ViewPrompts.EVENT_TYPE_PROMPT);
            viewPresenter.displayEventTypes();
            String input = in.nextLine();
            switch (input) {
                case "1":
                    selectedType = EventType.PARTY;
                    break;
                case "2":
                    selectedType = EventType.TALK;
                    break;
                case "3":
                    selectedType = EventType.PANEL;
                    break;
                default:
                    viewPresenter.display(ViewPrompts.INVALID_INPUT_ERROR);
            }
        }
        return selectedType;
    }

    /**
     * Processes the request to display the event titles of all <code>Events</code> of an
     * inputted <code>EventType</code>.
     * This method allows the client to select a specific type.
     */
    public void viewEventsOfType() {
        viewEventsOfType(selectEventType());
    }

    /**
     * Processes the request to display the event titles of all <code>Events</code> that are
     * of type <code>EventType</code>.
     *
     * @param eventType the type of user to display all usernames of
     */
    private void viewEventsOfType(EventType eventType) {
        List<String> eventTitles = eventManager.getAllEventsOfType(eventType);
        viewPresenter.displayEventsOfType(eventTitles, eventType);
    }


    private void viewEventDistribution() {
        Map<EventType, Integer> typeToNumEvents = new HashMap<>();
        typeToNumEvents.put(EventType.PARTY, eventManager.getAllEventsOfType(EventType.PARTY).size());
        typeToNumEvents.put(EventType.TALK, eventManager.getAllEventsOfType(EventType.TALK).size());
        typeToNumEvents.put(EventType.PANEL, eventManager.getAllEventsOfType(EventType.PANEL).size());
        viewPresenter.displayEventDistribution(typeToNumEvents);
    }
    //</editor-fold>

    //<editor-fold desc="User Menu Methods">

    private UserType selectUserType() {
        return promptUserType(ViewPrompts.USER_TYPE_PROMPT, ViewPrompts.INVALID_INPUT_ERROR);
    }

    /**
     * Processes the request to display the usernames of all <code>Users</code> of an inputted <code>UserType</code>.
     * This method allows the client to select a specific type.
     */
    public void viewUsernamesOfType() {
        viewUsernamesOfType(selectUserType());
    }

    /**
     * Processes the request to display the usernames of all <code>Users</code> that are of type <code>UserType</code>.
     *
     * @param userType the type of user to display all usernames of
     */
    public void viewUsernamesOfType(UserType userType) {
        List<String> usernames = userManager.getAllUsernamesOfType(userType);
        viewPresenter.displayUsernamesOfType(usernames, userType);
    }

    private void viewUserDistribution() {
        Map<UserType, Integer> typeToNumUsers = new HashMap<>();
        typeToNumUsers.put(UserType.ATTENDEE, userManager.getAllUsernamesOfType(UserType.ATTENDEE).size());
        typeToNumUsers.put(UserType.ORGANIZER, userManager.getAllUsernamesOfType(UserType.ORGANIZER).size());
        typeToNumUsers.put(UserType.SPEAKER, userManager.getAllUsernamesOfType(UserType.SPEAKER).size());
        typeToNumUsers.put(UserType.VIP, userManager.getAllUsernamesOfType(UserType.VIP).size());
        typeToNumUsers.put(UserType.ADMIN, userManager.getAllUsernamesOfType(UserType.ADMIN).size());
        viewPresenter.displayUserDistribution(typeToNumUsers);
    }

    //</editor-fold>

    //<editor-fold desc="Ranking Menu Methods">

    private void viewTopFiveLargest(int input) {
        List<String> topFiveEvents = new ArrayList<>();
        List<Number> topFiveNumbers = new ArrayList<>();
        List<String> allEventsCopy = eventManager.getEventList();
        int count = 0;
        while (count < 5 && !allEventsCopy.isEmpty()) {
            String currentEventWithMost;
            if (input == 1) {
                currentEventWithMost = getMostProportion(allEventsCopy);
            } else {
                currentEventWithMost = getMostPeople(allEventsCopy);
            }
            if (!currentEventWithMost.equals("")) {
                topFiveEvents.add(currentEventWithMost);
                if (input == 1) {
                    topFiveNumbers.add(((float) eventManager.getAttendees(currentEventWithMost).size() /
                            eventManager.getCapacity(currentEventWithMost)));
                } else {
                    topFiveNumbers.add(eventManager.getAttendees(currentEventWithMost).size());
                }
                allEventsCopy.remove(currentEventWithMost);
            }
            count = count + 1;
        }
        if (input == 1) {
            viewPresenter.displayTopFiveProportion(topFiveEvents, topFiveNumbers);
        } else {
            viewPresenter.displayTopFiveMostPeople(topFiveEvents, topFiveNumbers);
        }
    }

    private void viewTopFiveUsers(UserType userType) {
        List<String> topFiveSpeakers = new ArrayList<>();
        List<String> topFiveNumbers = new ArrayList<>();
        List<String> specificUsersCopy = userManager.getAllUsernamesOfType(userType);
        int count = 0;
        while (count < 5 && !specificUsersCopy.isEmpty()) {
            String currentMostActiveSpeaker;
            if (userType.equals(UserType.SPEAKER)) {
                currentMostActiveSpeaker = getActiveSpeaker(specificUsersCopy);
            } else {
                currentMostActiveSpeaker = getActiveAttendee(specificUsersCopy);
            }
            if (!currentMostActiveSpeaker.equals("")) {
                String[] splitString = currentMostActiveSpeaker.split(" ");
                if (!topFiveSpeakers.contains(splitString[0])) {
                    topFiveSpeakers.add(splitString[0]);
                    topFiveNumbers.add(splitString[1]);
                    specificUsersCopy.remove(splitString[0]);
                }
            }
            count = count + 1;
        }
        if (userType.equals(UserType.SPEAKER)) {
            viewPresenter.displayTopFiveSpeakers(topFiveSpeakers, topFiveNumbers);
        } else {
            viewPresenter.displayTopFiveAttendees(topFiveSpeakers, topFiveNumbers);
        }
    }

    //</editor-fold>
    private String getActiveSpeaker(List<String> speakerNames) {
        int mostEvents = -1;
        String activeSpeaker = "";
        for (String speaker : speakerNames) {
            List<String> scheduledToSpeakAt = eventManager.getEventsBySpeaker(speaker);
            if (scheduledToSpeakAt.size() >= mostEvents) {
                mostEvents = scheduledToSpeakAt.size();
                activeSpeaker = speaker + " " + mostEvents;
            }
        }
        return activeSpeaker;
    }

    private String getActiveAttendee(List<String> attendeeNames) {
        int mostEvents = -1;
        String activeAttendee = "";
        for (String user : attendeeNames) {
            List<String> eventsOfUser = eventManager.getSignedUpEvents(user);
            if (eventsOfUser.size() > mostEvents) {
                mostEvents = eventsOfUser.size();
                activeAttendee = user + " " + mostEvents;
            }
        }
        return activeAttendee;
    }

    private String getMostPeople(List<String> eventTitles) {
        String eventWithMostPeople = "";
        int mostPeople = -1;
        for (String event : eventTitles) {
            int numSignedUp = eventManager.getAttendees(event).size();
            if (numSignedUp > mostPeople) {
                eventWithMostPeople = event;
                mostPeople = numSignedUp;
            }
        }
        return eventWithMostPeople;
    }

    private String getMostProportion(List<String> eventTitles) {
        String eventWithMostPeople = "";
        int mostProportion = -1;

        for (String event : eventTitles) {
            int numSignedUp = eventManager.getAttendees(event).size();
            int capacity = eventManager.getCapacity(event);
            float proportion = ((float) numSignedUp / capacity);
            if ((proportion) > mostProportion) {
                mostProportion = numSignedUp / capacity;
                eventWithMostPeople = event;
            }
        }
        return eventWithMostPeople;
    }
}
