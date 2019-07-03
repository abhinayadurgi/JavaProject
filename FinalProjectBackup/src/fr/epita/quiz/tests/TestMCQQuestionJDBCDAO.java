package fr.epita.quiz.tests;

import java.util.ArrayList;
import java.util.List;

import fr.epita.quiz.datamodel.Difficulty;
import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.datamodel.Quiz;
import fr.epita.quiz.service.ConfigurationService;
import fr.epita.quiz.service.MCQQuestionJDBCDAO;

public class TestMCQQuestionJDBCDAO {

	public static void main(String[] args) throws Exception {
		
		//Given
		ConfigurationService conf = ConfigurationService.getInstance();
		boolean confInit = conf.isInit();
		if (!confInit) {
			System.out.println("problem while initializing the conf");
			return;
		}
		
		MCQQuestionJDBCDAO dao = MCQQuestionJDBCDAO.getInstance();
		
		//when
		//create
		
		Quiz quiz = new Quiz(); // the quiz for which the mcqquestion is created
		quiz.setId(99);
		MCQQuestion mcqquestion = new MCQQuestion();
		String content = "Is java object oriented";
		String d = "EASY";
		Difficulty diff = Difficulty.valueOf(d.toUpperCase());
		List<String> topics = new ArrayList<>();
		topics.add("java");
		mcqquestion.setContent(content);
		mcqquestion.setDifficulty(diff.toInteger());
		mcqquestion.setTopics(topics);
		dao.createMCQQuestion(mcqquestion, quiz);
	
		
		//update
		
		//MCQQuestion mcqquestion = new MCQQuestion(); //the mcq question to be updated
		mcqquestion.setId(129);
		mcqquestion.setContent("Is java an object oriented language?");
		dao.updateMCQQuestion(mcqquestion);
		
		
		//read
		
		String topicsString="Java";
		List<MCQQuestion> questionlist = dao.readMCQQuestionsByTopics(topicsString);
		if(questionlist.isEmpty())
		{
			throw new Exception ("Empty List");
		}
		for(MCQQuestion question : questionlist)
		{
			System.out.println(question.getId());
			System.out.println(question.getContent());
		}
		
		
		//delete
		
		//MCQQuestion mcqquestion = new MCQQuestion(); // the MCQQuestion to be deleted
		mcqquestion.setId(129);
		dao.deleteMCQQuestionByID(mcqquestion);

	}

}
