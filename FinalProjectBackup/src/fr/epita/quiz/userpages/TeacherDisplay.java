package fr.epita.quiz.userpages;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.datamodel.Difficulty;
import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.Quiz;
import fr.epita.quiz.exception.BadInputException;
import fr.epita.quiz.exception.CreateFailedException;
import fr.epita.quiz.exception.DataAccessException;
import fr.epita.quiz.exception.DeleteFailedException;
import fr.epita.quiz.exception.ReadFailedException;
import fr.epita.quiz.exception.UpdateFailedException;
import fr.epita.quiz.laucher.WelcomePage;
import fr.epita.quiz.service.AnswerJDBCDAO;
import fr.epita.quiz.service.DisplayMenus;
import fr.epita.quiz.service.MCQChoiceJDBCDAO;
import fr.epita.quiz.service.MCQQuestionJDBCDAO;
import fr.epita.quiz.service.QuestionJDBCDAO;
import fr.epita.quiz.service.QuizJDBCDAO;

/**
 *  <h2> Class with methods for teacher activity </h2>
 * @author Durgi
 *
 */
public class TeacherDisplay {
	
private  Scanner scanner = new Scanner(System.in);
	
	QuizJDBCDAO quizdao = QuizJDBCDAO.getInstance();
	QuestionJDBCDAO questiondao =QuestionJDBCDAO .getInstance();
	MCQQuestionJDBCDAO mcqquestiondao = MCQQuestionJDBCDAO.getInstance();
	MCQChoiceJDBCDAO mcqchoicedao = MCQChoiceJDBCDAO.getInstance(); 
	AnswerJDBCDAO answerdao = AnswerJDBCDAO.getInstance();
	
	String invalidID = "InvalidID";
	
	DisplayMenus displaymenus = new DisplayMenus();
	WelcomePage welcomePage = new WelcomePage();
	
	/**
	 * <h2> Method to check if a quiz  exists in the table </h2>
	 * @param quiz Quiz search criterion
	 * @return returns true if the quiz title exists; false if it does not
	 * @throws DataAccessException  The exception that is thrown if there is a problem  with the access to the table
	 */
	public boolean checkIfQuizTitleExists(Quiz quiz) throws DataAccessException {
		boolean exists = true;
		String quizTitle = quiz.getTitle();
		List <Quiz> quizList = quizdao.readQuiz();
		List<String> quizTitles = new ArrayList<>();
		for ( Quiz quizobj : quizList)
		{
			quizTitles.add(quizobj.getTitle().toLowerCase());
		}
		if ( !quizTitles.contains(quizTitle.toLowerCase()))
		{
			exists = false;
		}
		return exists;
	}
	
	/**
	 * <h2> Method to check if a quiz ID exists </h2>
	 * @param quiz The quiz object with the ID to check
	 * @return returns true if the quiz ID exists; false if it does not
	 * @throws DataAccessException  The exception that is thrown if there is a problem  with the access to the table
	 */
	public boolean checkIfQuizIDExists(Quiz quiz) throws DataAccessException {
		boolean exists = true;
		int quizID = quiz.getId();
		List<Quiz> quizlist = quizdao.readQuiz();
		List<Integer> quizIDList = new ArrayList<>();
		for (Quiz quizobj : quizlist )
		{
			quizIDList.add(quizobj.getId());
		}
		if(!quizIDList.contains(quizID)) {
			exists = false;
		}
		return exists;
	}
	
	/**
	 * <h2> Method to check if an open question exists </h2>
	 * @param question The question object to check for existence
	 * @return returns boolean true if exists; false if it doesn't
	 * @throws ReadFailedException The exception that is thrown if there is a problem  with the access to the table
	 */
	public boolean checkIfQuestionExists(Question question) throws ReadFailedException, SQLException
	{
		boolean exists = true;
		String questionContent  = question.getContent().toLowerCase();
		List<Question>  questionList = questiondao.readQuestion();
		List<String> questions = new ArrayList<>();
		for (Question questionObj : questionList)
		{
			questions.add(questionObj.getContent().toLowerCase());
		}
		if(!questions.contains(questionContent)) {
			exists = false;
		}
		return exists;
	}
	
