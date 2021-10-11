package view;

import event.EventType;
import user.UserType;
import user.UserTypeConsolePresenter;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * The ViewPresenter class is responsible for displaying messages that appear when in the View menu.
 * Its primary task is to provide the user with useful statistics about the conference.
 */
class ViewPresenter extends UserTypeConsolePresenter<ViewPrompts> {

    @Override
    protected EnumMap<ViewPrompts, String> initializeMessages() {
        EnumMap<ViewPrompts, String> m = new java.util.EnumMap<>(ViewPrompts.class);
        m.put(ViewPrompts.USER_TYPE_PROMPT, "Please select a user type:");
        m.put(ViewPrompts.INVALID_INPUT_ERROR, "Sorry, that input was not valid.");
        m.put(ViewPrompts.NO_USERS_OF_TYPE_ERROR, "There are no users of the selected type.");
        m.put(ViewPrompts.EVENT_TYPE_PROMPT, "Please select an event type:");
        m.put(ViewPrompts.NO_EVENTS_OF_TYPE_ERROR, "There are no events of the selected type.");
        m.put(ViewPrompts.NO_EVENTS_ERROR, "There are no currently events in the conference.");
        m.put(ViewPrompts.NO_USERS_ATTENDING_EVENTS_ERROR, "There are currently no users attending any events.");
        m.put(ViewPrompts.NO_SPEAKERS_ERROR, "There are currently no speakers speaking at any events.");
        return m;
    }

    public void displayInvalidInputError() {
        System.out.println("Sorry, your input was invalid.");
    }

    //<editor-fold desc="View Menus">

    /**
     * Displays the menu of initial actions that the user can do when entering the Viewing menu.
     */
    public void displayViewMenu() {
        System.out.println("===== VIEW INFORMATION MENU =====");
        System.out.println("1 - Return to the main menu");
        System.out.println("2 - View room information");
        System.out.println("3 - View event statistics");
        System.out.println("4 - View user statistics");
        System.out.println("5 - View ranking statistics");
    }

    /**
     * Displays the menu of actions to view event statistics.
     */
    public void displayEventStatisticsMenu() {
        System.out.println("===== VIEW EVENT STATISTICS MENU =====");
        System.out.println("1 - Return to the main menu");
        System.out.println("2 - View event distribution by type");
        System.out.println("3 - View events of a given type");
    }

    /**
     * Displays the menu of actions to view user statistics.
     */
    public void displayUserStatisticsMenu() {
        System.out.println("===== VIEW USER STATISTICS MENU =====");
        System.out.println("1 - Return to the main menu");
        System.out.println("2 - View user distribution");
        System.out.println("3 - View users of a given type");
    }

    /**
     * Displays the menu of actions to view ranking statistics.
     */
    public void displayRankingMenu() {
        System.out.println("===== VIEW RANKING STATISTICS MENU =====");
        System.out.println("1 - Return to the main menu");
        System.out.println("2 - View the top 5 events with the most people");
        System.out.println("3 - View the top 5 events with the highest proportion of people");
        System.out.println("4 - View the top 5 speakers assigned to the most events");
        System.out.println("5 - View the top 5 users signed up for the most events");
    }

    //</editor-fold>

    //<editor-fold desc="View User Information">

    /**
     * Displays the menu of actions for selecting a specific UserType to view.
     */
    public void displayUserTypes() {
        System.out.println("1 - ATTENDEE");
        System.out.println("2 - ORGANIZER");
        System.out.println("3 - SPEAKER");
        System.out.println("4 - VIP");
        System.out.println("5 - ADMIN");
    }

    /**
     * Prints the given list of usernames representing users of a specific type to the screen.
     * This method gives a specific error message if there are no users of the given type.
     *
     * @param usernames the list of usernames to be printed to the screen.
     * @param userType  the type of user being displayed
     */
    public void displayUsernamesOfType(List<String> usernames, UserType userType) {
        System.out.println("===== ALL " + userType.toString() + " USERNAMES =====");
        if (usernames.isEmpty()) {
            display(ViewPrompts.NO_USERS_OF_TYPE_ERROR);
        } else {
            displayContents(usernames);
        }
    }

    /**
     * Prints each UserType and the number of Users in the conference that correspond to each UserType.
     *
     * @param typeToNumUsers a map with keys being each UserType and the values being the number of Users that
     *                       correspond to their UserType key
     */
    public void displayUserDistribution(Map<UserType, Integer> typeToNumUsers) {
        System.out.println("===== USER DISTRIBUTION  =====");
        int totalUsers = 0;
        for (Integer numUsersOfType : typeToNumUsers.values()) {
            totalUsers += numUsersOfType;
        }
        System.out.println("There are " + totalUsers + " total users in the conference.");
        System.out.println("===== TYPE BREAKDOWN =====");
        for (UserType type : typeToNumUsers.keySet()) {
            if (typeToNumUsers.get(type) == 1) {
                System.out.println("There is " + typeToNumUsers.get(type) + " user of type " + type + ".");
            } else {
                System.out.println("There are " + typeToNumUsers.get(type) + " users of type " + type + ".");
            }
        }
        System.out.print("\n");
    }

    //<editor-fold desc="View Event Information">

    /**
     * Displays the menu of actions for selecting a specific EventType to view.
     */
    public void displayEventTypes() {
        System.out.println("1 - PARTY");
        System.out.println("2 - TALK");
        System.out.println("3 - PANEL");
    }


