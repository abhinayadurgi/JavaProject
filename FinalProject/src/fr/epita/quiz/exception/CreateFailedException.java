package fr.epita.quiz.exception;

/**
 * The CreateFailedException is thrown when there is an error writing to the database
 * @author Durgi
 *
 */
public class CreateFailedException extends DataAccessException{
	
	public CreateFailedException(Object beanThatWasNotCreated) {
		super(beanThatWasNotCreated);
	}
	
	public CreateFailedException(Object beanThatWasNotCreated, Exception initialCause) {
		super(beanThatWasNotCreated, initialCause);
	}
	

}
