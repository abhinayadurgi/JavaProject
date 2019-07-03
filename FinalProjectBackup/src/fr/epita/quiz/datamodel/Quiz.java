package fr.epita.quiz.datamodel;


/**
 * <h1> Class Quiz </h1>
 * @param title name of the Quiz
 * @param id Unique id of the quiz
 * 
 * @author Durgi
 *
 */
public class Quiz {
	
	private String title;
	private int id;
	
	/**
	 *  <h2> creates a new Quiz with title and ID </h2> 
	 * @param title
	 * @param id
	 */
	public Quiz(String title, int id) {
		this.title = title;
		this.id = id;
	}
	
	public Quiz() {
		
	}



	/**
	 * @return title returns the quiz title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title title of the quiz to be set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return ID returns the ID of the quiz
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id ID of the quiz to be set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	
	

}
