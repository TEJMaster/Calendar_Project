package TextUI;

import Template.Template;
import User.UserSystem;

import Calendar.CalendarSystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** a Text UI class that handles creation process of a calendar and add it to the program. This stores a user system
 * and a calendar system which are used to get calendar templates and create calendar**/
public class CalendarCreator {
    private final UserSystem userSystem;
    private final CalendarSystem calendarSystem;

    /** this method construct a calendar creator by using the given user system and calendar system **/
    public CalendarCreator(UserSystem userSystem, CalendarSystem calendarSystem){
        this.userSystem = userSystem;
        this.calendarSystem = calendarSystem;
    }

    /** This method asks the user to create a certain type of calendar and then calls each input method to get the list
     * of answers requires to create a calendar. Then calls the create calendar method in calendar system that correspond
     * to each calendar type using the input got from methods
     * @param x, a scanner object that gets the input from user
     * @param UserName, a string object that stores the username of the current user **/
    public void CreateCalendar(Scanner x, String UserName) {
        while (true){
            PrintCreationOptions();
            String answer = x.nextLine();
            if (answer.equals("r")) {
                return; }
            else if (!(answer.equals("0") | answer.equals("1") | answer.equals("2"))) {
                System.out.println("invalid button"); }
            else {
                Template temp = userSystem.GetTemplate(answer);
                System.out.println("while creating the calendar, you can press r to cancel creation");
                List<String> Input = new ArrayList<>();
                switch (answer) {
                    case "0": {
                        List<String> input = inputFromDateCalendarTemplate(x, temp, Input);
                        if (input.isEmpty()) { return; }
                        calendarSystem.createDateCalendar(UserName, input);
                        break; }
                    case "1": {
                        List<String> input = inputFromTimetableTemplate(x, temp, Input);
                        if (input.isEmpty()) { return; }
                        calendarSystem.createTimetable(UserName, input);
                        break; }
                    case "2": {
                        List<String> input = inputFromMonthlyCalendarTemplate(x, temp, Input);
                        if (input.isEmpty()) { return; }
                        calendarSystem.createMonthlyCalendar(UserName, input);
                        break; } }
                System.out.println("your calendar is being created successfully!");
                return; } } }

    /** This method asks which type of calendar the user want to create. The types are date calendar, timetable
     * and monthly calendar  **/
    public void PrintCreationOptions() {
        System.out.println("Which type of Calendar do you want to create?(DateCalender/TimeTable/" +
                "MonthlyCalendar) ");
        System.out.println("Press 0 for a Date Calender , Press 1 for a Time Table, Press 2 for a " +
                "Monthly Calendar, or press r to cancel");
    }

    /** the method get the answer from the date calendar template that are being used to create a date
     * calendar. During the creation process, the user can press r to return an empty list which is used
     * to cancel the creation process
     * @param x, a scanner object that gets the input from user
     * @param temp, a template object that contains object for date calendars
     * @return returns a list of answer got from users */
    public List<String> inputFromDateCalendarTemplate(Scanner x, Template temp, List<String> Input){
            System.out.println(temp.getQuestionList().get(0));
            String answer1 = x.nextLine();
            if (answer1.equals("r")){
                return new ArrayList<>();
            }
            Input.add(answer1);
            String answer2 = getChoiceResult(x, temp, 1);
            if (answer2.equals("r")){
                return new ArrayList<>();
            }
            Input.add(answer2);
            return Input;
    }

    /** the method get the answer from the timetable template that are being used to create a date
     * calendar. During the creation process, the user can press r to return an empty list which is used
     * to cancel the creation process
     * @param x, a scanner object that gets the input from user
     * @param temp, a template object that contains object for timetable template
     * @return returns a list of answer got from users */
    public List<String> inputFromTimetableTemplate(Scanner x, Template temp, List<String> Input ) {
            System.out.println(temp.getQuestionList().get(0));
            String answer1 = x.nextLine();
            if (answer1.equals("r")){
                return new ArrayList<>();
            }
            Input.add(answer1);
            String answer2 = getYesNoResult(x, temp , 1);
            if (answer2.equals("r")){
                return new ArrayList<>();
            }
            Input.add(answer2);
            String answer3 = getChoiceResult(x, temp , 2);
            if (answer3.equals("r")){
                return new ArrayList<>();
            }
            Input.add(answer3);
            return Input;
    }

