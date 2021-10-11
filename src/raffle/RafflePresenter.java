package raffle;

import system.console.ConsolePresenter;

import java.util.EnumMap;

/**
 * The <code>RafflePresenter</code> class is responsible for displaying messages related to raffles.
 * These messages include errors and success confirmations that occur when an Admin tries to pull a raffle winner.
 * This responsibility implies that the class currently acts as a Presenter and a UI.
 */
public class RafflePresenter extends ConsolePresenter<RafflePrompts> {

    /**
     * Returns an <code>EnumMap</code> that maps each enum key of type <code>RafflePrompts</code> to its respective
     * console message.
     *
     * @return an <code>EnumMap</code> that maps each enum key of type <code>RafflePrompts</code> to its respective
     * console message
     */
    @Override
    protected EnumMap<RafflePrompts, String> initializeMessages() {
        EnumMap<RafflePrompts, String> m = new EnumMap<>(RafflePrompts.class);
        m.put(RafflePrompts.NO_ENTRIES_ERROR, "Sorry, there are currently no names entered in the raffle.");
        m.put(RafflePrompts.RAFFLE_SUCCESS, "A winner has been pulled and the names in the raffle have been cleared." +
                "\nYou should now notify the winner.");
        return m;
    }

    /**
     * Displays the given winner of the raffle to the screen.
     *
     * @param user the username of the user who won the raffle.
     */
    public void displayRaffleWinner(String user) {
        System.out.println("The winner of the raffle is " + user + "!");
    }

}
