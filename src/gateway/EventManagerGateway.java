package gateway;

import event.manager.EventManagerFacade;

/**
 * Gateway class for saving and loading instances of EventManager.
 */
class EventManagerGateway extends SerializedManagerGateway<EventManagerFacade> {

    private final String fileName = "eventManager.ser";

    /**
     * Returns the path to the file in which the manager is stored.
     *
     * @return the path to the file in which the manager is stored
     */
    @Override
    protected String getFilePath() {
        return fileDirectory + "/" + fileName;
    }

    /**
     * Returns an instance of <code>EventManager</code> that contains no events.
     *
     * @return an instance of <code>EventManager</code> that contains no events
     */
    @Override
    protected EventManagerFacade createEmptyManager() {
        return new EventManagerFacade();
    }

    /**
     * Returns the error message to be logged when an error occurs in saving to file.
     *
     * @return the error message to be logged when an error occurs in saving to file
     */
    @Override
    protected String getReadingErrorMessage() {
        return "Error in reading event manager";
    }

    /**
     * Returns the error message to be logged when an error occurs in reading from file.
     *
     * @return the error message to be logged when an error occurs in reading from file
     */
    @Override
    protected String getSavingErrorMessage() {
        return "Error in saving event manager";
    }

}
