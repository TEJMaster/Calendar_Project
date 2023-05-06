package Activity;

/** This entity class is created to store activity efficiently. Information of the activity including: name of the
 * activity, id of the activity, time duration of the activity, description of the activity, category of the activity,
 * and whether the activity is important will be stored. Other than getters and setters, the class also includes a
 * method to convert the activity to a string.
 * @author Group0031
 * **/

public class Activity {
   private String Name;
   private final int ActivityId;
   private final String TimeDuration;
   private final String Description;
   private final String Category;
   private final String Importance;
   private final String date;

   /**
    * Creates an activity with the specified activity name, activity id, time duration, description, category, whether
    * the activity is important, and date of the activity.
    * @param Name The name of this activity.
    * @param ActivityId The unique ID of this activity.
    * @param TimeDuration The time duration of this activity.
    * @param Description The description of this activity.
    * @param Category The Category of this activity.
    * @param Importance Whether this activity is important.
    * @param date The date of this activity.
    */
   public Activity(String Name, int ActivityId, String TimeDuration, String Description, String Category,
                   String Importance, String date) {
      this.Name = Name;
      this.ActivityId = ActivityId;
      this.TimeDuration = TimeDuration;
      this.Description = Description;
      this.Category = Category;
      this.Importance = Importance;
      this.date = date;
   }

   /**
    * Gets the name of this activity.
    * @return A String representing the account’s name.
    */
   public String getName(){
      return this.Name;
   }

   /**
    * Sets the name of this activity.
    * @param name A String containing the new name for this activity.
    */
   public void setName(String name) {
      Name = name;
   }

   /**
    * Gets the unique ID of this activity
    * @return An int representing the activity’s ID.
    */
   public int getActivityId(){
      return this.ActivityId;
   }

   /**
    * Gets the category of this activity
    * @return A String representing the activity’s category.
    */
   public String getCategory() {
      return this.Category;
   }

   /**
    * Gets the importance of this activity
    * @return A String representing the activity’s importance (Yes or No).
    */
   public String getImportance(){
      return this.Importance;
   }

   /**
    * Displays a description for this activity including general information in name, time duration, unique id, and
    * description.
    * @return A String representing the information including name, time duration, unique id, and description.
    */
   public String toString(){
      String string1 = ("This is an activity ") + this.Name;
      String string2 = ("It has time duration ") + this.TimeDuration;
      String string3 = ("It has id ") + this.getActivityId();
      return string1 + "\n" + string2 + "\n" + string3 + "\n" + "Description: " + this.Description;
   }

   /**
    * Gets the date of this activity
    * @return A String representing the activity’s date.
    */
   public String getDate(){
      return this.date;
   }

   /**
    * Displays general information of this activity including its name, unique id, time duration, description, category,
    * importance, and date for storing data purpose.
    * @return A String representing the activity's information including name, unique id, time duration, description,
    * category, importance, and date.
    */
   public String storeString(){
      return Name + "," + ActivityId + "," + TimeDuration + "," + Description +
              "," + Category + "," + Importance + "," + date;
   }
}