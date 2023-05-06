package Template;
import java.util.List;

/**
 * An entity class that stores a list of questions served as a template for calendars. The template can be modified by
 * admin users and all the questions of template are stored in csv files.
 * @author Group0031
 */
public class Template {
    private final List<String> questionList;

    /**
     * Create a template with given list of questions.
     * @param questions given list of questions
     */
    public Template(List<String> questions){
        this.questionList = questions;
    }

    /**
     * Gets the list of questions the template stores
     * @return A List of String representing all the questions the template stores.
     */
    public List<String> getQuestionList(){return this.questionList;}

    /**
     * Displays the questions stored in the template, separated by comma.
     * @return A String of questions stored in the template, separated by comma.
     */
    public String toString(){
        StringBuilder res = new StringBuilder();
        for (String question: this.questionList){
            res.append(question);
            res.append("\n");
        }
        return res.toString();
    }
}