	/**
	 *  <h2> Method to check if a question ID exists </h2>
	 * @param question Question search criterion
	 * @return returns true if the question ID exists ; false if it does not
	 * @throws ReadFailedException  The exception that is thrown if there is a problem  with the access to the table
	 */
	public boolean checkIfQuestionIDExists(Question question) throws ReadFailedException, SQLException {
		boolean exists = true;
		int questionId = question.getId();
		List <Question> questionList = questiondao.readQuestion();
		List<Integer> questionIDList = new ArrayList <>();
		for (Question questionobj : questionList)
		{
			questionIDList.add(questionobj.getId());
		}
		if ( !questionIDList.contains(questionId)) {
			exists = false;
		}
		return exists;
	}
	
	/**
	 * <h2> Method to check if an MCQ question exists </h2>
	 * @param question The mcqquestion object to check for existence
	 * @return returns boolean true if exists; false if it doesn't
	 * @throws ReadFailedException The exception that is thrown if there is a problem  with the access to the table
	 * @param mcqquestion
	 * @return returns boolean true if exists; false if it doesn't
	 */
	public boolean checkIfMCQQuestionExists(MCQQuestion mcqquestion) throws ReadFailedException, SQLException
	{
		boolean exists = true;
		String mcqquestionContent = mcqquestion.getContent().toLowerCase();
		List<MCQQuestion> mcqquestionList = mcqquestiondao.selectMCQQuestion();
		List<String> mcqquestions = new ArrayList<>();
		for(MCQQuestion mcqquestionObj : mcqquestionList)
		{
			mcqquestions.add(mcqquestionObj.getContent().toLowerCase());
		}
		if(!mcqquestions.contains(mcqquestionContent)) {
			exists = false;
		}
		return exists;
	}
	
	/**
	 * <h2> Method to check if an MCQ Question ID exists </h2>
	 * @param mcqquestion search criterion
	 * @return returns true if exists; false if it does not
	 * @throws ReadFailedException  The exception that is thrown if there is a problem  with the access to the table
	 */
	public boolean checkIfMCQQuestionIDExists(MCQQuestion mcqquestion) throws ReadFailedException, SQLException {
		boolean exists = true;
		int mcqquestionId = mcqquestion.getId();
		List<MCQQuestion> mcqquestionList = mcqquestiondao.selectMCQQuestion();
		List <Integer> mcqquestionIDList = new ArrayList <>();
		for (MCQQuestion mcqquestionobj : mcqquestionList)
		{
			mcqquestionIDList.add(mcqquestionobj.getId());
		}
		if ( !mcqquestionIDList.contains(mcqquestionId)) {
			exists = false;
		}
		return exists;
	}
	
	/**
	 * <h2> Methods to set the difficulty for a question </h2> 
	 * @return returns the difficulty 
	 */
	public Difficulty setDifficulty()
	{
		Difficulty diff = Difficulty.valueOf("VERY_EASY");
		List < String > difficulty = new  ArrayList <>(Arrays.asList("VERY_EASY","EASY","MEDIUM","HARD","VERY_HARD","EXTREMELY_HARD"));
		System.out.println("The Difficulty levels are:");
		for  ( String s  : difficulty)
		{System.out.println(s); }
		boolean difficultySet = false;
		do {
		System.out.println("Enter the difficulty");
		String d = scanner.nextLine();
		if(difficulty.contains(d.toUpperCase())) {
		diff = Difficulty.valueOf(d.toUpperCase());
		difficultySet = true;
		}
		else { System.out.println("Enter correct value"); }
		}while(!difficultySet);
		return diff;
	}
	
	/**
	 * <h2> Method to set the topics for a question </h2>
	 * @return returns the topics list
	 */
	public  List<String> setTopics()
	{
		List<String> topics = new ArrayList<>();
		int i=1;
		do
		{
		System.out.println("Enter topics  ");
		String topic = scanner.nextLine();
		topics.add(topic);
		System.out.println("More topics? yes/no");
		String topicsChoice =  scanner.nextLine();
		if (!topicsChoice.trim().equals("yes"))
		{
			 i=0;
		}
		}while (i==1);
		return topics;
	}
	
