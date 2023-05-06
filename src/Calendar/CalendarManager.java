package Calendar;

import Activity.Activity;
import Activity.ActivityManager;
import Data.InformationReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * A use case class that manages the calendars.
 * It stores the calendars based on their type (dateCalendar, timeTable, monthlyCalendar, deletedCalendar).
 * Getter and setter methods for calendar.
 * @author Group0031
 */

public class CalendarManager {
    private final List<Calendar> dateCalendarList;
    private final List<Calendar> timeTableList;
    private final List<Calendar> monthlyCalendarList;
    private final List<Calendar> deletedCalendar = new ArrayList<>();

    /**
     * Creates dateCalendarList, TimeTableList, and monthlyCalendarList by injecting the gateway and calling its read
     * calendar method
     * @param informationReader, a gateway class that reads csv file
     */
    public CalendarManager(InformationReader informationReader)
    {
        List<List<Calendar>> Calendars = informationReader.ReadCalendar();
        this.dateCalendarList = Calendars.get(0);
        this.timeTableList =  Calendars.get(1);
        this.monthlyCalendarList =  Calendars.get(2);
    }

    /**
     * Getting a unique calendar ID based on the numbers of calendars stored in the system. For example, if there are
     * 4 calendars stored in the system, the id for the new calendar will be 4 + 1 = 5. This makes sure the ID for each
     * calendar created is unique.
     * @return An int representing the new ID for a calendar.
     */
    private int setCalendarID(){ return dateCalendarList.size() + timeTableList.size() + monthlyCalendarList.size(); }

    /**
     * Adding a calendar given the calendar type, title, whether it's public or private or friend-only,
     * username of the creator and activities on dates. If the calendar is a date calendar, it's added to the
     * dateCalendarList. If the calendar is a time table, it's added to the timeTableList.
     * Else it's added to the monthlyCalendarList.
     * @param calendarType A String representing the type of this calendar.
     * @param title A String representing the title of this calendar.
     * @param type A String representing whether the calendar is public, private, or friend-only.
     * @param userName A String representing the username of the creator of the calendar.
     * @param dateToID A hashmap having dates as keys and list of activity ids on the date as values.
     */
    public void addCalendar(String calendarType, String title, String type, String userName,
                       HashMap<String, List<Integer>> dateToID){
        List<String> EditorList = new ArrayList<>();
        CalendarFactory cf = new CalendarFactory();
        Calendar c = cf.getCalendar(calendarType, title, type, userName, dateToID, EditorList,
                setCalendarID());
        if (calendarType.equalsIgnoreCase("DATECALENDAR")){
            dateCalendarList.add(c);
        }
        else if (calendarType.equalsIgnoreCase("TIMETABLE")){
            timeTableList.add(c);
        }
        else{
            monthlyCalendarList.add(c);
        }
    }

    /**
     * Adds a calendar given a calendar. If the calendar is a date calendar, it's added to dateCalendarList. If it's a
     * time table, it's added to timeTableList. Else, it's added to monthlyCalendarList.
     * @param calendar A Calendar representing the calendar that is being added.
     */
    public void addCalendar(Calendar calendar) {
        if (calendar instanceof DateCalendar) {
            dateCalendarList.add(calendar);
        }
        else if (calendar instanceof TimeTable) {
            timeTableList.add(calendar);
        }
        else { monthlyCalendarList.add(calendar);}
    }

    /**
     * Delete a calendar given the ID of the calendar.
     * @param calendarID An int representing the ID of the calendar that is needed to be deleted. The calendar is
     * removed from dateCalendarList if it's a date calendar, removed from timeTableList if it's a time table. else
     * removed from monthlyCalendarList.
     */
    public void deleteCalendar(int calendarID) {
        Calendar calendar = getCalendar(calendarID);
        deletedCalendar.add(calendar);
        if (calendar instanceof DateCalendar & calendar.getDates().size() > 32) {
            dateCalendarList.remove(calendar);
        }
        else if (calendar instanceof TimeTable) {
            timeTableList.remove(calendar);
        }
        else { monthlyCalendarList.remove(calendar);}
    }

    /**
     * Gets the Calendar given the ID of the calendar.
     * @param calendarID An int representing the ID of the calendar we want to look for.
     * @return A Calendar has the same id as the given id. Returns null if all the calendars stored in
     * dateCalendarList, timeTableList, and monthlyCalendarList do not have the given ID.
     */
    public Calendar getCalendar(int calendarID){
        for (Calendar c: dateCalendarList){
            if (c.getCalendarID() == calendarID) {
                return c;
            }
        }
        for (Calendar c: timeTableList){
            if (c.getCalendarID() == calendarID) {
                return c;
            }
        }
        for (Calendar c: monthlyCalendarList){
            if (c.getCalendarID() == calendarID){
                return c;
            }
        }
        return null;
    }

