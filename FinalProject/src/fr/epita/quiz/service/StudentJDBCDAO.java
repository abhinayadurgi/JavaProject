package fr.epita.quiz.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.exception.CreateFailedException;
import fr.epita.quiz.exception.SearchFailedException;



/** The JDBCDAO class that connects to the student database
 * @author Durgi
 *
 */
public class StudentJDBCDAO {
	
	private static StudentJDBCDAO  instance;
	
	public StudentJDBCDAO() {
	}
	/**
	 * @return returns the StudentJDBCDAO instance
	 */
	public static StudentJDBCDAO getInstance() {
		if (instance == null) {
			instance = new StudentJDBCDAO();
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
	 * <h2> Creates a student in the student table</h2>
	 * @param student student that is created
	 * @throws CreateFailedException The exception that is thrown when the student is not created
	 */
	public  void createStudent(Student student) throws SQLException, CreateFailedException {
		String createQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.student.createQuery","");
		try (Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(createQuery); ){
		pstmt.setString(1,student.getName());
		pstmt.executeUpdate();
		}catch (SQLException sqle) {
			throw new CreateFailedException(student);
		}
	}
	
	/**
	 * <h2> Search for a  student based on search criterion </h2>
	 * <p> Gets a  Student  based on a search criterion </p>
	 * @throws SearchFailedException The exception that is thrown when the there is a problem getting the Student 
	 * @param studentcriterion The student search criterion
	 * @return student returns the student that matches the search criterion
	 * @throws SearchFailedException The exception that is thrown when the student is not fetched
	 */
	public  Student searchStudent(Student studentcriterion) throws SQLException, SearchFailedException {
		Student student = new Student ();
		String searchQuery = ConfigurationService.getInstance()
				.getConfigurationValue("db.queries.student.searchQuery","");
		try(Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(searchQuery);){
		String search_name= studentcriterion.getName();
		pstmt.setString(1,search_name);
		ResultSet rs = pstmt.executeQuery();
		int student_ids[] = new int[1];
		String student_names[] = new String[1];
		int i=0;
		while(rs.next()) {
			int id = rs.getInt("student_id");
			String text = rs.getString("name");
			student_ids[i] = id;
			student_names[i] = text;
		}
		student.setId(student_ids[0]);
		student.setName(student_names[0]);
		rs.close();
		return student;
	}catch(SQLException sqle)
		{
		throw new SearchFailedException(studentcriterion);
		}
	}
}
