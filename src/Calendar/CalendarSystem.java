package Calendar;

import Activity.Activity;
import Activity.ActivityManager;
import Data.InformationReader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * A controller class that manages the CalendarManager and ActivityManager class.
 * It contains methods for creating new activities and calendars,
 * and also getter and setter methods for calendar and activity.
 * @author Group0031
 */
public class CalendarSystem {
    private final CalendarManager calendarManager;
    private final ActivityManager activityManager;
    /**
     * Creates CalendarManager and ActivityManager based on the data stored in the csv files.
     * @param informationReader a class that helps to read calendars and activities from data stored
     */

    public CalendarSystem(InformationReader informationReader){
        this.calendarManager = new CalendarManager(informationReader);
        this.activityManager = new ActivityManager(informationReader);
    }

    /**
     * Gets A List of Activity on a given date on a calendar with the same ID as the given calendar ID.
     * @param calendarID An int representing the calendar ID.
     * @param date A String representing the date.
     * @return A List of Activity on a given date on a calendar with the same ID as the given calendar ID.
     */
    public List<Activity> getActivityListOfDate(int calendarID, String date) {
        List<Integer> activityIDList = getActivityIDList(calendarID, date);
        List<Activity> activityList = new ArrayList<>();
        for (Integer integer : activityIDList) {
            activityList.add(getActivityObject(integer));
        }
        return activityList;
    }

    /**
     * Gets A List of Integer representing all the activity ids on a given date on a calendar with the same id as the
     * given calendar id.
     * @param calendarID An int representing the given calendar id.
     * @param date A String representing the given date.
     * @return A List of Integer representing all the activity ids on a given date on a calendar with the same id as the
     * given calendar id.
     */
    public List<Integer> getActivityIDList(int calendarID, String date) {
        return calendarManager.getAllActivityIdOfDate(calendarID, date);
    }

    /**
     * Gets an Activity given the id the activity.
     * @param activityID An int representing the given activity id.
     * @return An Activity given the activity id.
     */
    public Activity getActivityObject(int activityID){
        return activityManager.getActivity(activityID);
    }

    /**
     * Creates a dateCalendar given the username of the creator and inputs from the dateCalendar template entered by
     * the user, and adds the calendar to the program.
     * @param userName A String representing the username of the creator.
     * @param input A List of String representing the input from the date calendar template entered by the user.
     */
    public void createDateCalendar(String userName, List<String> input) {
        // creates a new empty date calendar and add it to calendarManager
        String title = input.get(0);
        HashMap<String, List<Integer>> DateToID = new HashMap<>();
        int year = DisplayYear();
        int j;
        for(j = 0; j < 12; j++)
        {   String month;
            if (j < 9){
                month = "0" + (j + 1);
            }
            else{
                month = String.valueOf(j + 1);
            }
            int numDays = GetNumberOfDays(j + 1);

            AddMonth(DateToID, year, numDays, month);
        }

        String Type = input.get(1);
        calendarManager.addCalendar("DATECALENDAR", title, Type, userName, DateToID);
    }

    /**
     * Creates a monthlyCalendar given the username of the creator and inputs from the monthlyCalendar template entered
     * by the user, and adds the calendar to the program.
     * @param userName A String representing the username of the creator.
     * @param input A List of String representing the input from the monthly calendar template entered by the user.
     */
    public void createMonthlyCalendar(String userName, List<String> input) {
        // creates a new empty monthly calendar and add it to calendarManager

        int month = Integer.parseInt(input.get(0));
        HashMap<String, List<Integer>> DateToID = new HashMap<>();
        int year = DisplayYear();
        int numDays = GetNumberOfDays(month);

        String monthStored;
        if (month < 10){
            monthStored = "0" + month;
        }
        else{
            monthStored = String.valueOf(month);
        }
        AddMonth(DateToID, year, numDays, monthStored);
        String title = input.get(1);

        String Type = input.get(2);
        calendarManager.addCalendar("MONTHLYCALENDAR", title, Type, userName, DateToID);
    }

    /**
     * add all the days of the month provided into the date to ID Map as keys
     * @param dateToID A Hashmap that has the key as date strings and values as list of activity ids .
     * @param year an int representing the current year
     * @param numDays, int representing number of days in the month.
     * @param monthStored, a string representing which month to add to the date id
     */
    private void AddMonth(HashMap<String, List<Integer>> dateToID, int year, int numDays, String monthStored) {
        int i;
        for (i = 0; i < numDays; i++) {
            String date;
            if (i < 9) {
                int storedDate = i + 1;
                date = "0" + storedDate + "/" + monthStored + "/" + year;
            } else {
                date = i + 1 + "/" + monthStored + "/" + year;
            }
            dateToID.put(date, new ArrayList<>());
        }
    }

