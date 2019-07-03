package fr.epita.quiz.service;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.Quiz;
import fr.epita.quiz.exception.CreateFailedException;
import fr.epita.quiz.exception.DeleteFailedException;
import fr.epita.quiz.exception.ReadFailedException;
import fr.epita.quiz.exception.SearchFailedException;
import fr.epita.quiz.exception.UpdateFailedException;
//import fr.epita.quiz.tests.ConfigurationService;

/**
 * The JDBCDAO class that connects to the Question table
 * @author Durgi
 *
 */
public class QuestionJDBCDAO {
	
	private static QuestionJDBCDAO  instance;
	
	private QuestionJDBCDAO(){
		
	}
	
	/**
	 * @return returns the QuestionJDBCDAO instance
	 */
	public static QuestionJDBCDAO getInstance() {
		if(instance == null)
		{
			instance = new QuestionJDBCDAO();
		}
		return instance;
	}
	
	private Connection getConnection() throws SQLException {
		ConfigurationService conf = ConfigurationService.getInstance();
		String username = conf.getConfigurationValue("db.username", "");
		String password = conf.getConfigurationValue("db.password", "");
		String url = conf.getConfigurationValue("db.url", "");
		Connection connection = DriverManager.getConnection(url, username, password);
		return connection;
	}
	
	/**
	 *  <h2> Creates a Question in the database </h2>
	 * <p> If a problem occurs throws a {@link} CreateFailedException </p>
	 * usage example : QuestionJDBCDAO questiondao = new... try{ questiondao.createQuestion
	 * (QuestionInstance, QuizInstance)};
	 * catch (CreateFailed e)
	 * @param question The question that is created
	 * @param quiz The quiz associated with the question
	 * @throws CreateFailedException The exception that is thrown when the question is not created
	 */
	public  void createQuestion(Question question,Quiz quiz) throws SQLException, CreateFailedException
	{
		int quiz_id = quiz.getId();
		String text = question.getContent();
		List<String>topics = question.getTopics();
		String[] topics_array = topics.toArray(new String[topics.size()]);
		Integer diff=question.getDifficulty();
		String createQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.question.createQuery","");
		try(Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(createQuery);){
		pstmt.setInt(1,quiz_id);
		pstmt.setString(2,text);
		Array aArray = connection.createArrayOf("VARCHAR", topics_array);
		pstmt.setArray(3, aArray);
		pstmt.setInt(4, diff);
		 pstmt.executeUpdate();
		}catch(SQLException sqle)
		{
			throw new CreateFailedException(question);
		}
	}
	
	/**
	 *  <h2> Updates a Question </h2>
	 * <p> If a problem occurs throws the {@link} UpdateFailedException </p>
	 * usage example : QuestionJDBCDAO questiondao = new .. try { questiondao.updateQuestion(QuestionInstance);}
	 * catch ( UpdateFailed e ) 
	 * @param question The question that is updated
	 * @throws UpdateFailedException The exception that is thrown when the question is not updated
	 */
	public  void updateQuestion(Question question) throws SQLException, UpdateFailedException {
		int questionID = question.getId();
		String updatedQuestion = question.getContent();
		String updateQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.question.updateQuestion","");
		try(Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(updateQuery);){
			pstmt.setString(1,updatedQuestion);
			pstmt.setInt(2,questionID);
			pstmt.executeUpdate();
		}catch(SQLException sqle)
		{
			throw new UpdateFailedException(question);
		}
	}
	
