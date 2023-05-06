package TextUI;

import Activity.Activity;
import Calendar.Calendar;
import Calendar.CalendarSystem;
import User.TrialUser;
import User.UserSystem;
import java.util.List;
import java.util.Scanner;

/** a text UI class let allow a user to view and modify a calendar if he has the access to editing the creator's
 * content. This class stores an activity management that is in charge of viewing and changing activities and
 * calendar updating system which modifies a calendar. **/
public class CalendarViewer {
    private final UserSystem userSystem;
    private final CalendarSystem calendarSystem;
    private final ActivityManagementSystem activityManagementSystem;
    private final CalendarUpdatingSystem calendarUpdatingSystem;
    /** This constructs a calendar viewer using the given user system, calendar system and calendar updating system
     * Then it creates a new activity management system to view and change activities. **/
    public CalendarViewer(UserSystem userSystem, CalendarSystem calendarSystem, CalendarUpdatingSystem
            calendarUpdatingSystem) {
        this.userSystem = userSystem;
        this.calendarSystem = calendarSystem;
        this.activityManagementSystem = new ActivityManagementSystem();
        this.calendarUpdatingSystem = calendarUpdatingSystem;
    }

    /**
     * this method use the id got from select own calendar to chose any creation created by the user himself
     * and check if the calendar id chosen by it is valid, if it is valid then the method start the calendar menu for
     * the chosen calendar
     * @param x, a scanner object that record the input from user
     * @param userName, a String that stores the username of current user
     * @param quitSystem, a controller that is in charge of save and quit process when user logs out
     */
    public void StartOwnCalendarMenu(Scanner x, String userName, QuitSystem quitSystem) {
        while (true) {
            int id = SelectOwnCalendar(x, userName);
            if (!(calendarSystem.getCalendar(id) == null)) {
                Calendar calendar = calendarSystem.getCalendar(id);
                if (!calendar.getUserName().equals(userName)) {
                    System.out.println("the calendar you inputted is not your own, please choose your calendar again.");
                } else {
                    String type = calendarSystem.GetCalendarType(id);
                    System.out.println("The calendar you selected is a " + type + "!");
                    CalendarMenu(x, userName, calendar, quitSystem);
                    return;
                }
            } else {
                System.out.println("the id is invalid! please choose your calendar again.");
            }
        }
    }

    /** this method use the id got from select public or both public and friend only calendar depending on whether the
     * user has access to friend only content of the creator and check if the calendar id chosen by it is valid, if it
     * is valid then the method start the calendar menu for the chosen calendar
     * @param x, a scanner object that record the input from user
     * @param userName, a String that stores the username of current user
     * @param quitSystem, a controller that is in charge of save and quit process when user logs out
     */
    public void StartOtherCalendarMenu(Scanner x, String userName, String OtherUserName, QuitSystem quitSystem) {
        while (true) {
            int id;
            if (CheckFriendAccess(userName, OtherUserName)) {
                id = SelectPublicFriendCalendar(x, OtherUserName);
            } else {
                id = SelectPublicCalendar(x, OtherUserName);
            }
            if (!(calendarSystem.getCalendar(id) == null)) {
                Calendar calendar = calendarSystem.getCalendar(id);
                if (calendar.getType().equals("0")) {
                    System.out.println("the calendar you inputted is a private calendar, please choose your calendar again.");
                } else if (calendar.getType().equals("2") & !CheckFriendAccess(userName, OtherUserName)) {
                    System.out.println("the calendar you inputted is a Friend only calendar and you are not friend with the" +
                            "creator" + ", please choose your calendar again.");
                } else {
                    String type = calendarSystem.GetCalendarType(id);
                    System.out.println("The calendar you selected is a " + type + "!");
                    CalendarMenu(x, userName, calendar, quitSystem);
                    return;
                }
            } else {
                System.out.println("the id is invalid! please choose your calendar again.");
            }
        }
    }

