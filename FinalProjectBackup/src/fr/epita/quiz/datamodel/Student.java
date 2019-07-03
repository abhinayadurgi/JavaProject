package fr.epita.quiz.datamodel;

/**
 * <h1> Class Student </h1>
 * @param name Name of the student
 * @param id Id of the student
 * 
 * @author Durgi
 *
 */
public class Student {
	private String name;
	private int  id;
	
	/**
	 * <h2>Creates a new student with name and Id 
	 * @param name
	 * @param id
	 */
	public Student(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public Student() {
		
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
