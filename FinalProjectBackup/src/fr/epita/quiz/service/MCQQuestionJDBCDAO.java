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

import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.Quiz;
import fr.epita.quiz.exception.CreateFailedException;
import fr.epita.quiz.exception.DeleteFailedException;
import fr.epita.quiz.exception.ReadFailedException;
import fr.epita.quiz.exception.UpdateFailedException;
//import fr.epita.quiz.tests.ConfigurationService;


/**
 * The JDBCDAO class that connects to the MCQQuestion table
 * @author Durgi
 *
 */
public class MCQQuestionJDBCDAO {
	
	private static MCQQuestionJDBCDAO instance;
	
	private MCQQuestionJDBCDAO(){
	}
	
	/**
	 * @return returns the MCQQuestionJDBCDAO instance
	 */
	public static MCQQuestionJDBCDAO getInstance() {
		if(instance == null)
		{
			instance = new MCQQuestionJDBCDAO();
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
	 * <h2> Creates an MCQ Question in the database </h2>
	 * <p> If a problem occurs throws a {@link} CreateFailedException </p>
	 * usage example : MCQQuestionJDBCDAO mcqquestiondao = new... try{ mcqquestiondao.createMCQQuestion
	 * (MCQQuestionInstance, QuizInstance)};
	 * catch (CreateFailed e)
	 * @param question The MCQquestion that is created
	 * @param quiz The Quiz associated with the MCQQuestion
	 * @throws CreateFailedException The exception that is thrown when the MCQ question is not created
	 */
	public void createMCQQuestion(MCQQuestion question,Quiz quiz) throws SQLException, CreateFailedException {
		int quiz_id=quiz.getId();
		String text = question.getContent();
		List<String>topics = question.getTopics();
		String[] topics_array = topics.toArray(new String[topics.size()]);
		Integer diff = question.getDifficulty();
		String createQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.mcqquestion.createQuery","");
		try(Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(createQuery);){
		pstmt.setInt(1,quiz_id);
		pstmt.setString(2,text);
		Array aArray = connection.createArrayOf("VARCHAR",topics_array);
		pstmt.setArray(3,aArray);
		pstmt.setInt(4,diff);
		pstmt.executeUpdate();
		}catch(SQLException sqle) {
			throw new CreateFailedException(question);
		}
	}
	
	/**
	 * <h2> Updates an MCQ Question </h2>
	 * <p> If a problem occurs throws the {@link} UpdateFailedException </p>
	 * usage example : MCQQuestionJDBCDAO mcqquestiondao = new .. try { mcqquestiondao.updateMCQQuestion(MCQQuestionInstance);}
	 * catch ( UpdateFailed e ) 
	 * @param mcqquestion The MCQQuestion that is updated
	 * @throws UpdateFailedException The exception that is thrown the MCQ Question is not updated
	 */
	public  void updateMCQQuestion(MCQQuestion mcqquestion) throws SQLException, UpdateFailedException {
		int id = mcqquestion.getId();
		String content = mcqquestion.getContent();
		String updateQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.mcqquestion.updateQuery","");
		try(Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(updateQuery);){
		pstmt.setString(1, content);
		pstmt.setInt(2, id);
		pstmt.executeUpdate();
		}catch(SQLException sqle) {
			throw new UpdateFailedException(mcqquestion);
		}
		}
	
	/**
	 * <h2> Gets a list of MCQ Questions for a quiz </h2>
	 * <p> If a problem occurs throws {@link} ReadFailedException </p>
	 * usage example : MCQQuestionJDBCDAO mcqquestiondao = new .. try{ mcqquestiondao.readMCQQuestionByQuizID(QuizInstance);}
	 * catch (ReadFailed e) 
	 * @param quiz The quiz associated with the MCQquestions
	 * @return mcqquestionlist returns a list of MCQ questions associated with the quiz
	 * @throws ReadFailedException The exception that is thrown when the there is a problem getting the MCQ questions list
	 */
	public List<MCQQuestion>  readMCQQuestionsByQuizID(Quiz quiz) throws SQLException, ReadFailedException {
		List <MCQQuestion> mcqquestionlist = new ArrayList<MCQQuestion>();
		String readMCQQuestionsByQuizID =ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.mcqquestion.readQuestionByQuizID","");
		try(Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(readMCQQuestionsByQuizID);){
		int quiz_id = quiz.getId();
		pstmt.setInt(1,quiz_id);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int id = rs.getInt("mcqquestion_id");
			String text = rs.getString("question");
			MCQQuestion mcqquestion = new MCQQuestion();
			mcqquestion.setId(id);
			mcqquestion.setContent(text);
			mcqquestionlist.add(mcqquestion);
		}
		rs.close();
		}catch(SQLException sqle) {
			throw new ReadFailedException();
		}
		return mcqquestionlist;
	}
	
	
	/**
	 *  <h2> Gets a list of MCQ Questions for given topics </h2>
	 * <p> If a problem occurs throws {@link} ReadFailedException </p>
	 * usage example : MCQQuestionJDBCDAO mcqquestiondao = new .. try{ mcqquestiondao.readMCQQuestionsByTopics(Topics);}
	 * catch (ReadFailed e) 
	 * @param topics The topics associated with the MCQ Question
	 * @return  mcqquestionlist returns a list of MCQ Questions
	 * @throws ReadFailedException The exception that is thrown when the there is a problem getting the MCQ questions list
	 */
	public List<MCQQuestion>  readMCQQuestionsByTopics(String topics) throws SQLException, ReadFailedException {
		String[] tempArray;
		String delimiter = ",";
		tempArray = topics.split(delimiter);
		String readMCQQuestionsByTopics =ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.mcqquestion.readQuestionsByTopic","");
		HashSet<Integer> id_list=new HashSet<Integer>();
		List<MCQQuestion> mcqquestionlist = new ArrayList<MCQQuestion>();
		for (int i = 0; i < tempArray.length; i++) {
			String search_word = "%" + tempArray[i] + "%";
			try(Connection connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(readMCQQuestionsByTopics);){
			pstmt.setString(1,search_word.toLowerCase());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				int id = rs.getInt("mcqquestion_id");
				String text = rs.getString("question");
				if(!id_list.contains(id)) {
				MCQQuestion question = new MCQQuestion();
				question.setId(id);
				question.setContent(text);
				mcqquestionlist.add(question);
				id_list.add(id);
				}
			}
			rs.close();
		}catch(SQLException sqle){
			throw new ReadFailedException();
		}
		}
		return mcqquestionlist;
	}
	
