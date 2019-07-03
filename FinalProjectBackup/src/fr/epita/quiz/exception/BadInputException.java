package fr.epita.quiz.exception;

public class BadInputException extends Exception {

	public BadInputException(String errorMessage) {

		System.out.println(errorMessage);
	}
	

}
