package gateway;

import requests.RequestManager;

/**
 * Gateway class for saving and loading instances of RequestManager.
 */
public class RequestManagerGateway extends SerializedManagerGateway<RequestManager> {

    private final String fileName = "requestManager.ser";

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
     * Returns an instance of <code>RequestManager</code> that contains no requests.
     *
     * @return an instance of <code>RequestManager</code> that contains no requests
     */
    @Override
    protected RequestManager createEmptyManager() {
        return new RequestManager();
    }

    /**
     * Returns the error message to be logged when an error occurs in saving to file.
     *
     * @return the error message to be logged when an error occurs in saving to file
     */
    @Override
    protected String getReadingErrorMessage() {
        return "Error in reading request manager";
    }

    /**
     * Returns the error message to be logged when an error occurs in reading from file.
     *
     * @return the error message to be logged when an error occurs in reading from file
     */
    @Override
    protected String getSavingErrorMessage() {
        return "Error in saving request manager";
    }

}
