package fr.epita.quiz.datamodel;

/**
 * <h1> Class MCQChoice </h1>
 * @param choice Choice of a MCQ Question
 * @param valid If this choice is valid or not
 * 
 * @author Durgi
 *
 */
public class MCQChoice {
	private int id;
	private String choice;
	private boolean valid;
	private MCQQuestion mcqquestion;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * <h2> Creates an MCQ choice object </h2>
	 * @param id ID of the mcq choice
	 * @param choice Content of the choice
	 * @param valid Validity of the choice
	 * @param mcqquestion Corresponding MCQ question
	 */
	public MCQChoice(int id, String choice, boolean valid, MCQQuestion mcqquestion) {
		this.id = id;
		this.choice = choice;
		this.valid = valid;
		this.mcqquestion = mcqquestion;
	}

	public MCQChoice() {
	}

	/**
	 * @return choice returns the MCQ choice
	 */
	public String getChoice() {
		return choice;
	}

	/**
	 * @param choice choice to set
	 */
	public void setChoice(String choice) {
		this.choice = choice;
	}

	/**
	 * @return valid returns the validity
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * @param valid sets the validity
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * @return mcqquestion returns the mcqquestion
	 */
	public MCQQuestion getMcqquestion() {
		return mcqquestion;
	}

	/**
	 * @param mcqquestion mcqquestion to set
	 */
	public void setMcqquestion(MCQQuestion mcqquestion) {
		this.mcqquestion = mcqquestion;
	}
	
	
	
}
