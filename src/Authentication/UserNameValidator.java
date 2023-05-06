package Authentication;

import java.util.regex.Pattern;
/**
 * A class implements Validator interface, can check if the username from user input is valid.
 * @author Group0031
 */
public class UserNameValidator implements Validator{
    /**
     * Checks if the given username is valid. It is valid only if it's a valid gmail account.
     * @param userName the String username given by user input
     * @return true if the username is valid (is a valid gmail account), otherwise return false.
     */
    @Override
    public boolean validate(String userName){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@gmail.com";
        Pattern pat = Pattern.compile(emailRegex);
        if (userName == null)
            return false;
        return pat.matcher(userName).matches();
    }

    /**
     * Returns a message as a prompt for user when they are asked to enter username
     * @return  message describes what kind of username is a valid username.
     */
    @Override
    public String getDescription(){
        return "Username has to be a gmail account. (e.g. xxxx@gmail.com)";
    }
}