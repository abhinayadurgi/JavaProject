package fr.epita.quiz.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *  Exports a quiz to a file
 * @author Durgi
 *
 */
public class FilesUtil {
	
	static final  String PATH = "src/fr/epita/quiz/files/QuizExport.txt";
	
	/**
	 * <h2> Clears the contents of the file </h2>
	 * <p>Flushes a file to make it empty before a user starts a quiz</p>
	 * <p> Throws  IOException  if a problem occurs in clearing the file <p>
	 * @throws IOException Exception that is thrown when a problem occurs
	 */
	public void clearFile() throws IOException
	{
	try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(PATH));){
	writer.write("");
	writer.flush();
	}
	}
	
	
	/**
	 * <h2> Exports the Quiz to the file <h2>
	 * <p> Writes the question and the answer content as a student takes a quiz </p>
	 * @param content The content that is written into the file
	 * @throws IOException Exception that is thrown when a problem occurs
	 */
	public  void writeToTextFile(String content) throws IOException {
		String text =  content +  System.lineSeparator();
        Files.write(Paths.get(PATH), text.getBytes(), StandardOpenOption.APPEND);
    }
				
	}


