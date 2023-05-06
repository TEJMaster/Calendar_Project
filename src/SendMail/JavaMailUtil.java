package SendMail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A use case class responsible for sending an email with text message of temporary password to the users when
 * users forgot their passwords. The email will be sent using csc207phase2@gmail.com and the restriction by smtp is
 * that the recipient has also to be a gmail account, which is why the registration system specifies the user to
 * enter gmail account as username. The temporary password is made of capital letter A followed by the part of the
 * username before @gmail.com and followed by number 123. If the user does not change the password afterwards, the
 * password will be set as the temporary password.
 * @author Group0031
 */
public class JavaMailUtil {
    /**
     * Send an email to the given recipient with a message.
     * @param recipient A String representing the recipient (email address).
     * @throws MessagingException an invalid method is invoked on an expunged Message.
     */
    public void SendMail(String recipient) throws MessagingException {
        String tempPassword = "A" + recipient.split("@")[0] + "123";
        System.out.println("Prepare to send email.");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        String myAccountEmail = "csc207phase2@gmail.com";
        String password = "CSC207@phase2";
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Message message = prepareMessage(session, myAccountEmail, recipient, tempPassword);
        assert message != null;
        Transport.send(message);
    }

    /**
     * To prepare a message that is going to be sent to the recipient using myAccountEmail. The message should be about
     * what the temporary password for the user is, with a subject being temporary password for your account.
     * @param session A Session with all the stored properties.
     * @param myAccountEmail A String representing the sender's email address.
     * @param recipient A String representing the recipient's email address.
     * @param tempPassword A String representing the temporary password for the user.
     * @return A Message about what the temporary password for the recipient is with a subject being temporary password
     * for your account.
     */
    private Message prepareMessage(Session session, String myAccountEmail, String recipient, String tempPassword)
    {
        Message message = new MimeMessage(session);
        try{
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Temporary password for you Calendar Account");
            message.setText("Hey, your password to log in is " + tempPassword);
            return message;
        }
        catch(Exception ex){
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}