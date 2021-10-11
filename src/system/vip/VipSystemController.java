package system.vip;

import system.ManagerParameterObject;
import system.general.SystemController;
import user.UserType;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The <code>VipSystemController</code> class defines the specialized actions that a VIP can do.
 * This class determines which additional menu options a VIP sees once they log in and directs the requests
 * to complete those actions. These actions are related to signing up for VIP-specific events.
 */
public class VipSystemController extends SystemController {

    /**
     * Commands available to the user related to VIP-specific actions
     */
    protected final Map<String, Runnable> vipCommands = new HashMap<>();
    private final VipSystemPresenter presenter;

    /**
     * Constructs a <code>SpeakerSystemController</code> object to process the speaker's requests.
     *
     * @param presenter a <code>VipSystemPresenter</code> that enables the display of messages related to the menu
     * @param managers  the managers responsible for data during program execution
     * @param curUser   the username of the currently logged in user
     * @param in        the instance of Scanner currently looking at the Console
     */
    public VipSystemController(VipSystemPresenter presenter,
                               ManagerParameterObject managers, String curUser, Scanner in) {
        super(managers, curUser, in);
        this.presenter = presenter;
        initializeVipCommands();
    }

    /**
     * Returns the presenter for this controller.
     *
     * @return a VipSystemPresenter for this controller
     */
    @Override
    protected VipSystemPresenter getPresenter() {
        return presenter;
    }

    //<editor-fold desc="Initializing Menu Commands">

    /**
     * Initializes the commands available to the VIP user in the main menu.
     */
    @Override
    protected void initializeMainCommands() {
        super.initializeMainCommands();
        mainCommands.put("5", this::processVipMenu);
    }

    /**
     * Initializes the commands available to the VIP in the specialized VIP menu.
     */
    protected void initializeVipCommands() {
        vipCommands.put("2", eventController::displayVipSchedule);
        vipCommands.put("3", () -> signUpController.signUpForEvent(curUser, UserType.VIP));
        vipCommands.put("4", () -> signUpController.cancelSpotInEvent(curUser, UserType.VIP));
    }
    //</editor-fold>

    private void processVipMenu() {
        processMenu(() -> getPresenter().displayVipMenu(), vipCommands);
    }
}
