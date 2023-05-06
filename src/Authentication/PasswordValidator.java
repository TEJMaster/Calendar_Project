package Authentication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * A class implements Validator interface, can check if the password from user input is valid.
 * @author Group0031
 */
public class PasswordValidator implements Validator{
    /**
     * Checks if the given password is strong enough to be considered as a weak/good password instead of a too weak one.
     * @param password A String representing the password given by user input
     * @return true if the password is valid (can be considered as a weak/good password instead of a too weak one).
     */
    @Override
    public boolean validate(String password){
        return passwordStrength(password) >= 1;
    }

    /**
     * Returns a message which needs as a prompt for user when they are asked to enter password
     * @return A String that describes what password is considered as too weak/weak/good.
     */
    @Override
    public String getDescription(){
        return "Password is good if it contains at least 1 capital letter, " +
                "at least one lowercase letter, at least 1 number, at least one special character," + "\n" +
                "and is at least 10 characters long. " +
                "Password is weak if it contains at least one lowercase letter, at least one number," +
                " and is at least 8 characters long. " + "\n" +
                "Otherwise, password is considered too weak." +
                " You need to have at least a weak password.";
    }

    /**
     * Get the password's strength given a password entered by the user.
     * @param password A String representing the password the user entered as an input.
     * @return An int representing the strength of the password, 2 means good, 1 means weak, 0 means too weak.
     */
    public int passwordStrength(String password){
        String good = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^0-9a-zA-Z])(?=\\S+$).{10,}$";
        String weak = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        Pattern pattern1 = Pattern.compile(good);
        Pattern pattern2 = Pattern.compile(weak);
        Matcher matcher1 = pattern1.matcher(password);
        Matcher matcher2 = pattern2.matcher(password);
        if(matcher1.matches()){
            System.out.println("Password is good.");
            return 2; // Good
        }
        else if (matcher2.matches()){
            System.out.println("Password is weak, you can change it later.");
            return 1; // Weak
        }
        else{
            return 0; // Too weak
        }
    }
}