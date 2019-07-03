package fr.epita.quiz.datamodel;



/**
 * Difficulty levels that can be used 
 * <li> {@link #VERY_EASY} </li>
 * <li> {@link #EASY } </li>
 * <li> {@link #MEDIUM } </li>
 * <li> {@link #HARD } </li>
 * <li> {@link #VERY_HARD } </li>
 * <li> {@link #EXTREMELY_HARD } </li>
 * @author Durgi
 *
 */
public enum Difficulty {
	
	VERY_EASY(0),
	EASY(1),
	MEDIUM(2),
	HARD(3),
	VERY_HARD(4),
	EXTREMELY_HARD(5),
	
	;
	
	private Integer numericDifficulty;
	
	
	private Difficulty(Integer difficulty) {
		this.numericDifficulty = difficulty;
	}
	
	/**
	 * @return numericDifficulty  the corresponding difficulty value
	 */
	public Integer getDifficulty() {
		return this.numericDifficulty;
	}

	public Integer toInteger() {
		return numericDifficulty;
	}


}