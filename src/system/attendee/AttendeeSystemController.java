package system.attendee;

import raffle.RaffleController;
import system.ManagerParameterObject;
import system.general.SystemController;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The <code>AttendeeSystemController</code> class defines the specialized actions that an Attendee can do.
 * This class determines which additional menu options an Attendee sees once they log in and directs the requests
 * to complete those actions. These actions are related to adding reviews to events.
 */
public class AttendeeSystemController extends SystemController {

    /**
     * Commands available to the user related to attendee-specific actions
     */
    protected final Map<String, Runnable> attendeeCommands = new HashMap<>();
    private final RaffleController raffleController;
    private final AttendeeSystemPresenter presenter;

    /**
     * Constructs a controller to process the user's requests given a menu of options.
     *
     * @param presenter an <code>AttendeeSystemPresenter</code> that enables the display of messages related to the menu
     * @param managers  the managers responsible for data during program execution
     * @param curUser   the username of the currently logged in user
     * @param in        the instance of Scanner currently looking at the Console
     */
    public AttendeeSystemController(AttendeeSystemPresenter presenter, ManagerParameterObject managers, String curUser,
                                    Scanner in) {
        super(managers, curUser, in);
        this.raffleController = new RaffleController(managers.getUserManager());
        this.presenter = presenter;
        initializeAttendeeCommands();
    }

    /**
     * Returns the presenter for this controller.
     *
     * @return an AttendeeSystemPresenter for this controller
     */
    @Override
    protected AttendeeSystemPresenter getPresenter() {
        return presenter;
    }

    /**
     * Initializes the commands available to the attendee in the main menu.
     */
    @Override
    protected void initializeMainCommands() {
        super.initializeMainCommands();
        mainCommands.put("5", this::processAttendeeMenu);
    }

    /**
     * Initializes the commands available to the attendee in the specialized attendee menu.
     */
    protected void initializeAttendeeCommands() {
        attendeeCommands.put("2", this::writeReview);
    }

    private void processAttendeeMenu() {
        processMenu(() -> getPresenter().displayAttendeeMenu(), attendeeCommands);
    }

    private void writeReview() {
        eventController.writeReview(curUser);
        raffleController.addEntry(curUser);
    }
}