	/**
	 * <h2> Method to create a quiz </h2>
	 * <p> Creates a quiz with a title </p>
	 * <p> Checks if the quiz already exists. If it does not exist, it creates the quiz. </p>
	 * @throws DataAccessException  The exception that is thrown if there is a problem  with the access to the table
	 */
	public void createQuiz() throws DataAccessException
	{
		boolean titleExists;
		String title;
		Quiz quiz = new Quiz();
		System.out.println("Enter the quiz title");
			title = scanner.nextLine();
			quiz.setTitle(title);
			titleExists = checkIfQuizTitleExists(quiz);
			if (!titleExists) {
				quizdao.createQuiz(quiz);
			}
			else {
			System.out.println("Already exists");
			}
	}
	
	/**
	 * <h2> The method to update a quiz </h2>
	 * <p> Updates an existing quiz </p>
	 * <p> Generates the quiz list for the user to choose from </p>
	 * <p> The user chooses the quiz to be updated by entering the quiz number </p>
	 * <p> The user enters the new quiz title </p>
	 * @throws DataAccessException The exception that is thrown if there is a problem  with the access to the table
	 */
	public void updateQuiz( ) throws SQLException, DataAccessException {
		boolean quizIDExists;
		String newTitle;
		Quiz quiz = new Quiz();
		//displays the available quizzes
		displaymenus.generateQuizList();
		System.out.println("Enter the quiz ID to be updated");
		try {
		int quizID = Integer.parseInt(scanner.nextLine());
		quiz.setId(quizID);
		quizIDExists = checkIfQuizIDExists(quiz);
		if ( quizIDExists)
		{
			System.out.println("Enter the new title");
			newTitle = scanner.nextLine();
			quiz.setTitle(newTitle);
			quizdao.updateQuiz(quiz);
		}
		else {
			System.out.println(invalidID);
		}
		}
		catch (NumberFormatException nfe)
		{
			System.out.println(invalidID);
		}
	}
	
	/**
	 * <h2> The method to delete a quiz </h2>
	 * <p> Deletes an existing quiz </p>
	 *  <p> Generates the quiz list for the user to choose from </p>
	 *   <p> The user chooses the quiz to be deleted  by entering the quiz number </p>
	 * <p> Deletes the associated open questions, answers, MCQ Questions and MCQ Choices associated with the quiz </p>
	 * @throws DataAccessException The exception that is thrown if there is a problem  with the access to the table
	 */
	public void deleteQuiz() throws SQLException, DataAccessException
	{
		boolean quizIDExists;
		Quiz quiz = new Quiz();
		displaymenus.generateQuizList();
		try {
		System.out.println("Enter the Quiz ID to be deleted");
		int quizID = Integer.parseInt(scanner.nextLine());
		quiz.setId(quizID);
		quizIDExists = checkIfQuizIDExists(quiz);
		if( quizIDExists)
		{
			answerdao.deleteAnswerByQuizId(quiz);
			questiondao.deleteQuestionByQuizId(quiz);
			List<MCQQuestion> mcqquestionlist = mcqquestiondao.readMCQQuestionsByQuizID(quiz);
			if(!mcqquestionlist.isEmpty()) {
			for(MCQQuestion mcqquestion : mcqquestionlist)
			{
				mcqchoicedao.deleteMCQChoiceByQuestionID(mcqquestion);	
				System.out.println("MCQ choices deleted");
			}
			mcqquestiondao.deleteMCQQuestionByQuizId(quiz);
		}
			quizdao.deleteQuiz(quiz);
		}
		else {
			System.out.println(invalidID);
		}
		}
		catch(NumberFormatException nfe)
		{
			System.out.println(invalidID);
		}
	}
	
