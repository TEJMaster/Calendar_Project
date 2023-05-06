package Data;

import Activity.Activity;
import Activity.ActivityManager;
import Calendar.Calendar;
import Calendar.CalendarManager;
import User.TrialUser;
import User.UserManager;
import Template.*;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A Gateway class for updating information in the storage files.
 * @author Group0031
 */
public class InformationSaver{
    private final CalendarManager calendar;
    private final ActivityManager activity;
    private final UserManager user;
    private final TemplateManager templateManager;

    /**
     * Creates an information saver given calendar manager, activity manager, user manager, and a template manager. It
     * stores them as attributes.
     * @param cal the given calendar manager.
     * @param act the given activity manager.
     * @param user the given user manager.
     * @param tm the given template manager.
     */
    public InformationSaver(CalendarManager cal, ActivityManager act, UserManager user, TemplateManager tm) {
        this.calendar = cal;
        this.activity = act;
        this.user = user;
        this.templateManager = tm;
    }

    /**
     * This is the soul of Saver, the method to write a given message into files given a fileName (filepath).
     * @param message the given message to be written.
     * @param fileName The given filepath where the message should be written to.
     */
    public static void write(String message, String fileName)
    {
        try{
            FileWriter csvWriter = new FileWriter(fileName, true);

            csvWriter.append(message);
            csvWriter.append("\n");
            csvWriter.flush();
            csvWriter.close();
        }
        catch(IOException e){
            System.out.println("Unable to record a calendar");
        }
    }

    /**
     * To write the welcome message which is given as a String into welcome message storage file.
     * @param WelcomeMessage A String representing the welcome message.
     */
    public void writeWelcome(String WelcomeMessage){
        write(WelcomeMessage, "phase2/src/data/WelcomeMessage.csv");
    }

    /**
     * To write the calendar information which is given as a String into calendar storage file.
     * @param cal A String representing the calendar information.
     */
    public static void writeCalendar(Calendar cal)  {write(cal.toString(), "phase2/src/data/calendar.csv"); }

    /**
     * To write the activity information which is given as a String into activity storage file.
     * @param act A String representing the activity information.
     */
    public static void writeActivity(Activity act){ write(act.storeString(), "phase2/src/data/activity.csv");}

    /**
     * To write the user information which is given as a String into user storage file.
     * @param user A String representing the user information.
     */
    public static void writeRegularUser(TrialUser user){ write(user.toString(), "phase2/src/data/user.csv");}

    /**
     * To write the DateCalendar template information which is given as a String into DateCalendar template storage file.
     * @param question A String representing the question from the template.
     */
    public static void writeDateCalendarTemplate(String question) {write(question,
            "phase2/src/data/dateCalendarTemplate.csv");}

    /**
     * To write the TimeTable template information which is given as a String into TimeTable template storage file.
     * @param question A String representing the question from the template.
     */
    public static void writeTimeTableTemplate(String question) {write(question,
            "phase2/src/data/timeTableTemplate.csv");}

    /**
     * To write the MonthlyCalendar template information which is given as a String into MonthlyCalendar template
     * storage file.
     * @param question A String representing the question from the template.
     */
    public static void writeMonthlyCalendarTemplate(String question) {write(question,
            "phase2/src/data/monthlyCalendarTemplate.csv");}

    /**
     * Clear CSV file with a given file path and get it ready to be overwritten.
     * @param path A String representing the given filepath.
     */
    public static void clearCSV(String path) {
        try {
            List<String[]> allElements = new ArrayList<>();
            FileWriter sw = new FileWriter(path);
            CSVWriter writer = new CSVWriter(sw);
            writer.writeAll(allElements);
            writer.close();
        } catch (IOException e) {
            System.out.println("not able to find file");
        }
    }

    /**
     * Clear calendar.csv file and then write calendar information to achieve the goal to update.
     */
    private void updateCal() {
        clearCSV("phase2/src/data/calendar.csv");
        for (Calendar c : calendar.getDateCalendarList()) {
            writeCalendar(c);
        }
        for (Calendar t : calendar.getTimeTableList()) {
            writeCalendar(t);
        }
        for (Calendar t : calendar.getMonthlyCalendarList()) {
            writeCalendar(t);
        }
    }

    /**
     * Clear activity.csv file and then write activity information to achieve the goal to update.
     */
    private void updateAct(){
        clearCSV("phase2/src/data/activity.csv");
        for(Activity act: activity.getActivityList()){
            writeActivity(act);
        }
    }

    /**
     * Clear user.csv file and then write user information to achieve the goal to update.
     */
    public void updateUser(){
        clearCSV("phase2/src/data/user.csv");
        for(TrialUser u: user.getUsers()){
            writeRegularUser(u);
            }
        }

    /**
     * Clear dateCalendarTemplate.csv file and then write template information to achieve the goal to update.
     */
    private void updateDateCalendarTemplate(){
        clearCSV("phase2/src/data/dateCalendarTemplate.csv");
        BasicQuestionListIterator DateIter = new BasicQuestionListIterator(templateManager.getDateCalendarTemplate());
        while(DateIter.hasNext()){
            writeDateCalendarTemplate(DateIter.next());
        }
    }

    /**
     * Clear timeTableTemplate.csv file and then write template information to achieve the goal to update.
     */
    private void updateTimeTableTemplate(){
        clearCSV("phase2/src/data/timeTableTemplate.csv");
        BasicQuestionListIterator TimeTableIter = new BasicQuestionListIterator(templateManager.getTimeTableTemplate());
        while(TimeTableIter.hasNext()){
            writeTimeTableTemplate(TimeTableIter.next());
        }
    }

    /**
     * Clear monthlyCalendarTemplate.csv file and then write template information to achieve the goal to update.
     */
    private void updateMonthlyCalendarTemplate(){
        clearCSV("phase2/src/data/monthlyCalendarTemplate.csv");
        BasicQuestionListIterator MonthlyCalendarIter = new BasicQuestionListIterator(templateManager.getMonthlyCalendarTemplate());
        while(MonthlyCalendarIter.hasNext()){
            writeMonthlyCalendarTemplate(MonthlyCalendarIter.next());
        }
    }

    /**
     * The method to call methods to update activities, calendars, users, dateCalendarTemplate, timeTableTemplate, and
     * monthlyCalendarTemplate, which is used to update all the information when the user logs out.
     */
    public void update() {
        updateAct();
        updateCal();
        updateUser();
        updateDateCalendarTemplate();
        updateMonthlyCalendarTemplate();
        updateTimeTableTemplate();
    }
}
