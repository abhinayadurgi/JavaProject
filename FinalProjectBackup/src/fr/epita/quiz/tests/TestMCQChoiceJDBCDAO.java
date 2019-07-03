package fr.epita.quiz.tests;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.exception.CreateFailedException;
import fr.epita.quiz.exception.DeleteFailedException;
import fr.epita.quiz.exception.ReadFailedException;
import fr.epita.quiz.service.ConfigurationService;
import fr.epita.quiz.service.MCQChoiceJDBCDAO;
import fr.epita.quiz.service.MCQQuestionJDBCDAO;

public class TestMCQChoiceJDBCDAO {

	public static void main(String[] args) throws CreateFailedException, SQLException, ReadFailedException, DeleteFailedException {

		//Given
		ConfigurationService conf = ConfigurationService.getInstance();
		boolean confInit = conf.isInit();
		if (!confInit) {
			System.out.println("problem while initializing the conf");
			return;
		}
		
		MCQChoiceJDBCDAO dao = MCQChoiceJDBCDAO.getInstance();
		
		//when
		//create
		
		List<MCQChoice> mcqchoicelist = new ArrayList<MCQChoice>();
		MCQChoice mcqchoice1 = new MCQChoice();
		MCQChoice mcqchoice2 = new MCQChoice();
		
		MCQQuestion mcqquestion = new MCQQuestion();
		mcqquestion.setId(161);
		
		mcqchoice1.setChoice("yes");
		mcqchoice1.setValid(false);
		mcqchoicelist.add(mcqchoice1);
		
		mcqchoice2.setChoice("no");
		mcqchoice2.setValid(true);
		mcqchoicelist.add(mcqchoice2);
		
		dao.createMCQChoice(mcqchoicelist, mcqquestion);
		
		
		//read
		
		//MCQQuestion mcqquestion = new MCQQuestion();
		mcqquestion.setId(161);
		
		//List <MCQChoice> mcqchoicelist = dao.getMCQChoiceByID(mcqquestion);
		for(MCQChoice mcqchoice : mcqchoicelist)
		{
			System.out.println(mcqchoice.getChoice());
		}
		
			
		//delete
		
		//MCQQuestion mcqquestion = new MCQQuestion();
		mcqquestion.setId(161);
		dao.deleteMCQChoiceByQuestionID(mcqquestion);
	
	}
}
