package TextUI;

import Activity.Activity;
import Calendar.Calendar;
import Calendar.CalendarSystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** a text UI class that displays and managers activities  **/
public class ActivityManagementSystem {

    /** This method gets activities in the calendar by inputting a date *
     * @param x, A scanner object that gets the user input
     * @param calendarSystem, A controller class that controls activities and calendars
     * @param calendar, A Calendar representing the calendar that we are checking the dates to find
     * activities for
     * @return A list of activities on the specific date from the calendar **/
    public List<Activity> GetActivityByDate(Scanner x, CalendarSystem calendarSystem,
                                                        Calendar calendar) {
        int id = calendar.getCalendarID();
        while (true) {
            System.out.println("Which day's activities do you want to view? input weekday like Monday, Tuesday, " +
                    "Wednesday for a TimeTable" + " input specific Date for a DateCalendar/MonthlyCalendar" + "\n" +
                    "in the form dd/mm/yyyy");
            String day = x.nextLine();

            if (!calendar.getDates().contains(day)){
                System.out.println("Invalid date! please try again!");
            }
            else{
                 return calendarSystem.getActivityListOfDate(id, day); }
            }
        }

    /** This method gets all the activities in the calendar
     * @param calendarSystem, A controller class that controls activities and calendars
     * @param calendar, A Calendar representing the calendar that we are getting the activities from
     * @return A list of activities from the calendar **/
    public List<Activity> GetAllActivities( CalendarSystem calendarSystem,
                                           Calendar calendar) {
            int id = calendar.getCalendarID();
            return calendarSystem.getAllActivities(id);
    }

    /** This method prints out all the activities in the list
     * @param Activities, A list of activities on a specific calendar
     * @param c, A Calendar representing the calendar where the list of activities we are
     * printing are from **/
    public void PrintActivities(List<Activity> Activities, Calendar c){
        if (Activities.isEmpty()) {
            System.out.println("there is no activities for the option you selected!");
        }
        else{
            System.out.println("Here are the activities on this calendar: ");
        for (Activity a: Activities ){
            System.out.println(a.toString() + "\n" + "This activity is on " + c.getDateByActivity(a) + "\n");
        }
        }
    }

    /** Add an activity to the calendar. For now, only add activities to date calendars within the current year and
     we assume the time duration should range from 00:00 - 24:00
     * @param x, A scanner object that gets the user input
     * @param calendarSystem, A controller class that controls activities and calendars
     * @param calendar, A Calendar representing the calendar that the user is adding activities to
     * @param Username, A string representing the username of the user adding the activities to
     * the calendar **/
    public void AddActivity(Scanner x, CalendarSystem calendarSystem, Calendar calendar, String Username){
        System.out.println("During the activity creation process, you can press r to cancel");
        System.out.println("What is the name of your activity?");
        String name = x.nextLine();
        if (CheckCancel(name)) {
            return; }
        String date = AskActivityDate(x, calendar);
        if (CheckCancel(date)){
            return; }
        System.out.println("Which time period do you want to add your activity " +
                "(type in start time/end time e.g. 08:00/10:00)?");
        String TimeDuration = x.nextLine();
        if (CheckCancel(TimeDuration)) {
            return; }
        System.out.println("Please include Some description of this activity");
        String Description = x.nextLine();
        if (CheckCancel(Description)){
            return; }
        System.out.println("what category does the activity belong to? Food, study .. etc");
        String Category = x.nextLine();
        if (CheckCancel(Category)) {
            return; }
        String Importance = AskImportance(x);
        if (CheckCancel(Importance)){
            return; }
        Activity activity = calendarSystem.CreateActivity(name, TimeDuration, Description, Category,
                Importance, date);
        calendar.addActivity(date, activity, Username, LocalDate.now().toString());
        System.out.println("Activity created successfully!");
    }

    /** This method asks the user what date will the activity be added to
     * @param x, A scanner object that gets the user input
     * @param calendar, A Calendar representing the calendar that we are adding the activity to
     * @return A string representing the date the activity was added to **/
    public String AskActivityDate(Scanner x, Calendar calendar){
        while (true){
            System.out.println("Which day do you want to add an activity type in Monday - Sunday for template or " +
                    "specific date formatted dd/mm/yyyy for DateCalendar");
            String date = x.nextLine();
            if (!(calendar.getDates().contains(date) | date.equals("r"))) {
                System.out.println("invalid date! please input your answer again");
            }
            else {
                return date;
            }
        }
    }