	/**
	 * <h2> Method to create an open question </h2>
	 * <p>  Creates an open question in a quiz </p>
	 * <p> Displays the list of available quizzes for the user to choose from </p>
	 * <p> The user chooses the quiz by entering the quiz number </p>
	 * <p> Takes the question and check if the question already exists. If it does, the question is not created</p>
	 *  <p> If it doesn't, gets the topics and difficulty from the user and creates the question</p>
	 * @throws DataAccessException The exception that is thrown if there is a problem  with the access to the table
	 * @throws NumberFormatException The exception that is thrown when the user does not input the correct choice </p>
	 */
	public void createOpenQuestion() throws SQLException, DataAccessException,NumberFormatException {
		boolean quizIDExists;
		boolean questionExists;
		int quizId;
		String text;
		Quiz quiz = new Quiz();
		Question question = new Question();
		List<String> topics = new ArrayList<>();
		displaymenus.generateQuizList();
		try {
		System.out.println("Enter the quiz ID for the question");
		quizId = Integer.parseInt(scanner.nextLine());
		quiz.setId(quizId);
		quizIDExists = checkIfQuizIDExists(quiz);
		if(quizIDExists)
		{
		System.out.println("Enter the open question");
		text = scanner.nextLine();
		question.setContent(text);
		questionExists = checkIfQuestionExists(question);
		if(!questionExists)
		{
		//topics
		topics = setTopics();
		//difficulty
		Difficulty diff = setDifficulty();
		question.setDifficulty(diff.toInteger());
		question.setContent(text);
		question.setTopics(topics);
		questiondao.createQuestion(question, quiz);
		}
		else
		{
			System.out.println("This open question already exists");
		}
		}
		else { System.out.println(invalidID);}
	}
		catch(NumberFormatException nfe)
		{
			System.out.println(invalidID);
		}
	}
	
	
	/**
	 * <h2> The method to update an open question </h2>
	 * <p> Updates an existing open question </p>
	 * <p> Generates the open question list for the user to choose from </p>
	 * <p> The user chooses the question to be updated by entering the question number </p>
	 * <p> The user enters the new question </p>
	 * @throws DataAccessException The exception that is thrown if there is a problem  with the access to the table
	 * @throws ReadFailedException The exception that is thrown when there is a problem to fetch the data
	 * @throws UpdateFailedException The exception that is thrown when the quiz is not updated
	 */
	public void updateOpenQuestion() throws ReadFailedException, SQLException, UpdateFailedException {
		boolean openQuestionIDExists;
		int id;
		String updatedQuestion;
		Question question = new Question();
		 displaymenus.generateQuestionList();
		 try
		 {
		 System.out.println("Enter the question ID to be updated");
		 id = Integer.parseInt(scanner.nextLine());
		 question.setId(id);
		 openQuestionIDExists = checkIfQuestionIDExists(question);
		 if(openQuestionIDExists) {
			 System.out.println("Enter the new question");
			 updatedQuestion = scanner.nextLine();
			 question.setContent(updatedQuestion);
			 questiondao.updateQuestion(question);
		 }
		 else {
			 System.out.println(invalidID);
		 }
		 }
		 catch(NumberFormatException nfe)
		 {
			 System.out.println(invalidID);
		 }
	}
	
	/**
	 * <h2> The method to delete an open question </h2>
	 * <p> Deletes an existing question </p>
	 *  <p> Generates the question list for the user to choose from </p>
	 *   <p> The user chooses the question to be deleted  by entering the question number </p>
	 * <p> Deletes the associated answers with the quiz </p>
	 * @throws DataAccessException The exception that is thrown if there is a problem  with the access to the table
	 * @throws ReadFailedException The exception that is thrown when there is a problem to fetch the data
	 * @throws DeleteFailedException The exception that is thrown when the question is not updated
	 */
	public void deleteOpenQuestion() throws ReadFailedException, SQLException, DeleteFailedException {
		boolean openQuestionIDExists;
		int id;
		Question question = new Question();
		displaymenus.generateQuestionList();
		System.out.println("Enter the question ID to be deleted");
		try {
		id = Integer.parseInt(scanner.nextLine());
		question.setId(id);
		openQuestionIDExists = checkIfQuestionIDExists(question);
		if(openQuestionIDExists) {
			answerdao.deleteAnswerByQuestionId(question);
			questiondao.deleteQuestionByQuestionID(question);
		}
		else {
			System.out.println(invalidID);
		}
	}
		catch(NumberFormatException nfe)
		{
			System.out.println(invalidID);
		}
		}
	