    /**
     * Prints the given list of event titles representing events of a specific type to the screen.
     * This method gives a specific error message if there are no events of the given type.
     *
     * @param eventTitles the list of event titles to be printed to the screen.
     * @param eventType   the type of event being displayed
     */
    public void displayEventsOfType(List<String> eventTitles, EventType eventType) {
        System.out.println("===== ALL " + eventType.toString() + " TITLES =====");
        if (eventTitles.isEmpty()) {
            display(ViewPrompts.NO_EVENTS_OF_TYPE_ERROR);
        } else {
            displayContents(eventTitles);
        }
    }


    /**
     * Prints each EventType and the number of Events in the conference that correspond to each EventType.
     *
     * @param typeToNumEvents a map with keys being each EventType and the values being the number of Events that
     *                        correspond to their EventType key
     */
    public void displayEventDistribution(Map<EventType, Integer> typeToNumEvents) {
        System.out.println("===== EVENT DISTRIBUTION  =====");
        int totalEvents = 0;
        for (Integer numEventsOfType : typeToNumEvents.values()) {
            totalEvents += numEventsOfType;
        }
        System.out.println("There are " + totalEvents + " total events in the conference.");
        System.out.println("===== TYPE BREAKDOWN =====");
        for (EventType type : typeToNumEvents.keySet()) {
            if (typeToNumEvents.get(type) == 1) {
                System.out.println("There is " + typeToNumEvents.get(type) + " event of type " + type + ".");
            } else {
                System.out.println("There are " + typeToNumEvents.get(type) + " events of type " + type + ".");
            }
        }
        System.out.print("\n");

    }
    //</editor-fold>

    //<editor-fold desc="View Ranking Information">

    /**
     * Prints the top 5 most popular Events with their corresponding number of Users attending the event.
     *
     * @param topFiveMostPeople a list of the top 5 most popular Events sorted from most popular to less popular
     * @param topFiveNumbers    a list of the corresponding number of attendees attending the most popular Events
     */
    public void displayTopFiveMostPeople(List<String> topFiveMostPeople, List<Number> topFiveNumbers) {
        System.out.println("===== TOP 5 EVENTS WITH THE MOST PEOPLE =====");
        if (topFiveMostPeople.isEmpty()) {
            display(ViewPrompts.NO_EVENTS_ERROR);
        } else {
            int count = 1;
            for (String eventTitle : topFiveMostPeople) {
                System.out.println(count + ". " + eventTitle + " - Attendees: " + topFiveNumbers.get(count - 1));
                count = count + 1;
            }
        }
    }

    /**
     * Prints the top 5 most packed Events with their corresponding value of how filled the event is
     * (how popular the event is with respect to the event's capacity).
     *
     * @param topFiveMostPeople a list of the top 5 most popular Events sorted from most popular to less popular
     * @param topFiveNumbers    a list of the corresponding number of attendees in the top 5 attending the most popular
     *                          Events with respect to each Event's capacity.
     */
    public void displayTopFiveProportion(List<String> topFiveMostPeople, List<Number> topFiveNumbers) {
        System.out.println("===== TOP 5 EVENTS WITH THE HIGHEST PROPORTION WITH RESPECT TO THE EVENT CAPACITY=====");
        if (topFiveMostPeople.isEmpty()) {
            display(ViewPrompts.NO_EVENTS_ERROR);
        } else {
            int count = 1;
            for (String eventTitle : topFiveMostPeople) {
                System.out.println(count + ". " + eventTitle + " - Event is at " +
                        Math.round((Float) topFiveNumbers.get(count - 1) * 10000) / 100.0 + "% capacity.");
                count = count + 1;
            }
        }
    }

    /**
     * Prints the top 5 most active Attendees with the corresponding number of Events the Attendees are attending.
     *
     * @param topFiveAttendees a list of the top 5 most active Attendees sorted from most active to less active
     * @param topFiveNumbers   a list of the corresponding number of events each attendee in the top 5 is attending
     */
    public void displayTopFiveAttendees(List<String> topFiveAttendees, List<String> topFiveNumbers) {
        System.out.println("===== TOP 5 USERS ATTENDING THE MOST EVENTS =====");
        if (topFiveAttendees.isEmpty()) {
            display(ViewPrompts.NO_USERS_ATTENDING_EVENTS_ERROR);
        } else {
            int count = 1;
            for (String username : topFiveAttendees) {
                System.out.println(count + ". " + username + " - Attending "
                        + topFiveNumbers.get(count - 1) + " events.");
                count = count + 1;
            }
        }
    }

    /**
     * Prints the top 5 most active Speakers with the corresponding number of Events the Speakers are speaking at.
     *
     * @param topFiveSpeakers a list of the top 5 most active Speakers sorted from most active to less active
     * @param topFiveNumbers  a list of the corresponding number of events each speaker in the top 5 is speaking at
     */
    public void displayTopFiveSpeakers(List<String> topFiveSpeakers, List<String> topFiveNumbers) {
        System.out.println("===== TOP 5 SPEAKERS SPEAKING AT THE MOST EVENTS =====");
        if (topFiveSpeakers.isEmpty()) {
            display(ViewPrompts.NO_SPEAKERS_ERROR);
        } else {
            int count = 1;
            for (String username : topFiveSpeakers) {
                System.out.println(count + ". " + username + " - Speaking at "
                        + topFiveNumbers.get(count - 1) + " events.");
                count = count + 1;
            }
        }
    }

}

