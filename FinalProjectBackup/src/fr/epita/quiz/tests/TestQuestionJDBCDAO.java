package fr.epita.quiz.tests;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quiz.datamodel.Difficulty;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.Quiz;
import fr.epita.quiz.exception.CreateFailedException;
import fr.epita.quiz.exception.ReadFailedException;
import fr.epita.quiz.exception.UpdateFailedException;
import fr.epita.quiz.service.ConfigurationService;
import fr.epita.quiz.service.QuestionJDBCDAO;
import fr.epita.quiz.service.QuizJDBCDAO;

public class TestQuestionJDBCDAO {

	public static void main(String[] args) throws Exception {
		
		//Given
				ConfigurationService conf = ConfigurationService.getInstance();
				boolean confInit = conf.isInit();
				if (!confInit) {
					System.out.println("problem while initializing the conf");
					return;
				}
				
				QuestionJDBCDAO dao = QuestionJDBCDAO.getInstance();
				
				//when
				
				//create
				
				Quiz quiz = new Quiz(); //the quiz which the question creation is tested
				quiz.setId(10);
				
				Question question = new Question(); // the question that is created
				String d = "EASY";
				Difficulty diff = Difficulty.valueOf(d.toUpperCase());
				List<String> topics = new ArrayList<>();
				topics.add("Test topic1");
				topics.add("Test topic2");
				question.setContent("Test Question"); 
				question.setDifficulty(diff.toInteger());
				question.setTopics(topics);
				dao.createQuestion(question, quiz);
				
				
				//update
				
				Question updateQuestion = new Question(); // question to be updated
				updateQuestion.setId(97);
				updateQuestion.setContent("Updated Question");
				dao.updateQuestion(updateQuestion);
				
				
				//search for questions
				
				String topicsString = "Test";
				List<Question> questionList = dao.readQuestionsByTopics(topicsString);
				if (questionList.isEmpty())
				{
					throw new Exception("Empty List");
				}
				for(Question questionObj : questionList)
				{
					System.out.println(questionObj.getId());
					System.out.println(questionObj.getContent());
				}
				
				
				//delete 
				Question deleteQuestion = new Question();
				deleteQuestion.setId(97);
				dao.deleteQuestionByQuestionID(deleteQuestion);
				
				
				
				
				

	}

}
