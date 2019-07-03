package fr.epita.quiz.datamodel;

import java.util.List;

/**
 * <h1> Class Question </h1>
 * @param question 
 * @param topics Topics of the questions
 * @param difficulty Difficulty of the question
 * 
 * @author Durgi
 *
 */
public class Question {
	private int id;
	private String content;
	private List<String> topics;
	private Integer difficulty;
	

	/**
	 * <h2> Creates a new Question </h2>
	 * @param id ID of the question
	 * @param content the question content 
	 * @param topics topics related to the question
	 * @param difficulty difficulty of the question
	 */
	public Question(int id, String content, List<String> topics, int difficulty) {
		this.id = id;
		this.content = content;
		this.topics = topics;
		this.difficulty = difficulty;
	}

	public Question() {
	}

	
	
	/**
	 * @return Id returns the ID of the question
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id ID to set for the question
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return returns the content of the question
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param question set the content
	 */
	public void setContent(String question) {
		this.content = question;
	}
	
	
	/**
	 * @return topics returns the topics of the question
	 */
	public List<String> getTopics() {
		return topics;
	}

	/**
	 * @param topics sets the topics of the question
	 */
	public void setTopics(List<String> topics) {
		this.topics = topics;
	}

	/**
	 * @return difficulty returns the difficulty
	 */
	public Integer getDifficulty() {
		return difficulty;
	}

	/** 
	 * @param difficulty set the difficulty level of the question
	 */
	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	

}
