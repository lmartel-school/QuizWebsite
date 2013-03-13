package question;

import java.sql.Connection;
import java.util.regex.Pattern;

import quiz.Quiz;
import quiz.QuizConstants;

public class FillInQuestion extends Question {

	protected FillInQuestion(String[] attrs, Connection conn) {
		super(attrs, conn);
	}
	
	public FillInQuestion(Quiz parent, int number, String prompt, String answer){
		super(parent, number, Question.QUESTION_TYPE.FILL_IN);
		attributes.putNew(this, Question.QUESTION_ATTRIBUTE.PROMPT, prompt);
		attributes.putNew(this, Question.QUESTION_ATTRIBUTE.CORRECT, answer);
	}
	
	private String getCorrectAnswer(){
		QuestionAttribute correct = attributes.getFirst(Question.QUESTION_ATTRIBUTE.CORRECT);
		return correct.getAttrValue();
	}

	@Override
	public boolean checkAnswer(String answer) {
		return AnswerChecker.check(answer, getCorrectAnswer());
	}
	
	private String getPrompt(){
		QuestionAttribute prompt = attributes.getFirst(Question.QUESTION_ATTRIBUTE.PROMPT);
		return prompt.getAttrValue();
	}

	@Override
	public String renderQuizMode() {
		String HTML = "";
		String prompt = getPrompt();
		prompt = prompt.replaceFirst(Pattern.quote(QuizConstants.FILL_IN_DELIMITER), "<input type=\"text\" name=\"answer" + questionNumber +"\">");
		HTML += "<p>" + prompt + "</p><br>" + '\n';
		HTML += "<input type=\"submit\" value=\"Submit\">" + '\n';
		return HTML;
	}

	public static String renderCreateMode() {
		String HTML = "";
		HTML += "<input type=\"hidden\" name=\"type\" value=\"" + Question.QUESTION_TYPE.FILL_IN.value + "\">" + '\n';
		HTML += "<p>Text prompt (indicate blank location using \"" + QuizConstants.FILL_IN_DELIMITER + "\" without the quotes):</p>" + '\n';
		HTML += "<textarea cols=\"40\" rows=\"5\" name=\"prompt\">Enter question prompt here</textarea>" + '\n';
		HTML += "<p>Correct answer:</p>" + '\n';
		HTML += "<input type=\"text\" name=\"answer\"><br>" + '\n';
		return HTML;
	}
}
