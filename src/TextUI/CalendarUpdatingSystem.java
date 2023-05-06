package TextUI;
import Calendar.Calendar;
import Calendar.CalendarSystem;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/** A text UI class that makes changes to existing calendars, and it stores a calendar system. **/
public class CalendarUpdatingSystem {
    private final CalendarSystem calendarSystem;
    /** constructs the calendar updating system using a calendar system. **/
    public CalendarUpdatingSystem(CalendarSystem calendarSystem) {
        this.calendarSystem = calendarSystem;
    }

    /** This method changes the type of the calendar by first asking which new type does the user want the
     * calendar to be and then change the type of calendar by using the set type method in calendar
     * @param x, a scanner object that gets the input of user
     * @param calendar, a calendar object of the calendar that we want to change type**/
    public void ChangeCalendarType(Scanner x, Calendar calendar) {
        while (true) {
            System.out.println("Do you wish this to be private, public or Friends only? " +
                    "\n" + "(please enter 0 for private, 1 for public and 2 for Friends only)");
            String type = x.nextLine();
            if (!(type.equals("0") | type.equals("1") | type.equals("2"))) {
                System.out.println("invalid input! please try again");}
            else if (type.equals("0")) {
                calendar.setType(type);
                System.out.println("Calendar " + calendar.getCalendarID()+ " has been set to private");
                return;
            }
            else if (type.equals("1")){
                calendar.setType(type);
                System.out.println("Calendar " + calendar.getCalendarID() + " has been set to public");
                return;
            }
            else {
                calendar.setType(type);
                System.out.println("Calendar " + calendar.getCalendarID() + " has been set to Friend Only");
                return;
            }
        }}

    /** This method reverse the type of calendar to the last type of calendar by using the last type attribute
     * in calendar
     * @param calendar, a calendar object of the calendar that we want to reverse type **/
    public void ReverseCalendarType( Calendar calendar) {
            String lastType = calendar.getLastType();
            if (lastType.equals("0")) {
                calendar.setType(lastType);
                System.out.println("Calendar " + calendar.getCalendarID()+ " has been reversed to private");
            }
            else if (lastType.equals("1")){
                calendar.setType(lastType);
                System.out.println("Calendar " + calendar.getCalendarID() + " has been reversed to public");
            }
            else {
                calendar.setType(lastType);
                System.out.println("Calendar " + calendar.getCalendarID() + " has been reversed to Friend Only");
            }
        }

    /** This method delete the input calendar by using delete calendar method in calendar manager
     * @param calendar, a calendar object of the calendar that we want delete **/
    public void DeleteCalendar(Calendar calendar){
        int id = calendar.getCalendarID();
        calendarSystem.getCalendarManager().deleteCalendar(id);
        System.out.println("calendar deleted successfully!");
    }

    /** This method undo a deleted calendar for a user by gets the deleted calendar list in the calendar system and
     *  allow user to undo a deleted calendars by calling the select deleted calendar method to
     *  let the user choose which deleted calendar to add back to the system
     * @param x, a scanner object used to get the input from user **/
    public void UndoDeletion(Scanner x){
        while (true) {
            List<Calendar> DeletedCalendar = calendarSystem.getCalendarManager().getDeletedCalendarList();
            if (DeletedCalendar.isEmpty()) {
                System.out.println("there are no deleted calendars");
                return;
            }
            else {
                int id = SelectDeletedCalendars(x, DeletedCalendar);
                if (!(calendarSystem.getDeletedCalendar(id) == null)) {
                    Calendar calendar = calendarSystem.getDeletedCalendar(id);
                    calendarSystem.getCalendarManager().addCalendar(calendar);
                    System.out.println("un deleted selected calendar!");
                    return;
                    }
                else{System.out.println("invalid input! please try again");} } }
    }

    /** This method allow user to select a deleted calendar from the input list of deleted calendars
     * if the id inputted can not be converted to int. The while loop will allow user to enter their
     * input again
     * @param x, a scanner object used to get the input from user
     * @param yourDeletedCalendar, a list of deleted calendar in the program **/
    public int SelectDeletedCalendars(Scanner x, List<Calendar> yourDeletedCalendar){
        while (true) {
            System.out.println("this is the title and the id of the calendars you deleted: ");
            calendarSystem.PrintTitlesOfCalendars(yourDeletedCalendar);
            System.out.println("Which Calendar do you want to select? please input its id");
            String id = x.nextLine();
            try {
                return Integer.parseInt(id);
            } catch (java.lang.NumberFormatException e) {
                System.out.println("the id is invalid! please choose your calendar again.");
            }
        }
    }

    /** This method allow user to undo recent change for the activities in the calendar. Either added or deleted a
     * calendar. The method first call print change in calendar system that print about the most recent change and
     * ask the user whether they want to reverse the change
     * @param x, a scanner object used to get the input from user
     * @param calendar , the calendar object of the calendar user is currently viewing
     * @param calendarSystem, the controller that handles calendar and activities
     * @param Username a string that stores the username of the current user **/
    public void UndoChange(Scanner x, CalendarSystem calendarSystem, Calendar calendar, String Username) {
        while (true){
            calendarSystem.printChanges(calendar.getCalendarID());
            System.out.println("Do you want to reverse this Activity change? Yes/No");
            String answer = x.nextLine();
            if (answer.equals("Yes") | answer.equals("yes")) {
                calendarSystem.UndoChanges(calendar, Username, LocalDate.now().toString());
                System.out.println("changes reversed successfully");
                return;
            }
            else if (answer.equals("No") | answer.equals("no")){
                return;
            }
            else{System.out.println("invalid input! please try again");}
        }
    }
}