	/**
	 *  <h2> gets a list of the MCQ questions </h2>
	 *   <p> If a problem occurs throws {@link} ReadFailedException </p>
	 *  usage example : MCQQuestionJDBCDAO mcqquestiondao = new .. try{ mcqquestiondao.selectMCQQuestion();}
	 * catch (ReadFailed e) 
	 * @return  mcqquestionlist returns a list of MCQ Questions
	 * @throws ReadFailedException The exception that is thrown when the there is a problem getting the MCQ questions list
	 */
	public List<MCQQuestion> selectMCQQuestion() throws SQLException, ReadFailedException
	{
		List<MCQQuestion> mcqquestionlist = new ArrayList<MCQQuestion>();
		String selectMCQQuestion =ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.mcqquestion.selectQuestionQuery","");
		try(Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(selectMCQQuestion);){
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("mcqquestion_id");
				String text = rs.getString("question");
				MCQQuestion mcqquestion = new MCQQuestion();
				mcqquestion.setId(id);
				mcqquestion.setContent(text);
				mcqquestionlist.add(mcqquestion);
		}
			rs.close();
		}catch(SQLException sqle)
		{
			throw new ReadFailedException();
		}
		return mcqquestionlist;
	}
	
	/**
	 * <h2> Deletes the MCQ Questions  of a quiz </h2>
	 *  <p> This method is called when a quiz  is deleted </p>
	 *  <p> When a quiz  is deleted, all the MCQ Questions  associated with the quiz  are deleted to preserve the referential 
	 *  integrity </p>
	 *  <p> If a problem occurs throws the {@link} DeleteFailedException </p>
	 *  usage example : MCQQuestionJDBCDAO mcqquestiondao = new .. try{ mcqquestiondao.deleteMCQQuestionByQuizId(QuizInstance);}
	 * catch (ReadFailed e) 
	 * @param quiz The quiz associated with the MCQQuestions
	 * @throws DeleteFailedException The exception that is thrown when the MCQ Question is not deleted
	 */
	public void deleteMCQQuestionByQuizId(Quiz quiz) throws SQLException, DeleteFailedException {
		int quiz_id = quiz.getId();
		String deleteByQuizIdQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.mcqquestion.deleteByIdQuery","");
		try(Connection connection =getConnection();
		PreparedStatement pstmt = connection.prepareStatement(deleteByQuizIdQuery);){
		pstmt.setInt(1, quiz_id);
		pstmt.executeUpdate();
	}catch(SQLException sqle)
		{
		throw new DeleteFailedException();
		}
	}
	
	/**
	 * <h2> Deletes an MCQ Question  </h2>
	 *  <p> If a problem occurs throws the {@link} DeleteFailedException </p>
	 *  usage example : MCQQuestionJDBCDAO mcqquestiondao = new .. try{ mcqquestiondao.deleteMCQQuestionById(MCQQuestionInstance);}
	 * catch (ReadFailed e) 
	 * @param mcqquestion The mcqquestion that is deleted
	 * @throws DeleteFailedException The exception that is thrown when the MCQ Question is not deleted
	 */
	public void deleteMCQQuestionByID(MCQQuestion mcqquestion) throws DeleteFailedException {
		int MCQQuestionID = mcqquestion.getId();
		String deleteByIdMCQQuestionIDQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.mcqquestion.deleteQuestionByMCQQuestionIDQuery","");
		try(Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(deleteByIdMCQQuestionIDQuery);){
			pstmt.setInt(1, MCQQuestionID);
			pstmt.executeUpdate();
		}catch(SQLException sqle)
		{
			throw new DeleteFailedException();
		}
		
	}
	
	/**<h2> Search for an MCQ Question based on searchcriterion </h2>
	 * <p> Gets an MCQ Question based on a search criterion </p>
	 * @param questioncriterion The MCQ Question search criterion
	 * @return mcqquestion  returns the MCQ Question that matches the search criterion
	 * @throws ReadFailedException The exception that is thrown when the there is a problem getting the MCQ questions list
	 */
	public  MCQQuestion searchMCQQuestion(MCQQuestion questioncriterion) throws SQLException, ReadFailedException {
		String searchQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.mcqquestion.searchQuery","");
		MCQQuestion mcqquestion = new MCQQuestion();
		try(Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(searchQuery);){
		String search_question= questioncriterion.getContent();
		pstmt.setString(1,search_question);
		ResultSet rs = pstmt.executeQuery();
		int question_ids[] = new int[1];
		String questions[] = new String[1];
		int i=0;
		while(rs.next()) {
			int id = rs.getInt("mcqquestion_id");
			String text = rs.getString("question");
			question_ids[i] = id;
			questions[i] = text;
		}
		mcqquestion.setId(question_ids[0]);
		mcqquestion.setContent(questions[0]);
		rs.close();
		}catch(SQLException sqle) {
			throw new ReadFailedException();
		}
		return  mcqquestion;
	}

}
