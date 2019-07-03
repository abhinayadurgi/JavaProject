package fr.epita.quiz.userpages;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.datamodel.Answer;
import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.Quiz;
import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.exception.BadInputException;
import fr.epita.quiz.exception.CreateFailedException;
import fr.epita.quiz.exception.DataAccessException;
import fr.epita.quiz.exception.ReadFailedException;
import fr.epita.quiz.exception.SearchFailedException;
import fr.epita.quiz.laucher.WelcomePage;
import fr.epita.quiz.service.StudentJDBCDAO;
import fr.epita.quiz.service.AnswerJDBCDAO;
import fr.epita.quiz.service.DisplayMenus;
import fr.epita.quiz.service.FilesUtil;
import fr.epita.quiz.service.MCQChoiceJDBCDAO;
import fr.epita.quiz.service.MCQQuestionJDBCDAO;
import fr.epita.quiz.service.QuestionJDBCDAO;
import fr.epita.quiz.service.QuizJDBCDAO;


/**Class with methods for student activity 
 * @author Durgi
 *
 */
public class StudentDisplay {
	Scanner scanner = new Scanner(System.in);
	
	String fileContent;
	TeacherDisplay teacherdisplay = new TeacherDisplay();
	DisplayMenus displaymenus = new DisplayMenus();
	FilesUtil fileWriter = new FilesUtil();
	WelcomePage welcomePage = new WelcomePage();
	
	 static Student student = new Student ();
	 
	 
	 StudentJDBCDAO  studentdao = StudentJDBCDAO.getInstance();
	 QuizJDBCDAO quizdao = QuizJDBCDAO.getInstance();
	 QuestionJDBCDAO questiondao = QuestionJDBCDAO.getInstance();
	 AnswerJDBCDAO answerdao = AnswerJDBCDAO.getInstance();
	 MCQQuestionJDBCDAO mcqquestiondao = MCQQuestionJDBCDAO.getInstance(); 
	 MCQChoiceJDBCDAO mcqchoicedao= MCQChoiceJDBCDAO.getInstance(); 
	 
	 
	 
	 /**
	  *  <h2> Login for an existing user </h2>
	  *  <p> Checks if the user already exists </p>
	 * @return returns 1 if the user login is successful; 0 if the login fails
	 * @throws SearchFailedException  The exception that is thrown a problem occurs in fetching user details from database
	 */
	public  int  studentLogin() throws SQLException, IOException, SearchFailedException{
			int init = 0;
			System.out.println("Enter name:");
			String name = scanner.nextLine();
			student.setName(name);
			try {
			student = studentdao.searchStudent(student);
			if (student.getName() == null)
			{
				System.out.println("User not found. Please sign up");
			}
			else
			{
			System.out.println("Welcome  " + student.getName());
			fileContent = "Name: " + student.getName();
			fileWriter.writeToTextFile(fileContent);
			init = 1;
			}
		} catch(SQLException sqle) {
			System.out.println("Problem in logging in");
		}
			return init;
	}
	 
	 /** <h2> Sign Up for a new user </h2>
	  * <p> Adds a new user </p>
	 * @throws SearchFailedException The exception that is thrown if a problem occurs in fetching user details from database
	 * @throws CreateFailedException The exception that is thrown if the new user is not created
	 */
	public  void studentSignUp() throws SQLException, IOException, SearchFailedException, CreateFailedException {
			
			System.out.println("Enter name:");
			String name = scanner.nextLine();
			student.setName(name);
			Student studentSearchResult = studentdao.searchStudent(student);
			if ( studentSearchResult.getName()  != null)
			{
				System.out.println("Already Exists");
			}
			else {
			studentdao.createStudent(student);
			}
			student = studentdao.searchStudent(student);
			System.out.println("Welcome" + student.getName());
			fileContent = "Name: " + student.getName();
			fileWriter.writeToTextFile(fileContent);
		}
	