	/**
	 *  Method to create an MCQ  question </h2>
	 * <p>  Creates an MCQ question in a quiz </p>
	 * <p> Displays the list of available quizzes for the user to choose from </p>
	 * <p> The user chooses the quiz by entering the quiz number </p>
	 * <p> Checks if the quiz Id is valid </p>
	 * <p> If yes, prompts for the question</p>
	 * <p> Checks if the question already exists. If it does not already exist, it continues </p>
	 * <p> Takes the question, topics and difficulty from the user </p>
	 * <p> Creates the MCQ choices for the question </p>
	 * <p> An MCQ question   should have more than one choice and  one valid choice </p> 
	 * @throws DataAccessException The exception that is thrown if there is a problem  with the access to the table
	 * @throws NumberFormatException The exception that is thrown when the user does not input the correct choice </p>
	 */
	public void createMCQQuestion() throws SQLException, DataAccessException {
		boolean quizIDExists;
		boolean mcqquestionExists;
		int quizID;
		String question;
		Quiz quiz = new Quiz();
		MCQQuestion mcqquestion = new MCQQuestion();
		displaymenus.generateQuizList();
		System.out.println("Enter the Quiz ID to add the MCQ Question in");
		try {
		quizID = Integer.parseInt(scanner.nextLine());
		quiz.setId(quizID);
		quizIDExists = checkIfQuizIDExists(quiz);
		if(quizIDExists) {
			System.out.println("Enter the question");
			question  = scanner.nextLine();
			mcqquestion.setContent(question);
			mcqquestionExists = checkIfMCQQuestionExists(mcqquestion);
			if(!mcqquestionExists)
			{
			//topics
			List <String> topics = setTopics();
			//difficulty
			Difficulty diff = setDifficulty();
			
			mcqquestion.setDifficulty(diff.toInteger());
			mcqquestion.setContent(question);
			mcqquestion.setTopics(topics);
			mcqquestiondao.createMCQQuestion(mcqquestion, quiz);
			
			MCQQuestion  mcqquestionID = mcqquestiondao.searchMCQQuestion(mcqquestion);
			int x=1;
			int validCounter = 0;
			int choiceCounter = 0;
			List<MCQChoice> mcqChoiceList = new ArrayList<>();
			do
			{
				System.out.println("Enter the choice");
				String choiceOption  = scanner.nextLine();
				MCQChoice mcqChoice = new  MCQChoice();
				mcqChoice.setChoice(choiceOption);
				System.out.println("Enter the validity true/false");
				Boolean validity  = Boolean.valueOf(scanner.nextLine());
				if(validity) { validCounter ++; }
				if ((validity) && validCounter > 1)
				{
					System.out.println("Enter only one valid choice");
					continue;
				}
				mcqChoice.setValid(validity);
				mcqChoiceList.add(mcqChoice);
				choiceCounter ++;
				System.out.println("More Choices? yes/no");
				String option  = scanner.nextLine();
				if  ( (option.trim().equals("no") ) &&  (validCounter <  1) )
				{
					System.out.println("Enter  one valid option");
					x = 1;
				}
				else if ((option.equals("no"))  && (choiceCounter < 2)) 
				{
					System.out.println("Enter atleast 2 choices");
					x =1;
				}
				else if (option.equals("yes"))
				{
					x=1;
				}
				else
				{
					x = 0;
				}
		}while(x==1);
			mcqchoicedao.createMCQChoice(mcqChoiceList, mcqquestionID);
		}
		
		else {
			System.out.println("This mcq question laready exists");
		}
		}
		else {
			System.out.println("This Quiz ID is not valid");
		}
		}
		catch(NumberFormatException nfe)
		{
			System.out.println(invalidID);
		}
	}
	
