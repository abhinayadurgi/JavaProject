package fr.epita.quiz.service;

import java.sql.SQLException;
import java.util.List;

import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.Quiz;
import fr.epita.quiz.exception.DataAccessException;
import fr.epita.quiz.exception.ReadFailedException;

/**
 * Displays the list of Quizzes and Questions
 * @author Durgi
 *
 */
public class DisplayMenus {
	
	QuizJDBCDAO quizdao = QuizJDBCDAO.getInstance();
	 QuestionJDBCDAO questiondao = QuestionJDBCDAO.getInstance(); 
	MCQQuestionJDBCDAO mcqquestiondao = MCQQuestionJDBCDAO.getInstance();
	
	/**
	 * <h2> Quiz List generator <h2>
	 * <p> Generates the list of Quizzes available for the user to choose </p>
	 * <p> Throws a DataAccessException if a problem occurs
	 * <p> usage example : DisplayMenus displaymenu = new ... displaymenu.generateQuizList() </p>
	 * @throws DataAccessException The exception that is thrown when there is a problem in accessing the quiz list from the database
	 */
	public void generateQuizList() throws  DataAccessException {
		
		List<Quiz> quizlist = quizdao.readQuiz();
		for (Quiz q : quizlist)
		{
			System.out.println(q.getId()+" . " + q.getTitle());
		}
		}

	
	/**
	 * <h2> Question List generator </h2>
	 * <p> Generates the list of Questions when a student takes a quiz or when the teacher works with questions (update, delete,..) </p>
	 * <p> If a problem occurs throws a {@link} ReadFailedException </p>
	 * <p>usage example : DisplayMenus displaymenu = new ... displaymenu.generateQuestionList()</p> 
	 * @throws ReadFailedException Exception that is thrown when there is a problem in accessing the question list from the database
	 */
	public void  generateQuestionList() throws SQLException, ReadFailedException {
	List<Question> questionlist = questiondao.readQuestion();
	for(Question question : questionlist)
	{
		System.out.println(question.getId() + "." + question.getContent());
	}
}
	
	/**
	 * <h2> MCQQuestion List generator </h2>
	 * <p> Generates the list of MCQ Questions when a student takes a quiz or when the teacher works with MCQquestions (update, delete,..) </p>
	 * <p> If a problem occurs throws a {@link} ReadFaailedException </p>
	 * <p> usage example : DisplayMenus displaymenu = new displaymenu.generateMCQQuestionList()</p>
	 * @throws ReadFailedException Exception that is thrown when there is a problem in accessing the MCQQuestion list from the database
	 */
	public void generateMCQQuestionList() throws ReadFailedException, SQLException {
		List<MCQQuestion> mcqquestionlist = mcqquestiondao.selectMCQQuestion();
		for(MCQQuestion mcqquestion : mcqquestionlist)
		{
			System.out.println(mcqquestion.getId() + "." + mcqquestion.getContent());
		}
	}

}
