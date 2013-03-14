package quiz;

import question.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.Map.Entry;

public class InProgressQuiz {
	
	private Quiz quiz;
	private List<Question> questions;
	private Question activeQuestion;
	private Map<Integer, String> userAnswers;
	
	private Iterator<Question> questionIterator;
	private int score;
	private long startTimeMillis;
	private String start;
	
	public InProgressQuiz(Quiz quiz, Connection conn) {
		this.quiz = quiz;
		this.questions = quiz.getQuestions();
		activeQuestion = null;
		userAnswers = new HashMap<Integer, String>();
		questionIterator = questions.iterator();
		score = 0;
		if(hasNextQuestion()) moveToNextQuestion(); //Start at first question, if there is at least one question
		startTimeMillis = System.currentTimeMillis();
		Date date = new Date(startTimeMillis);
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
		start = formatter.format(date);
	}
	
	public boolean hasNextQuestion(){
		return questionIterator.hasNext();
	}
	
	public void moveToNextQuestion(){
		activeQuestion = questionIterator.next();
	}
	
	/**
	 * Submits an answer for the active question.
	 * @param submission
	 */
	public void submitAnswer(String submission){
		userAnswers.put(activeQuestion.getQuestionNumber(), submission);
	}
	
	public boolean checkQuestionByNumber(int n){
		Question q = quiz.getQuestionByNumber(n);
		return q.checkAnswer(userAnswerForQuestion(q));
	}
	
	public boolean checkActiveQuestion() {
		return checkQuestionByNumber(activeQuestion.getQuestionNumber());
	}
	
	
	public void gradeAll() {
		score = 0;
		for (Entry<Integer, String> e : userAnswers.entrySet()) {
			if(quiz.getQuestionByNumber(e.getKey()).checkAnswer(e.getValue())){
				score++;
			}
		}
	}
	
	public int getScore() {
		return score;
	}
	
	public int getMaxPossibleScore(){
		return quiz.getMaxScore();
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public Question getActiveQuestion() {
		return activeQuestion;
	}
	
	public String userAnswerForQuestion(Question q){
		return userAnswers.get(q.getQuestionNumber());
	}
	
	public String getFeedback() {
		String feedback;
		String userAnswer = userAnswerForQuestion(getActiveQuestion());
		if(checkActiveQuestion()) feedback = "\"" + userAnswer + "\" was correct! Nice job!";
		else feedback = "\"" + userAnswer + "\" wasn't quite right, sorry... we were looking for something like \"" + getActiveQuestion().getAnAnswer() + "\"";
		return feedback;
	}
	
	public String getStartTimeFormatted() {
		return start;
	}
	
	public long getStartTimeMillis() {
		return startTimeMillis;
	}

}
