package User;

import Data.InformationReader;

import java.util.ArrayList;
import java.util.List;

/**
 * A use case class that manages all the users.
 * It gives username, password, admin, paginate, IsFrozen, SuspendedDays, SuspendedDate, FriendList for a new user as
 * parameter.
 * @author Group0031
 */
public class UserManager{
        private final List<TrialUser> userLists;
    /**
     * Creates a userList that stores users using the injected information reader
     * @param informationReader, a gateway class that read csv files
     */
        public UserManager(InformationReader informationReader) { this.userLists = informationReader.ReadUser();}

    /**
     * Gets the list of users
     * @return A list that stores and represents users
     */
        public List<TrialUser> getUsers() {
            return this.userLists;
        }

    /**
     * Gets the users that are not admin users
     * @return list of users that are not admin users
     */
        public List<TrialUser> getNonAdminUsers() {
            List<TrialUser> NonAdminUsers = new ArrayList<>();
            for (TrialUser user : userLists) {
                if (!(user.getIsAdmin())) {
                    NonAdminUsers.add(user); }
            }
            return NonAdminUsers;
        }

    /**
     * Gets the usernames of users
     * @return Arraylist of string that represent the usernames of users
     */
        public ArrayList<String> getNames(){
            ArrayList<String> usernames = new ArrayList<>();

            for(TrialUser u: this.userLists){
                usernames.add(u.getUserName());
            }
            return usernames;
        }

    /**
     * Creates a trial or regular user depending on password input
     * @param userName String that represents the username of the user
     * @param password String that represents the password of the user
     * @param isAdmin boolean value for whether the user is an admin
     * @param LoginDate String that represents date of logging in
     * @param IsFrozen boolean value representing whether the user's account is frozen
     * @param SuspendedDays integer representing the number of days a user's account is suspended for
     * @param SuspendedDate a string representing the date the user's account suspension tarted
     * @param FriendList a list representing the friends of the user
     */
        public void UserCreator(String userName, String password, boolean isAdmin, String LoginDate, boolean IsFrozen,
                                int SuspendedDays, String SuspendedDate, List<String> FriendList) {
            if(password==null){
                TrialUser newUser = new TrialUser(userName, LoginDate, IsFrozen, SuspendedDays, SuspendedDate, FriendList);
                this.userLists.add(newUser);
            }
            else{
                RegularUser newUser = new RegularUser(userName, password, LoginDate, isAdmin, IsFrozen,
                        SuspendedDays, SuspendedDate, FriendList);
                this.userLists.add(newUser);
            }
        }

    /**
     * Creates a temporary user
     * @param userName String that represents the user's username
     * @param password String that represents the password of the user
     * @param isAdmin boolean value for whether the user is an admin
     * @param registeredDate String that represents the date the user registered
     * @param LoginDate String that represents date of logging in
     * @param IsFrozen boolean value representing whether the user's account is frozen
     * @param SuspendedDays integer representing the number of days a user's account is suspended for
     * @param SuspendedDate a string representing the date the user's account suspension tarted
     * @param FriendList a list representing the friends of the user
     */
        public void UserCreator(String userName, String password, boolean isAdmin, String registeredDate,
                                String LoginDate, boolean IsFrozen,  int SuspendedDays, String SuspendedDate,
                                List<String> FriendList ) {
            TemporaryUser newUser = new TemporaryUser(userName, password, isAdmin, registeredDate, LoginDate,
                    IsFrozen, SuspendedDays, SuspendedDate, FriendList);
            this.userLists.add(newUser);
        }

    /**
     * Get the user based on a username
     * @param ID String representing the username of a user
     * @return a TrialUser that matches the username string input
     */
        public TrialUser findUser(String ID){
            for(TrialUser u: this.userLists){
                if (u.getUserName().equals(ID)){
                    return u;
                }
            }
            return null;
        }

    /**
     * Checks if the password is correct
     * @param password String representing the inputted password
     * @param userName String representing the inputted username
     * @return boolean value representing if the password was correct
     */
        public boolean passwordCheck(String password, String userName) {
            for (TrialUser u : this.userLists) {
                if (u.getUserName().equals(userName)) {
                    return u.passWordCheck(password);
                }
            }
            return false;
        }

    /**
     * Gets the password of a user
     * @param userName String that represents a user's username
     * @return String that represents a user's password
     */
        public String getPassword(String userName){
            return findUser(userName).getPassword();
        }

    /**
     * Changes the password of a user
     * @param userName String that represents username
     * @param password String that represents password
     */
        public void changePassword(String userName, String password){
            findUser(userName).setPassword(password);
        }
    }