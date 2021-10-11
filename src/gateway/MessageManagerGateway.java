package gateway;

import messaging.manager.MessageManager;

/**
 * Gateway class for saving and loading instances of MessageManager.
 */
class MessageManagerGateway extends SerializedManagerGateway<MessageManager> {

    private final String fileName = "messageManager.ser";

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
     * Returns an instance of <code>MessageManager</code> that contains no messages.
     *
     * @return an instance of <code>MessageManager</code> that contains no messages
     */
    @Override
    protected MessageManager createEmptyManager() {
        return new MessageManager();
    }

    /**
     * Returns the error message to be logged when an error occurs in saving to file.
     *
     * @return the error message to be logged when an error occurs in saving to file
     */
    @Override
    protected String getReadingErrorMessage() {
        return "Error in reading message manager";
    }

    /**
     * Returns the error message to be logged when an error occurs in reading from file.
     *
     * @return the error message to be logged when an error occurs in reading from file
     */
    @Override
    protected String getSavingErrorMessage() {
        return "Error in saving message manager";
    }

}
