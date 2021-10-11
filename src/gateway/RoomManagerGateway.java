package gateway;

import room.RoomManager;

/**
 * Gateway class for saving and loading instances of RoomManager.
 */
class RoomManagerGateway extends SerializedManagerGateway<RoomManager> {

    private final String fileName = "roomManager.ser";

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
     * Returns an instance of <code>RoomManager</code> that contains no rooms.
     *
     * @return an instance of <code>RoomManager</code> that contains no rooms
     */
    @Override
    protected RoomManager createEmptyManager() {
        return new RoomManager();
    }

    /**
     * Returns the error message to be logged when an error occurs in saving to file.
     *
     * @return the error message to be logged when an error occurs in saving to file
     */
    @Override
    protected String getReadingErrorMessage() {
        return "Error in reading room manager";
    }

    /**
     * Returns the error message to be logged when an error occurs in reading from file.
     *
     * @return the error message to be logged when an error occurs in reading from file
     */
    @Override
    protected String getSavingErrorMessage() {
        return "Error in saving room manager";
    }
}
