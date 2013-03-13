package quiz;

import question.*;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

public class InProgressQuiz {
	
	private Quiz quiz;
	private List<Question> questions;
	private Question activeQuestion;
	private Map<Integer, String> userAnswers;
	
	private Iterator<Question> questionIterator;
	private int score;
	
	public InProgressQuiz(Quiz quiz, Connection conn) {
		this.quiz = quiz;
		this.questions = quiz.getQuestions();
		activeQuestion = null;
		questionIterator = questions.iterator();
		score = 0;
	}
	
	boolean hasNextQuestion(){
		return questionIterator.hasNext();
	}
	
	Question getNextQuestion(){
		return activeQuestion = questionIterator.next();
	}
	
	/**
	 * Submits an answer for the active question.
	 * @param submission
	 */
	public void submitAnswer(String submission){
		userAnswers.put(activeQuestion.getQuestionNumber(), submission);
	}
	
	/**
	 * This is an int so that we can add multi point scoring later.
	 * @param qNum
	 * @param guess
	 * @return
	 */
	public int gradeActiveQuestion() {
		if (activeQuestion.checkAnswer(userAnswers.get(activeQuestion.getQuestionNumber()))){
			return 1;
		}
		return 0;
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

}
