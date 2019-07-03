package fr.epita.quiz.exception;

/**
 * The SearchFailedException is thrown when a problem occurs in searching based on a criterion in the database
 * @author Durgi
 *
 */
public class SearchFailedException extends DataAccessException {
	public SearchFailedException(Object badInput) {
		super(badInput);
	}

	public SearchFailedException(Object badInput, Exception initialCause) {
		super(badInput,initialCause);
		System.out.println("Failed Search from "+ badInput.getClass().getName());
	}

}
