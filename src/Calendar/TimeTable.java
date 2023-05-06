package Calendar;

import Activity.Activity;

import java.util.HashMap;
import java.util.List;
/** An entity class of timetable. A timetable should also have title, type, username of
 * creator, activities on dates, editor list, recent changes, all changes, unique calendar ID, and last type of this
 * timeTable (for undo purpose).
 * @author Group0031
 */

public class TimeTable extends Calendar{
    private final HashMap<String, List<Integer>> dateToID;
    /**
     * Calls the super constructor by the same inputs we need for calendar.
     * @param title The title of this timeTable.
     * @param type The type of this timeTable.
     * @param  userName The username of the creator of this timeTable.
     * @param datetoID The activity's IDs on particular dates on this timeTable.
     * @param EditorList The editor list of usernames who have access to this timeTable.
     * @param calendarID The unique calendar ID of this timeTable.
     * @param AllChanges A list of all the changes that were made on this timeTable.
     */
    public TimeTable(String title, String type, String userName,
                     HashMap<String, List<Integer>> datetoID, List<String> EditorList, int calendarID,
                     List<String> AllChanges) {
        super(title, type, userName, datetoID, EditorList, calendarID, AllChanges);
        this.dateToID = datetoID;
    }

    /**
     * Add activity with time to this timeTable on a day.
     * @param day day of the activity.
     * @param activity the activity object that stores information of the activity.
     * @param Username the username of the owner for this timeTable.
     * @param time the time of the activity.
     */
    public void addActivity(String day, Activity activity, String Username, String time) {
        if (dateToID.containsKey(day)){
            super.addActivity(day, activity, Username, time);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Remove activity with time to this timeTable on a day.
     * @param day day of the activity.
     * @param activity the activity object that stores information of the activity.
     * @param Username the username of the owner for this timeTable.
     * @param time the time of the activity.
     */
    public void removeActivity(String day, Activity activity, String Username, String time) {
        if (dateToID.containsKey(day)){
            super.removeActivity(day, activity, Username, time);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * The toString method for timeTable (1 means timetable)
     * @return the String of the timeTable information
     */
    public String toString(){
        String s = super.toString();
        return s + "," + "1";
    }
}