    /**
     * Gets a deleted calendar given the id of the calendar.
     * @param calendarID An int representing the ID of the deleted calendar we want to look for.
     * @return A deleted Calendar has the same id as the given id. Returns null if all the calendars stored in the
     * deletedCalendar do not have the given id.
     */
    public Calendar getDeletedCalendar(int calendarID){
        for (Calendar c: deletedCalendar) {
            if (c.getCalendarID() == calendarID) {
                return c;
            }
        }
        return null;
    }

    /**
     * Gets all the calendars created by a user given the username of the user.
     * @param userName A String representing the username of the user we wish to look for his/her calendars.
     * @return List of Calendars that the user with given username creates.
     */
    public List<Calendar> getCalendarsByUsername(String userName){
        List<Calendar> UsersCalendars = new ArrayList<>();
        for (Calendar c: dateCalendarList){
            if (c.getUserName().equals(userName)){
                UsersCalendars.add(c);
            }
        }
        for (Calendar c: timeTableList){
            if (c.getUserName().equals(userName)){
                UsersCalendars.add(c);
            }
        }
        for (Calendar c: monthlyCalendarList){
            if(c.getUserName().equals(userName)){
                UsersCalendars.add(c);
            }
        }
        return UsersCalendars;
    }

    /**
     * Gets all the activities IDs on a given date on a calendar which has the given calendar ID.
     * @param calendarID An int representing the ID of the calendar.
     * @param date A String representing the date that we are looking for all the activities on the date.
     * @return A List of Integers representing all the activities IDs on a given date on a calendar which has the given
     * calendar ID.
     */
    public List<Integer> getAllActivityIdOfDate(int calendarID, String date){
        Calendar calendar = getCalendar(calendarID);
        return calendar.getActivityIDs(date);
    }

    /**
     * Gets all the calendars stored in dateCalendarList, timeTableList, and monthlyCalendarList.
     * @return A List of Calendar representing all the calendars stored in dateCalendarList, timeTableList,
     * and monthlyCalendarList.
     */
    public List<Calendar> GetAllCalendars() {
        List<Calendar> Calendars = new ArrayList<>();
        Calendars.addAll(dateCalendarList);
        Calendars.addAll(timeTableList);
        Calendars.addAll(monthlyCalendarList);
        return Calendars;
    }

    /**
     * Adds an editor given the username of the newly added editor on a calendar given calendar ID.
     * @param calendarID An int representing the calendar ID
     * @param username A String representing the username of the newly added editor
     */
    public void addEditor(int calendarID, String username){
        Calendar calendar = getCalendar(calendarID);
        if (calendar == null){
            System.out.println("the id is invalid! please input your id again.");
        }
        else if (calendar.getEditorList().contains(username)){
            System.out.println("this user is already an editor of this calendar!");
        }
        else {
            calendar.addEditor(username);
            System.out.println("editor added successfully");
        }
    }

    /**
     * Gets the type of the calendar given the id of the calendar.
     * @param calendarID An int representing the ID of the calendar.
     * @return A String representing the type of the calendar has the same ID as the given calendar ID. Returns null if
     * all the calendars stored in dateCalendarList, timeTableList, and monthlyCalendarList do not have the same id as
     * the given calendar id.
     */
    public String getCalendarType(int calendarID){
        for (Calendar c: dateCalendarList){
            if (c.getCalendarID() == calendarID) {
                return "DateCalendar";
            }
        }
        for (Calendar c: timeTableList){
            if (c.getCalendarID() == calendarID) {
                return "TimeTable";
            }
        }
        for (Calendar c: monthlyCalendarList){
            if (c.getCalendarID() == calendarID) {
                return "MonthlyCalendar";
            }
        }
        return "";
    }

    /**
     * Gets all the activities on a list of timetable given the calendar ID of the timetable and an activity manager.
     * @param calendarID An int representing the calendar ID of the timetable.
     * @param am An ActivityManager representing the use case class manages the activities.
     * @return A List of Activity representing all the activities on a timetable given the calendar ID of the timetable
     * and an activity manager.
     */
    public List<Activity> getAllActivitiesTimeTable(int calendarID, ActivityManager am){
        return getAllActivities(calendarID, am, timeTableList);
    }

