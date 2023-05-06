package User;

import Template.Template;
import Template.TemplateManager;
import Authentication.PasswordValidator;
import Data.InformationReader;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * A controller class that manages the UserManager and TemplateManager class
 * @author Group0031
 */
public class UserSystem {
    private final UserManager newUserManager;
    private final TemplateManager newTemplateManger;
    /**
     * Creates UserManager and TemplateManager based on the data stored in the csv files
     * @param informationReader a class that helps to read users and templates from stored data
     * @throws FileNotFoundException raised if files given is not found in the path.
     */
    public UserSystem(InformationReader informationReader) throws FileNotFoundException {
        this.newUserManager = new UserManager(informationReader);
        this.newTemplateManger = new TemplateManager(informationReader);
    }

    /**
     * Creates a new user
     * @param userName String representing a users username
     * @param password String representing a user's password
     * @param isAdmin boolean value representing whether the user is an admin
     * @param registeredDate String representing the date the user registered
     * @param LoginDate String representing the date the user logged in
     * @param IsFrozen boolean value representing whether the user's account is frozen
     * @param SuspendedDays integer representing the number of days the user's account is suspended for
     * @param SuspendedDate String representing the date the user's account suspension started
     * @param FriendList list of string representing a user's friends
     */
    public void createUser(String userName, String password, boolean isAdmin, String registeredDate, String LoginDate,
                           boolean IsFrozen, int SuspendedDays, String SuspendedDate, List<String> FriendList){
        newUserManager.UserCreator(userName, password, isAdmin, registeredDate, LoginDate, IsFrozen,
                SuspendedDays, SuspendedDate, FriendList);
    }

    /**
     * Create a new user with the necessary components of a user
     * @param userName String representing a users username
     * @param password String representing a user's password
     * @param isAdmin boolean value representing whether the user is an admin
     * @param LoginDate String representing the date the user logged in
     * @param IsFrozen boolean value representing whether the user's account is frozen
     * @param SuspendedDays integer representing the number of days the user's account is suspended for
     * @param SuspendedDate String representing the date the user's account suspension started
     * @param FriendList list of string representing a user's friends
     */
    public void createUser(String userName, String password, boolean isAdmin, String LoginDate, boolean IsFrozen,
                           int SuspendedDays, String SuspendedDate, List<String> FriendList){
        newUserManager.UserCreator(userName, password, isAdmin, LoginDate, IsFrozen, SuspendedDays, SuspendedDate,
                FriendList);
    }

    /**
     * Gets the date the user's account suspension ends
     * @param user a user with a suspended account
     * @return String that represents the date the user's suspension ends
     */
    public String getUnSuspendDate(TrialUser user){
        String SuspendedDate = user.GetSuspendedDate();
        LocalDate SuspendDate = LocalDate.parse(SuspendedDate);
        return SuspendDate.plusDays(user.GetSuspendedDays()).toString();
    }

    /**
     * Gets the user based on the username
     * @param UserName String representing a username
     * @return User with the same username as the input
     */
    public TrialUser getUser(String UserName){
        return newUserManager.findUser(UserName);
    }

    /**
     * Checks if the password if correct
     * @param password String that represents the password inputted
     * @param userName String that represents the UserName inputted
     * @return boolean value for whether the password inputted for the specific user is correct
     */
    public boolean checkPassword(String password, String userName){
        return newUserManager.passwordCheck(password, userName);
    }

    /**
     * Creates a new template based on input
     * @param num String representing which new template to create
     * @return A Template based on the input. "0" for DateCalendar, "1" for TimeTable and a MonthlyCalendar otherwise
     */
    public Template GetTemplate(String num){
        if (num.equals("0")) {
            return this.newTemplateManger.getDateCalendarTemplate();
        }
        else if (num.equals("1")){
            return this.newTemplateManger.getTimeTableTemplate();
        }
        else{
            return this.newTemplateManger.getMonthlyCalendarTemplate();
        }
    }

    /**
     * Create a new User Manager
     * @return User Manager
     */
    public UserManager getNewUserManager() {
        return newUserManager;
    }

    /**
     * Create a new template manager
     * @return Template Manager
     */
    public TemplateManager getNewTemplateManger(){return newTemplateManger; }

    /**
     * Change the password of a user
     * @param userName String representing the username of a user
     */
    public void changePassword(String userName){
        PasswordValidator passwordValidator = new PasswordValidator();
        Scanner sc = new Scanner(System.in);
        System.out.println(passwordValidator.getDescription());
        System.out.println("Please enter your new password: ");
        String newPassword = sc.nextLine();
        if (newPassword.equals(newUserManager.getPassword(userName))){
            System.out.println("You entered the same password as you had before.");
            return;
        }
        if (passwordValidator.validate(newPassword)){
            newUserManager.getPassword(userName);
            newUserManager.changePassword(userName, newPassword);
        }
        else{
            System.out.println("You failed to change your password.");
            System.out.println(passwordValidator.getDescription());
        }
    }
}