    /** this method use the id got from select all calendar that allows an admin user to select any calendar and then
     *  check if the calendar id chosen by it is valid, if it is valid then the method start the calendar menu for
     *  the chosen calendar
     * @param x, a scanner object that record the input from user
     * @param userName, a String that stores the username of current user
     * @param quitSystem, a controller that is in charge of save and quit process when user logs out
     */
    public void StartAllCalendarMenu(Scanner x, String userName, QuitSystem quitSystem) {
        while (true) {
            int id = GetAllCalendars(x);
            if (!(calendarSystem.getCalendar(id) == null)) {
                Calendar calendar = calendarSystem.getCalendar(id);
                String type = calendarSystem.GetCalendarType(id);
                System.out.println("The calendar you selected is a " + type + "!");
                CalendarMenu(x, userName, calendar, quitSystem);
                return;
            } else {
                System.out.println("the id is invalid! please choose your calendar again.");
            }
        }
    }

    /**
     * this method allow user to chosen one of the calendar from all the public calendar the provided user
     * created. If the input calendar id got from user can not be converted to int.
     * @param x, a scanner object that gets the input from user
     * @param Username, the String object that stores the username of the user provided
     * @return returns an int that represent the id of the calendar the user chose
     */
    public int SelectPublicCalendar(Scanner x, String Username) {
        while (true) {
            System.out.println("this is the title and the id of the public calendars the user created: ");
            List<Calendar> Calendars = calendarSystem.getPublicCalendarsByUsername(Username);
            calendarSystem.PrintTitlesOfCalendars(Calendars);
            System.out.println("Which Calendar do you want to choose? please input its id");
            String answer = x.nextLine();
            try {
                return Integer.parseInt(answer);
            } catch (java.lang.NumberFormatException e) {
                System.out.println("the id is invalid! please choose your calendar again.");
            }
        }
    }

    /**
     * this method allow user to chosen one of the calendar from all the public calendar and friend only
     * calendar the provided user created. If the input calendar id got from user can not be converted to int.
     * @param x, a scanner object that gets the input from user
     * @param Username, the String object that stores the username of the user provided
     * @return returns an int that represent the id of the calendar the user chose
     */
    public int SelectPublicFriendCalendar(Scanner x, String Username) {
        while (true) {
            System.out.println("this is the title and the id of the public calendars the user created: ");
            List<Calendar> Calendars = calendarSystem.getFriendPublicByUsername(Username);
            calendarSystem.PrintTitlesOfCalendars(Calendars);
            System.out.println("Which Calendar do you want to choose? please input its id");
            String answer = x.nextLine();
            try {
                return Integer.parseInt(answer);
            } catch (java.lang.NumberFormatException e) {
                System.out.println("the id is invalid! please choose your calendar again.");
            }
        }
    }

    /**
     * This is the calendar menu of the program. It gives the user nine different options of operating on a
     * calendar, view activity of a day, view all activities and add an activity. delete an activity, undo changes
     * on activity, change the type of calendar and so on. If the button pressed by user is not in one of the options
     * the while loop will allow the user to enter the input again.
     * @param x, a scanner object that gets the input from the user
     * @param userName, a string representing the username of the current user
     * @param calendar, a calendar object of the current object we are viewing
     * @param quitSystem, a controller that is in charge of the save process when a user logs out
     **/
    public void CalendarMenu(Scanner x, String userName, Calendar calendar, QuitSystem quitSystem) {
        while (true) {
            printCalendarOptions();
            String answer = x.nextLine();
            switch (answer) {
                case ("0"):
                    PrintActivityByDate(x, calendar); break;
                case ("1"):
                    PrintAllActivities(x, calendar); break;
                case ("2"):
                    if (CheckAccess(calendar, userName)) {
                        AddOrDeleteActivity(x, calendar, userName); } break;
                case ("3"):
                    if (CheckAccess(calendar, userName) & CheckChange(calendar)) {
                        calendarUpdatingSystem.UndoChange(x, calendarSystem, calendar, userName); } break;
                case ("4"): viewAllChanges(calendar); break;
                case ("5"):
                    if (CheckAccess(calendar, userName)) {
                        calendarUpdatingSystem.ChangeCalendarType(x, calendar); } break;
                case ("6"):
                    if (CheckReverse(x, calendar)) {
                        calendarUpdatingSystem.ReverseCalendarType(calendar); } break;
                case ("7"): CheckAddEditor(x, calendar, userName); break;
                case ("8"):
                    if (CheckDeletionAccess(calendar, userName)) {
                        calendarUpdatingSystem.DeleteCalendar(calendar);
                        return;
                    } else { break; }
                case ("9"): return;
                case ("q"): quitSystem.ExitCheck(x, userName); return;
                default: System.out.println("invalid button");
            } } }

