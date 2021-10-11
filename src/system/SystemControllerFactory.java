package system;

import system.admin.AdminSystemController;
import system.admin.AdminSystemPresenter;
import system.attendee.AttendeeSystemController;
import system.attendee.AttendeeSystemPresenter;
import system.general.SystemController;
import system.organizer.OrganizerSystemController;
import system.organizer.OrganizerSystemPresenter;
import system.speaker.SpeakerSystemController;
import system.speaker.SpeakerSystemPresenter;
import system.vip.VipSystemController;
import system.vip.VipSystemPresenter;
import user.UserType;

import java.util.Scanner;

/**
 * This <code>SystemControllerFactory</code> is a Factory class that implements the Factory Method design pattern to
 * process calls for the creation of a <code>SystemController</code> object. It is used to:
 * <ul>
 *     <li>Remove hard dependencies with <code>StartupController</code>
 *     <li>Make <code>SystemController</code> more extensible</li> by allowing easy creation of new subclasses
 *     <li>Encapsulate the <code>SystemController</code> constructor calls</li>
 * </ul>
 *
 * @see SystemController
 * @see OrganizerSystemController
 * @see SpeakerSystemController
 */
public class SystemControllerFactory {

    /**
     * Creates a new <code>SystemController</code> object depending on whether the current user is an Attendee,
     * Organizer, or Speaker.
     *
     * @param userType the type of user
     * @param managers the managers responsible for data during program execution
     * @param curUser  the username of the currently logged in user
     * @param in       the instance of Scanner currently looking at the Console
     * @return a SystemController object depending on the current user's type
     */
    public SystemController createSystemController(UserType userType,
                                                   ManagerParameterObject managers, String curUser, Scanner in) {
        SystemController systemController;
        switch (userType) {
            case VIP:
                systemController = new VipSystemController(new VipSystemPresenter(), managers, curUser, in);
                break;
            case ORGANIZER:
                systemController = new OrganizerSystemController(new OrganizerSystemPresenter(), managers, curUser, in);
                break;
            case SPEAKER:
                systemController = new SpeakerSystemController(new SpeakerSystemPresenter(), managers, curUser, in);
                break;
            case ADMIN:
                systemController = new AdminSystemController(new AdminSystemPresenter(), managers, curUser, in);
                break;
            default: //Attendee
                systemController = new AttendeeSystemController(new AttendeeSystemPresenter(), managers, curUser, in);
                break;
        }
        return systemController;
    }
}
