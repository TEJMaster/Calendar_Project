package Messages;
import Data.InformationSaver;

/**
 * An interface that extends the Observer, it updates the information stored in AdminMessageManager to the file
 * stores respond messages.
 * It also updates the state of message from unresolved to resolved in file stores messages sent to admin users.
 * @author Group0031
 */
public class RespondMessageObserver implements Observer {

    /**
     * Updates Message sent in file stores information of admin messages.
      * @param manager Given AdminMessageManager
     */
    private void updateAdminMessage(AdminMessageManager manager){
        InformationSaver.clearCSV("phase2/src/data/AdminMessage.csv");
        for(AdminMessage admin: manager.getAllAdminMessages()){
            InformationSaver.write(admin.toString(), "phase2/src/data/AdminMessage.csv");
        }
    }


    // change all messages responded to "resolved", change their state in file
    // and write respond to file
    /**
     * Change all messages responded to "resolved", change their state in file, and write respond messages to file stores
     * responses.
     * @param message given message for updates
     * @param manager given AdminMessageManager for where the updates will be made on.
     */
    @Override
    public void update(Messages message, AdminMessageManager manager) {
        if (message instanceof RespondMessage) {
            for(AdminMessage admin: ((RespondMessage) message).getRespondTo()) {
                admin.changeState("resolved");
            }
           updateAdminMessage(manager);
           InformationSaver.write(message.toString(), "phase2/src/data/Response.csv");
        }
    }
}