    /** This method prints out all the option available for viewing and modifying a calendar **/
    public void printCalendarOptions() {
        System.out.println("Please choose an option below to view your calendar");
        System.out.println("option 1: press 0 to view your activities of a certain day");
        System.out.println("option 2: press 1 to view all activities on this calendar");
        System.out.println("option 3: press 2 to add or delete an activity");
        System.out.println("option 4: press 3 to undo activity changes made on this calendar");
        System.out.println("option 5: press 4 to view all the editing history made on this calendar");
        System.out.println("option 6: press 5 to change this calendar to public, private or Friend Only");
        System.out.println("option 7: press 6 to reverse to the previous type");
        System.out.println("option 8: press 7 to add an editor to this calendar");
        System.out.println("option 9: press 8 to delete this calendar");
        System.out.println("option 10: press 9 to return to main menu");
        System.out.println("press q to logout");
    }

    /** This method provide the option to either add or delete activity. If the user chose to add activity then
     * it calls the add activity method in activity management system and use delete activity method otherwise
     * if the user's input is invalid the method will allow the user to input their answer again
     * @param x, a scanner object that gets the input from user
     * @param userName, a string representing the username of the current user
     * @param calendar, a calendar object of the current object we are viewing **/
    public void AddOrDeleteActivity(Scanner x, Calendar calendar, String userName) {
        while (true) {
            System.out.println("Do you want to add or delete activity? Press 0 to add and press 1 to delete");
            String answer = x.nextLine();
            if (!(answer.equals("0") | answer.equals("1") | answer.equals("2"))) {
                System.out.println("invalid button");
            } else if (answer.equals("0")) {
                activityManagementSystem.AddActivity(x, calendarSystem, calendar, userName);
                return;
            } else {
                Activity activity = activityManagementSystem.ChooseActivity(x, calendarSystem, calendar);
                if (activity != null) {
                    activityManagementSystem.DeleteActivity(activity,calendar, userName);}
                return;
            }
        }
    }

    /** This method prints out the activities of a certain date/ day inside the calendar by calling the
     * get activity by date method in activity management system. This method also gives the option for user
     * to view all activity of the same category or importance for a certain date, and it calls get activity
     * of category or get activity of importance method for each choice
     * @param x, a scanner object that gets the input from user
     * @param calendar, a calendar object of the current object we are viewing **/
    public void PrintActivityByDate(Scanner x, Calendar calendar) {
        String answer = activityManagementSystem.checkIfCategoryImportance(x);
        List<Activity> Activities = activityManagementSystem.GetActivityByDate(x, calendarSystem, calendar);
        List<Activity> DateActivities;
        if(answer.equals("0")){
            DateActivities = Activities;
        }
        else if (answer.equals("1")) {
            DateActivities = activityManagementSystem.getActivityOfCategory(x, Activities);
        }
        else {DateActivities = activityManagementSystem.getActivityOfImportance(x, Activities); }

        activityManagementSystem.PrintActivities(DateActivities, calendar);
    }

