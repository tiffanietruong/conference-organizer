package gateway;

import user.manager.UserManagerFacade;

/**
 * Gateway class for saving and loading instances of UserManager.
 */
class UserManagerGateway extends SerializedManagerGateway<UserManagerFacade> {

    private final String fileName = "userManager.ser";

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
     * Returns an instance of <code>UserManager</code> that contains no users.
     *
     * @return an instance of <code>UserManager</code> that contains no users
     */
    @Override
    protected UserManagerFacade createEmptyManager() {
        return new UserManagerFacade();
    }

    /**
     * Returns the error message to be logged when an error occurs in saving to file.
     *
     * @return the error message to be logged when an error occurs in saving to file
     */
    @Override
    protected String getReadingErrorMessage() {
        return "Error in reading user manager";
    }

    /**
     * Returns the error message to be logged when an error occurs in reading from file.
     *
     * @return the error message to be logged when an error occurs in reading from file
     */
    @Override
    protected String getSavingErrorMessage() {
        return "Error in saving user manager";
    }
}
