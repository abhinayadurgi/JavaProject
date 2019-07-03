package fr.epita.quiz.exception;

/**
 * The DeleteFailedException is thrown when  a problem occurs in deleting records in the database
 * @author Durgi
 *
 */
public class DeleteFailedException extends DataAccessException {
	
	public DeleteFailedException(Object beanThatWasNotDeleted) {
		super(beanThatWasNotDeleted);
	}

	public DeleteFailedException(Object beanThatWasNotDeleted, Exception initialCause) {
		super(beanThatWasNotDeleted,initialCause);
	}
	public DeleteFailedException() {
		
}
}