	/** <h2> Gets the Quiz associated to the question </h2>
	 * @param question The question associated with the quiz
	 * @return quizlist returns the quiz that is associated with the question
	 * @throws ReadFailedException The exception that is thrown when the there is a problem getting the questions list
	 */
	public  List<Quiz> getQuizIDFromQuestion(Question question) throws SQLException, ReadFailedException {
		String getQuizFromQuestionQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.question.searchQuizByQuestion","");
		List<Quiz> quizlist =  new ArrayList<Quiz>();
		try(Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(getQuizFromQuestionQuery);){
		String search_question= question.getContent();
		pstmt.setString(1,search_question);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int id = rs.getInt("quiz_id");
			Quiz quiz = new Quiz();
			quiz.setId(id);
			quizlist.add(quiz);
		}
		}catch(SQLException sqle)
		{
			throw new ReadFailedException();
		}
	return quizlist;
	}
	
	/**
	 * <h2> gets a list of  questions </h2>
	 *   <p> If a problem occurs throws {@link} ReadFailedException </p>
	 *  usage example : QuestionJDBCDAO uestiondao = new .. try{ questiondao.readQuestion();}
	 * catch (ReadFailed e) 
	 * @return questionlist returns a list of questions
	 * @throws ReadFailedException The exception that is thrown when the there is a problem getting the questions list
	 */
	public List<Question> readQuestion() throws SQLException, ReadFailedException{
		String readQuestionQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.question.readQuestion","");
		List<Question> questionlist =  new ArrayList<Question>();
		try(Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(readQuestionQuery);){
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int id = rs.getInt("question_id");
			String question_content = rs.getString("question");
			Question question = new Question();
			question.setId(id);
			question.setContent(question_content);
			questionlist.add(question);
		}
		rs.close();
		}catch(SQLException sqle)
		{
			throw new ReadFailedException();
		}
		return questionlist;
		}
	
	/**
	 * <h2> Gets a list of  Questions for a quiz </h2>
	 * <p> If a problem occurs throws {@link} ReadFailedException </p>
	 * usage example : QuestionJDBCDAO questiondao = new .. try{ questiondao.readQuestionByQuizID(QuizInstance);}
	 * catch (ReadFailed e) 
	 * @param quiz The quiz associated with the questions
	 * @return questionlist returns a list of questions associated with the quiz
	 * @throws ReadFailedException The exception that is thrown when the there is a problem getting the questions list
	 */
	public List<Question> readQuestionByQuizID(Quiz quiz) throws SQLException, ReadFailedException {
		List<Question>questionlist = new ArrayList<Question>();
		int quiz_id = quiz.getId();
		String selectByQuizIDQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.question.selectByQuizID", "");
		try(Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(selectByQuizIDQuery);){
		pstmt.setInt(1,quiz_id);
		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("question_id");
			String text = rs.getString("question");
			Question question = new Question();
			question.setId(id);
			question.setContent(text);
			questionlist.add(question);	
		}
		rs.close();
		}catch(SQLException sqle) {
			throw new ReadFailedException();
		}
		return questionlist;
	}
	
	/**
	 *   <h2> Gets a list of Questions for given topics </h2>
	 *   <p> The topics given by the user are used as search criterion to select the questions </p>
	 *   <p> A question may be related to 2 topics and can be fetched twice. To eliminate duplicates, the question_id when fetched 
	 *   is stored in a Hashset. If a question is fetched the second time, it is ignored</p>
	 * <p> If a problem occurs throws {@link} ReadFailedException </p>
	 * usage example : QuestionJDBCDAO questiondao = new .. try{ questiondao.readQuestionsByTopics(Topics);}
	 * catch (ReadFailed e) 
	 * @param topics the topics associated with the Questions
	 * @return questionlist returns a list of questions 
	 * @throws ReadFailedException The exception that is thrown when the there is a problem getting the questions list
	 */
	public List<Question>  readQuestionsByTopics(String topics) throws SQLException, ReadFailedException {
		String[] tempArray;
		String delimiter = ",";
		tempArray = topics.split(delimiter);
		String readQuestionByTopicsQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.question.selectByTopics","");
		// To eliminate Duplicates
		HashSet<Integer> id_list=new HashSet<Integer>();
		List<Question> questionlist = new ArrayList<Question>();
		for (int i = 0; i < tempArray.length; i++) {
			
			String search_word = "%" + tempArray[i] + "%";
			try(Connection connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(readQuestionByTopicsQuery);){
			pstmt.setString(1,search_word.toLowerCase());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				int id = rs.getInt("question_id");
				String text = rs.getString("question");
				if(!id_list.contains(id)) {
				Question question = new Question();
				question.setId(id);
				question.setContent(text);
				questionlist.add(question);
				id_list.add(id);
				}
			}
			rs.close();
		}catch(SQLException sqle)
			{
			throw new ReadFailedException();
			}
		}
		return questionlist;
	}
	
	/**
	 *  <h2> Deletes the Questions  of a quiz </h2>
	 *  <p> This method is called when a quiz  is deleted </p>
	 *  <p> When a quiz  is deleted, all the  Questions  associated with the quiz  are deleted to preserve the referential 
	 *  integrity </p>
	 *  <p> If a problem occurs throws the {@link} DeleteFailedException </p>
	 *  usage example : QuestionJDBCDAO questiondao = new .. try{ questiondao.deleteQuestionByQuizId(QuizInstance);}
	 * catch (ReadFailed e) 
	 * @param quiz that quiz associated with the questions
	 * @throws DeleteFailedException The exception that is thrown when the  Question is not deleted
	 */
	public void deleteQuestionByQuizId(Quiz quiz) throws SQLException, DeleteFailedException {
		int quiz_id = quiz.getId();
		String deleteByQuizIDQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.question.deleteByQuizIdQuery", "");
		try(Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(deleteByQuizIDQuery);){
		pstmt.setInt(1,quiz_id);
		pstmt.executeUpdate();
	}catch(SQLException sqle)
		{
		throw new DeleteFailedException();
		}
	}
	
	/**
	 * <h2> Deletes a Question  </h2>
	 *  <p> If a problem occurs throws the {@link} DeleteFailedException </p>
	 *  usage example : QuestionJDBCDAO questiondao = new .. try{ questiondao.deleteQuestionById(QuestionInstance);}
	 * catch (ReadFailed e) 
	 * @param question The question that is deleted
	 * @throws DeleteFailedException The exception that is thrown when the  Question is not deleted
	 */
	public void deleteQuestionByQuestionID(Question question) throws SQLException, DeleteFailedException {
		int question_id = question.getId();
		String deleteByQuestionIDQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.question.deleteByQuestionIDQuery", "");
		try(Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(deleteByQuestionIDQuery);){
			pstmt.setInt(1,question_id);
			pstmt.executeUpdate();
		}catch(SQLException sqle){
			throw new DeleteFailedException(question);
		}
	}
	
	/**
	 * <h2> Search for a  Question based on searchcriterion </h2>
	 * <p> Gets a  Question based on a search criterion </p>
	 * @param questioncriterion The Question search criterion
	 * @return question returns the Question that matches the search criterion
	 * @throws SearchFailedException The exception that is thrown when the there is a problem getting the  Questions list
	 */
	public  Question searchQuestion(Question questioncriterion) throws SQLException, SearchFailedException {
		String searchQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.question.searchQuery","");
		Question question = new Question();
		try(Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(searchQuery);){
		String search_question= questioncriterion.getContent();
		pstmt.setString(1,search_question);
		ResultSet rs = pstmt.executeQuery();
		int question_ids[] = new int[1];
		String questions[] = new String[1];
		int i=0;
		while(rs.next()) {
			int id = rs.getInt("question_id");
			String text = rs.getString("question");
			question_ids[i] = id;
			questions[i] = text;
		}
		question.setId(question_ids[0]);
		question.setContent(questions[0]);
		rs.close();
		}catch(SQLException sqle)
		{
			throw new SearchFailedException(questioncriterion);
		}
		return  question;
	}
	
	
	
	

}
