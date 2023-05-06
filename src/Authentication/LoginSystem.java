package Authentication;

import Calendar.CalendarSystem;
import SendMail.EmailSender;
import TextUI.MainMenuSystem;
import TextUI.QuitSystem;
import User.TemporaryUser;
import User.TrialUser;
import User.UserSystem;
import Data.InformationReader;
import Data.InformationSaver;

import java.util.Scanner;
/** a Text UI class that is in charge of the login class of all type of users. It stores a user system, a string
 * which represent the welcome message that is going to be displayed after a user logs in, a main menu system that
 * will be displayed after a user logs in. And the registration system in order to use the local date and
 * check difference method. An email sender that sends a temporary password to the user.
 * @author Group0031
 * **/
public class LoginSystem {
    private final UserSystem userSystem;
    private final String WelcomeMessage;
    private final MainMenuSystem mainMenuSystem;
    private final RegistrationSystem registrationSystem;
    private final EmailSender emailSender = new EmailSender();


    /** construct a login system using the objects provided
     * @param userSystem, a controller class that controls user and template
     * @param calendarSystem, a controller class that controls calendar and activities
     * @param saver, a gateway class that saves info to csv files
     * @param registrationSystem, a controller class that is in charge of registering users
     * @param reader, a gateway class that read csv file **/
    public LoginSystem(UserSystem userSystem, CalendarSystem calendarSystem, InformationSaver saver,
                       RegistrationSystem registrationSystem, InformationReader reader){
        this.userSystem = userSystem;
        this.WelcomeMessage = reader.GetWelcomeMessage();
        this.mainMenuSystem = new MainMenuSystem(userSystem, calendarSystem, saver, reader);
        this.registrationSystem = registrationSystem;
    }

    /** This method takes care of the login process of the program. if a user press f the email sender would send
     * an email containing a temporary password to the user. This method also calls the check login temp and
     * login normal to see whether the user is being suspended or temp account is expired
     * @param x, a scanner object that takes the user input
     * @param quitSystem, a UI class that in charging of saving when a user logs out **/
    public void login(Scanner x, QuitSystem quitSystem) throws Exception {
        while (true){
            System.out.println("While logging in, you can enter q to quit.");
            System.out.println("Please Enter your Username/Email: ");
            String Username = x.nextLine();
            if (Username.equals("q")){
                quitSystem.saveAndQuit();
                return;
            }
            System.out.println("Enter any key to continue if you registered as a trial user. Otherwise: ");
            System.out.println("Please Enter your Password or enter f to get a temporary password through email: ");
            String password = x.nextLine();
            if (password.equals("q")){
                quitSystem.saveAndQuit();
                return; }
            if (password.equals("f")){
                emailSender.sendMail(Username);
                setTempPassword(Username);
                return; }
            if (userSystem.checkPassword(password, Username)) {
                TrialUser user = userSystem.getUser(Username);
                user.SetLoginDate(registrationSystem.getDate());
                user.SetFrozen(false);
                if(user instanceof TemporaryUser){
                   loginTemp(x, quitSystem, user, Username);
                }
                else{
                    loginNormal(x, quitSystem, user, Username);}
                return; }
            else {
                System.out.println("Wrong Username or password! please try again"); } } }

    /** This method is in charge of checking if the given temporary user can log in into the system
     * It checks whether the user is suspended and if the temporary account is expired
     * @param x, a scanner object that gets the input from user
     * @param user, a user object that we are going to check
     * @param Username, the username of the user provided **/
    public void loginTemp(Scanner x, QuitSystem quitSystem, TrialUser user, String Username){
        if (CheckDate((TemporaryUser) user) & CheckSuspended(user)) {
            PrintWelcomeMessage(Username);
            mainMenuSystem.MainMenu(x, Username, userSystem, quitSystem, registrationSystem);
        }
        else if (!CheckSuspended(user)) {
            System.out.println("your account has been suspended until " + userSystem.getUnSuspendDate(user));
        }

        else{
            System.out.println("your temporary account is expired!");
        }
    }

    /** This method is in charge of checking if the given regular user can log in into the system
     * It checks whether the user is suspended.
     * @param x, a scanner object that gets the input from user
     * @param user, a user object that we are going to check
     * @param Username, the username of the user provided **/
    public void loginNormal(Scanner x, QuitSystem quitSystem, TrialUser user, String Username){
        if (CheckSuspended(user)) {
            PrintWelcomeMessage(Username);
            mainMenuSystem.MainMenu(x, Username, userSystem, quitSystem, registrationSystem);
        }
        else {System.out.println("your account has been suspended until " + userSystem.getUnSuspendDate(user));
        }
    }

    /** This method is in charge of checking if the given user has been suspended by using the get days between
     * method in registration system if the user is indeed suspended to see if the difference between the current
     * day and the day that the user was suspended is larger or equal to the number of suspend day the admin set.
     * @param user , a user object of the user we are going to check
     * @return returns true is the user is not suspended or is eligible to log in and return false otherwise**/
    public boolean CheckSuspended(TrialUser user){
        if (user.GetSuspendedDate().equals("null")){
            return true;
        }
        else{
        int numDays = registrationSystem.GetsDaysBetween(user.GetSuspendedDate());
        return (numDays >= user.GetSuspendedDays());}
    }

    /** check if the temporary user account is expired by checking if the difference between current day and the
     * registration date is larger or equal to 30
     * @param user , a temporary user object we are going to check
     * @return , return true if the user account is not expired and return false otherwise**/
    public boolean CheckDate(TemporaryUser user){
         int numDays = registrationSystem.GetsDaysBetween(user.GetRegisteredDate());
         return !(numDays >= 30);
    }

    /** Print the welcome message after the user logs in, if the welcome message attribute
     * is empty print out default message which is Welcome + username + ^_^
     * @param Username, a string that stores the username of user**/
    public void PrintWelcomeMessage(String Username){
        if (WelcomeMessage.equals("")) {
            System.out.println("Welcome " + Username + " ^_^");}
        else {System.out.println(WelcomeMessage); }
    }

    /** generate a temporary password that the user uses to log in
     * @param userName, a string that stores the username of user
     * @return a String that stores the temporary password generated **/
    public String generateTempPassword(String userName){
        return "A" + userName.split("@")[0] + "123";
    }

    /** the method set the password of the current user to a temporary password, if the user is not registered
     * print out a message
     * @param userName, a string that stores the username of user **/
    public void setTempPassword(String userName){
        try{
            userSystem.getUser(userName).setPassword(generateTempPassword(userName));
            System.out.println("Email successfully sent. Please log in using the temporary password.");
            System.out.println("Please note that you should change your password after logging in, or your password " +
                    "will be set as the temporary password sent.");
        }
        catch(NullPointerException e){
            System.out.println("We found the user name you entered is not registered yet. " +
                    "Please make sure you register first.");
        }
    }
}