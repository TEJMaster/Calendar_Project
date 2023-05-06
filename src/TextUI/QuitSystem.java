package TextUI;

import Calendar.CalendarSystem;
import User.TrialUser;
import User.UserSystem;
import Data.InformationSaver;

import java.util.Scanner;

/** a controller class that handles the save and quit process when a user logs out. It stores saver to save the
 * information needed and both user and calendar system  **/
public class QuitSystem {
    private final InformationSaver saver;
    private final UserSystem userSystem;
    private final CalendarSystem calendarSystem;
    /** constructs a quit system using the input calendar system , user system and information saver **/
    public QuitSystem(CalendarSystem calendarSystem, UserSystem userSystem, InformationSaver saver){
        this.userSystem = userSystem;
        this.saver = saver;
        this.calendarSystem = calendarSystem;
    }

    /** This method saves all the information in the program by first checking if all the activity are inside a calendar
     * and then calls the update method in information saver **/
    public void saveAndQuit(){
        calendarSystem.CheckAndRemoveActivity();
        saver.update();
        System.exit(0);
    }

    /** This method will check if a user is a trial user by checking if the password is null. If the user is not
     *  trial user, then the method will give the user option to either save and quit or not save.
     * @param x a scanner object that gets the input of user
     * @param username a String that represent the username of the current user of the system
     * **/
    public void ExitCheck(Scanner x, String username){
        TrialUser User = userSystem.getUser(username);
        if (User.passWordCheck(null)) {
            saver.updateUser();
            System.exit(0);
        }
        else{
            System.out.println("would you like to save your data? Press 0 to save and exit or Press any button to quit " +
                "without saving");

            String answer = x.nextLine();
            if (answer.equals("0")){
                saveAndQuit();
            }
            else{
                System.exit(0);
            }
        }
    }
}