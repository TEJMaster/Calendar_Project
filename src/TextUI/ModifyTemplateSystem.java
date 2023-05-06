package TextUI;

import Template.TemplateManager;
import java.util.List;
import java.util.Scanner;

/**
 * A Controller class that allows the admin user for inputs to modify the template.
 * @author Group0031
 */
public class ModifyTemplateSystem {

    /**
     * Ask the user for which template to modify (0 being date calendar, 1 being timetable, 2 for monthly calendar).
     * The admin user modifies the template by being asked for which question to be edited and edit the question by
     * typing a new one to replace it.
     * @param x Scanner to get user input
     * @param tm Given Template Manager
     */
    public void ModifyTemplate(Scanner x, TemplateManager tm){
        System.out.println("Which template do you wish to modify? Press 0 for date calendar, 1 for timetable, 2 for" +
                " monthly calendar");
        String answer = x.nextLine();
        switch (answer) {
            case "0": {
                System.out.println("The previous question list for date calendar template is: ");
                System.out.println(tm.getDateCalendarTemplate().toString());
                int questionNumber = Integer.parseInt(getQuestionToEdit(x));
                List<String> questionList = tm.getDateCalendarTemplate().getQuestionList();
                updateTemplate(x, questionList, questionNumber);
                System.out.println("New template is: ");
                System.out.println(tm.getDateCalendarTemplate().toString());
                break;
            }
            case "1": {
                System.out.println("The previous question list for time table template is: ");
                System.out.println(tm.getTimeTableTemplate().toString());
                int questionNumber = Integer.parseInt(getQuestionToEdit(x));
                List<String> questionList = tm.getTimeTableTemplate().getQuestionList();
                updateTemplate(x, questionList, questionNumber);
                System.out.println("New template is: ");
                System.out.println(tm.getTimeTableTemplate().toString());
                break;
            }
            case "2": {
                System.out.println("The previous question list for monthly calendar template is: ");
                System.out.println(tm.getMonthlyCalendarTemplate().toString());
                int questionNumber = Integer.parseInt(getQuestionToEdit(x));
                List<String> questionList = tm.getMonthlyCalendarTemplate().getQuestionList();
                updateTemplate(x, questionList, questionNumber);
                System.out.println("New template is: ");
                System.out.println(tm.getMonthlyCalendarTemplate().toString());

                break;
            }
            default:
                System.out.println("Wrong key.");
                break;
        }
    }

    /**
     * Helper function to get a String from the user to know which question to edit
     * @param x Scanner needed to get user's input
     * @return A String representing the user's input of which question to edit.
     */
    public String getQuestionToEdit(Scanner x){
        System.out.println("Which question do you wish to edit? Enter number (e.g. 1 means to edit the first question)");
        return x.nextLine();
    }

    /**
     * Helper function to update the template based on given questionList and user input and the question number to be
     * edited.
     * @param x given Scanner to get input from users.
     * @param questionList given questionList to be changed (original questionList from the template).
     * @param questionNumber an int representing which question is going to be modified.
     */
    public void updateTemplate(Scanner x, List<String> questionList, int questionNumber){
        System.out.println("Please update the question.");
        String newQuestion = x.nextLine();
        questionList.set(questionNumber - 1, newQuestion);
    }
}