package fr.epita.quiz.tests;

import java.util.ArrayList;
import java.util.List;

import fr.epita.quiz.datamodel.Quiz;
import fr.epita.quiz.exception.CreateFailedException;
import fr.epita.quiz.exception.DataAccessException;
import fr.epita.quiz.exception.UpdateFailedException;
import fr.epita.quiz.service.ConfigurationService;
import fr.epita.quiz.service.QuizJDBCDAO;

public class TestQuizJDBCDAO {

	public static void main(String[] args) throws Exception {
		
		//Given
		ConfigurationService conf = ConfigurationService.getInstance();
		boolean confInit = conf.isInit();
		if (!confInit) {
			System.out.println("problem while initializing the conf");
			return;
		}
		
		QuizJDBCDAO dao = QuizJDBCDAO.getInstance();
		
		//when
		//create
		
		Quiz newQuiz = new Quiz();
		newQuiz.setTitle("Test Quiz");
		dao.createQuiz(newQuiz);
		
		//then
		
		//update
		
		String newQuizTitle = "Updated Test Quiz";
		int quizID = 97;
		Quiz updateQuiz = new Quiz();
		updateQuiz.setId(quizID);
		updateQuiz.setTitle(newQuizTitle);
		dao.updateQuiz(updateQuiz);
		
		
		//readQuiz
		
		List<Quiz> quizList = new ArrayList<Quiz>();
		quizList = dao.readQuiz();
		if(quizList.isEmpty())
		{
			throw new Exception("The list is empty");
		}
		for(Quiz quiz : quizList)
		{
			System.out.println(quiz.getTitle());
		}
		
		
		//delete Quiz
		Quiz deleteQuiz = new Quiz();
		deleteQuiz.setId(97);
		dao.deleteQuiz(deleteQuiz);
		
		
		
		
		
		

	}

}
