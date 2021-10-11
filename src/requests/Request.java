package requests;


import messaging.AbstractMessage;

import java.time.format.DateTimeFormatter;


/**
 * This class represents a request that a user can send about the conference
 */
public class Request extends AbstractMessage {
    private String replyAuthor;
    private String reply;
    private boolean status;

    public Request(String text, String author) {
        super(text, author);
        this.reply = "";
        this.status = false;
    }

    /**
     * Gets the status for the request
     *
     * @return the status of the request
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Sets the status of this request
     *
     * @param status the state to which the status should be set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Sets the author of the reply
     *
     * @param author name of the author
     */
    public void setReplyAuthor(String author) {
        replyAuthor = author;
    }

    /**
     * Gets the reply
     *
     * @return the reply
     */
    public String getReply() {
        return reply;
    }

    /**
     * Sets the reply for this request
     *
     * @param text the reply
     */
    public void setReply(String text) {
        reply = text;
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
        return String.format("%s\n%s said:\n> %s\n \t>%s\n", formattedTime, author, text, reply);
    }
}
