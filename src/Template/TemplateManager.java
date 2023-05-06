package Template;
import Data.InformationReader;
import java.util.List;

/**
 * A Use case class that can manage the template (timetable template, date calendar template,
 * and monthly calendar template)
 * @author Group0031
 */
public class TemplateManager {
    private final Template TimeTableTemplate;
    private final Template DateCalendarTemplate;
    private final Template MonthlyCalendarTemplate;

    /**
     * Creates DateCalendarTemplates, TimeTableTemplates and MonthlyCalendarTemplates
     * @param informationReader a class that helps to read DateCalendar, Timetable and MonthlyCalendar data
     */
    public TemplateManager(InformationReader informationReader){
        this.DateCalendarTemplate = new Template(readDateCalendarTemplate(informationReader));
        this.TimeTableTemplate = new Template(readTimeTableTemplate(informationReader));
        this.MonthlyCalendarTemplate = new Template(readMonthlyTemplate(informationReader));
    }

    /**
     * Gets the monthly calendar template
     * @return Template representing the MonthlyCalendarTemplate
     */
    public Template getMonthlyCalendarTemplate() { return this.MonthlyCalendarTemplate;}

    /**
     * Gets the timetable template
     * @return template representing the TimeTableTemplate
     */
    public Template getTimeTableTemplate(){
        return this.TimeTableTemplate;
    }

    /**
     * Gets the date calendar template
     * @return template representing the DateCalendarTemplate
     */
    public Template getDateCalendarTemplate(){
        return this.DateCalendarTemplate;
    }

    /**
     * Gets the list of questions from the DateCalendarTemplate
     * @param informationReader a class that helps to read the data for DateCalendarTemplate
     * @return list of strings representing questions for DateCalendar
     */
    public List<String> readDateCalendarTemplate(InformationReader informationReader){
        return informationReader.ReadDateTemplate();
    }

    /**
     * Gets the list of questions from the TimeTableTemplate
     * @param informationReader a class that helps to read the data for TimeTableTemplate
     * @return list of strings representing questions for TimeTable
     */
    public List<String> readTimeTableTemplate(InformationReader informationReader){
        return informationReader.ReadTimeTableTemplate();
    }

    /**
     * Gets the list of questions from the MonthlyTemplate
     * @param informationReader a class that helps to read the data for MonthlyTemplate
     * @return list of strings representing questions for MonthlyTemplate
     */
    public List<String> readMonthlyTemplate(InformationReader informationReader){
        return informationReader.ReadMonthlyTemplate();
    }
}