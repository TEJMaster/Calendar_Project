package Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

import TextUI.QuitSystem;
import User.UserSystem;

import static java.time.temporal.ChronoUnit.DAYS;

/**  This class is being created to Register an account. It initializes and stores
 *  Username validator and password validator which are being used to create username and password of the account
 *  There are three types of user that are being registered: Trial user, Regular user and Admin User.
 * @author Group0031
 *  **/
 public class RegistrationSystem {
     private final UserNameValidator userNameValidator;
     private final PasswordValidator passwordValidator;

      /** This is the constructor of Registration system which creates a new username validator and password validator
       *  which contains regex to check Username and password. **/
     public RegistrationSystem(){
         this.userNameValidator = new UserNameValidator();
         this.passwordValidator = new PasswordValidator();
     }

     /** Register a user account using the get type and create password method
     * @param x, a scanner object that gets the input of the user
     * @param UserSystem the controller class of users and template
     * @param type The type of the user which got from get type method that has value "0" for trial user
     * "1" for regular user and "2" for admin user.
     * @return the username of the user created**/
     public String registerUser(Scanner x, UserSystem UserSystem, String type) {
        while (true) {
            System.out.println(userNameValidator.getDescription());
            System.out.println("Please Enter a Username: ");
            String Username = x.nextLine();
            if (!userNameValidator.validate(Username)) {
                System.out.println("Invalid Username. Please enter your email.");
            } else {
                if (UserSystem.getUser(Username) != null) {
                    System.out.println("Username is taken! please try again!");
                } else {
                    if (type.equals("0")) {
                        List<String> FriendList = new ArrayList<>();
                        FriendList.add(Username);
                        UserSystem.createUser(Username, null, false, getDate(), false,
                                0, "null", FriendList);
                        System.out.println("Your Account has been created successfully");
                    } else if (type.equals("1")) {
                        registerRegularUser(x, UserSystem, Username);
                        System.out.println("Your Account has been created successfully");
                    } else {
                        registerAdminUser(x, UserSystem, Username);
                        System.out.println("Your Account has been created successfully");
                    }
                    return Username;
                }
            } } }

    /** This method creates the password for the user and return it after verification. If the user input a different
     * password in the second time. He needs to restart the process because of the while loop. This method also use
     * password validator attribute to check the strength of password. THe process also restarts if the strength is
     * too week
     * @param x a scanner object that gets the input of user
     * @return returns the password the user created **/
    public String CreatePassword(Scanner x) {
        while (true){
            System.out.println(passwordValidator.getDescription());
            System.out.println("Please Enter a Password: ");
            String password = x.nextLine();
            if (passwordValidator.validate(password)){
                System.out.println("Please Enter your Password again to verify: ");
                String CheckPassword = x.nextLine();
            if (password.equals(CheckPassword)) {
                return password; }
            else {
                System.out.println("Your password are not the same! please try again!");
            }
        }
        else{
            System.out.println("Your password strength is too weak, please enter another one.");
        }
        }
        }

        /** For regular and admin user, this method would ask and return the answer of if the user wants the account
         * to be temporary which means it would expire in 30 days.
         * @param x, a scanner object that gets the input of user
         * @return return true if the user's answer is Yes and false if the user do not want its account to be temporary
         * **/
        public boolean AskTemp(Scanner x){
        while (true){
        System.out.println("do you want your account to be temporary?(only available for 30 days) " +
                "please enter Yes /No");
        String answer = x.nextLine();
        if (answer.equals("Yes")|answer.equals("yes")) {
            return true; }
            else if (answer.equals("No")|answer.equals("no")) {
                return false; }
            else { System.out.println("invalid button"); }
        }
        }

    /** This method gives us the type of user the user at the keyboard want to create. The user need to press "0"
     * for trial user "1" for regular user and "2" for admin user. If the user press the wrong button this while
     * loop will allow the user to enter again
     * @param x, a scanner object that gets the input of user
     * @return returns the string which represent the type of account the user want to create**/
    public String GetType(Scanner x){
        while (true){
        System.out.println("Press 0 to create a trial account, Press 1 to create an regular account, or" +
                   " Press 2 to create an admin account" );
        String type = x.nextLine();
        if (!(type.equals("0")|type.equals("1")|type.equals("2"))) {
            System.out.println("invalid button");
        }
        else{ return type;}
        }
    }

    /** This method handles the transition process which takes place after an account has been registered. It gives
     * user options to either login by pressing 0 or quite the program by pressing q
     * @param x, a scanner object that gets the input of user
     * @param quitSystem, a UI class which handles the save process when user logs out
     * @param loginSystem, a UI class that handles user login process
     * @param Username, a String that stores the username of the registered user **/
    public void RegisterTransition(Scanner x, QuitSystem quitSystem, LoginSystem loginSystem, String Username)
            throws Exception {
        while (true) {
            System.out.println("now that u registered your account, press 0 to login or press q to quit the program");
            String answer = x.nextLine();
            if (!(answer.equals("0") | answer.equals("q"))) {
                System.out.println("invalid button");
            }
            else if (answer.equals("0")) {
                loginSystem.login(x, quitSystem);
            }
            else { quitSystem.ExitCheck(x, Username);
                return;}
        }}

    /** this method returns the current date that are being displayed on user's local clock then convert it to string
     * it is being used to record the login date and start date of temporary accounts
     * @return a String representation of the current date **/
    public String getDate(){return LocalDate.now().toString();}

    /** this method returns the difference between the input date and the current date that are being displayed on user's
     * local clock then convert it to int. It is being used to check to suspend and temporary account deadlines
     * @param DateTime a String that represent a date
     * @return the method returns the difference in days between the input date and the current date **/
    public int GetsDaysBetween(String DateTime){
        LocalDate time = LocalDate.now();
        LocalDate Date = LocalDate.parse(DateTime);
        long daysBetween = DAYS.between(Date,time);
        return Integer.parseInt(String.valueOf(daysBetween));
    }

    /** Register a regular user account using the creation password and ask temp method, if the result got from ask
     * temp is true then create a regular temporary user and a permanent regular user otherwise
     * @param x, a scanner object that gets the input of user
     * @param userSystem, a controller for user and template
     * @param Username, A string representing the username of the user that is being created**/
    public void registerRegularUser(Scanner x, UserSystem userSystem, String Username){
        String password = CreatePassword(x);
        boolean answer = AskTemp(x);
        List<String> FriendList = new ArrayList<>();
        FriendList.add(Username);
        if (answer) {
            String LocalDate = getDate();
            userSystem.createUser(Username, password, false, LocalDate, getDate(), false,
                    0, "null", FriendList);
        }
        else {
            userSystem.createUser(Username, password, false, getDate(), false,
                    0 , "null", FriendList);
        }
    }

    /** Register an admin user account using the creation password and ask temp method, if the result got from ask
     * temp is true then create an admin temporary user and a permanent admin user otherwise
     * @param x, a scanner object that gets the input of user
     * @param userSystem, a controller for user and template
     * @param Username, A string representing the username of the user that is being created**/
    public void registerAdminUser(Scanner x, UserSystem userSystem, String Username){
        String password = CreatePassword(x);
        boolean answer = AskTemp(x);
        List<String> FriendList = new ArrayList<>();
        FriendList.add(Username);
        if (answer) {
            String LocalDate = getDate();
            userSystem.createUser(Username, password, true, LocalDate, getDate(), false,
                    0 , "null", FriendList);
        }
        else {
            userSystem.createUser(Username, password, true, getDate(), false,
                    0 , "null", FriendList);
        }
    }
}