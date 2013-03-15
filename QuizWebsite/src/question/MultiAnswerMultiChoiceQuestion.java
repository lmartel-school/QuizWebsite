package question;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import quiz.Quiz;
import quiz.QuizConstants;

public class MultiAnswerMultiChoiceQuestion extends MultiChoiceQuestion {

	public MultiAnswerMultiChoiceQuestion(Quiz parent, int number,
			String prompt, List<String> correctAnswers,
			List<String> wrongAnswers) {
		super(parent, number, prompt, correctAnswers, wrongAnswers);
		this.type = QUESTION_TYPE.MULTI_CHOICE_MULTI_ANSWER;
	}
	
	public MultiAnswerMultiChoiceQuestion(String[] attrs, Connection conn) {
		super(attrs, conn);
	}
	
	private List<String> cloneCorrectAnswers(){
		List<QuestionAttribute> correctAnswers = attributes.get(QUESTION_ATTRIBUTE.CORRECT);
		List<String> clones = new ArrayList<String>();
		for(QuestionAttribute a : correctAnswers){
			clones.add(a.getAttrValue());
		}
		return clones;
	}
	
	@Override
	public boolean checkAnswer(String answer) {
		List<String> userAnswers = Arrays.asList(answer.split(QuizConstants.TEXTAREA_NEWLINE_REGEX));
		List<String> correctAnswers = cloneCorrectAnswers();
		Collections.sort(userAnswers);
		Collections.sort(correctAnswers);
		return correctAnswers.equals(userAnswers);
	}
	
	@Override
	public String getCompleteAnswer(){
		List<QuestionAttribute> answerList = attributes.get(Question.QUESTION_ATTRIBUTE.CORRECT);
		String result = "";
		for(int i = 0; i < answerList.size(); i++){
			result += answerList.get(i).getAttrValue();
			if(i != answerList.size() - 1) result += ", ";
		}
		return result;
	}
	
	@Override
	public String renderQuizMode() {
		String HTML = "";
		HTML += "<p>" + getPrompt() + "</p><br>" + '\n';
		List<String> choices = getChoices();
		for(int i = 0; i < choices.size(); i++){
			//name is "answerquestionnumber_i"
			HTML += "<input type=\"checkbox\" name=\"answer" + questionNumber + "_" + i + "\" value=\"" + choices.get(i) + "\">" + choices.get(i) + "<br>" + '\n';
		}
		return HTML;
	}
	
	public static String renderCreateMode(){
		String HTML = "";
		HTML += "<input type=\"hidden\" name=\"type\" value=\"" + Question.QUESTION_TYPE.MULTI_CHOICE_MULTI_ANSWER.value + "\">" + '\n';
		HTML += "<p>Prompt:</p>" + '\n';
		HTML += "<textarea cols=\"40\" rows=\"5\" name=\"prompt\">Enter question prompt here</textarea>" + '\n';
		HTML += "<p>Correct answers:</p>" + '\n';
		HTML += "<textarea cols=\"40\" rows=\"5\" name=\"correct\">Enter correct answers to display here,\n one per line</textarea>" + '\n';
		HTML += "<p>Wrong answers:</p>" + '\n';
		HTML += "<textarea cols=\"40\" rows=\"5\" name=\"wrong\">Enter wrong answers to display here,\n one per line</textarea><br>" + '\n';
		HTML += "<p>The user must check every correct answer, and check none of the wrong answers, to recieve credit.</p>";
		
		return HTML;
	}

}
