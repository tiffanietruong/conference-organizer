package messaging;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * The <code>AbstractMessage</code> class defines a public interface for all messaging interactions.
 * This includes messages sent between users of all types as well as requests sent by users to Organizers.
 */
public abstract class AbstractMessage implements Serializable {

    /**
     * An Abstract class that provides methods for messages that can be sent between users.
     */
    protected final UUID Id;
    protected final String author;
    protected final LocalDateTime timeSent;
    protected String text;

    /**
     * Creates a new message object using the given information.
     *
     * @param text   the text to be included in the message
     * @param author the author of the message
     */
    public AbstractMessage(String text, String author) {
        this.Id = UUID.randomUUID();
        this.text = text;
        this.author = author;
        this.timeSent = LocalDateTime.now();
    }

    /**
     * Gets the unique ID for this message, which is used to refer to this message.
     *
     * @return the ID for this message
     */
    public UUID getId() {
        return Id;
    }

    /**
     * Gets the author for this message.
     *
     * @return the author for this message
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns a string representation of this message. The string representation consists of the time
     * the message was sent, author of this message, and the text of this message.
     *
     * @return a string representation of this message
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM HH:mm");
        String formattedTime = timeSent.format(formatter);
        return String.format("%s\n%s said:\n> %s\n", formattedTime, author, text);
    }

}
