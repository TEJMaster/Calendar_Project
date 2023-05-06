package Calendar;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
/**
 * An entity class represents an dateCalendar, which is a child class of abstract class Calendar.
 * @author Group0031
 */

public class DateCalendar extends Calendar {

    /**
     * Creates a DateCalendar with specific title, type, username of the creator, activities on dates, list of editors,
     * unique calendar id, and list of changes that were made on the DateCalendar.
     * @param title A String representing the title of the DateCalendar.
     * @param type A String representing the type of the DateCalendar (public, private or friend-only).
     * @param userName A String representing the username of the creator of the DateCalendar.
     * @param dateToID A Hashmap having dates as keys and activity ids of all the activities on a date as values.
     * @param EditorList A List of String representing all the editors of the DateCalendar.
     * @param calendarID An int representing the unique id of the DateCalendar.
     * @param AllChanges A List of String representing all the changes that were made on the DateCalendar.
     */
    public DateCalendar(String title, String type, String userName,
                        HashMap<String, List<Integer>> dateToID, List<String> EditorList, int calendarID,
                        List<String> AllChanges ) {
        super(title, type, userName, dateToID, EditorList, calendarID, AllChanges);
    }// The constructor of the date calendar

    /**
     * Display the general information of DateCalendar as a String (inherited from the toString method from Calendar),
     * and with ,0 afterwards if it's a DateCalendar; ,2 afterwards if it's a monthly calendar. If the dates stored in
     * the hashmap of dateToID is less than 32 (means 28,29,30, or 31 days are stored), it means it's a monthly calendar.
     * @return A String represented the information of a DateCalendar/MonthlyCalendar depends on the number of keys in
     * hashmap dateToID.
     */
    public String toString(){
        Set<String> key = this.getDates();
        String s = super.toString();
        if (key.size() > 32) {
            return s + "," + "0";
        }
        else{
            return s + "," + "2";
        }
    }
}