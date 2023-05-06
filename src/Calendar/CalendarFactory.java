package Calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A factory class that can construct a new date calendar, time table or monthly calendar.
 * It gives calendar type, title, Public, userName, dateToID, EditorList, calendarID for a new calendar as parameter.
 * @author Group0031
 */
public class CalendarFactory {
    /**
     * Creates a new calendar with given type, title, whether is public, userName who creates the calendar, what
     * activities are on a date, a list of editors for this calendar, and a calendar ID.
     * @param title A string representing the type of this new account.
     * @param type A String representing whether the calendar is a public, private or friend only calendar.
     * It has value "0" for private, "1" for public and "2" for friend only,
     * @param userName A string representing the username of this new account.
     * @param dateToID A Hashmap representing what activities are there on each date as keys.
     * @param editorList A list of Strings representing editors for this calendar.
     * @param calendarID A integer representing the ID of the calendar.
     * @return A calendar with these given information.
     */

    public Calendar getCalendar(String calendarType, String title, String type, String userName, HashMap<String,
            List<Integer>> dateToID, List<String> editorList, int calendarID) {
        editorList.add(userName);
        List<String> Changes = new ArrayList<>();
        if (calendarType == null) {
            return null;
        } else if (calendarType.equalsIgnoreCase("DATECALENDAR")) {
            return new DateCalendar(title, type, userName, dateToID, editorList, calendarID, Changes);
        } else if (calendarType.equalsIgnoreCase("TIMETABLE")) {
            return new TimeTable(title, type, userName, dateToID, editorList, calendarID, Changes);
        } else if (calendarType.equalsIgnoreCase("MONTHLYCALENDAR")) {
            return new DateCalendar(title, type, userName, dateToID, editorList, calendarID, Changes);
        } else {
            return null;
        }
    }
}