package fr.epita.quiz.exception;

/**
 * The ReadFailedException is thrown when a problem occurs to fetch data from the database
 * @author Durgi
 *
 */
public class ReadFailedException extends DataAccessException {
	public ReadFailedException(Object badInput) {
		super(badInput);
	}

	public ReadFailedException(Object badInput, Exception initialCause) {
		super(badInput,initialCause);
	}
	public ReadFailedException() {
		super();
	}

}
