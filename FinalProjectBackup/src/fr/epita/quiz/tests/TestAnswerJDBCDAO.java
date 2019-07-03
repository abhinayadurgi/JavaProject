package fr.epita.quiz.tests;

import fr.epita.quiz.datamodel.Answer;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.Quiz;
import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.exception.CreateFailedException;
import fr.epita.quiz.exception.DeleteFailedException;
import fr.epita.quiz.service.AnswerJDBCDAO;
import fr.epita.quiz.service.ConfigurationService;
import fr.epita.quiz.service.MCQQuestionJDBCDAO;

public class TestAnswerJDBCDAO {

	public static void main(String[] args) throws CreateFailedException, DeleteFailedException {
		
		//given
		ConfigurationService conf = ConfigurationService.getInstance();
		boolean confInit = conf.isInit();
		if (!confInit) {
			System.out.println("problem while initializing the conf");
			return;
		}

		AnswerJDBCDAO  dao =  AnswerJDBCDAO.getInstance();
		
		//when
		//create
		
		Answer answer = new Answer();
		answer.setText("No it is not possible in java");
		Question question = new Question(); // the question for which the answer has to be created
		question.setId(129);
		Student student = new Student(); 
		student.setId(65);
		Quiz quiz = new Quiz();
		quiz.setId(99);
		dao.createAnswer(answer, question, student, quiz);
		
		
		//delete
		//Answer answer = new Answer(); // the answer to be deleted
		//Question question = new Question (); // the question of the answer to be deleted
		question.setId(129);
		dao.deleteAnswerByQuestionId(question);
		
	
	}

}