	/**
	 * <h2> Method to answer open questions for a quiz </h2>
	 * <p> Displays the open questions corresponding to the chosen quiz</p>
	 * <p>  Puts the answers in the table as the user answers to the questions in the answer table </p>
	 * <p> The answers are recorded with the corresponding quiz_id, question_id and the student_id </p>
	 * <p> Writes the quiz content to a file </p>
	 * @param questionList The list of questions corresponding to the chosen quiz 
	 * @throws CreateFailedException The exception that is thrown when the answer is not stored in the table
	 * @throws ReadFailedException The exception that is thrown when the questions are not fetched from the table
	 */
	public void answerOpenQuestion(List<Question> questionList) throws CreateFailedException, ReadFailedException, SQLException, IOException
	{
		Answer answer = new Answer();
		for(Question question : questionList)
		{
			System.out.println("Question");
			System.out.println(question.getContent());
			fileContent = System.lineSeparator()+"Question : " + question.getContent(); 
			fileWriter.writeToTextFile(fileContent);
			 
			System.out.println("Enter answer");
			String text = scanner.nextLine();
			answer.setText(text);
			fileContent = "Your Answer : "  + text;
			fileWriter.writeToTextFile(fileContent);
			List<Quiz>quizlist = questiondao.getQuizIDFromQuestion(question);
			answerdao.createAnswer(answer, question, student,quizlist.get(0));
			System.out.println("Answer Recorded");
		}
	}
	/**
	 * <h2> Method to answer MCQ questions for a quiz </h2>
	 * <p> Displays  the MCQ  questions corresponding to the chosen quiz</p>
	 * <p>Displays the MCQ choices for the question  </p>
	 * <p> The user answers a question by typing in the number of the answer. Checks for invalid answers and prompts the user to 
	 * enter the correct values </p>
	 * <p> Checks the validity of the answer and keeps track of the score with a counter</p>
	 * <p> Displays the MCQ score at the end </p>
	 * <p> Writes the quiz content to a file </p>
	 * @param mcqQuestionList List of the MCQ Question corresponding to the chosen quiz
	 * @throws ReadFailedException Exception that is thrown if the data is not fetched from the table
	 */
	public void answerMCQQuestion(List<MCQQuestion> mcqQuestionList) throws ReadFailedException, SQLException, IOException
	{
		int counter = 0;
		for (MCQQuestion mcqquestion : mcqQuestionList)
		{
			List<Integer> choiceList = new ArrayList<>();
			int answerChoice =1;
			System.out.println(mcqquestion.getContent());
			fileContent = System.lineSeparator() + mcqquestion.getContent();
			fileWriter.writeToTextFile(fileContent);
			List<MCQChoice> mcqchoicelist =mcqchoicedao .getMCQChoiceByID(mcqquestion);
			int i = 1;
			for(MCQChoice choice :mcqchoicelist )
			{ 
				System.out.println(i +" . " +choice.getChoice());
				fileContent = i + "." + choice.getChoice();
				fileWriter.writeToTextFile(fileContent);
				choiceList.add(i);
				i++;
			}
			do {
			System.out.println("Enter the answer number from the choice list");
			try {
			answerChoice = Integer.parseInt(scanner.nextLine());
			}
			catch ( NumberFormatException nfe) {
				System.out.println("Incorrect Value"); 
				answerChoice = 0;
				}
			} while (!choiceList.contains(answerChoice));
			fileContent = "Your answer : " + String.valueOf(answerChoice);
			fileWriter.writeToTextFile(fileContent);
			Boolean validity = mcqchoicelist.get(answerChoice-1).isValid();
			if(validity)
			{
				counter++;
			}
		}
		System.out.println("Your MCQ score is " + counter);
		fileContent = System.lineSeparator()+ "Your MCQ  score : " + String.valueOf(counter);
		fileWriter.writeToTextFile(fileContent);
	}
	
