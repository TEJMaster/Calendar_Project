package Messages;

import java.util.List;

/**
 * An entity class which is a child class of Messages, representing the messages sent from admin to users (messages to
 * respond messages sent to them).
 */
public class RespondMessage extends Messages {
    private final List<AdminMessage> respondTo;

    /**
     * Creates a message that responds to the message that sent to the admin users.
     * @param message Given List of messages sent to admin users
     * @param respondMessage A String representing the content of the message responded to messages sent to admin users.
     */
    public RespondMessage(List<AdminMessage> message, String respondMessage){
        super(respondMessage);
        this.respondTo = message;
    }

    /**
     * Gets the messages the RespondMessage is responding to.
     * @return A list of AdminMessage (messages sent to admin users from regular users) the RespondMessage is responding
     * to.
     */
    public List<AdminMessage> getRespondTo(){return respondTo;}
}
