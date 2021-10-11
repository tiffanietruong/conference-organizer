package system.console;

import java.util.EnumMap;
import java.util.List;

/**
 * The <code>ConsolePresenter</code> class is an abstract class that defines a shared interface for classes that print
 * messages to the Console. The set of possible messages that can be printed is the Enum type parameter.
 *
 * @param <T> the enum containing the set of possible messages that this presenter can print
 */
@SuppressWarnings("SameParameterValue")
abstract public class ConsolePresenter<T extends Enum<T>> {

    private final EnumMap<T, String> messages;
    private final String defaultEndingStr;

    /**
     * Creates a new <code>ConsolePresenter</code> with a given string that will be appended to every string
     * displayed by the presenter.
     *
     * @param defaultEndingStr the ending to append to every string displayed by the presenter
     */
    protected ConsolePresenter(String defaultEndingStr) {
        this.defaultEndingStr = defaultEndingStr;
        this.messages = initializeMessages();
    }

    /**
     * Creates a new <code>ConsolePresenter</code> with a newline as the string appended to every string
     * displayed by the presenter.
     */
    protected ConsolePresenter() {
        this("\n");
    }

    /**
     * Returns an <code>EnumMap</code> that maps each enum key of type <code>T</code> to its respective console message
     *
     * @return an <code>EnumMap</code> that maps each enum key of type <code>T</code> to its respective console message
     */
    abstract protected EnumMap<T, String> initializeMessages();

    /**
     * Displays the string corresponding to the key <code>message</code>.
     * Precondition: the key <code>message</code> must have been added to the presenter during the execution of
     * {@link #initializeMessages() initializeMessages}.
     *
     * @param message the key whose corresponding string will be displayed
     */
    public void display(T message) {
        System.out.print(messages.get(message) + defaultEndingStr);
    }

    /**
     * Displays the contents of the given list to the screen.
     * The format of the output is one line with each entry of the list separated by commas.
     *
     * @param information the list containing information to be printed
     */
    protected void displayContents(List<String> information) {
        for (int i = 0; i < information.size(); i++) {
            System.out.print(information.get(i));
            if (i != information.size() - 1) {   /* add a comma if not at last username */
                System.out.print(", ");
            }
        }
        System.out.println("\n");
    }

}
