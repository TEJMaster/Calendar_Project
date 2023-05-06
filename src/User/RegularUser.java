package User;

import java.util.List;

/** An entity class that represents a Regular User/Admin User, which is a child class of Trial user, thus includes
 * all the general information a trail user should have and on top of that, stores password and whether the user is
 * identified as an admin user.
 * @author Group0031
 */
public class RegularUser extends TrialUser {
    private String password;
    private final boolean isAdmin;
    /**
     * Creates a new user with given password, whether is an Admin.
     * @param password is a string that stores the password
     * @param IsAdmin is a boolean expression that indicates if user is an admin
     * @param Username is a string that stores the username of the user
     * @param LoginDate is a string that store the date which the user logs in
     * @param IsFrozen is a boolean value that indicates if the user's account is frozen
     * @param SuspendedDays is an integer that represents how many days a user's account is suspended for
     * @param SuspendedDate is a string that represents that date that the user's suspension started
     * @param FriendList is a list of strings that contains the friends of this user
     *
     */
    public RegularUser(String Username, String password, String LoginDate, boolean IsAdmin, boolean IsFrozen
            , int SuspendedDays, String SuspendedDate, List<String> FriendList){
        super(Username, LoginDate, IsFrozen, SuspendedDays, SuspendedDate, FriendList);
        this.password = password;
        this.isAdmin = IsAdmin;
    }

    /**
     * Method that indicates if user is an admin
     * @param isAdmin a boolean that has value true if the user is admin
     * @return An int indicating whether the user is an admin 1 if not and 0 if is
     */
    public String BooleanToString(boolean isAdmin){
        if(isAdmin){
            return "0";}
        else{
            return "1";}
    }

    /**
     * Gets the password of the user
     * @return a String that represents the user's password
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * Sets the password for the user
     * @param newPass A String containing the new password
     */
    public void setPassword(String newPass){
        this.password = newPass;
    }

    /**
     * Checks if the user's password is correct
     * @param inputWord A string representing the password input of the user
     * @return boolean value of if the input password is correct
     */
    public boolean passWordCheck(String inputWord){
        return this.password.equals(inputWord);
    }

    /**
     * Gets whether the user is an admin
     * @return boolean value of if user is an admin True if user is and False if user is not
     */
    public boolean getIsAdmin(){return isAdmin;}

    /**
     * Converts user's information into a string containing username, whether is admin, password, login date, whether or not
     * user account is frozen, number of days user is suspended, date where user's suspension started and users friends.
     * @return A string representing user's information
     */
    @Override
    public String toString() {
        return this.getUserName() + ","  + BooleanToString(isAdmin)+ "," + password + "," + GetLoginDate()
                + "," + FreezeToString(this.getFrozen())+ "," + this.GetSuspendedDays() + ","
                + this.GetSuspendedDate() + "," + ListToString(this.getFriendList());
    }
}