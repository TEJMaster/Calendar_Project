package Calendar;

import Activity.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/** An abstract class represents all types of calendars. A calendar can store this calendar's title, type, user name of
 * creator, activities on dates, editor list, recent changes, all changes, unique calendar ID, and last type of this
 * calendar (for undo purpose).
 * @author Group0031
 */
public abstract class Calendar {
    private final String title;
    private String type;
    private final String userName;
    private final HashMap<String, List<Integer>> datetoID;
    private final List<String> EditorList;
    private String RecentChange;
    private final List<String> AllChanges;
    private final int calendarID;
    private String lastType = null;

    /**
     * Creates a calendar with the specified title, type, user name of creator, activities on dates, editor list, unique
     * calendar ID, and list of all changes on this calendar.
     * @param title The title of this calendar.
     * @param type The type of this calendar.
     * @param userName The user name of the creator of this calendar.
     * @param datetoID The activity's IDs on particular dates on this calendar.
     * @param EditorList The editor list of usernames who have access to this calendar.
     * @param calendarID The unique calendar ID of this calendar.
     * @param AllChanges A list of all the changes that were made on this calendar.
     */
    public Calendar(String title, String type, String userName, HashMap<String, List<Integer>> datetoID,
                    List<String> EditorList, int calendarID, List<String> AllChanges){
        this.title = title;
        this.type = type;
        this.userName = userName;
        this.datetoID = datetoID;
        this.EditorList = EditorList;
        this.RecentChange = "";
        this.AllChanges = AllChanges;
        this.calendarID = calendarID;
    }

    /**
     * Gets the title of this calendar
     * @return A String representing the title of this calendar.
     */
    public String getCalendarTitle(){return this.title;}

    /**
     * Gets the type of this calendar. The calendar can either be public, private, or friend-only.
     * @return A String representing the type of this calendar.
     */
    public String getType(){return this.type;}

    /**
     * Sets the type of this calendar, and the last type of this calendar will be set as the previous type of
     * this calendar.
     * @param type A String representing the new type for this calendar.
     */
    public void setType(String type) {
        this.lastType = this.type;
        this.type = type;
    }

    /**
     * Gets the username of the creator of this calendar
     * @return A String representing the username of the creator of this calendar.
     */
    public String getUserName(){return userName;}

    /**
     * Gets the editor list of this calendar
     * @return A list of strings representing the usernames of editors who have access to this calendar.
     */
    public List<String> getEditorList(){return this.EditorList;}

    /**
     * Gets all the dates stored on this calendar
     * @return A Set of strings representing the dates stored on this calendar.
     */
    public Set<String> getDates(){return datetoID.keySet();}

    /**
     * Gets the unique ID of this calendar
     * @return An int representing the unique ID of this calendar.
     */
    public int getCalendarID(){ return calendarID; }

    /**
     * Gets the last type of this calendar
     * @return A String representing the last type of this calendar.
     */
    public String getLastType(){return this.lastType;}

    /**
     * Gets the recent change of this calendar
     * @return A String representing the recent change of this calendar.
     */
    public String getRecentChange() { return RecentChange; }

    /**
     * Gets all the changes that were made on this calendar (only this log-in)
     * @return A List of String representing all the changes that were made on this calendar (only this log-in).
     */
    public List<String> getChanges(){ return this.AllChanges; }

    /**
     * Gets all the activity's IDs on a specific date.
     * @param date A String representing a particular date on this calendar.
     * @return A List of integers representing all the activity IDs of activities on a given date.
     */
    public List<Integer> getActivityIDs(String date){ return this.datetoID.get(date); }

    /**
     * Add a user into the editor list of this calendar.
     * @param Username A String representing the username of the user will be added to the editor list of this calendar.
     */
    public void addEditor(String Username){
        this.EditorList.add(Username);
    }

    /**
     * Add an activity to this calendar given a specific date this activity will be on, what the activity is, the
     * username of the user made the change, and the time the change is made.
     * @param date A String representing the date of this newly added activity will be on.
     * @param activity An Activity representing the general information of this newly added activity.
     * @param userName A String representing the username of the user who makes the change (adding activity)
     * @param time A String representing the time of the change (adding activity) is made.
     */
    public void addActivity(String date, Activity activity, String userName, String time){
        List<Integer> stored = this.getActivityIDs(date);
        stored.add(activity.getActivityId());
        this.datetoID.put(date, stored);
        this.RecentChange =  "+" + activity.getActivityId();
        this.AllChanges.add(userName + " added activity " + activity.getName() + " on " + time);
    }

    /**
     * Remove an activity from this calendar given a specific date this activity will be on, what the activity is, the
     * username of the user made the change, and the time the change is made.
     * @param date A String representing the date of this newly removed activity was on.
     * @param activity An Activity representing the general information of this newly removed activity.
     * @param userName A String representing the username of the user who makes the change (removing activity)
     * @param time A String representing the time of the change (removing activity) is made.
     */
    public void removeActivity(String date, Activity activity, String userName, String time){
        List<Integer> stored = this.getActivityIDs(date);
        stored.remove(new Integer(activity.getActivityId()));
        this.datetoID.put(date, stored);
        this.RecentChange =  "-" + activity.getActivityId();
        this.AllChanges.add(userName + " deleted activity " + activity.getName() + " on " + time);
    }

    /**
     * Gets the date of a given activity.
     * @param ac An Activity representing the activity we will look for its date.
     * @return A String representing the date of the given activity is on. If the activity is not on any day, "" is
     * returned.
     */
    public String getDateByActivity(Activity ac){
        int id = ac.getActivityId();
        for (String key: datetoID.keySet()){
            if (datetoID.get(key).contains(id)){
                return key;
            }
        }
        return "";
    }

    /**
     * Gets IDs of all the activities on this calendar.
     * @return A List of Integers representing the IDs of all the activities on this calendar.
     */
    public List<Integer> getAllActivityIDs(){
        List<Integer> res = new ArrayList<>();
        for (List<Integer> acID: datetoID.values()){
            res.addAll(acID);
        }
        return res;
    }

    /**
     * Store the hashmap of date as keys and IDs of activities on that date as values as a String.
     * @return A String representing the hashmap of date as keys and IDs of activities on that date as values.
     */
    private String storeHashmap(){
        String start = "{";
        String end = "}";
        StringBuilder content = new StringBuilder();
        Set<String> keySet = datetoID.keySet();

        for (String key : keySet) {
            String mapping = datetoID.get(key).toString();
            content.append(key).append("=").append(mapping).append("-");
        }

        return start + content + end;
    }

    /**
     * Displays A List of Strings (in this case, the editor list for this calendar will be used) as a single String.
     * @param EditorList The List of Strings representing the editor list for this calendar.
     * @return A String representing the list of editors for this calendar.
     */
    public String ListToString(List<String> EditorList){
        StringBuilder Editors = new StringBuilder();
        for (String Editor: EditorList){
            String newEditor = Editor + "/";
            Editors.append(newEditor);
        }
        return Editors.toString();
    }

    /**
     * Displays a description of the general information of this calendar including its title, type, creator's username,
     * activities on dates, ID of this calendar, list of editors, and all the changes made on this calendar.
     * @return A String representing the description of this calendar including its title, type, creator's username,
     * activities on dates, ID of this calendar, list of editors, and all the changes made on this calendar.
     */
    public String toString(){
        return this.getCalendarTitle() + "," + this.type + "," + this.getUserName() + "," +
                this.storeHashmap() + "," + this.getCalendarID() + "," + ListToString(this.getEditorList()) + "," +
                ListToString(this.getChanges());
    }
}
