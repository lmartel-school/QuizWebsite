package quiz;

import question.*;

import java.sql.*;
import java.util.*;

public class InProgressQuiz {
	
	private Quiz quiz;
	private List<Question> questions;
	private List<QuestionAttribute> answers;
	private List<String> userAnswers;
	
	private int curQuestionNum;
	private int score;
	
	public InProgressQuiz(Quiz quiz, Connection conn) {
		this.quiz = quiz;
		curQuestionNum = 1;
		score = 0;
	}
	
	
	
	public void incrementCurQuestion() {
		curQuestionNum++;
	}
	
	/**
	 * This is an int so that we can add multi point scoring later.
	 * @param qNum
	 * @param guess
	 * @return
	 */
	public int gradeOne(int qNum, String guess) {
		if (answers.get(qNum).equals(guess)){
			return 1;
		}
		return 0;
	}
	
	
	public void gradeAll() {
		for (int i = 0; i < userAnswers.size(); i++) {
			if (userAnswers.get(i).equals(answers.get(i))) score++;
		}
	}
	
	public int getScore() {
		return score;
	}

}
