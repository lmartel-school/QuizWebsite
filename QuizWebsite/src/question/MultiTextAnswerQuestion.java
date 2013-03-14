package question;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

import quiz.Quiz;
import quiz.QuizConstants;

public class MultiTextAnswerQuestion extends Question {

	public MultiTextAnswerQuestion(String[] attrs, Connection conn) {
		super(attrs, conn);
	}
	
	public MultiTextAnswerQuestion(Quiz parent, int number, String prompt, List<String> correctAnswers, boolean orderMatters) {
		super(parent, number, Question.QUESTION_TYPE.MULTI_TEXT_ANSWER);
		attributes.putNew(this, Question.QUESTION_ATTRIBUTE.PROMPT, prompt);
		for(String a : correctAnswers){
			attributes.putNew(this, Question.QUESTION_ATTRIBUTE.CORRECT, a);
		}
		attributes.putNew(this, Question.QUESTION_ATTRIBUTE.ORDERED_ANSWERS, Boolean.toString(orderMatters));
	}
	
	/**
	 * Clones the list of correct answers from the map to allow sorting while
	 * preserving the ordering of the original.
	 */
	private List<String> cloneCorrectAnswers(){
		List<QuestionAttribute> correctAnswers = attributes.get(QUESTION_ATTRIBUTE.CORRECT);
		List<String> clones = new ArrayList<String>();
		for(QuestionAttribute a : correctAnswers){
			clones.add(a.getAttrValue());
		}
		return clones;
	}

	/**
	 * This method takes an answer String pulled straight from a text box, splits it into a list
	 * and then evaluates it against the list of correct answers based on the orderMatters attribute.
	 */
	@Override
	public boolean checkAnswer(String answer) {
		List<String> userAnswers = Arrays.asList(answer.split(QuizConstants.TEXTAREA_NEWLINE_REGEX));
		List<String> realAnswers = cloneCorrectAnswers();
		if(!doesOrderMatter()){
			Collections.sort(userAnswers);
			Collections.sort(realAnswers);
		}
		return realAnswers.equals(userAnswers);
	}
	
	private boolean doesOrderMatter(){
		return Boolean.parseBoolean(attributes.getFirst(QUESTION_ATTRIBUTE.ORDERED_ANSWERS).getAttrValue());
	}
	
	
	@Override
	public String renderQuizMode() {
		String HTML = "";
		HTML += "<p>" + getPrompt() + "</p><br>" + '\n';
		HTML += "<p>Enter your answers, one per line:</p>";
		HTML += "<textarea cols=\"40\" rows=\"5\" name=\"answer" + questionNumber + "\"></textarea>";
		return HTML;
	}
	
	public static String renderCreateMode(){
		String HTML = "";
		HTML += "<input type=\"hidden\" name=\"type\" value=\"" + Question.QUESTION_TYPE.MULTI_TEXT_ANSWER.value + "\">" + '\n';
		HTML += "<p>Prompt:</p>" + '\n';
		HTML += "<textarea cols=\"40\" rows=\"5\" name=\"prompt\">Enter question prompt here</textarea>" + '\n';
		HTML += "<p>Correct answers:</p>" + '\n';
		HTML += "<textarea cols=\"40\" rows=\"5\" name=\"correct\">Enter correct answers to display here,\n one per line</textarea>" + '\n';
		HTML += "<p>The user must enter every correct answer to recieve credit. Do you want to check that their answers are in the same order as yours?</p>";
		HTML += "<input type=\"radio\" name=\"inOrder\" value=\"true\"> Yes, they must read my mind!<br>";
		HTML += "<input type=\"radio\" name=\"inOrder\" value=\"false\"> No, they just need to have them all in there.<br>";
		
		return HTML;
	}

	@Override
	public String getCompleteAnswer() {
		List<QuestionAttribute> answerList = attributes.get(Question.QUESTION_ATTRIBUTE.CORRECT);
		String result = "";
		for(int i = 0; i < answerList.size(); i++){
			result += answerList.get(i).getAttrValue();
			if(i != answerList.size() - 1) result += " ";
		}
		if(doesOrderMatter()) result += " (order matters)";
		return result;
	}

}
