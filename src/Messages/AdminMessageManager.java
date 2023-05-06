package Messages;

import java.util.ArrayList;
import java.util.List;

/**
 * A Use case class of Admin Messages (messages sent from regular users to admin users).
 * @author Group0031
 */
public class AdminMessageManager {
    private final List<AdminMessage> allAdminMessages;
    /**
     * Creates an AdminMessageManager which stores a list of all the messages sent to admin users.
     * @param list A list of AdminMessage representing all the messages sent to admin users.
     */
    public AdminMessageManager(List<AdminMessage> list){
        this.allAdminMessages = list;
    }

    /**
     * Adds a given Admin Message to the AdminMessageManager's stored list of messages.
     * @param admin An AdminMessage representing the message needs to be added.
     */
    public void addMessage(AdminMessage admin){
        allAdminMessages.add(admin);
    }

    /**
     * Gets all the admin messages stored in the AdminMessageManager
     * @return all the admin messages stored in the AdminMessageManager
     */
    public List<AdminMessage> getAllAdminMessages() {return allAdminMessages;}

    /**
     * Get all the admin messages that a regular user sent given his username from all the information stored in
     * this AdminMessageManager.
     * @param name A String representing the username of the given user that we are looking for all the messages he sent.
     * @return A List of AdminMessage representing all the admin messages that a regular user sent given his username
     * from all the information stored in  this AdminMessageManager.
     */
    public List<AdminMessage> findAdmin(String name){
        List<AdminMessage> list = new ArrayList<>();
        for (AdminMessage m: this.getAllAdminMessages()){
            if(m.getUser().equals(name)){
                list.add(m);
            }
        }
        return list;
    }
}