    /** This method prints out all the activities inside the calendar by calling the
     * get activity by date method in activity management system. This method also gives the option for user
     * to view all activity of the same category or importance for a certain date, and it calls get activity
     * of category or get activity of importance method for each choice
     * @param x, a scanner object that gets the input from user
     * @param calendar, a calendar object of the current object we are viewing **/
    public void PrintAllActivities(Scanner x, Calendar calendar) {
        String answer = activityManagementSystem.checkIfCategoryImportance(x);
        List<Activity> AllActivities;
        List<Activity> Activities = activityManagementSystem.GetAllActivities(calendarSystem, calendar);
        if (answer.equals("0")) {
            AllActivities = Activities;
        }
        else if(answer.equals("1")) {
            AllActivities = activityManagementSystem.getActivityOfCategory(x, Activities);
        }
        else {
            AllActivities = activityManagementSystem.getActivityOfImportance(x, Activities);
        }
        activityManagementSystem.PrintActivities(AllActivities, calendar);
    }

    /** this method prints out all the change history of the calendar by using the get changes method in calendar
     *  @param calendar, a calendar object of the current object we are viewing  **/
    public void viewAllChanges(Calendar calendar) {
        List<String> AllChanges = calendar.getChanges();
        if (AllChanges.isEmpty()) {
            System.out.println("there are no activity changes made on this calendar!");
        }
        else{
            for (String change: AllChanges) {
        System.out.println(change);}}
    }

    /** check all the calendar the current user created, if the user did not create any, print out a message and return
     * to main menu. It also checks if the user is frozen and if it is print out a message and return to main menu
     * @param Username, a string that represent the username of the current user
     * @return returns false if the user is frozen or has no creation and true otherwise **/
    public boolean CheckCreation(String Username){
        List<Calendar> Calendars = calendarSystem.getCalendarsByUsername(Username);
        TrialUser user = userSystem.getUser(Username);
        if (user.getFrozen()) {
            System.out.println("the user account your inputted has been frozen due to inactivity! please ask him to " +
                    "log in to unfreeze his account");
            return false;
        }
        if (Calendars.isEmpty()) {
            System.out.println("Oops, this user did not created any calendars! please return to the main menu to " +
                    "create one!");
            return false;
        }
        else{
            return true;
        }
    }

    /** check if the current user has access to delete the calendar inputted because only creators and admins
     * can delete calendars
     * @param calendar, a calendar object of the current object we are viewing
     * @param Username, a string that represent the username of the current user
     * @return return true if the user has access to delete the calendar and false otherwise **/
    public boolean CheckDeletionAccess(Calendar calendar, String Username) {
        if (userSystem.getUser(Username).getIsAdmin()) {
            return true;
        }
        else if (calendar.getUserName().equals(Username)) {
            return true;
        }
        else {
            System.out.println("Only Creator and Admin Can delete calendars!");
            return false;
        }
    }

    /** check if the current user has changes of type to reverse and prints the last type to ask if the user want to
     * reverse to this type. If the user entered an invalid input. The method would ask the user again
     * @param calendar,  a calendar object of the current object we are viewing
     * @param x, a scanner object that gets the input from user
     * @return return true if the user want to reverse to the last type and false otherwise **/
    public boolean CheckReverse(Scanner x, Calendar calendar){
        if (calendar.getLastType() == null) {
            System.out.println("there are no changes on the type of this calendar");
            return false;
        }
        else {while (true){
            PrintLatestChange(calendar);
            System.out.println("Do you want to reverse to this type? input Yes/No");
            String answer = x.nextLine();
            if (answer.equals("Yes") | answer.equals("yes")) {
                return true;
            }
            else if (answer.equals("No") | answer.equals("no")){
                return false;
            }
            else{System.out.println("invalid input! please try again");}
        }
    }
    }

    /** this method print out the last change in type for the given calendar
     * @param calendar,  a calendar object of the current object we are viewing **/
    public void PrintLatestChange(Calendar calendar){
        String lastType = calendar.getLastType();
        if (lastType.equals("0")) {
            System.out.println("your previous calendar type was private");
        }
        else if (lastType.equals("1")) {
            System.out.println("your previous calendar type was public");
        }
        else {System.out.println("your previous calendar type was Friend Only");}
    }

