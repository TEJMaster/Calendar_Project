package TextUI;

import Authentication.RegistrationSystem;
import Calendar.CalendarSystem;
import Messages.MessagingSystem;
import User.TrialUser;
import User.UserManager;
import User.UserSystem;
import Data.InformationReader;
import Data.InformationSaver;

import java.util.List;
import java.util.Scanner;

/** A Text UI class that displays and handles the options provided inside the main menu system. For example
 *  It stores a saver to save the welcome message in admin options. A calendar creator to create calendars.
 *  A calendar viewer used to view calendars. A modify template system to modify calenders templates **/
public class MainMenuSystem {
    private final InformationSaver saver;
    private final CalendarCreator calendarCreator;
    private final CalendarViewer calendarViewer;
    private final ModifyTemplateSystem modifyTemplateSystem;
    private final MessagingSystem messagingSystem;
    private final CalendarUpdatingSystem CalendarUpdatingSystem;
    InformationReader Reader;

    /** This method constructs the main menu system using the parameters provided
     * @param userSystem, a controller class that control user and template
     * @param calendarSystem, a controller class that control activites and calendars
     * @param saver, a gateway class that saves information
     * @param reader, a gateway class that reads csv files **/
    public MainMenuSystem(UserSystem userSystem, CalendarSystem calendarSystem, InformationSaver saver,
                          InformationReader reader){
        this.saver = saver;
        this.calendarCreator = new CalendarCreator(userSystem, calendarSystem);
        this.modifyTemplateSystem = new ModifyTemplateSystem();
        this.CalendarUpdatingSystem = new CalendarUpdatingSystem(calendarSystem);
        this.calendarViewer = new CalendarViewer(userSystem, calendarSystem, CalendarUpdatingSystem);
        this.Reader = reader;
        this.messagingSystem = new MessagingSystem(Reader);
    }

    /** This is the main menu of the program. If the username provided is an admin user. Then display admin
     * user options and display regular options otherwise.
     * @param x, a scanner object that gets the input from user
     * @param Username, a string that stores the username of users
     * @param quitSystem, a Controller that handles the save and quit process when a user logs out
     * @param registrationSystem, a controller that handles the registration process **/
    public void MainMenu(Scanner x, String Username, UserSystem userSystem, QuitSystem quitSystem,
                         RegistrationSystem registrationSystem) {
            if (userSystem.getUser(Username).getIsAdmin()){
                AdminOptions( x, Username, userSystem, registrationSystem, quitSystem);
            }
            else{ regularOptions( x, Username, userSystem, quitSystem);}
    }

    /** This method print out all the options that user can do in the main menu. this method will print different
     * messages if the user that the username is representing is an admin or regular user .
     * @param userSystem, a controller class that handle user and templates
     * @param Username, a string representing the username of the current user **/
    public void PrintOptions(UserSystem userSystem, String Username) {
        System.out.println("option 1: Press 0 to create a new calendar from a template");
        System.out.println("option 2: Press 1 to view your calendars");
        System.out.println("option 4: Press 2 to change password");
        System.out.println("option 5: Press 3 to undelete calendars");
        System.out.println("option 6: Press 4 to add a friend to your friendList");
        if (!userSystem.getUser(Username).getIsAdmin()) {
            System.out.println("option 3: Press 5 to view public or Friend only calendars created by other users");
            System.out.println("option 8: Press 6 to write a message to the admin");
            System.out.println("option 9: Press 7 to check response from your admin");}
        else {
            System.out.println("Admin option 1: Press 5 to Modify template");
            System.out.println("Admin option 2: Press 6 to create a welcome message");
            System.out.println("Admin option 3: Press 7 to create accounts for other admin users");
            System.out.println("Admin option 4: Press 8 to view messages sent by other users");
            System.out.println("Admin option 5: Press 9 to freeze accounts that has not log in for x days");
            System.out.println("Admin option 6: input 10 to suspend a non admin user for x days");
            System.out.println("Admin option 7: input 11 to view and change any calendar created by other users");
        }
        System.out.println("Log out option: Press q to log out");
    }

