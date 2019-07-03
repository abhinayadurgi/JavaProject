package fr.epita.quiz.laucher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import fr.epita.quiz.exception.BadInputException;
import fr.epita.quiz.exception.DataAccessException;
import fr.epita.quiz.service.ConfigurationService;
import fr.epita.quiz.userpages.StudentDisplay;
import fr.epita.quiz.userpages.TeacherDisplay;

/**
 * First Display page 
 * Contains the main method
 * @author Durgi
 *
 */
public class WelcomePage {
	
	
	static String adminLogin = ConfigurationService.getInstance()
			.getConfigurationValue("admin.login","");
	static String adminPassword = ConfigurationService.getInstance()
			.getConfigurationValue("admin.password","");
	
	
	private   boolean authenticate(Scanner scanner) {
		System.out.println("Please enter your login : ");
		String login = scanner.nextLine();
		System.out.println("Please enter your password : ");
		String password = scanner.nextLine();
		return adminLogin.equals(login) && adminPassword.equals(password);
	}
	
	/**
	 * First menu
	 *Common to both student and teacher
	 *Calls the login method for user based on the choice
	 *For student, there is no password. 
	 *Teacher logins in login and password
	 *Checks if the properties file is properly initialized
	 * @throws DataAccessException The exception that is thrown when a problem occurs connecting to the database
	 */
	public  void displayWelcomeMenu(Scanner scanner) throws SQLException, DataAccessException, BadInputException, IOException
	{
		ConfigurationService conf = ConfigurationService.getInstance();
		boolean confInit = conf.isInit();
		if (!confInit) {
			System.out.println("problem while initializing the conf");
			return;
		}
		
		System.out.println("WELCOME");
		System.out.println("ARE  YOU:");
		System.out.println("1. Student");
		System.out.println("2. Teacher");
		int userChoice = Integer.parseInt(scanner.nextLine());
		
		switch (userChoice) {
		case(1):
		{
			StudentDisplay studentdisplay = new StudentDisplay();
			studentdisplay.studentMenu();
		}
		break;
		case(2):
		{
			if(authenticate(scanner))
			{
			TeacherDisplay teacherdisplay = new TeacherDisplay();
			teacherdisplay.teacherMenu();
			}
			else
			{
				System.out.println("Invalid Login / Password");
				displayWelcomeMenu(scanner);
			}
		}
		break;
		default:
		{
			System.out.println("Enter a valid value");
			displayWelcomeMenu(scanner);
		}
		}
	}

	/**
	 * The main method of the application 
	 *  Displays the Welcome menu
	 */
	
	public static void main(String[] args) throws SQLException, DataAccessException, BadInputException, IOException {
		
		Scanner scanner = new Scanner(System.in);  
		WelcomePage welcomePage = new WelcomePage();
		welcomePage.displayWelcomeMenu(scanner);
}
}
		
	