    /** check all the public calendar the current user created, if the user did not create any, print out a message and
     * return to main menu. It also checks if the user is frozen and if it is print out a message and return to main menu
     * @param Username, a string that represent the username of the current user
     * @return returns false if the user is frozen or has no creation and true otherwise **/
    public boolean CheckPublicCreation(String Username){
        List<Calendar> Calendars = calendarSystem.getPublicCalendarsByUsername(Username);
        TrialUser user = userSystem.getUser(Username);
        if (user.getFrozen()) {
            System.out.println("the user account your inputted has been frozen due to inactivity! please ask him to " +
                    "log in to unfreeze his account");
            return false;
        }
        if (Calendars.isEmpty()) {
            System.out.println("Oops, this user did not created any public calendars! please return to the main menu to " +
                    "create one!");
            return false;
        }
        else{
            return true;
        }
    }

    /** check all the public and friend only calendar the current user created, if the user did not create any, print
     * out a message and return to main menu. It also checks if the user is frozen and if it is print out a message
     * and return to main menu
     * @param Username, a string that represent the username of the current user
     * @return returns false if the user is frozen or has no creation and true otherwise **/
    public boolean CheckPublicFriendCreation(String Username){
        List<Calendar> Calendars = calendarSystem.getFriendPublicByUsername(Username);
        TrialUser user = userSystem.getUser(Username);
        if (user.getFrozen()) {
            System.out.println("the user account your inputted has been frozen due to inactivity! please ask him to " +
                    "log in to unfreeze his account");
            return false;
        }
        if (Calendars.isEmpty()) {
            System.out.println("Oops, this user did not created any Friend only and public calendars! " +
                    "please return to the main menu to " + "create one!");
            return false;
        }
        else{
            return true;
        }
    }

    /** check all the calendars in the program, if there isn't any, print
     * out a message and return to main menu.
     * @return returns false if there is no creation and true otherwise **/
    public boolean CheckAllCreations(){
        List<Calendar> Calendars = calendarSystem.GetAllCalendars();
        if (Calendars.isEmpty()) {
            System.out.println("Oops, there are no calendars in the system!");
            return false; }

        else{
            return true;
        }
    }

    /** check if the given user has access to the other user's friend only calendars. If the user has access
     * calls the check public friend creation method and only the check public creation otherwise
     * @param Username, a string that represent the username of the current user
     * @param OtherUsername, a string that represent the username of the creator the user want to view
     * @return returns false if the user is frozen or has no creation and true otherwise **/
    public boolean CheckOtherCreation(String Username, String OtherUsername){
        if (userSystem.getUser(OtherUsername).getFriendList().contains(Username)) {
            return CheckPublicFriendCreation(OtherUsername);
        }
        else {return CheckPublicCreation(OtherUsername);}
    }

    /** This method handles the add editor process by first checking what type of calendar the given calendar is
     * notice that we can not add editor for private and friend only calendars since the editing access is only
     * granted to specific group of people like friends and creators. if the calendar is public the method asks
     * the editor's name the user want to add and returns it
     * @param x, a scanner object that gets input from user
     * @param calendar, a calendar object of the current object we are viewing
     * @param userSystem, a controller that handles user and templates
     * @return returns the string that represent the username of the editor we want to add **/
    public String GetEditor(Scanner x, Calendar calendar, UserSystem userSystem) {
        while (true) {
            if (calendar.getType().equals("0")) {
                System.out.println("Oops you selected a private calendar, Only you can be able to edit this!");
                return null;
            }
            else if (calendar.getType().equals("2")) {
                System.out.println("Oops you selected a Friend only calendar, editing access are given to friends " +
                        "of creator");
                return null;
            }
            else {
            System.out.println("What is the username of the Editor you want to add");
            String userName = x.nextLine();
            if (!(userSystem.getUser(userName) == null)) {
                return userName;
            } else {
                System.out.println("Username is not in the system please try again!");
            }
        }}
    }

