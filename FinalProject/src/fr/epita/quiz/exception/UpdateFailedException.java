package fr.epita.quiz.exception;

/**
 * The UpdateFailedException is thrown when a problem occurs in updating a record in the database
 * @author Durgi
 *
 */
public class UpdateFailedException extends DataAccessException {
	public UpdateFailedException(Object beanThatWasNotUpdated) {
		super(beanThatWasNotUpdated);
	}

	public UpdateFailedException(Object beanThatWasNotUpdated, Exception initialCause) {
		super(beanThatWasNotUpdated,initialCause);
	}

}
