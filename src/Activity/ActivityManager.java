package Activity;

import java.util.List;
import java.util.NoSuchElementException;

import Data.InformationReader;

/**  A user case class which manages Activity. This class stores a list which contains
 * all the activities inside the program and contains method to create and delete a specific activity.
 * @author Group0031
 **/
public class ActivityManager {
    private final List<Activity> ActivityList;


/** Constructs a Activity Manager by injecting the gateway class information reader and calling the read activity
 * method to initialize activity list **/
 public ActivityManager(InformationReader informationReader) {
     this.ActivityList = informationReader.ReadActivity();
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
        int ActivityId = ActivityList.size() + 1;
        Activity NewEvent = new Activity(Name, ActivityId, TimeDuration, Description, Category, Importance, date);
        addActivity(NewEvent);
        return NewEvent;
    }

    /** This method deletes an activity by removing it from the activity list.
     * @param ActivityID, the activity ID of the activity that is being removed **/
    public void deleteActivity(int ActivityID) {
        this.ActivityList.remove(getActivity(ActivityID));
    }

    /** A getter that returns the activity list
     * @return returns the list of activity that is in the program **/
    public List<Activity> getActivityList(){
        return this.ActivityList;
    }

    /** A method that adds an activity object to the activity list
     * @param NewEvent, a new activity object to add to the activity list **/
    public void addActivity(Activity NewEvent){
        ActivityList.add(NewEvent);
    }

    /** A method that gets an activity object in the activity list using the input id. If the activity is not
     * found throw an no such element exception
     * @param ActivityId, an activity id of the activity we want to find
     * @return returns the activity object inside the activity list **/
    public Activity getActivity(int ActivityId){
        for (Activity a: ActivityList){
            if (a.getActivityId() == ActivityId) {
                return a;
            }
        }
        // NoSuchElementException if there are no more elements to check and activity is still not found.
        throw new NoSuchElementException();
    }
}