    /** the method get the answer from the monthly calendar template that are being used to create a date
     * calendar. During the creation process, the user can press r to return an empty list which is used
     * to cancel the creation process
     * @param x, a scanner object that gets the input from user
     * @param temp, a template object that contains object for monthly calendar
     * @return returns a list of answer got from users */
    public List<String> inputFromMonthlyCalendarTemplate(Scanner x, Template temp, List<String> Input ) {
            String answer1 = getYesNoResult(x, temp, 0 );
            if (answer1.equals("r")) {
                return new ArrayList<>();}
            else if (answer1.equalsIgnoreCase("yes")) {
                Input.add(String.valueOf(LocalDate.now().getMonthValue()));
                Input.add(LocalDate.now().getMonth().toString());
                Input.add("1");
                return Input;}
            else {
                String answer2 = GetMonth(x, temp);
                if (answer2.equals("r")) {
                    return new ArrayList<>();
                }
                Input.add(answer2);
                System.out.println(temp.getQuestionList().get(2));
                String answer3 = x.nextLine();
                if (answer3.equals("r")) {
                    return new ArrayList<>();
                }
                Input.add(answer3);
                String answer4 = getChoiceResult(x, temp , 3);
                if (answer4.equals("r")) {
                    return new ArrayList<>();}
                Input.add(answer4);
                    return Input;
                }
    }

    /** the method gets the input from the
     * @param x, a scanner object that gets the input from user
     * @param temp, a template object that contains object for date calendars
     * @return returns the String representing which month the user want to create for monthly calendar*/
    public String GetMonth(Scanner x, Template temp) {
        while (true) {
        System.out.println(temp.getQuestionList().get(1));
        String answer1 = x.nextLine();
        if (answer1.equals("r") | checkMonth(answer1)){
            return answer1;
        }
        else {
            System.out.println("the input is invalid! please input your answer again");
        }
    }
    }

    /** the method check if the input is an available month by trying to convert it to int and see if its between
     * 12 and 1
     * @param input, the string storing the input the user entered
     * @return returns true if the user inputted an available month and false otherwise*/
    public boolean checkMonth(String input) {
        try {
            int num = Integer.parseInt(input);
            return (num <= 12 & num >=1);
            }
        catch (java.lang.NumberFormatException e) {
            return false;
        }
    }

    /** since we are asking yes no question frequently in the template, this method displays
     * the question using temp and an index to checks if answer is valid if it is not in one of the yes or
     * no or cancel button, the while loop will make user input the answer again
     * @param x, a scanner object that gets the input of user
     * @param temp, a template object that stores the yes no question
     * @param index, the index which the template has the yes no  question**/

    public String getYesNoResult(Scanner x, Template temp, int index) {
        while (true) {
            System.out.println(temp.getQuestionList().get(index));
            String answer2 = x.nextLine();
            if (!(answer2.equalsIgnoreCase("Yes")|(answer2.equalsIgnoreCase("No")) |
                    answer2.equals("r"))) {
                System.out.println("invalid input! please try again");
            } else {
                return answer2;
            }
        }
    }

    /** since we are asking the get type of calendar question frequently in the template, this method displays
     * the question using temp and an index to checks if answer is valid if it is not in one of the types or
     * cancel button, the while loop will make user input the answer again
     * @param x, a scanner object that gets the input of user
     * @param temp, a template object that stores the type question
     * @param index, the index which the template has the type question**/
    public String getChoiceResult(Scanner x, Template temp, int index) {
        while (true) {
            System.out.println(temp.getQuestionList().get(index));
            String answer2 = x.nextLine();
            if (!(answer2.equals("0") | answer2.equals("1") | answer2.equals("2")| answer2.equals("r"))) {
                System.out.println("invalid input! please try again");
            } else {
                return answer2;
            }
        }
    }
}