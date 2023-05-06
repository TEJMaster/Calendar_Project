package User;

import java.util.List;

/**
 * A entity class that represents a users that includes information such as the user's username, whether or not they
 * are an admin, their login date, whether or not their account is frozen, the number of days their account is suspended
 * for, the date the user suspension started, and a list that represents the user's friends. Note: Trial users do not
 * have passwords.
 * @author Group0031
 */
public class TrialUser {
    private String userName;
    boolean isAdmin = false;
    private String LoginDate;
    private boolean IsFrozen;
    private int SuspendedDays;
    private String SuspendedDate;
    private final List<String> FriendList;

    /**
     * Creates a trial user with a given username, login date, whether the account is frozen, days the account is
     * suspended for, date the suspension started, and list of user friends.
     * @param userName String representing the user's username
     * @param loginDate String representing the date the user is logging in from
     * @param IsFrozen boolean value representing if the user account is frozen
     * @param SuspendedDays integer representing the number of days the user is suspended for
     * @param SuspendedDate String representing the date the user's suspension started
     * @param FriendList list of strings representing the user's friends
     */
    public TrialUser(String userName, String loginDate, boolean IsFrozen, int SuspendedDays, String SuspendedDate,
                     List<String> FriendList){
        this.userName = userName;
        this.LoginDate = loginDate;
        this.IsFrozen = IsFrozen;
        this.SuspendedDays = SuspendedDays;
        this.SuspendedDate = SuspendedDate;
        this.FriendList = FriendList;
    }

    /**
     * Gets if the user is an admin
     * @return boolean value for if the user is an admin.
     */
    public boolean getIsAdmin(){return this.isAdmin;}

    /**
     * Gets the user's username
     * @return String representing the user's username
     */
    public String getUserName(){
        return this.userName;
    }

    /**
     * Sets the user's username based user input
     * @param ID String representing user's username input
     */
    public void setUserName(String ID){
        this.userName = ID;
    }

    /**
     * Get the date the user logged in
     * @return String representing the date in which the user logged in
     */
    public String GetLoginDate(){
        return this.LoginDate;
    }

    /**
     * Sets the login date in which the user logged in
     * @param LoginDate String representing the date the user has logged in
     */
    public void SetLoginDate(String LoginDate){
        this.LoginDate = LoginDate;
    }

    /**
     * Sets whether the user's account is frozen
     * @param IsFrozen boolean value that representing whether the user's account is frozen
     */
    public void SetFrozen( boolean IsFrozen){
        this.IsFrozen = IsFrozen;
    }

    /**
     * Sets the password of the user's account
     * @param password String representing the password for the user's account
     */
    public void setPassword(String password){
    }

    /**
     * Gets if the user's account is frozen
     * @return boolean value representing whether the user's account is currently frozen
     */
    public boolean getFrozen(){
        return this.IsFrozen;
    }

    /**
     * Gets the date that the users account's suspension started from
     * @return String representing the date during which the user's suspension started
     */
    public String GetSuspendedDate(){
        return this.SuspendedDate;
    }

    /**
     * Sets the date in which the user's account suspension started
     * @param SuspendedDate String that represents the date the user's account suspension started
     */
    public void SetSuspendedDate(String SuspendedDate){
        this.SuspendedDate = SuspendedDate;
    }

    /**
     * Gets the number of days a user's account is suspended for
     * @return integer that represents the number of days on a user's account suspension
     */
    public int GetSuspendedDays(){
        return this.SuspendedDays;
    }

    /**
     * Sets the number of days a user's account is suspended for
     * @param suspendedDays integer that represents the number of days a user's account is suspended for
     */
    public void SetSuspendedDays(int suspendedDays){
        this.SuspendedDays = suspendedDays;
    }

    /**
     * Converts whether a user's account is frozen to a string
     * @param IsFrozen boolean value indicating whether a user's account is frozen
     * @return String where 1 represents a user's account is frozen and 0 if a user's account is not
     */
    public String FreezeToString(boolean IsFrozen){
        if (IsFrozen){
            return "1";
        }
        else {
            return "0";
        }
    }

    /**
     * Checks if the user's password is correct, since trial users don't have password this always returns True
     * @param password String representing the users' password
     * @return boolean value returning if the inputted password is correct
     */
    public boolean passWordCheck(String password){
        return true;
    }

    /**
     * Gets the password of the user, since trial users don't have passwords this always returns an empty string
     * @return String representing a user's password
     */
    public String getPassword(){return "";}

    /**
     * Converts a user's friend list information into a string
     * @param FriendList a list of string representing a user's friends
     * @return a string representing the user's various friends
     */
    public String ListToString(List<String> FriendList){
        StringBuilder Friends = new StringBuilder();
        for (String Friend: FriendList){
            String newFriend = Friend + "/";
            Friends.append(newFriend);
        }
        return Friends.toString();
    }

    /**
     * Adds a friend to a user
     * @param Username A string representing the username of the user that will be added to the friend list of the user
     */
    public void addFriend(String Username){
        this.FriendList.add(Username);
    }

    /**
     * Gets the list of strings that represent the user's friends
     * @return list of strings that represents the user's friends
     */
    public List<String> getFriendList(){
        return this.FriendList;
    }

    /**
     * Converts the user's information into a string. This includes: the user's username, 1 for the user's password,
     * the log in date, whether the user's account is frozen, number of days the account is suspended for,
     * the date the user account suspension started and the list of user friends.
     * @return String representing user's account information, and 1 for user's password
     */
    public String toString(){
        return this.userName + "," + "1" + "," + this.GetLoginDate() + "," + FreezeToString(this.getFrozen()) + "," +
                this.GetSuspendedDays() + "," + this.GetSuspendedDate() + "," + ListToString(this.getFriendList());
    }
}
