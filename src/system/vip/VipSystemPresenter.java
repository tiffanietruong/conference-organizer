package system.vip;

import system.general.SystemPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>VipSystemPresenter</code> class is responsible for displaying menu options specific to a VIP.
 * This responsibility implies that the class currently acts as a Presenter and a UI.
 */
public class VipSystemPresenter extends SystemPresenter {
    /**
     * Prompts for VIP related actions
     */
    protected final List<String> vipPrompts = new ArrayList<>();

    /**
     * Constructs a new <code>VipSystemPresenter</code> object.
     */
    public VipSystemPresenter() {
        initializeVipPrompts();
    }

    /**
     * Initializes the prompts corresponding to options in the main menu.
     */
    @Override
    protected void initializeMainPrompts() {
        super.initializeMainPrompts();
        mainPrompts.add("5 - VIP action menu");
    }

    /**
     * Initializes the prompts corresponding to options in the VIP menu.
     */
    protected void initializeVipPrompts() {
        vipPrompts.add("1 - Return to the main menu");
        vipPrompts.add("2 - View VIP event schedule");
        vipPrompts.add("3 - Sign up for a VIP event");
        vipPrompts.add("4 - Cancel your spot in a VIP event");
    }

    /**
     * Displays the VIP menu to the user.
     */
    public void displayVipMenu() {
        System.out.println("===== VIP ACTIONS =====");
        for (String prompt : vipPrompts) {
            System.out.println(prompt);
        }
    }
}