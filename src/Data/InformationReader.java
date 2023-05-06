package Data;

import Activity.Activity;
import Calendar.Calendar;
import Calendar.DateCalendar;
import Calendar.TimeTable;
import Messages.AdminMessage;
import User.RegularUser;
import User.TemporaryUser;
import User.TrialUser;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/** a gateway class that gets all the information from many csv files. **/
public class InformationReader {

    /** this method reads activity csv in side the data folder to get all the activities that are being stores
     * inside
     * @return a list of all the activities being stored in the csv file**/
    public List<Activity> ReadActivity() {
        List<Activity> ActivityList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("phase2/src/data/activity.csv");
            CSVReader csvReader = new CSVReader(fileReader);
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                Activity newOne = new Activity(record[0], Integer.parseInt(record[1]), record[2], record[3], record[4],
                        record[5], record[6]);
                ActivityList.add(newOne);
            }
            return ActivityList;
        } catch (IOException e) {
            System.out.println("Error: No storage file found");
            return new ArrayList<>();
        } catch (CsvValidationException c) {
            System.out.println("CSV Invalid");
            return new ArrayList<>();
        }
    }

    /** this method reads calendar csv inside the data folder to get all the calendars that are being stores
     * inside
     * @return a list that contains the monthly calendar list, timetable list and date calendar list
     * being stored in the csv file**/
    public List<List<Calendar>> ReadCalendar() {
        List<List<Calendar>> CalendarLists = new ArrayList<>();
        List<Calendar> dateCalendarList = new ArrayList<>();
        List<Calendar> timeTableList = new ArrayList<>();
        List<Calendar> monthlyList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("phase2/src/data/calendar.csv");
            CSVReader csvReader = new CSVReader(fileReader);
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                UpdateCalendar(record, timeTableList, dateCalendarList, monthlyList);
            }
            CalendarLists.add(0, dateCalendarList);
            CalendarLists.add(1, timeTableList);
            CalendarLists.add(2, monthlyList);
            return CalendarLists;
        } catch (IOException e) {
            System.out.println("No match file found");
            return new ArrayList<>();
        } catch (CsvValidationException c) {
            System.out.println("CSV Invalid");
            return new ArrayList<>();
        }
    }

    /** this method converts the string of the date to id hashmap stored inside calendar csv file to
     * Hashmap objects that has string date as keys and activity ids as values
     * @return a hashmap object has string date as keys and activity ids as values **/
    private HashMap<String, List<Integer>> stringToHashmap(String dateToID) {
        HashMap<String, List<Integer>> newMap = new HashMap<>();
        String[] lists = dateToID.substring(1, dateToID.length() - 1).split("-");
        for (String information : lists) {
            String[] info = information.split("=");
            char[] infoID = info[1].toCharArray();
            List<Integer> IDs = new ArrayList<>();
            for (char s : infoID) {
                if (s != ',' && s != '[' && s != ']' && s != ' ') {
                    IDs.add(Integer.parseInt(Character.toString(s)));
                }
            }
            newMap.put(info[0], IDs);
        }
        return newMap;
    }

    /** this method reads user csv inside the data folder to get all the users that are being stores
     * inside
     * @return a list of users that contains all the users in the csv file **/
    public List<TrialUser> ReadUser() {
        List<TrialUser> userLists = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("phase2/src/data/user.csv");
            CSVReader csvReader = new CSVReader(fileReader);
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                UpdateUser(record, userLists);
            }
            return userLists;
        } catch (IOException e) {
            System.out.println("Invalid");
            return new ArrayList<>();
        } catch (CsvValidationException c) {
            System.out.println("CSV Invalid");
            return new ArrayList<>();
        }
    }

    /** this method creates a timetable, date calendar or monthly calendar based on the string array got from the
     * reader method
     * @param record, a string array got from read calendar
     * @param timeTableList, a list of timetable calendars in the csv
     * @param dateCalendarList, a list of date calendars in the csv
     * @param monthlyCalendarList, a list of monthly calendars in the csv**/
    public void UpdateCalendar(String[] record, List<Calendar> timeTableList, List<Calendar> dateCalendarList,
                               List<Calendar> monthlyCalendarList) {
        String[] Editors = record[5].split("/");
        String[] changes = record[6].split("/");
        List<String> EditorList = new ArrayList<>(Arrays.asList(Editors));
        List<String> AllChanges = new ArrayList<>(Arrays.asList(changes));
        if (record[7].equals("0")) {
            DateCalendar newCalendar = new DateCalendar(record[0], record[1], record[2],
                    stringToHashmap(record[3]), EditorList, Integer.parseInt(record[4]), AllChanges);
            dateCalendarList.add(newCalendar);
        }
        if (record[7].equals("1")) {
            TimeTable newCalendar = new TimeTable(record[0], record[1], record[2],
                    stringToHashmap(record[3]), EditorList, Integer.parseInt(record[4]), AllChanges);
            timeTableList.add(newCalendar);
        }
        if (record[7].equals("2")){
            DateCalendar newCalendar = new DateCalendar(record[0], record[1], record[2], stringToHashmap(record[3]),
                    EditorList, Integer.parseInt(record[4]), AllChanges);
            monthlyCalendarList.add(newCalendar);
        }
    }

    /** this method reads the message sent to admins in AdminMessage.
     * @return returns a list of messages in admin message csv **/
    public List<AdminMessage> ReadMessage() {
        List<AdminMessage> allAdminMessages = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("phase2/src/data/AdminMessage.csv");
            CSVReader csvReader = new CSVReader(fileReader);
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                String[] message = record[0].split("~");
                allAdminMessages.add(new AdminMessage(record[0], record[1], record[2]));
            }
        } catch (IOException e) {
            System.out.println("there are no messages");
        } catch (CsvValidationException c) {
            System.out.println("CSV Invalid");
        }
        return allAdminMessages;
    }

    /** this method creates a trial user, regular user, temporary user based on the string array got from the
     * reader user method
     * @param record, a string array got from read user
     * @param userLists, a list of users in the user csv file **/
    public void UpdateUser(String[] record, List<TrialUser> userLists) {
        if (record.length == 7) {
            String[] Friends = record[6].split("/");
            List<String> FriendList = new ArrayList<>(Arrays.asList(Friends));
            TrialUser newUser = new TrialUser(record[0], record[2], StringToBoolean(record[3]),
                    Integer.parseInt(record[4]), record[5], FriendList);
            userLists.add(newUser);
        } else if (record.length == 8) {
            String[] Friends = record[7].split("/");
            List<String> FriendList = new ArrayList<>(Arrays.asList(Friends));
            RegularUser newUser = new RegularUser(record[0], record[2], record[3], numberToBoolean(record[1]),
                    StringToBoolean(record[4]), Integer.parseInt(record[5]), record[6], FriendList);
            userLists.add(newUser);
        } else {
            String[] Friends = record[8].split("/");
            List<String> FriendList = new ArrayList<>(Arrays.asList(Friends));
            TemporaryUser newUser = new TemporaryUser(record[0], record[2], numberToBoolean(record[1]),
                    record[3], record[4], StringToBoolean(record[5]), Integer.parseInt(record[6]), record[7],
                    FriendList);
            userLists.add(newUser);
        }
    }

    /** This method is used to get the is admin attribute in user. Since we are storing "0" for admin user and
     * 1 for non admin user. we need to convert it to boolean**/
    private boolean numberToBoolean(String num) {
        return num.equals("0");
    }

    /** This method is used to get the is frozen attribute in user. Since we are storing "0" for not frozen user and
     * 1 for frozen user. we need to convert it to boolean**/
    public boolean StringToBoolean(String bool) {
        return bool.equals("1");
    }

    /** this method reads and print out the response got from admin in Response csv file. If the username stores in
     * csv file is equal to the username given meaning the response is for the current user we add it to the response
     * list and the print it out at the end
     * @param Username, the username of the current user **/
    public void ReadResponse(String Username) {
        try {
            FileReader fileReader = new FileReader("phase2/src/data/Response.csv");
            CSVReader csvReader = new CSVReader(fileReader);
            String[] record;
            List<String> listOfResponse = new ArrayList<>();
            while ((record = csvReader.readNext()) != null) {
                if (Username.equals(record[0])) {
                    String output = record[1] + ": " + record[2];
                    listOfResponse.add(output);

                }
            }
            for (String response: listOfResponse){
                System.out.println(response);
            }
            if (listOfResponse.isEmpty()) {
                System.out.println("there are no messages!");
            }
        } catch (IOException e) {
            System.out.println("there are no messages");
        } catch (CsvValidationException c) {
            System.out.println("CSV Invalid");
        }
    }

    /** this method reads and get the welcome message in welcome message csv, if there is non, return an empty string
     * @return a string that stores the welcome message  **/
    public String GetWelcomeMessage() {
        try {
            FileReader fileReader = new FileReader("phase2/src/data/WelcomeMessage.csv");
            CSVReader csvReader = new CSVReader(fileReader);
            String[] message = csvReader.readNext();
            if (message != null) {
                return message[0];
            } else {
                return "";
            }
        } catch (IOException e) {
            return "";
        } catch (CsvValidationException c) {
            System.out.println("CSV Invalid");
            return "";
        }
    }

    /**
     * Gets a list of questions by reading from a given filepath (of template, in this case). The file stores the
     * each question line-by-line, so the buffered reader read the file line by line and stores all the questions as a
     * list. This is a helper method for the ReadDateTemplate(), ReadTimeTableTemplate(), and ReadMonthlyTemplate().
     * @param filepath A String representing the given file path(of a template).
     * @return A List of Strings representing the list of questions from a template.
     */
    public List<String> ReadBuffered(String filepath) {
        List<String> questionList = new ArrayList<>();
        try {
            File file = new File(filepath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                questionList.add(line);
            }
            fr.close();
            return questionList;
        }
        catch (IOException e) {
            System.out.println("Error: File not found.");
            return questionList;
        }
    }

    /**
     * Use ReadBuffered() as a helper method to get questions to be asked from a DateCalendar Template.
     * @return A List of String representing all the questions to be asked from a DateCalendar Template.
     */
    public List<String> ReadDateTemplate(){
        return ReadBuffered("phase2/src/data/dateCalendarTemplate.csv");
    }

    /**
     * Use ReadBuffered() as a helper method to get questions to be asked from a TimeTable Template.
     * @return A List of String representing all the questions to be asked from a TimeTable Template.
     */
    public List<String> ReadTimeTableTemplate(){
        return ReadBuffered("phase2/src/data/timeTableTemplate.csv");
    }

    /**
     * Use ReadBuffered() as a helper method to get questions to be asked from a MonthlyCalendar Template.
     * @return A List of String representing all the questions to be asked from a MonthlyCalendar Template.
     */
    public List<String> ReadMonthlyTemplate(){
        return ReadBuffered("phase2/src/data/monthlyCalendarTemplate.csv");
    }
}