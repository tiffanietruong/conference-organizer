package raffle;

import user.UserType;
import user.manager.UserManagerFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/***
 * The <code>RaffleController class</code> process requests about the ongoing raffles in the conference.
 * In particular, it facilitates entering Attendees into the raffle when they write reviews for events
 * and allows Admins to pull a winner for the current raffle.
 */
public class RaffleController {

    private final List<String> participants;
    private final RafflePresenter presenter;
    private final UserManagerFacade userManager;

    /**
     * Constructs a controller to manage the raffles in the conference.
     *
     * @param userManager the manager responsible for user data during program execution
     */
    public RaffleController(UserManagerFacade userManager) {
        this.participants = new ArrayList<>();
        this.presenter = new RafflePresenter();
        this.userManager = userManager;
    }

    /**
     * Pulls a winner for the current raffle.
     * This method selects a random user from the list of participants, displays information about the raffle success,
     * and resets the raffle by clearing the existing list of participants.
     *
     * @return the username of the user who won the raffle if a winner could be pulled; the empty string otherwise.
     */
    public String pullWinner() {
        if (participants.isEmpty()) {
            presenter.display(RafflePrompts.NO_ENTRIES_ERROR);
            return "";
        } else {
            int index = new Random().nextInt(participants.size());
            String winner = participants.get(index);
            presenter.displayRaffleWinner(winner);
            userManager.changeUserType(winner, UserType.VIP);
            participants.clear();
            presenter.display(RafflePrompts.RAFFLE_SUCCESS);
            return winner;
        }

    }

    /**
     * Adds an entry to the raffle associated with the given user.
     *
     * @param username the username of the user who another entry into the raffle
     */
    public void addEntry(String username) {
        participants.add(username);
    }
}
