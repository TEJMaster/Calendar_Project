package Template;

/**
 * A concrete class implementing Iterator that iterates through Template's QuestionList specifically
 */
public class BasicQuestionListIterator implements Iterator{
    public String[] questions;
    int index;
    /**
     * Creates an Iterator with a template, in which we want to reveal its underlying questionList
     * @param temp the template whose questionList will be iterate through
     */
    public BasicQuestionListIterator(Template temp){
        this.questions = temp.getQuestionList().toArray(new String[0]);
    }

    /**
     * Determine when the Iteration will terminate
     * @return false when the Iteration reaches an end
     */
    @Override
    public boolean hasNext() {
        return index < this.questions.length;
    }

    /**
     * Get the next element in the questionList
     * @return the next element in the questionList
     */
    @Override
    public String next() {
        if(this.hasNext()){
            return questions[index++];
        }
        return null;
    }
}
