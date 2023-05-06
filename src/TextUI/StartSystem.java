package TextUI;

import java.io.FileNotFoundException;
import java.util.Scanner;

import Calendar.CalendarSystem;
import User.UserSystem;
import Authentication.LoginSystem;
import Authentication.RegistrationSystem;
import Data.InformationReader;
import Data.InformationSaver;

/** A text UI class that initializes the program. It stores an information reader,a  user system, calendar system,
 * login system, registration system and quit system **/
public class StartSystem {
    private final UserSystem userSystem;
    CalendarSystem calendarSystem;
    InformationReader Reader;
    private final LoginSystem loginSystem;
    private final RegistrationSystem registrationSystem;
    private final QuitSystem quitSystem;

    /** Constructs the start system by creating new controllers and gateways. */
    public StartSystem() throws FileNotFoundException {
        this.Reader = new InformationReader();
        this.userSystem = new UserSystem(Reader);
        this.calendarSystem = new CalendarSystem(Reader);
        InformationSaver saver = new InformationSaver(calendarSystem.getCalendarManager(), calendarSystem.getActivityManager(),
                userSystem.getNewUserManager(), userSystem.getNewTemplateManger());
        this.registrationSystem = new RegistrationSystem();
        this.loginSystem = new LoginSystem(userSystem, calendarSystem, saver, registrationSystem, Reader);
        this.quitSystem = new QuitSystem( calendarSystem,userSystem, saver);
    }

    /** the start system gives the user two operations, login or create a new account. If the user input an invalid
     * button the program will ask the user to enter again. */
    public void run() throws Exception {
        Scanner x = new Scanner(System.in);
        while (true) {
        System.out.println("Welcome to our calendar program, press 0 to login or press 1 to create a new account");
        String answer = x.nextLine() ;
        if (answer.equals("0")){
            loginSystem.login(x, quitSystem);
        }
        else if (answer.equals("1")) {
            String type = registrationSystem.GetType(x);
            String Username = registrationSystem.registerUser(x, userSystem, type);
            registrationSystem.RegisterTransition(x, quitSystem, loginSystem, Username);
            return;
        }
        else {System.out.println("invalid button!"); }
        }
    }
}