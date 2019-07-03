package fr.epita.quiz.tests;

import java.sql.SQLException;

import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.exception.CreateFailedException;
import fr.epita.quiz.exception.SearchFailedException;
import fr.epita.quiz.service.ConfigurationService;
import fr.epita.quiz.service.StudentJDBCDAO;

public class TestStudentJDBCDAO {

	public static void main(String[] args) throws CreateFailedException, SQLException, SearchFailedException {
		
		//given
				ConfigurationService conf = ConfigurationService.getInstance();
				boolean confInit = conf.isInit();
				if (!confInit) {
					System.out.println("problem while initializing the conf");
					return;
				}
				
				StudentJDBCDAO dao = new StudentJDBCDAO();
				
				//create
				
				Student student = new Student();
				student.setName("Test Student");
				dao.createStudent(student);
				
				
				//search
				Student searchStudent = new Student();
				student.setName("Bill");
				Student searchResult = dao.searchStudent(searchStudent);
				searchResult.getName();
	}

}
