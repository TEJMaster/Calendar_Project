package SendMail;

/**
 * A Controller Class that can call the JavaMailUtil use case class to send an email given a username.
 * @author Group0031
 */
public class EmailSender{
    private final JavaMailUtil javaMailUtil;
    /**
     * Creates an EmailSender that stores javaMailUtil and can call the method SendMail in javaMailUtil.
     */
    public EmailSender(){
        this.javaMailUtil = new JavaMailUtil();
    }
    /**
     * Send email to the given userName to inform the user with his temporary password.
     * @param userName A String representing the given username.
     * @throws Exception Exceptions mentioned in the sendMail method in JavaMailUtil class.
     */
    public void sendMail(String userName) throws Exception{
        this.javaMailUtil.SendMail(userName);
    }
}