	/**
	 * <h2> Method to take a quiz from a list of quizzes </h2>
	 * <p> Displays a list of the available quizzes </p>
	 * <p> The user chooses the quiz by entering the quiz number </p> 
	 * @throws DataAccessException The exception that is thrown if there is a problem  with the access to the table
	 * @throws BadInputException 
	 */
	public void takeQuizByQuizList() throws SQLException, DataAccessException, IOException, BadInputException {
		Quiz quiz = new Quiz();
		System.out.println("QUIZ LIST");
		displaymenus.generateQuizList();
		try
		{
		System.out.println("Which quiz would you like to take today?");
		int quizChoice = Integer.parseInt(scanner.nextLine());
		quiz.setId(quizChoice);
		if( teacherdisplay.checkIfQuizIDExists(quiz)) {
		List<Question>questionList = questiondao.readQuestionByQuizID(quiz);
		if(!questionList.isEmpty()) {
			answerOpenQuestion(questionList);
		}

			List<MCQQuestion> mcqquestionlist = mcqquestiondao.readMCQQuestionsByQuizID(quiz);
			if(!mcqquestionlist.isEmpty()) {
				answerMCQQuestion(mcqquestionlist);
		}
			System.out.println("END OF QUIZ");
		}
		else
		{
			System.out.println("Enter Valid Quiz ID");
			takeQuizByQuizList();
		}
		}
		catch(Exception e)
		{
			throw new BadInputException("Enter the correct value");
		}
		finally {
			choiceMenu();
		}
	}
	
	/**
	 * <h2> Method to take a quiz based on topics </h2>
	 * <p> User enters the topics  </p>
	 * <p> This method generates the quiz based on the given topics </p> 
	 * @throws DataAccessException  The exception that is thrown if there is a problem  with the access to the table
	 * @throws BadInputException 
	 */
	public void takeQuizByTopics() throws SQLException, DataAccessException, IOException, BadInputException {
		boolean questionsExist = false;
		System.out.println("Enter the topics separated by commas");
		String topics  = scanner.nextLine();
		List<Question> questionlist = questiondao.readQuestionsByTopics(topics);
		
		if(!questionlist.isEmpty()) {
			questionsExist = true;
		answerOpenQuestion(questionlist);
		}
		
		List<MCQQuestion> mcqquestionlist = mcqquestiondao.readMCQQuestionsByTopics(topics);
		if(!mcqquestionlist.isEmpty())
		{
			questionsExist = true;
			answerMCQQuestion(mcqquestionlist);
		}
		if (!questionsExist)
		{
			System.out.println("There are no questions for this topic! ");
		}
		System.out.println("END OF QUIZ");
		choiceMenu();
	}
	
	/**
	 * <h2> Student  first menu </h2>
	 * <p> Menu for a user to login / sign up </p>
	 * @throws DataAccessException  The exception that is thrown if there is a problem  with the access to the table
	 * @throws BadInputException 
	 */
	public void studentMenu() throws SQLException, DataAccessException, IOException, BadInputException {
		
	System.out.println("WELCOME\n  ARE  YOU: \n 1.EXISTING USER \n 2.NEW USER \n ENTER THE CHOICE 1/2");
	int choice = Integer.parseInt(scanner.nextLine());
	fileWriter.clearFile();
	switch(choice)
	{
	case 1:
	{
		int init = studentLogin();
		if(init == 0)
		{
	studentSignUp();
	}
		choiceMenu();
	}
		break;
	case 2:
	{
		studentSignUp();
		choiceMenu();
	}
		break;
	default:
		System.out.println("Enter a valid choice");
		studentMenu();
	}
	}
	
	/**
	 * <h2> Generates the student choice menu </h2>
	 * <p> A student can take a quiz by topics or choose a quiz from a list of quizzes </p>
	 * @throws SQLException
	 * @throws DataAccessException  The exception that is thrown if there is a problem  with the access to the table
	 * @throws BadInputException 
	 */
	public void choiceMenu() throws SQLException, DataAccessException, IOException, BadInputException {
	System.out.println("What would you like to do?");
	System.out.println("1.Take quiz by topics");
	System.out.println("2.See the quiz list");
	System.out.println("3. Sign out");
	int menuChoice = Integer.parseInt(scanner.nextLine());
	switch(menuChoice)
	{
	case(1):
	{
		takeQuizByTopics();
	}
	break;
	case(2):
	{
		takeQuizByQuizList();
	}
	break;
	case(3):
	{
		System.out.println("Bye " + student.getName());
		welcomePage.displayWelcomeMenu(scanner);
	}
	break;
	default:
		System.out.println("Enter valid choice");
		choiceMenu();
	}
	
scanner.close();	
}
}



