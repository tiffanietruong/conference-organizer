package system.speaker;

import system.ManagerParameterObject;
import system.general.SystemController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The <code>SpeakerSystemController</code> class defines the specialized actions that a Speaker can do.
 * This class determines which additional menu options a Speaker sees once they log in and directs the requests
 * to complete those actions. Such actions are generally related to the events that a Speaker is speaking at in the
 * conference.
 */
public class SpeakerSystemController extends SystemController {

    /**
     * Commands available to the user related to speaker-specific actions
     */
    protected final Map<String, Runnable> speakerCommands = new HashMap<>();
    private final SpeakerSystemPresenter presenter;

    /**
     * Constructs a <code>SpeakerSystemController</code> object to process the speaker's requests.
     *
     * @param presenter a <code>SpeakerSystemPresenter</code> that enables the display of messages related to the menu
     * @param managers  the managers responsible for data during program execution
     * @param curUser   the username of the currently logged in user
     * @param in        the instance of Scanner currently looking at the Console
     */
    public SpeakerSystemController(SpeakerSystemPresenter presenter,
                                   ManagerParameterObject managers, String curUser, Scanner in) {
        super(managers, curUser, in);
        this.presenter = presenter;
        initializeSpeakerCommands();
    }

    /**
     * Returns the presenter for this controller.
     *
     * @return a SpeakerSystemPresenter for this controller
     */
    @Override
    protected SpeakerSystemPresenter getPresenter() {
        return presenter;
    }

    //<editor-fold desc="Initializing Menu Commands">

    /**
     * Initializes the commands available to the speaker in the main menu.
     */
    @Override
    protected void initializeMainCommands() {
        super.initializeMainCommands();
        mainCommands.put("5", this::processSpeakerMenu);
    }

    /**
     * Initializes the commands available to the speaker in the specialized speaker menu.
     */
    protected void initializeSpeakerCommands() {
        speakerCommands.put("2", () -> eventController.displaySpeakerEvents(curUser));
        speakerCommands.put("3", this::messageAllYourEventAttendees);
        speakerCommands.put("4", this::messageAttendeesForOneEvent);
    }
    //</editor-fold>

    private void processSpeakerMenu() {
        processMenu(() -> getPresenter().displaySpeakerMenu(), speakerCommands);
    }

    //<editor-fold desc="Processing Messaging-Related Actions">

    /**
     * Messages all attendees of the events at which the current user is speaking.
     */
    private void messageAllYourEventAttendees() {
        List<String> recipients = eventController.getAttendeesForEventsBySpeaker(curUser);
        messageController.sendMessage(curUser, recipients);
    }

    /**
     * Messages all attendees of a single event at which the current user is speaking.
     */
    private void messageAttendeesForOneEvent() {
        String title = eventController.readEventTitle();
        if (eventController.isSpeakerForEvent(curUser, title)) {
            List<String> recipients = eventController.getAttendeesForEvent(title);
            messageController.sendMessage(curUser, recipients);
        }
    }
    //</editor-fold>
}
