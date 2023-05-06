package Messages;

import Data.InformationSaver;

/**
 * An interface that extends the Observer, it updates the message regular users sent to file stores messages to admin
 * users.
 * @author Group0031
 */
public class AdminMessageObserver implements Observer {
    /**
     * Updates the number of Admin messages and their states to the file stores admin messages information.
     */
    @Override
    public void update(Messages message, AdminMessageManager manager) {
        if (message instanceof AdminMessage) {
            manager.addMessage((AdminMessage) message);
            InformationSaver.clearCSV("phase2/src/data/AdminMessage.csv");
            for(AdminMessage admin: manager.getAllAdminMessages()) {
                InformationSaver.write(admin.toString(), "phase2/src/data/AdminMessage.csv");
            }
        }
    }
}