package Messages;

import Data.InformationReader;

import java.util.*;

/**
 * Messaging System that governs how Regular Users are going to interact with AdminUsers, how messages are going to be
 * initiated and stored, and how their state will change
 */
public class MessagingSystem{

    /**
     * Usage of Observer Pattern and Initializing Messaging System
     */
    private final Observer AdminObserver;
    private final Observer RespondObserver;
    private final AdminMessageManager AdminManager;

    public MessagingSystem(InformationReader reader){
        this.AdminObserver = new AdminMessageObserver();
        this.RespondObserver = new RespondMessageObserver();
        this.AdminManager = new AdminMessageManager(reader.ReadMessage());
    }


    /**
     *  Write messages created by regular user to file
     * @param x get input from user
     * @param Username create new message from username and input
     */
    public void WriteMessage(Scanner x, String Username){
        System.out.println("Please write your Message to the admin below, Please do not include \",\" in your message");
        String input = x.nextLine();
        Messages newMessage = new AdminMessage(Username, input);
        newMessage.AddObserver(this.AdminObserver);
        newMessage.notifyAllObservers(this.AdminManager);
        System.out.println("message sent!");
    }

    /**
     * // Admin user respond only when the List of Messages are not empty
     * @param x get input from admin user
     */
    public void ReadAndRespond(Scanner x){
        HashMap<String, List<String>> UsernameToMessage = ReadMessage();
        if(!UsernameToMessage.isEmpty()){
            respondToMessage(x, UsernameToMessage);
        }
    }

    /**
     * Admin Users Read Message
     * @return username: a list of strings created by this user
     */
    public HashMap<String, List<String>> ReadMessage(){
        HashMap<String, List<String>> UsernameToMessage = new HashMap<>();

        for(AdminMessage admin: this.AdminManager.getAllAdminMessages()){
            if (UsernameToMessage.containsKey(admin.getUser())){
                UsernameToMessage.get(admin.getUser()).add(admin.getMessage()+":" + admin.getState());
            }
            else {
                List<String> newList = new ArrayList<>();
                newList.add(admin.getMessage() + ":" + admin.getState());
                UsernameToMessage.put(admin.getUser(), newList);
            }
        }
        System.out.println("Users with messages: " + UsernameToMessage.keySet());
        return UsernameToMessage;
    }

    /**
     * Admin User Respond to Message and Messages being responded changes state
     * @param x get admin user input
     * @param UsernameToMessage username: a list of strings created by this user
     */
    public void respondToMessage(Scanner x, HashMap<String, List<String>> UsernameToMessage) {
        while (true){
            System.out.println("Which person's message do you want to respond to? Please input username");
            String name = x.nextLine();
            if (UsernameToMessage.containsKey(name)){
                printLineByLine(UsernameToMessage.get(name));
                System.out.println("please input your response, Please do not include \",\" in your message");
                String response = x.nextLine();
                StringBuilder respondTo = new StringBuilder("[");
                for(AdminMessage message: this.AdminManager.findAdmin(name)){
                    respondTo.append("/").append(message.getMessage());
                }
                respondTo.append("]");
                String nameAndResponse = name + ",Response to" + respondTo + ","+ response;
                Messages newMessage = new RespondMessage(this.AdminManager.findAdmin(name), nameAndResponse);
                newMessage.AddObserver(this.RespondObserver);
                newMessage.notifyAllObservers(this.AdminManager);
                System.out.println("message sent!");
                return;
            }

            else{
                System.out.println("invalid User Please try again!");
            }
        }
    }

    /**
     * Print messages line by line
     * @param messages list of messages waiting to be printed
     */

    public void printLineByLine(List<String> messages){
        for(String message: messages){
            System.out.println("Message " + (messages.lastIndexOf(message) + 1) + ": " + message);
        }
    }


}