    /** This method handle all the cases for an admin. If admin press 0 - 4 the method will call the common option
     * method which are options that regular and admin user both have. If the button the user pressed is not an
     * available option, the while loop will allow the user to enter again. The option admin have includes suspend
     * user, freeze users, change template, create welcome message, register another admin, respond to user messages
     * and view change all calendars in the program.
     * @param x, a scanner object that gets the user input
     * @param Username, a string that contains the username of the user that is using the program
     * @param userSystem, a controller class which handle user and templates
     * @param registrationSystem, a controller that handle user registration
     * @param quitSystem, a controller which handle the quit and save process. **/
    public void AdminOptions(Scanner x, String Username, UserSystem userSystem,
                             RegistrationSystem registrationSystem, QuitSystem quitSystem) {
        while(true) {
            PrintOptions(userSystem, Username);
            String answer = x.nextLine();
            switch (answer) {
                case "0":
                case "1":
                case "2":
                case "3":
                case "4":
                    CommonOptions(answer, x, Username, userSystem, quitSystem); break;
                case("5"):
                    modifyTemplateSystem.ModifyTemplate(x, userSystem.getNewTemplateManger()); break;
                case("6"):
                    CreateWelcomeMessage(x); break;
                case("7"):
                    registrationSystem.registerUser(x, userSystem, "2"); break;
                case("8"):
                    messagingSystem.ReadAndRespond(x); break;
                case("9"):
                    ChooseAndFreeze(x, userSystem, Username, registrationSystem); break;
                case("10"):
                    SuspendAccounts(x, userSystem, registrationSystem); break;
                case("11"):
                    if (calendarViewer.CheckAllCreations()) {
                    calendarViewer.StartAllCalendarMenu(x, Username, quitSystem);} break;
                case("q"): quitSystem.ExitCheck(x, Username);
                return;
                default:
                System.out.println("Invalid button, please retry your input.");
                break; }} }

    /** This method handle the options that admin and regular users both have. If the button the user pressed is not an
     * available option, the while loop will allow the user to enter again. It handles the create calendar, view own
     * calendar, change password, add friend and undo deletion process.
     * @param x, a scanner object that gets the user input
     * @param Username, a string that contains the username of the user that is using the program
     * @param userSystem, a controller class which handle user and templates
     * @param quitSystem, a controller which handle the quit and save process. **/
    public void CommonOptions(String answer, Scanner x, String Username, UserSystem userSystem,
                              QuitSystem quitSystem) {
        switch (answer) {
            case("0"):
                calendarCreator.CreateCalendar(x, Username);
                break;
            case("1"):
                if(calendarViewer.CheckCreation(Username)){
                    calendarViewer.StartOwnCalendarMenu(x, Username, quitSystem);}
                break;
            case ("2"):
                userSystem.changePassword(Username);
                break;
            case ("3"):
                CalendarUpdatingSystem.UndoDeletion(x);
                break;
            case ("4"):
                TrialUser user = userSystem.getUser(Username);
                String OtherUsername = GetOtherName(x, userSystem);
                AddedFriend(user, OtherUsername);
                break;
        }
    }

