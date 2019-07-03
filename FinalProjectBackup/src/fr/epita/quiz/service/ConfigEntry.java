package fr.epita.quiz.service;

/**
 * Config Entry is an enum class for the properties in the conf file
 * @author Durgi
 *
 */
public enum ConfigEntry {
		DB_QUERIES_QUIZ_CREATEQUERY("db.queries.quiz.createQuery"),
		DB_QUERIES_QUIZ_UPDATEQUERY("db.queries.quiz.updateQuery"),
		DB_QUERIES_QUIZ_DELETEQUERY("db.queries.quiz.deleteQuery"),
		DB_QUERIES_QUIZ_SEARCHQUERY("db.queries.quiz.searchQuery"),
		DB_QUERIES_QUIZ_SELECTQUERY("db.queries.quiz.selectQuery"),
		
		//DB_QUESTION
		DB_QUERIES_QUESTION_CREATEQUERY("db.queries.question.createQuery"),
		DB_QUERIES_QUESTION_UPDATEQUERY("db.queries.question.updateQuestion"),
		DB_QUERIES_SEARCH_QUIZ_BY_QUESTION("db.queries.question.searchQuizByQuestion"),
		DB_QUERIES_SELECT_QUERY("db.queries.question.readQuestion"),
		DB_QUERIES_QUESTION_SEARCH_BY_QUIZ_ID("db.queries.question.selectByQuizID"),
		DB_QUERIES_QUESTION_SELECT_BY_TOPICS("db.queries.question.selectByTopics"),
		DB_QUERIES_QUESTION_DELETE_BY_QUIZID_QUERY("db.queries.question.deleteByQuizIdQuery"),
		DB_QUERIES_QUESTION_DELETE_BY_QUESTIONID_QUERY("db.queries.question.deleteByQuestionIDQuery"),
		
		//DB_STUDENT
		DB_QUERIES_STUDENT_CREATEQUERY("db.queries.student.createQuery"),
		DB_QUERIES_STUDENT_SEARCHQUERY("db.queries.student.searchQuery"),
		DB_QUERIES_STUDENT_UPDATEQUERY("db.queries.student.updateQuery"),
		DB_QUERIES_STUDENT_DELETEQUERY("db.queries.student.deleteQuery"),
		
		//DB_ANSWER
		DB_QUERIES_ANSWER_CREATEANSWER("db.queries.answer.createQuery"),
		DB_QUERIES_ANSWER_DELETE_BY_QUESTION_ID_QUERY("db.queries.anser.deleteByQuestionIDQuery"),
		DB_QUERIES_ANSWER_DELETE_BY_QUIZ_ID_QUERY("db.queries.answer.deleteByQuizIDQuery"),

		//DB_MCQQUESTION
		DB_QUERIES_MCQQUESTION_CREATE_QUERY("db.queries.mcqquestion.createQuery"),
		DB_QUERIES_MCQQUESTION_UPDATE_QUERY("db.queries.mcqquestion.updateQuery"),
		DB_QUERIES_MCQQUESTION_READ_QUESTION_BY_QUIZID_QUERY("db.queries.mcqquestion.readQuestionByQuizID"),
		DB_QUERIES_MCQQUESTION_READ_QUESTION_BY_TOPIC("db.queries.mcqquestion.readQuestionsByTopic"),
		DB_QUERIES_MCQQUESTION_SELECT_QUESTION_QUERY("db.queries.mcqquestion.selectQuestionQuery"),
		DB_QUERIES_MCQQUESTION_DELETE_BY_ID_QUERY("db.queries.mcqquestion.deleteByIdQuery"),
		DB_QUERIES_MCQQUESTION_DELETE_BY_MCQQUESTIONID_QUERY("db.queries.mcqquestion.deleteQuestionByMCQQuestionIDQuery"),	
		
		//DB_MCQCHOICE
		DB_QUERIES_MCQCHOICE_CREATEQUERY("db.queries.mcqchoice.createQuery"),
		DB_QUERIES_MCQCHOICE_DELETEBYQUESTIONID("db.queries.mcqchoice.deleteByQuestionID"),
		DB_QUERIES_MCQCHOICE_SELECTCHOICEBYQUESTIONID("db.queries.mcqchoice.selectChoiceByQuestionID"),
		
		//Database connections
		DB_URL("db.url"),
		DB_USERNAME("db.username"),
		DB_PASSWORD("db.password"),
		;

		private String key;
		
		public String getKey() {
			return key;
		}
		
		private ConfigEntry(String key) {
			this.key = key;
		}
}
