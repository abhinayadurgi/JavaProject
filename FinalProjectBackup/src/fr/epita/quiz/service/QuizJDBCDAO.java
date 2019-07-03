package fr.epita.quiz.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quiz.datamodel.Quiz;
import fr.epita.quiz.exception.CreateFailedException;
import fr.epita.quiz.exception.DataAccessException;
import fr.epita.quiz.exception.DeleteFailedException;
import fr.epita.quiz.exception.ReadFailedException;
import fr.epita.quiz.exception.UpdateFailedException;


/** The JDBCDAO class that connects to the Quiz table
 * @author Durgi
 *
 */
public class QuizJDBCDAO {
	
	private static   QuizJDBCDAO instance;
	
private QuizJDBCDAO() {
		
	}
	
	/**
	 * @return returns the QuizJDBCDAO instance
	 */
	public static QuizJDBCDAO getInstance() {
		if (instance == null) {
			instance = new QuizJDBCDAO();
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
	 *  <h2> Creates a Quiz in the database </h2>
	 * <p> If a problem occurs throws a {@link} CreateFailedException </p>
	 * usage example : QuizJDBCDAO quizdao = new... try{ quizdao.createQuestion
	 * ( QuizInstance)};
	 * catch (CreateFailed e)
	 * @param quiz that quiz that is created
	 * @throws CreateFailedException The exception that is thrown when the quiz is not created
	 */
	public  void createQuiz(Quiz quiz) throws  CreateFailedException {
		String createQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.quiz.createQuery","");
		String name = quiz.getTitle();
		try(Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(createQuery);){
		pstmt.setString(1, name);
		pstmt.executeUpdate();
		}catch(SQLException sqle) {
			throw new CreateFailedException(quiz);
		}
	}
	
	/**
	 *  <h2> Updates a Quiz </h2>
	 * <p> If a problem occurs throws the {@link} UpdateFailedException </p>
	 * usage example : QuizJDBCDAO quizdao = new .. try { quizdao.updateQuiz(QuizInstance);}
	 * catch ( UpdateFailed e ) 
	 * @param quiz The quiz that is updated ( new title and the id of the quiz to be updated )
	 * @throws UpdateFailedException The exception that is thrown when the quiz is not updated
	 */
	public  void updateQuiz(Quiz quiz) throws  UpdateFailedException {
		String updateQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.quiz.updateQuery","");
		int id = quiz.getId();
		String newTitle = quiz.getTitle();
		try(Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(updateQuery);){
		pstmt.setString(1, newTitle);
		pstmt.setInt(2, id);
		pstmt.executeUpdate();
		}catch(SQLException sqle) {
			throw new UpdateFailedException(quiz);
		}
	}
	
	/**
	 * <h2> Gets the list of quizzes </h2>
	 * @return quizlist returns a list of quizzes
	 * @throws DataAccessException The exception that is thrown when the quiz list is not fetched
	 */
	public  List<Quiz> readQuiz() throws  DataAccessException {
		List<Quiz> quizlist = new ArrayList<Quiz>();
		String selectQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.quiz.selectQuery","");
		try(Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(selectQuery);){
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("ID");
			String topic = rs.getString("NAME");
			Quiz quiz = new Quiz(topic,id);
			quizlist.add(quiz);
		}
		rs.close();
		return quizlist;
	}catch(SQLException sqle) {
		throw new ReadFailedException();
	}
	}
	
	/**
	 *  <h2> Deletes  a quiz </h2>
	 *  <p> If a problem occurs throws the {@link} DeleteFailedException </p>
	 *  usage example : QuizJDBCDAO quizdao = new .. try{ quiz.deleteQuiz(QuizInstance);}
	 * catch (ReadFailed e) 
	 * @param quiz The quiz that is deleted
	 * @throws DeleteFailedException The exception that is thrown when the quiz is not deleted
	 */
	public  void deleteQuiz(Quiz quiz) throws  DeleteFailedException {
		int id = quiz.getId();
		String deleteQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.quiz.deleteQuery","");
		try(Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(deleteQuery);){
		pstmt.setInt(1,id);
	    pstmt.executeUpdate();
		}catch(SQLException sqle) {
			throw new DeleteFailedException(quiz);
		}
	}
}