    /** This method asks the user what the importance of the activity is
     * @param x, A scanner object that gets the user input
     * @return A string that says either yes or no representing if the activity is important **/
    public String AskImportance(Scanner x){
        while (true){
            System.out.println("Do you think this activity is important? (Yes/No)");
            String importance = x.nextLine();
            if (!(importance.equalsIgnoreCase("Yes")|(importance.equalsIgnoreCase("No")) |
                    importance.equals("r"))) {
                System.out.println("invalid input! please input your answer again");}
            else {
                return importance;
            }
        }
    }

    /** This method checks how the user wants to view activities
     * @param x, A scanner object that gets the user input
     * @return A string representing if the user wants to view any activities, activities by category,
     * or activities by importance **/
    public String checkIfCategoryImportance(Scanner x){
        while(true) {
            System.out.println("Do you want to view any activities, activities of a specific category or " +
                    "activities by importance" + "\n" + "press 0 to view any activities, press 1 to view by category " +
                    "or press 2 to view by importance");
            String answer = x.nextLine();
            if (!(answer.equals("0") | answer.equals("1") | answer.equals("2"))) {
                System.out.println("invalid button");
            } else {
                return answer;
            }
        }
    }

    /** In this method an activity is being chosen from a calendar
     * @param x, A scanner object that gets the user input
     * @param calendarSystem, A controller class that controls activities and calendars
     * @param calendar, A Calendar representing the calendar where the activity is being chosen from
     * @return An Activity representing the activity being chosen **/
    public Activity ChooseActivity(Scanner x, CalendarSystem calendarSystem, Calendar calendar){
        while (true) {
        List<Activity> Activities = GetAllActivities(calendarSystem, calendar);
        PrintActivities(Activities, calendar);
        if (Activities.isEmpty()) {
            return null;
        }
        System.out.println("which activity do you want to select? Please input its id");
        String id = x.nextLine();
        try {
            try {return calendarSystem.getActivityObject(Integer.parseInt(id));
            }
            catch (NoSuchElementException e) {
                System.out.println("the id is invalid! please choose your calendar again.");
            }
        } catch (java.lang.NumberFormatException e) {
            System.out.println("the id is invalid! please choose your calendar again.");
        } }
    }

    /** This method deletes an activity from a calendar
     * @param activity, An Activity that represents the activity the user is deleting
     * @param calendar, A Calendar representing the calendar that the user is deleting activities from
     * @param Username, A string representing the username of the user who is deleting the activity **/
    public void DeleteActivity(Activity activity, Calendar calendar, String Username) {
          calendar.removeActivity(activity.getDate(), activity, Username, LocalDate.now().toString());
          System.out.println("activity deleted!");
    }

    /** This method gets the activities from a certain category
     * @param x, A scanner object that gets the user input
     * @param Activities, A list of activities in all categories
     * @return A list of activities in the category the user chose **/
    public List<Activity> getActivityOfCategory(Scanner x, List<Activity> Activities){
        System.out.println("Please enter the category: ");
        String category = x.nextLine();
        List<Activity> res = new ArrayList<>();
        for (Activity ac:Activities){
            if (ac.getCategory().equalsIgnoreCase(category)){
                res.add(ac);
            }
        }
        return res;
    }

    /** This method gets the activities from an importance
     * @param x, A scanner object that gets the user input
     * @param Activities, A list of both important and not important activities
     * @return A list of activities in the importance the user chose **/
    public List<Activity> getActivityOfImportance(Scanner x, List<Activity> Activities){
        while (true) {
        System.out.println("Do you want to view activities that are important or not important. Enter 0 for important" +
                " activities or enter 1 for not important");
        String importance = x.nextLine();
        List<Activity> res = new ArrayList<>();
        if (importance.equals("0")) {
            for (Activity ac:Activities){
                if (ac.getImportance().equalsIgnoreCase("Yes")){
                res.add(ac); } }
            return res;
        }
        else if (importance.equals("1")) {
            for (Activity ac:Activities){
                if (ac.getImportance().equalsIgnoreCase("No")){
                    res.add(ac); } }
            return res;
        }
        else { System.out.println("invalid input! please try again!");}
    }  }

    /** This method checks if the user wants to cancel the activity creation process
     * @param input, A string representing the user's inputted values
     * @return A value representing whether the user wants to cancel the creation process;
     * True if the user wants to cancel, False if the user does not want to cancel **/
    public boolean CheckCancel(String input) {
        return input.equals("r");
    }
}