    /**
     * Creates a timetable given the username of the creator and inputs from the timetable template entered by
     * the user, and adds the calendar to the program.
     * @param userName A String representing the username of the creator.
     * @param input A List of String representing the input from the timetable template entered by the user.
     */
    public void createTimetable(String userName, List<String> input) {
        // creates a new empty timetable using the timetable template and add it to calendarManager
        String title = input.get(0);
        boolean weekends = input.get(1).equals("Yes");
        HashMap<String, List<Integer>> DateToID = new HashMap<String, List<Integer>>(){{
            put("Monday", new ArrayList<>());
            put("Tuesday", new ArrayList<>());
            put("Wednesday", new ArrayList<>());
            put("Thursday", new ArrayList<>());
            put("Friday", new ArrayList<>());
        }};
        if (weekends) {
            DateToID.put("Saturday", new ArrayList<>());
            DateToID.put("Sunday", new ArrayList<>());
        }

        String Type = input.get(2);

        calendarManager.addCalendar("TIMETABLE", title, Type, userName, DateToID);
    }

    /**
     * Get all the calendars stored in the program.
     * @return A List of Calendar representing all the calendars stored in the program.
     */
    public List<Calendar> GetAllCalendars() {
        return this.calendarManager.GetAllCalendars();
    }

    /**
     * Print titles of calendars given a list of calendars.
     * @param input A List of Calendar representing the given list of calendars.
     */
    public void PrintTitlesOfCalendars(List<Calendar> input){
        for (Calendar calendar: input){
            System.out.println("Title: " + calendar.getCalendarTitle() + "  ID number: " + calendar.getCalendarID());
        }
    }

    /**
     * Get calendar given the id of the calendar.
     * @param calendarID An int representing the given calendar id.
     * @return the calendar with the same id as the given calendar id.
     */
    public Calendar getCalendar(int calendarID){
        return this.calendarManager.getCalendar(calendarID);
    }

    /**
     * Get deleted calendar given the id of the deleted calendar.
     * @param calendarID An int representing the given deleted calendar id.
     * @return the deleted calendar with the same id as the given calendar id.
     */
    public Calendar getDeletedCalendar(int calendarID){
        return this.calendarManager.getDeletedCalendar(calendarID);
    }

    /**
     * Gets all the calendars created by a user given the username of the user.
     * @param userName A String representing the username of the user we wish to look for his/her calendars.
     * @return List of Calendars that the user with given username creates.
     */
    public  List<Calendar> getCalendarsByUsername(String userName){
        return this.calendarManager.getCalendarsByUsername(userName);
    }

    /** This method creates and add a new activity to the activity list using the information given
     * @param Name, A string that represent the name of the activity
     * @param TimeDuration, A String representing the time duration of this activity
     * @param Description, A String that describes this activity
     * @param Category, A String describes the category of the activity
     * @param Importance, A String that represent whether the activity is important
     * @param date, A String represent which date is the activity on
     * @return returns an activity object created from this information **/
    public Activity CreateActivity(String Name, String TimeDuration, String Description,
                               String Category, String Importance, String date){
        return activityManager.CreateActivity(Name, TimeDuration, Description, Category, Importance, date);
    }

    /**
     * Get all the activities from a calendar given a calendar id.
     * @param id An int representing the given calendar ID.
     * @return All the activities from a calendar has the same id as the given calendar id.
     */
    public List<Activity> getAllActivities(int id) {
        List<Activity> res;
        String type = this.calendarManager.getCalendarType(id);
        if (type.equals("DateCalendar")){
            res = this.calendarManager.getAllActivitiesDateCalendar(id, this.activityManager);
            }
        else if (type.equals("TimeTable")){
            res = this.calendarManager.getAllActivitiesTimeTable(id, this.activityManager);
        }
        else {
            res = this.calendarManager.getAllActivitiesMonthlyCalendar(id, this.activityManager);
        }
        return res;
    }

