package event.controller;

import event.manager.EventManagerFacade;
import event.manager.EventParameterObject;
import event.presenter.EventPresenter;
import event.presenter.EventPrompts;
import room.RoomManager;
import system.console.ConsoleInputController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.BooleanSupplier;

/**
 * The <code>EventValidationController</code> is responsible for validating the input given by the user to create
 * a new event.
 */
public class EventValidationController extends ConsoleInputController<EventPrompts> {
    private final List<BooleanSupplier> validators;
    private final EventManagerFacade eventManager;
    private final RoomManager roomManager;
    private final EventPresenter eventPresenter;
    /**
     * Stores the current parameters for the event being constructed.
     */
    private EventParameterObject parameters;

    /**
     * Constructs a new <code>EventValidationController</code> object.
     *
     * @param in             the instance of Scanner currently taking user input from the Console
     * @param eventManager   the manager responsible for event data during program execution
     * @param roomManager    the manager responsible for room information during program execution
     * @param eventPresenter a presenter to be used for displaying prompts to the user
     */
    protected EventValidationController(Scanner in, EventManagerFacade eventManager, RoomManager roomManager,
                                        EventPresenter eventPresenter) {
        super(in, eventPresenter);
        this.eventManager = eventManager;
        this.roomManager = roomManager;
        this.eventPresenter = eventPresenter;
        this.validators = initializeValidators();
    }

    /**
     * Returns a list of methods that can be called to validate the user's input.
     *
     * @return a list of validator methods
     */
    private List<BooleanSupplier> initializeValidators() {
        return new ArrayList<>(
                Arrays.asList(this::validateTitle,
                        this::validateVip,
                        this::validateStartTime,
                        this::validateSpeakerCapacity,
                        this::validateEventCapacity,
                        this::validateEventDuration,
                        this::validateRoomForEvent)
        );
    }

    /**
     * Reads the user's input for creating a new event and returns a <code>EventParameterObject</code> containing
     * this information if it is valid.
     *
     * @return a <code>EventParameterObject</code> containing the information provided by the user, or an empty
     * <code>Optional</code> if the user's input was not valid
     */
    protected Optional<EventParameterObject> validate() {
        parameters = new EventParameterObject();
        for (BooleanSupplier validator : validators) {
            if (!validator.getAsBoolean()) {
                return Optional.empty();
            }
        }
        return Optional.of(parameters);
    }

    private LocalDateTime readDateAndTime() {
        LocalDate date = promptDate(EventPrompts.EVENT_DATE_PROMPT, EventPrompts.INVALID_INPUT_DATE);
        LocalTime time = promptTime(EventPrompts.EVENT_START_TIME_PROMPT, EventPrompts.INVALID_INPUT_TIME);
        return LocalDateTime.of(date, time);
    }

    //<editor-fold desc="Validators">

    /**
     * Returns true if the given event title is a valid title for a new event.
     * A title is invalid iff there already exists an event with the given title.
     * If the title was valid, sets the title in <code>parameters</code>.
     *
     * @return <code>true</code> iff the given event title is an valid title for a new event
     */
    private boolean validateTitle() {
        String title = promptString(EventPrompts.EVENT_TITLE_PROMPT);
        if (eventManager.eventExists(title)) {
            eventPresenter.display(EventPrompts.EVENT_EXISTS_ERROR);
            return false;
        }
        parameters.setTitle(title);
        return true;
    }

    /**
     * Reads and sets whether the event is for VIPs only or not.
     *
     * @return <code>true</code>, indicating that the VIP status of the event was valid
     */
    @SuppressWarnings("SameReturnValue")
    private boolean validateVip() {
        boolean vip = promptYesNo(EventPrompts.EVENT_VIP_PROMPT, EventPrompts.EVENT_VIP_ERROR);
        parameters.setVip(vip);
        return true;
    }

    /**
     * Returns true if the given start time for the event is a valid start time.
     * A start time is invalid iff it is before 9 AM or after 5 PM
     *
     * @return <code>true</code> iff the given start time for the event is a valid start time
     */
    private boolean validateStartTime() {
        LocalDateTime startTime = readDateAndTime();
        if (startTime.getHour() < 9 || startTime.getHour() > 17) { // if time before 9 or after 5, it's invalid
            eventPresenter.display(EventPrompts.TIME_ERROR);
            return false;
        }
        parameters.setStartTime(startTime);
        return true;
    }

    /**
     * Returns true if the given speaker capacity for the event is a valid speaker capacity.
     *
     * @return <code>true</code> iff the given speaker capacity for the event is a valid speaker capacity
     */
    private boolean validateSpeakerCapacity() {
        int speakerCapacity = promptInt(EventPrompts.SPEAKER_CAPACITY_PROMPT, EventPrompts.INPUT_NOT_INT_ERROR);
        if (speakerCapacity < 0) {
            eventPresenter.display(EventPrompts.INVALID_SPEAKER_CAPACITY_ERROR);
            return false;
        }
        parameters.setSpeakerCapacity(speakerCapacity);
        return true;
    }

    /**
     * Returns true if the given event capacity corresponds to an a positive integer
     *
     * @return <code>true</code> iff the event capacity corresponds to a positive integer
     */
    private boolean validateEventCapacity() {
        int eventCapacity = promptInt(EventPrompts.EVENT_CAPACITY_PROMPT, EventPrompts.INPUT_NOT_INT_ERROR);
        if (eventCapacity <= 0) {
            eventPresenter.display(EventPrompts.INVALID_ATTENDEE_CAPACITY_ERROR);
            return false;
        }
        parameters.setEventCapacity(eventCapacity);
        return true;
    }

    /**
     * Returns true if the given event duration corresponds to a positive integer
     *
     * @return <code>true</code> iff if the event duration corresponds to a positive integer
     */
    private boolean validateEventDuration() {
        int duration = promptInt(EventPrompts.EVENT_DURATION_PROMPT, EventPrompts.INPUT_NOT_INT_ERROR);
        if (duration <= 0) {
            eventPresenter.display(EventPrompts.INVALID_DURATION_ERROR);
            return false;
        }
        parameters.setDuration(duration);
        return true;
    }

    /**
     * Returns true if given room ID corresponds to a room that a new event cannot be scheduled in.
     * An event cannot be scheduled in a room if the room has an event scheduled in it at the given time or
     * the given number of attendees exceeds the room's maximum capacity.
     *
     * @return <code>true</code> iff if the room corresponds to a valid room
     */
    private boolean validateRoomForEvent() {
        int roomID = promptInt(EventPrompts.EVENT_ROOM_ID_PROMPT, EventPrompts.INPUT_NOT_INT_ERROR);
        if (!roomManager.roomExists(roomID)) {
            eventPresenter.display(EventPrompts.ROOM_NOT_FOUND_ERROR);
            return false;
        } else if (eventManager.roomIsBooked(roomID, parameters.getStartTime(), parameters.getDuration())) {
            eventPresenter.display(EventPrompts.ROOM_IS_BOOKED);
            return false;
        } else if (parameters.getEventCapacity() > roomManager.getCapacityOfRoom(roomID)) {
            eventPresenter.display(EventPrompts.EVENT_CAPACITY_TOO_LARGE_ERROR);
            return false;
        }
        parameters.setRoomID(roomID);
        return true;
    }
    //</editor-fold>
}