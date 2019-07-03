package fr.epita.quiz.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.exception.CreateFailedException;
import fr.epita.quiz.exception.DeleteFailedException;
import fr.epita.quiz.exception.ReadFailedException;
//import fr.epita.quiz.tests.ConfigurationService;

/**
 *  JDBCDAO class that connects to the MCQChoice table
 * @author Durgi
 *
 */
public class MCQChoiceJDBCDAO {
	
	private static MCQChoiceJDBCDAO  instance;
	
	/**
	 * @return returns the MCQChoiceJDBCDAO instance
	 */
	public static  MCQChoiceJDBCDAO getInstance() {
		if (instance == null) {
			instance = new MCQChoiceJDBCDAO();
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
	 * <h2> Creates an MCQ Choice in the database </h2>
	 * <p> If a problem occurs throws a {@link} CreateFailedException </p>
	 * <p> Takes a list of the mcq choices for a question </p>
	 * usage example : MCQChoiceJDBCDAO mcqchoicedao = new ... try {mcqchoicedao.createMCQChoice(MCQChoiceList, QuestionInstance)};
	 * catch(CreateFailed e)
	 * @param MCQChoiceList List of the MCQ choices that is created in the MCQCHOICE table
	 * @param question The MCQQuestion that is associated with the choices
	 * @throws CreateFailedException The exception that is thrown when the choices are not created
	 */
	public void createMCQChoice(List<MCQChoice>MCQChoiceList,MCQQuestion question) throws SQLException, CreateFailedException
	{
		
		int mcqquestion_id = question.getId();
		String createQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.mcqchoice.createQuery","");
		try(Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(createQuery);){
			for (MCQChoice mcqChoice : MCQChoiceList)
			{
			String c = mcqChoice.getChoice();
			boolean v = mcqChoice.isValid();
			pstmt.setInt(1,mcqquestion_id );
			pstmt.setString(2,c);
			pstmt.setBoolean(3,v);
			pstmt.executeUpdate();
			}
		}catch(SQLException sqle) {
			throw new CreateFailedException(MCQChoiceList);
		}
	}
	
	/**
	 * <h2> Gets the list of MCQ choices for an MCQ question </h2>
	 * <p> Gets the MCQ choice list for a question when a student takes a quiz with MCQ Question </p>
	 * <p> If a problem occurs throws a {@link} ReadFailedException </p>
	 * usage example : MCQChoiceJDBCDAO mcqquestiondao = new ... try {mcqchoicedao.getMCQChoiceByID(QuestionInstane)}
	 * catch(ReadFailed e)
	 * @param question The MCQ question that is associated with the choicelist
	 * @return mcqchoicelist returns the list of MCQ choices associated with the MCQ question
	 * @throws ReadFailedException The exception that is thrown when a problem occurs in getting the choicelist
	 */
	public List<MCQChoice> getMCQChoiceByID(MCQQuestion question) throws SQLException, ReadFailedException
	{
		List <MCQChoice> mcqchoicelist = new ArrayList<MCQChoice>();
		int mcqquestion_id = question.getId();
		String selectByQuestionId = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.mcqchoice.selectChoiceByQuestionID","");
		try(Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(selectByQuestionId);){
		pstmt.setInt(1,mcqquestion_id );
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			String text = rs.getString("choice");
			Boolean validity = rs.getBoolean("valid");
			MCQChoice choice = new MCQChoice();
			choice.setChoice(text);
			choice.setValid(validity);
			mcqchoicelist.add(choice);
			}
		//rs.close();
		}catch(SQLException sqle) {
			throw new ReadFailedException();
		}
		return mcqchoicelist;
	}
	
	/**
	 *  <h2> Deletes the MCQ Choices of an MCQ  question </h2>
	 *  <p> This method is called when an MCQ question is deleted </p>
	 *  <p> When an MCQ question is deleted, all the MCQ choices associated with the MCQ question are deleted to preserve the referential 
	 *  integrity </p>
	 *  <p> If a problem occurs throws the {@link} DeleteFailedException </p>
	 *  usage example : MCQChoiceJDBCDAO mcqchoicedao = new ... try{ mcqchoicedao.deleteMCQChoiceByQuestionID(MCQQuestionInstance)} ;
	 *  catch(DeleteFailed e)
	 * @param question Question for which the choices are deleted
	 * @throws DeleteFailedException The exception that is thrown when the choices are not deleted
	 */
	public void deleteMCQChoiceByQuestionID(MCQQuestion question) throws SQLException, DeleteFailedException
	{
		int mcqquestion_id = question.getId();
		String deleteByQuestionId = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.mcqchoice.deleteByQuestionID","");
		try(Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(deleteByQuestionId);){
		pstmt.setInt(1,mcqquestion_id );
		pstmt.executeUpdate();
		}catch(SQLException sqle) {
			throw new DeleteFailedException();
		}
	}

}
