package Messages;

/**
 * An interface provides the basic access to notify the observers and update based on previous action.
 * @author Group0031
 */
public interface Observer {
    /**
     * Updates message to AdminMessageManager
     * @param message given message for updates
     * @param manager given AdminMessageManager for where the updates will be made on.
     */
    void update(Messages message, AdminMessageManager manager);
    }