    /** This method handle the options regular users have. If the button the user pressed is not an
     * available option, the while loop will allow the user to enter again. It handles  write message to admin,
     * view response from admin and view other user creations.
     * @param x, a scanner object that gets the user input
     * @param Username, a string that contains the username of the user that is using the program
     * @param userSystem, a controller class which handle user and templates
     * @param quitSystem, a controller which handle the quit and save process. **/
    public void regularOptions(Scanner x, String Username, UserSystem userSystem,
                               QuitSystem quitSystem) {
        while(true) {
            PrintOptions(userSystem, Username);
            String answer = x.nextLine();
        switch (answer) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
                CommonOptions(answer, x, Username, userSystem, quitSystem);
                break;
            case("5"):
                String OtherUsername =  GetOtherName(x, userSystem);
                if(calendarViewer.CheckOtherCreation(Username, OtherUsername)){
                    calendarViewer.StartOtherCalendarMenu(x, Username, OtherUsername, quitSystem);}
                break;
            case("6"):
                messagingSystem.WriteMessage(x, Username);
                break;
            case("7"):
                Reader.ReadResponse(Username);
                break;
            case("q"):
                quitSystem.ExitCheck(x, Username);
                return;
            default:
                System.out.println("Invalid button, please retry your input.");
                break;
        }}
    }

    /** This method is used for asking another user's name to suspend or view creations. Since the username entered could
     * be not stored in the system. (maybe the user enters the incorrect username. The method asks the user to
     * re-enter the username.
     * @param x, a scanner object that gets user input
     * @param userSystem, a controller class that handles users and template
     * @return returns the string that stores the username of the other user **/

    public String GetOtherName(Scanner x, UserSystem userSystem) {
        while (true) {
            System.out.println("What is the username of this user?");
            String userName = x.nextLine();
            if (!(userSystem.getUser(userName) == null)) {
                return userName;
            } else {
                System.out.println("Username is not in the system please try again!");
            }
        }
    }

    /** This method allows admin to write a new welcome message when a user logs in
     * @param x, a scanner object that gets the usr input **/
    public void CreateWelcomeMessage(Scanner x){
        System.out.println("Please input your new Welcome Message below");
        String Message = x.nextLine();
        saver.writeWelcome(Message);
        System.out.println("Welcome message updated!");
    }

    /** This method calls choose deadline method to choose how many days of inactivity to freeze the account and
     * then call the freeze account to freeze all other accounts based on the deadline
     * @param x , a scanner object that gets the user input
     * @param Username, a string that contains the username of the user that is using the program
     * @param userSystem, a controller class which handle user and templates
     * @param registrationSystem, a controller class that handles registration process **/
    public void ChooseAndFreeze(Scanner x, UserSystem userSystem, String Username, RegistrationSystem registrationSystem){
        int num = ChooseDeadline(x);
        FreezeAccounts(userSystem, Username, registrationSystem, num);
    }

    /** This method allow admin to choose how many days of inactivity to freeze the account
     * @param x , a scanner object that gets the user input
     * @return returns an int that represent the number of days of inactivity **/
    public int ChooseDeadline(Scanner x){
        while (true){
        System.out.println("please input the freezing deadline(not logged in since x number of days ago)");
        String num = x.nextLine();
        try {return Integer.parseInt(num);
        }
        catch (java.lang.NumberFormatException e) {
            System.out.println("the number is invalid! please input the number again!");
        }
        }
    }

    /** This method allow admin to choose how many days of inactivity to freeze the account
     * @param user , a user object that represent the current user
     * @param OtherUsername, a string object that store the username of friend the user want to add**/
    public void AddedFriend(TrialUser user, String OtherUsername) {
        if (user.getFriendList().contains(OtherUsername)) {
            System.out.println(OtherUsername + " is already friends with " + user.getUserName());
        }
        else {
        user.addFriend(OtherUsername);
        System.out.println( OtherUsername + " has being added to " + user.getUserName() + "'s FriendList");}
    }

    /** This method freezes all the accounts that are has more or equal days of inactivity than the input number
     * which the admin has set.
     * @param userSystem,  a controller class which handle user and templates
     * @param Username, a String which stores the username of the current user
     * @param registrationSystem, a controller that handle the registration process of user
     * @param num, a number of days of inactivity to freeze that are set by admin **/
    public void FreezeAccounts(UserSystem userSystem, String Username, RegistrationSystem registrationSystem, int num){
        UserManager userManager = userSystem.getNewUserManager();
        List<TrialUser> users = userManager.getUsers();
        for (TrialUser user : users){
            if (registrationSystem.GetsDaysBetween(user.GetLoginDate()) >= num & !user.getUserName().equals(Username))
            {
                user.SetFrozen(true);
            }
        }
        System.out.println("Accounts has been frozen");
    }

    /**let admin input how long to suspend a user for , if the admin did not input a number the while loop will allow
     * the admin to input again
     * @param x, a scanner object that gets the input of user
     * @return a int representing the number of days to suspend the user for */
    public int ChooseSuspendDate(Scanner x){
        while (true){
            System.out.println("please input how long do you want to suspend this user, please input number of days");
            String num = x.nextLine();
            try {return Integer.parseInt(num);
            }
            catch (java.lang.NumberFormatException e) {
                System.out.println("the number is invalid! please input the number again!");
            }
        }
    }

    /** the method gets all the non admin user in the program
     * @param userManager, a use case class that managers user
     * @return return true if there is non admin users in the system and false otherwise */
    public boolean DisplayRegularNames (UserManager userManager) {
        List<TrialUser> NonAdminUsers = userManager.getNonAdminUsers();
        if (NonAdminUsers.isEmpty()) {
            System.out.println("there are no non admin users in the system");
            return false;
        }
        else {
            System.out.println("here are all the non admin users in the system");
            for (TrialUser user: NonAdminUsers)  {
                    System.out.println(user.getUserName());
            }
            return true;
        }
    }

    /** the method allows an admin to choose a user and then suspend it by calling choose to suspend date and the setter
     * of suspend day and date inside user.
     * @param x, a scanner object that gets user input
     * @param userSystem, a controller that handles user and template
     * @param registrationSystem, a controller that register a user to the program */
    public void SuspendAccounts(Scanner x, UserSystem userSystem, RegistrationSystem registrationSystem) {
        while (true) {
            UserManager userManager = userSystem.getNewUserManager();
            if (DisplayRegularNames(userManager)) {
                String userid = GetOtherName(x, userSystem);
                TrialUser user = userManager.findUser(userid);
                if (user.getIsAdmin()) {
                    System.out.println("the account you choose is an admin please select again!");
                }
                else {
                    int day = ChooseSuspendDate(x);
                    user.SetSuspendedDays(day);
                    user.SetSuspendedDate(registrationSystem.getDate());
                    System.out.println("the account has been suspended!");
                    return;
                }
            }
            else { return; }
        }
    }
}