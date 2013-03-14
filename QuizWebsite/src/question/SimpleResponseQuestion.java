package question;

import quiz.Quiz;
import quiz.QuizConstants;

public abstract class SimpleResponseQuestion  {

	private SimpleResponseQuestion(){} //don't instantiate meeeee

	public static String renderCreateMode() {
		String HTML = "";
		HTML += "<input type=\"hidden\" name=\"type\" value=\"" + Question.QUESTION_TYPE.SIMPLE_RESPONSE.value + "\">" + '\n';
		HTML += "<p>Text prompt :</p>" + '\n';
		HTML += "<textarea cols=\"40\" rows=\"5\" name=\"prompt\">Enter question prompt here</textarea>" + '\n';
		HTML += "<p>Correct answer:</p>" + '\n';
		HTML += "<input type=\"text\" name=\"answer\"><br>" + '\n';
		return HTML;
	}
}
