package Authentication;

/**
 * An interface which provides the validator for username and password
 * @author Group0031
 */
interface Validator {
    /**
     * Check if the given string (str) is valid.
     * @param str A String that is given.
     * @return true the String is considered valid.
     */
    boolean validate(String str);

    /**
     * Gets the description of how the string is considered as valid.
     * @return a string representing how an input string is considered as valid.
     */
    String getDescription();
}