    /**
     * Print the recent change for a calendar has the same id as the given calendar id.
     * @param calendarID An int representing the given calendar id.
     */
    public void printChanges(int calendarID){
        Calendar calendar = getCalendar(calendarID);
        if (calendar == null){
            System.out.println("the id is invalid! please input your id again.");
        }
        else{
                String change = calendar.getRecentChange();
                int activityId = Integer.parseInt(change.substring(1));
                Activity activity = activityManager.getActivity(activityId);
                if (change.startsWith("+")){
                    System.out.println("in the most recent change you added activity " + activity.getName());
                }

                else{
                    System.out.println("in the most recent change you deleted activity " + activity.getName());
                }
            }

        }

    /**
     * Undo the recent change in activity by using the remove and add activity method
     * in a calendar by inputting the username of the change-maker and the time the change is made.
     * @param calendar A Calendar representing the calendar is being changed
     * @param Username A String representing the username of the change-maker
     * @param time A String representing the time when the change is made.
     */
    public void UndoChanges(Calendar calendar, String Username, String time){
        String change = calendar.getRecentChange();
        int activityId = Integer.parseInt(change.substring(1));
        Activity activity = activityManager.getActivity(activityId);
        String date = activity.getDate();
        if (change.startsWith("+")) {
            calendar.removeActivity(date, activity, Username, time);
        }
        else{
            calendar.addActivity(date, activity, Username, time);
        }
    }

    /** this method calls the get public calendar by username method in calendar manager to get all
     * the public calendar the user created
     * @param userName, A string that stores the username of user
     * @return returns the list of public calendar user created
     */
    public  List<Calendar> getPublicCalendarsByUsername(String userName){
        return this.calendarManager.getPublicCalendarsByUsername(userName);
    }

    /** this method calls the get friend public calendar by username method in calendar manager to get all
     * the public calendar and friend only calendar the user created
     * @param userName, A string that stores the username of user
     * @return returns the list of public and friend only calendar the user created
     */
    public  List<Calendar> getFriendPublicByUsername(String userName){
        return this.calendarManager.getFriendPublicCalendarsByUsername(userName);
    }

    /** this method checks if one of the calendar in the calendar system contains the activity provided
     * @param activity, An activity object that we are going to check
     * @return returns true if the activity is in the calendar system and false otherwise.
     */
    public boolean CheckActivity(Activity activity){
        for (Calendar calendar: calendarManager.GetAllCalendars()) {
            List<Activity> Activities = getAllActivities(calendar.getCalendarID());
            if (Activities.contains(activity)) {
                return true;
            }
        }
        return false;
    }

    /** this method checks if all the activity in the activity manager are in one of the calendars in calendar manager
     * and remove each deleted activities (we use this method because in order to get the activity info to
     * undo, we only delete the activity inside calendar but not in activity manager. This method gives the final
     * check of activities which is used when a user logs out.
     */
    public void CheckAndRemoveActivity() {
        List<Activity> Activities = activityManager.getActivityList();
        List<Activity> RemoveActivities = new ArrayList<>();
        for (Activity activity:Activities) {
            if (!CheckActivity(activity)) {
                RemoveActivities.add(activity);
            }
        }

        for (Activity activity:RemoveActivities) {
            activityManager.deleteActivity(activity.getActivityId());
        }
    }

    /** a getter of the calendar manager object in calendar system **/
    public CalendarManager getCalendarManager(){
        return this.calendarManager;
    }

    /** a getter of the activity manager object in calendar system **/
    public ActivityManager getActivityManager(){
        return this.activityManager;
    }

    /** this method calls the get calendar type method in calendar manager
     * @param id , the calendar id of the calendar we want to check
     * @return a String representing which type calendar it is **/
    public String GetCalendarType(int id){return this.calendarManager.getCalendarType(id);}

    /** this method add an editor to the calendar by calling the add editor method in calendar manager
     * @param calendarID, the calendar id of the calendar we want to add editor to
     * @param username, a string of the username of the editor**/
    public void addEditor(int calendarID, String username){
        this.calendarManager.addEditor(calendarID, username);
    }

    /** this method is used to get the current year as int, it is used to construct a date calendar
     * @return returns an int representing which year this is  **/
    public int DisplayYear(){
        LocalDate time = LocalDate.now();
        return time.getYear();
    }

    /** this method is used to get the number of days in the given month
     * @param month, an int representing which month this is
     * @return returns the number of days in the month as int**/
    public int GetNumberOfDays(int month) {
        int year = DisplayYear();
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                if (year % 4 == 0 & year % 100 != 0) {
                    return 29;
                } else {
                    return 28;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;

            default:
                return 0;
        }
    }
}
