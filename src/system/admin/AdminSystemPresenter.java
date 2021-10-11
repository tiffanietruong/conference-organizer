package system.admin;

import system.general.SystemPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>AdminSystemPresenter</code> class is responsible for displaying menu options specific to an Admin.
 * This responsibility implies that the class currently acts as a Presenter and a UI.
 */
public class AdminSystemPresenter extends SystemPresenter {

    /**
     * Prompts for Admin related actions
     */
    protected final List<String> adminPrompts = new ArrayList<>();

    /**
     * Constructs a new <code>AdminSystemPresenter</code> object.
     */
    public AdminSystemPresenter() {
        initializeAdminPrompts();
    }

    /**
     * Initializes the prompts corresponding to options in the main menu.
     */
    @Override
    protected void initializeMainPrompts() {
        super.initializeMainPrompts();
        mainPrompts.add("5 - Admin action menu");
    }

    /**
     * Initializes prompts corresponding to options in the specialized admin menu.
     */
    protected void initializeAdminPrompts() {
        adminPrompts.add("1 - Return to main menu");
        adminPrompts.add("2 - Ban a user");
        adminPrompts.add("3 - Display list of banned users");
        adminPrompts.add("4 - Unban a user");
        adminPrompts.add("5 - Delete an account");
        adminPrompts.add("6 - Change a User's Type");
        adminPrompts.add("7 - Pull raffle winner");
    }

    /**
     * Displays the admin menu to the user.
     */
    public void displayAdminMenu() {
        System.out.println("===== ADMIN ACTIONS =====");
        for (String prompt : adminPrompts) {
            System.out.println(prompt);
        }
    }
}
