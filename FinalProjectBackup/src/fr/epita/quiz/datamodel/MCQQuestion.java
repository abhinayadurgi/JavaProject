package fr.epita.quiz.datamodel;

import java.util.List;

/**
 * <h1> Class MCQQuestion </h1>
 * @param text The MCQ Question
 * 
 * @author Durgi
 *
 */
public class MCQQuestion extends Question{
	int id;
	
	
	/**
	 * <h2> Creates a new MCQ Question object <h2>
	 * @param id ID of the MCQ question
	 * @param content content of the MCQ Question
	 * @param topics topics of the question
	 * @param difficulty difficulty of the question
	 */
	public MCQQuestion(int id, String content, List<String> topics, int difficulty) {
		super(id, content, topics, difficulty);
		this.id = id;
	}


	public MCQQuestion() {
	}

@Override
	public int getId() {
		return id;
	}

@Override
	public void setId(int id) {
		this.id = id;
	}
		
}
