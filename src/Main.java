import startup.StartUpController;

/**
 * This class is the entry point to the program.
 */
public class Main {

    /**
     * Begins execution of the program.
     *
     * @param args any command-line arguments
     */
    public static void main(String[] args) {
        StartUpController startup = new StartUpController();
        startup.run();
    }
}
