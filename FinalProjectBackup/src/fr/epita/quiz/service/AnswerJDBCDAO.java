package fr.epita.quiz.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import fr.epita.quiz.datamodel.Answer;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.Quiz;
import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.exception.CreateFailedException;
import fr.epita.quiz.exception.DeleteFailedException;

/**
 *  JDBCDAO class that connects to the Answer table
 * @author Durgi
 *
 */
public class AnswerJDBCDAO {
	
	private static AnswerJDBCDAO  instance;
	
private AnswerJDBCDAO() {
		
	}
	
	/**
	 * @return returns the AnswerJDBCDAO instance
	 */
	public static AnswerJDBCDAO getInstance() {
		if(instance == null) {
			instance = new AnswerJDBCDAO();
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
 * <h2> Creates an answer in the database </h2>
 * <p> if a problem occurs throws an {@link} CreateFailedException </p>
 * usage example: AnswerJDBCDAO answerdao = new ... try { answerdao.createAnswer(AnswerInstance, QuestionInstance,..);
 *  catch(CreateFailed e) 
 * @param answer Answer that is created in the ANSWER table
 * @param question The Question that is associated with the answer
 * @param student The Student that is associated with the answer
 * @param quiz  The Quiz that is associated with the answer
 * @throws CreateFailedException The exception that is thrown when the answer is not created
 */
public  void createAnswer(Answer answer, Question question, Student student,Quiz quiz) throws CreateFailedException {
	String createQuery = ConfigurationService.getInstance()
			.getConfigurationValue("db.queries.answer.createQuery","");
	try (Connection connection = getConnection();
	PreparedStatement pstmt = connection.prepareStatement(createQuery);){
	pstmt.setString(1,answer.getText());
	pstmt.setInt(2, question.getId());
	pstmt.setInt(3,student.getId());
	pstmt.setInt(4, quiz.getId());
	pstmt.executeUpdate();
	}catch(SQLException sqle) {
		throw new CreateFailedException(answer);
	}
}

/**
 * <h2> Deletes an answer of a quiz</h2>
 * <p> This method is called when a quiz is deleted </p>
 * <p> When a quiz is deleted, all the answers associated with the quiz are deleted to preserve referential integrity </p>
 * <p> If a problem occurs throws a {@link} DeleteFailedException </p>
 * usage example: AnswerJDBCDAO answerdao = new .. try { answerdao.deleteAnswerByQuizID(quizInstance); 
 * catch(DeleteFailed e)
 * @param quiz Quiz for which the answers are deleted
 * @throws DeleteFailedException The exception that is thrown when the answer is not deleted
 */
public void deleteAnswerByQuizId(Quiz quiz) throws SQLException, DeleteFailedException {
	int quizId = quiz.getId();
	String deleteByQuizIDQuery = ConfigurationService.getInstance()
			.getConfigurationValue("db.queries.answer.deleteByQuizIDQuery", "");
	try(Connection connection = getConnection();
	PreparedStatement pstmt = connection.prepareStatement(deleteByQuizIDQuery);){
	pstmt.setInt(1,quizId);
	pstmt.executeUpdate();
}catch(SQLException sqle) {
	throw new DeleteFailedException(quiz);
}
}

/**
 * <h2> Deletes an answer of a question </h2>
 * <p> This method is called when an open question is deleted </p>
 * <p> When a question is deleted, all the answers associated with the question are deleted to reserve referential integrity </p>
 * <p> If a problem occurs throws a {@link} DeleteFailedException </p>
 * usage example : AnswerJDBCDAO answerdao = new ... try { answerdao.deleteAnswerByQuestionId(QuestionInstance);
 * catch(DeleteFailed e)
 * @param question Question for which the answers are deleted
 * @throws DeleteFailedException The exception that is thrown when a problem occurs
 */
public void deleteAnswerByQuestionId(Question question) throws DeleteFailedException {
	String deleteByQuestionIDQuery = ConfigurationService.getInstance()
			.getConfigurationValue("db.queries.anser.deleteByQuestionIDQuery", "");
	int questionId = question.getId();
	try(Connection connection = getConnection();
	PreparedStatement pstmt = connection.prepareStatement(deleteByQuestionIDQuery);){
	pstmt.setInt(1,questionId);
	pstmt.executeUpdate();
	}catch(SQLException sqle) {
		throw new DeleteFailedException(question);
	}
}

}
