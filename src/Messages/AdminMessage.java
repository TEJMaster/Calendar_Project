package Messages;


/**
 * An entity class that represents messages from regular users to admin users
 * @author Group0031
 */
public class AdminMessage extends Messages {
    private String state;
    private final String user;

    /**
     * Creates a new message
     * @param User String that represents the user
     * @param message String that represents the message
     */
    public AdminMessage(String User, String message){
        super(message);
        this.user = User;
        this.state = "unresolved";
    }

    /**
     * Creates a new message
     * @param message String that represents message being delivered
     * @param User String that represents user
     * @param state String representing whether the message has been responded to
     */
    public AdminMessage(String message, String User, String state){
        super(message);
        this.user = User;
        this.state = state;
    }

    /**
     * Change the state of a message
     * @param newState String that represents the new state of the message
     */
    public void changeState(String newState){this.state = newState;}

    /**
     * Get the user
     * @return String representing a user
     */
    public String getUser(){return this.user;}

    /**
     * Get the message being sent
     * @return String representing a message
     */
    public String getMessage(){return super.toString();}

    /**
     * Get whether the message
     * @return String that represents the state of the message
     */
    public String getState(){return this.state;}

    /**
     * Display the message user, and message state information in a string
     * @return String that represents the message and user and state
     */
    public String toString(){return super.toString() + "," + this.user + "," + this.state; }
}
