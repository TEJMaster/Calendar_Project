package User;

import java.util.List;

/**
 * An entity class that represents a temporary user, which is a child class of RegularUser, thus the class includes
 * all the general information a regular has and on top of that, records the temporary user's registered date.
 * @author Group0031
 */
public class TemporaryUser extends RegularUser {
    private final String registeredDate;

    /**
     * Creates a temporary user with inputs username, password whether user is an admin, date user is trying to register
     * date user is trying to log in, whether user is frozen, number of days user is suspended for, date user's suspension
     * started and list of user friends.
     * @param username String representing name of user
     * @param password String representing user's password
     * @param isAdmin boolean value representing if user is an admin
     * @param registeredDate String for the date the user registered their account
     * @param LoginDate String representing the date that the user is logging in
     * @param IsFrozen boolean value representing if the user's account is frozen
     * @param SuspendedDays Integer representing how many days the user's account is suspended for
     * @param SuspendedDate String representing the date the user's accounts suspension started
     * @param FriendList List if strings that represents the user's friends
     */
    public TemporaryUser(String username, String password, boolean isAdmin, String registeredDate, String LoginDate,
                         boolean IsFrozen,  int SuspendedDays, String SuspendedDate, List<String> FriendList){
        super(username, password, LoginDate, isAdmin, IsFrozen, SuspendedDays, SuspendedDate, FriendList);
        this.registeredDate = registeredDate;
    }

    /**
     * Converts users information into a string message that includes the user's username, whether they are an admin,
     * password, date they registered for their account, date they are logging in, whether their account is frozen,
     * days their account is suspended for, date their account suspension started, list of their friends.
     * @return String representing user accounting information.
     */
    @Override
    public String toString() {
        return this.getUserName() + ","  + BooleanToString(this.getIsAdmin())+ "," + this.getPassword() + "," +
                this.GetRegisteredDate() + "," + GetLoginDate() + "," + FreezeToString(this.getFrozen())+ "," +
                this.GetSuspendedDays()+ "," + this.GetSuspendedDate() + "," + ListToString(this.getFriendList());
    }

    /**
     * Gets a string that represents the date the user registered for their account.
     * @return String of user registration date
     */
    public String GetRegisteredDate(){
        return  this.registeredDate;
    }
}