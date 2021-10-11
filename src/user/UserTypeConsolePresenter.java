package user;

import system.console.ConsolePresenter;

/**
 * This class is a specialized version of <code>ConsolePresenter</code> that displays user types.
 *
 * @param <T> the enum containing the set of possible messages that this presenter can print
 */
abstract public class UserTypeConsolePresenter<T extends Enum<T>> extends ConsolePresenter<T> {

    /**
     * Displays a menu of user types.
     */
    public void displayUserTypes() {
        System.out.println("1 - ATTENDEE");
        System.out.println("2 - ORGANIZER");
        System.out.println("3 - SPEAKER");
        System.out.println("4 - VIP");
        System.out.println("5 - ADMIN");
    }

}
