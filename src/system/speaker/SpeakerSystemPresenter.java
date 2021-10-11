package system.speaker;

import system.general.SystemPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>SpeakerSystemPresenter</code> class is responsible for displaying menu options specific to a Speaker.
 * This responsibility implies that the class currently acts as a Presenter and a UI.
 */
public class SpeakerSystemPresenter extends SystemPresenter {
    /**
     * Prompts for Speaker related actions
     */
    protected final List<String> speakerPrompts = new ArrayList<>();

    /**
     * Constructs a new <code>SpeakerSystemPresenter</code> object.
     */
    public SpeakerSystemPresenter() {
        initializeSpeakerPrompts();
    }

    /**
     * Initializes the prompts corresponding to options in the main menu.
     */
    @Override
    protected void initializeMainPrompts() {
        super.initializeMainPrompts();
        mainPrompts.add("5 - Speaker action menu");
    }

    /**
     * Initializes the prompts corresponding to options in the speaker menu.
     */
    protected void initializeSpeakerPrompts() {
        speakerPrompts.add("1 - Return to the main menu");
        speakerPrompts.add("2 - See the list of talks you are speaking at");
        speakerPrompts.add("3 - Message all attendees of all your talks");
        speakerPrompts.add("4 - Message all attendees of one of your talks");
    }

    /**
     * Displays the speaker menu to the user.
     */
    public void displaySpeakerMenu() {
        System.out.println("===== SPEAKER ACTIONS =====");
        for (String prompt : speakerPrompts) {
            System.out.println(prompt);
        }
    }
}
