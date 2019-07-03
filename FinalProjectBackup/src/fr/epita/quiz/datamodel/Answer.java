package fr.epita.quiz.datamodel;

/**
 * <h1> Class Answer </h1>
 * <p> Represents the answer object </p>
 * @param text Answer
 * @param quiz The quiz the answer belongs to
 * @param question The question of the answer
 * @param student The student who gave this answer
 * 
 * @author Durgi
 *
 */
public class Answer {
	private int id;
	private String text;
	private Quiz quiz;
	private Question question;
	private Student student;

	/** <h2> Creates an answer object </h2>
	 * <p> Creates an answer object with the id, text, quiz, question, student attributes </p>
	 * @param id id of the answer
	 * @param text content of the answer
	 * @param quiz The quiz that is associated with the answer
	 * @param question The question that is associated with the answer
	 * @param student The student associated with the answer
	 */
	public Answer(int id, String text, Quiz quiz, Question question, Student student) {
		this.id = id;
		this.text = text;
		this.quiz = quiz;
		this.question = question;
		this.student = student;
	}

	public Answer() {
	}
	
	/** 
	 * @return  ID 
	 */
	public int getId() { 
		return id;
	}

	/**
	 * @param id ID to set for the answer
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return text returns the answer content
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text sets the answer content
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return quiz returns the quiz associated with the answer
	 */
	public Quiz getQuiz() {
		return quiz;
	}

	/**
	 * @param quiz to set
	 */
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	/**
	 * @return question returns the question associated with the answer
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * @param question to set
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * @return student returns the student associated with the answer
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}
	
	
	
	
	
	
}