    /**
     * Gets all the activities on a list of dateCalendars given the calendar ID of the date calendar and an activity
     * manager.
     * @param calendarID An int representing the calendar ID of the date calendar.
     * @param am An ActivityManager representing the use case class manages the activities.
     * @return A List of Activity representing all the activities on a date calendar given the calendar ID of the date
     * calendar and an activity manager.
     */
    public List<Activity> getAllActivitiesDateCalendar(int calendarID, ActivityManager am){
        return getAllActivities(calendarID, am, dateCalendarList);
    }

    /**
     * Gets all the activities on a list of monthlyCalendars given the calendar ID of the monthly calendar and an
     * activity manager.
     * @param calendarID An int representing the calendar ID of the monthly calendar.
     * @param am An ActivityManager representing the use case class manages the activities.
     * @return A List of Activity representing all the activities on a monthly calendar given the calendar ID of the
     * monthly calendar and an activity manager.
     */
    public List<Activity> getAllActivitiesMonthlyCalendar(int calendarID, ActivityManager am){
        return getAllActivities(calendarID, am, monthlyCalendarList);
    }

    /**
     * Gets all the activities on a given list of calendars given the calendar ID of the calendar, an activity manager.
     * It is the helper method for getAllActivitiesTimeTable, getAllActivitiesDateCalendar, and
     * getAllActivitiesMonthlyCalendar.
     * @param calendarID An int representing the calendar ID of the calendar.
     * @param am An ActivityManager representing the use case class manages the activities.
     * @param list A List of Calendar.
     * @return A List of Activity representing all the activities on a given list of calendars given the calendar ID
     * of the calendar and an activity manager.
     */
    public List<Activity> getAllActivities(int calendarID, ActivityManager am, List<Calendar> list){
        List<Activity> res = new ArrayList<>();
        for (Calendar c:list){
            if(c.getCalendarID() == calendarID){
                List<Integer> storedID = c.getAllActivityIDs();
                for (int id: storedID){
                    res.add(am.getActivity(id));
                }
            }
        }
        return res;
    }

    /**
     * Gets all the date calendars stored.
     * @return A List of Calendar representing all the stored dateCalendars.
     */
    public List<Calendar> getDateCalendarList(){return this.dateCalendarList;}

    /**
     * Gets all the timetables stored.
     * @return A List of Calendar representing all the stored timetables.
     */
    public List<Calendar> getTimeTableList(){return this.timeTableList;}

    /**
     * Gets all the monthly calendars stored.
     * @return A List of Calendar representing all the stored monthly calendars.
     */
    public List<Calendar> getMonthlyCalendarList(){return this.monthlyCalendarList;}

    /**
     * Gets all the deleted calendars stored.
     * @return A List of Calendar representing all the stored deleted calendars.
     */
    public List<Calendar> getDeletedCalendarList(){return this.deletedCalendar;}

    /**
     * Gets all the public calendars created by the user given the username.
     * @param userName A String representing the username of the user.
     * @return all the public calendars created by the user given the username.
     */
    public List<Calendar> getPublicCalendarsByUsername(String userName) {
        ArrayList<Calendar> UsersCalendars = new ArrayList<>();
        for (Calendar c : dateCalendarList) {
            if (c.getUserName().equals(userName) && c.getType().equals("1")) {
                UsersCalendars.add(c);
            }
        }
        for (Calendar c : timeTableList) {
            if (c.getUserName().equals(userName) && c.getType().equals("1")) {
                UsersCalendars.add(c);
            }
        }
        for (Calendar c : monthlyCalendarList) {
            if (c.getUserName().equals(userName) && c.getType().equals("1")) {
                UsersCalendars.add(c);
            }
        }
        return UsersCalendars;

    }

    /**
     * Gets all the friend-only and public calendars created by the user given the username.
     * @param userName A String representing the username of the user.
     * @return all the friend-only and public calendars created by the user given the username.
     */
    public List<Calendar> getFriendPublicCalendarsByUsername(String userName) {
        List<Calendar> PublicCalendar = getPublicCalendarsByUsername(userName);
        for (Calendar c : dateCalendarList) {
            if (c.getUserName().equals(userName) && c.getType().equals("2")) {
                PublicCalendar.add(c);
            }
        }
        for (Calendar c : timeTableList) {
            if (c.getUserName().equals(userName) && c.getType().equals("2")) {
                PublicCalendar.add(c);
            }
        }
        for (Calendar c : monthlyCalendarList) {
            if (c.getUserName().equals(userName) && c.getType().equals("2")) {
                PublicCalendar.add(c);
            }
        }
        return PublicCalendar;
    }
}