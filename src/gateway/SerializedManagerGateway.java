package gateway;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An abstract gateway class that saves and loads instances of manager classes via serialization.
 *
 * @param <T> the manager class that this gateway saves and loads
 */
@SuppressWarnings("unchecked")
abstract class SerializedManagerGateway<T extends Serializable> {

    /**
     * The name of the directory in which all data files will be stored.
     */
    protected final String fileDirectory = "data";

    /**
     * Creates an instance of <code>SerializedManagerGateway</code>.
     */
    protected SerializedManagerGateway() {
        new File(fileDirectory).mkdirs();
        File temp = new File(getFilePath());
        if (!temp.exists()) {
            try {
                saveToFile(getFilePath(), createEmptyManager());
            } catch (IOException e) {
                e.printStackTrace();
                Logger.getGlobal().log(Level.SEVERE, getSavingErrorMessage(), e);
            }
        }
    }

    /**
     * Returns the path to the file in which the manager is stored.
     *
     * @return the path to the file in which the manager is stored
     */
    abstract protected String getFilePath();

    /**
     * Returns an instance of a manager of type <code>T</code> that contains none of its respective entity.
     *
     * @return an instance of a manager of type <code>T</code> that contains none of its respective entity
     */
    abstract protected T createEmptyManager();

    /**
     * Returns the error message to be logged when an error occurs in saving to file.
     *
     * @return the error message to be logged when an error occurs in saving to file
     */
    abstract protected String getSavingErrorMessage();

    /**
     * Returns the error message to be logged when an error occurs in reading from file.
     *
     * @return the error message to be logged when an error occurs in reading from file
     */
    abstract protected String getReadingErrorMessage();

    private void saveToFile(String fileName, T obj) throws IOException {
        OutputStream file = new FileOutputStream(fileName);
        ObjectOutput out = new ObjectOutputStream(file);
        out.writeObject(obj);
        out.close();
    }

    private T readFromFile(String fileName) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(fileName);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);

        T obj = (T) input.readObject();
        input.close();

        return obj;
    }

    /**
     * Saves an instance of a manager class to a .ser file.
     *
     * @param manager the manager instance to be saved
     */
    public void saveManager(T manager) {
        try {
            saveToFile(getFilePath(), manager);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getGlobal().log(Level.SEVERE, getSavingErrorMessage(), e);
        }
    }

    /**
     * Returns the manager instance that is saved in a .ser file.
     *
     * @return the saved manager instance
     */
    public T getSavedManager() {
        try {
            return readFromFile(getFilePath());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Logger.getGlobal().log(Level.SEVERE, getReadingErrorMessage(), e);
            return createEmptyManager();
        }
    }

}
