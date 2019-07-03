package fr.epita.quiz.exception;

/**
 * The DataAccessException is thrown when there is a problem accessing the database. 
 * It displays an error message
 * @author Durgi
 *
 */
public class DataAccessException extends Exception{
	
	public Object getFaultInstance() {
		return faultInstance;
	}

	private  Object faultInstance;
	
	
	public DataAccessException(Object faultInstance) {
		this.faultInstance = faultInstance;
		System.out.println("Problem connecting to the database \nPlease check connections and try again");
		System.exit(1);
		
	}
	
	public DataAccessException(Object faultInstance, Exception initialCause) {
		this.faultInstance = faultInstance;
		this.initCause(initialCause);
		System.out.println("Problem connecting to the database \nPlease check connections and try again");
		System.exit(1);
	}
	public DataAccessException(){
		System.out.println("Problem connecting to the database \nPlease check connections and try again");
		System.exit(1);
	}
	
	}