    /** This method first checks whether the user provided has access to edit the calendar and then calls the
     * get editor method to get the username of editor to add. Then it calls add editor method in calendar
     * system to add the editor
     *  @param x, a scanner object that gets input from user
     *  @param calendar, a calendar object of the current object we are viewing
     *  @param userName , string that represent the username of the current user  **/
    public void CheckAddEditor(Scanner x, Calendar calendar, String userName){
        if (CheckAccess(calendar, userName)) {
            String OtherUsername = GetEditor(x, calendar, userSystem);
            if (OtherUsername != null) {
                calendarSystem.addEditor(calendar.getCalendarID(), OtherUsername);} }
    }

    /** This method first prints out all the calendars inside the program and then allows admin user to choose one
     * by inputting its id. If the id is invalid, the method asks the user to enter again.
     *  @param x, a scanner object that gets input from user
     *  @return returns the id of calendar the admin user chose**/
    public int GetAllCalendars(Scanner x) {
        while (true) {
        List<Calendar> Calendars = calendarSystem.GetAllCalendars();
        System.out.println("this is the title and the id of all the calendars in the system: ");
        calendarSystem.PrintTitlesOfCalendars(Calendars);
        System.out.println("Which Calendar do you want to select? please input its id");
        String answer = x.nextLine();
        try {
            return Integer.parseInt(answer);
        } catch (java.lang.NumberFormatException e) {
            System.out.println("the id is invalid! please choose the calendar again.");
        }
        }
    }

    /** This method first prints out the calendars that are being created by the user and then allow user to choose one
     * by inputting its id. If the id is invalid, the method asks the user to enter again.
     *  @param x, a scanner object that gets input from user
     *  @return returns the id of calendar the user chose**/
    public int SelectOwnCalendar(Scanner x, String Username) {
        while (true) {
            List<Calendar> Calendars = calendarSystem.getCalendarsByUsername(Username);
            System.out.println("this is the title and the id of the calendars you created: ");
            calendarSystem.PrintTitlesOfCalendars(Calendars);

            System.out.println("Which Calendar do you want to select? please input its id");
            String answer = x.nextLine();
            try {
                return Integer.parseInt(answer);
            } catch (java.lang.NumberFormatException e) {
                System.out.println("the id is invalid! please choose your calendar again.");
            }
        }
    }

    /** This method checks whether the user has access of editing this calendar by first checking if the user is an admin
     * and if the selected calendar is friend only, check if the user is in the friend list of the creator of calendar.
     * It checks if the user is an editor of the calendar otherwise.(since creator is the only editor
     * in private calendars)
     *  @param calendar, a calendar object of the current object we are viewing
     *  @param Username , string that represent the username of the current user
     *  @return returns true if the user has access and false otherwise **/
    public boolean CheckAccess(Calendar calendar, String Username){
        if (userSystem.getUser(Username).getIsAdmin()) {
            return true;
        }
        else if (calendar.getType().equals("2")) {
            TrialUser creator = userSystem.getUser(calendar.getUserName());
            if (creator.getFriendList().contains(Username)){
                return true;
            }
            else {
                System.out.println("you do not have access to edit this calendar");
                return false;
            }
        }
        else { if (calendar.getEditorList().contains(Username)) {
            return true; }
            else {
                System.out.println("you do not have access to edit this calendar");
                return false;
            }
        }
    }

    /** This method checks if the user has made any changes on the calendar
     * @param calendar, a calendar object of the current object we are viewing
     * @return return true if the user has made any changes and false otherwise **/
    public boolean CheckChange(Calendar calendar){
        if (calendar.getRecentChange().equals("")){
            System.out.println("you did not make any change on this calendar!");
            return false;
        }
        else {
            return true;
        }
    }

    /** This method checks if the user is a friend of the creator of calendar
     * @param UserName, a string representing the username of the current user
     * @param OtherUsername, a string representing the username of the creator of calendar
     * @return return true if the user is a friend of the other user and false otherwise **/
    public boolean CheckFriendAccess(String UserName, String OtherUsername) {
        TrialUser user = userSystem.getUser(OtherUsername);
        return user.getFriendList().contains(UserName);
    }
}