	  /**
	 * <h2> The method to update an MCQ question </h2>
	 * <p> Updates an existing MCQ  question </p>
	 * <p> Generates the MCQ question list for the user to choose from </p>
	 * <p> The user chooses the question to be updated by entering the question number </p>
	 * <p> The user enters the new question </p>
	 * @throws ReadFailedException The exception that is thrown when there is a problem to fetch the data
	 * @throws UpdateFailedException The exception that is thrown when the quiz is not updated
	 */
	public void updateMCQQuestion() throws ReadFailedException, SQLException, UpdateFailedException {
		boolean mcqQuestionIDExists;
		int id;
		String updatedQuestion;
		MCQQuestion mcqquestion = new MCQQuestion();
		displaymenus.generateMCQQuestionList();
		System.out.println("Enter the MCQ question ID to be updated");
		try {
		id = Integer.parseInt(scanner.nextLine());
		mcqquestion.setId(id);
		mcqQuestionIDExists = checkIfMCQQuestionIDExists(mcqquestion);
		if(mcqQuestionIDExists) {
			System.out.println("Enter the updated question");
			updatedQuestion = scanner.nextLine();
			mcqquestion.setContent(updatedQuestion);
			mcqquestiondao.updateMCQQuestion(mcqquestion);
		}
		else {
			System.out.println(invalidID);
		}
	}
		catch(NumberFormatException nfe)
		{
			System.out.println(invalidID);
		}
	}
	
	/**
	 * <h2> The method to delete an MCQ Question </h2>
	 * <p> Deletes an existing MCQ Question </p>
	 *  <p> Generates the MCQ Question  list for the user to choose from </p>
	 *   <p> The user chooses the question to be deleted  by entering the quiz number </p>
	 * <p> Deletes the associated MCQ choices </p>
	 * @throws ReadFailedException The exception that is thrown when there is a problem to fetch the data
	 * @throws DeleteFailedException The exception that is thrown when the question is not updated
	 */
	public void deleteMCQQuestion() throws ReadFailedException, SQLException, DeleteFailedException
	{
		boolean mcqQuestionIDExists;
		int id;
		MCQQuestion mcqquestion = new MCQQuestion();
		displaymenus.generateMCQQuestionList();
		try
		{
		System.out.println("Enter the MCQ question ID to be deleted");
		id = Integer.parseInt(scanner.nextLine());
		mcqquestion.setId(id);
		mcqQuestionIDExists = checkIfMCQQuestionIDExists(mcqquestion);
		if(mcqQuestionIDExists) {
			mcqchoicedao.deleteMCQChoiceByQuestionID(mcqquestion);
			mcqquestiondao.deleteMCQQuestionByID(mcqquestion);
		}
		else {
			System.out.println(invalidID);
		}
	}
		catch(NumberFormatException nfe)
		{
			System.out.println(invalidID);
		}
	}
	
	/**
	 * <h2> Method that generates the menu for teacher activities </h2>
	 * <p> The teacher chooses an activity from the list by typing in the number of the choice </p>
	 * @throws DataAccessException The exception that is thrown if there is a problem  with the access to the table
	 */
	public void teacherMenu() throws SQLException, DataAccessException, BadInputException, IOException {
		int choice;
		System.out.println("Menu");
		System.out.println("1.Create a quiz");
		System.out.println("2. Update a quiz");
		System.out.println("3. Delete a quiz");
		System.out.println("4. Create an open question in a quiz");
		System.out.println("5. Update an open question");
		System.out.println("6. Delete an open question");
		System.out.println("7.  Create an  MCQQuestion in a quiz ");
		System.out.println("8. Update an MCQ Question");
		System.out.println("9. Delete an MCQ Question");
		System.out.println("10. Sign out");
		
		
		System.out.println("Enter choice");
		choice  = Integer.valueOf(scanner.nextLine());
		
		switch(choice)
		{
		case(1):
		{
			createQuiz();
			teacherMenu();
		}
		break;
		case(2):
		{
			updateQuiz();
			teacherMenu();
		}
			break;
		case(3):
		{
			deleteQuiz();
			teacherMenu();
		}
		break;
		case(4):
		{
			createOpenQuestion();
			teacherMenu();
		}
		break;
		case(5):
		{
			updateOpenQuestion();
			teacherMenu();
		}
		break;
		case(6):
		{
			deleteOpenQuestion();
			teacherMenu();
		}
		break;
		case(7):
		{
			createMCQQuestion();
			teacherMenu();
		}
		break;
		case(8):
		{
			updateMCQQuestion();
			teacherMenu();
		}
		break;
		case(9):
		{
			deleteMCQQuestion();
			teacherMenu();
		}
		break;
		case(10):
		{
			System.out.println("Bye");
			welcomePage.displayWelcomeMenu(scanner);
		}
		default:
			System.out.println("Enter a valid option");
		}
		scanner.close();
	}
	
	}

