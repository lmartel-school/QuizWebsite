package question;

import java.sql.Connection;

import quiz.Quiz;

public class PictureQuestion extends Question {

	protected PictureQuestion(String[] attrs, Connection conn) {
		super(attrs, conn);
	}
	
	public PictureQuestion(Quiz parent, int number, String pictureURL, String prompt, String answer){
		super(parent, number, Question.QUESTION_TYPE.PICTURE);
		attributes.putNew(this, Question.QUESTION_ATTRIBUTE.PICTURE_URL, pictureURL);
		attributes.putNew(this, Question.QUESTION_ATTRIBUTE.PROMPT, prompt);
		attributes.putNew(this, Question.QUESTION_ATTRIBUTE.CORRECT, answer);
	}
	
	private String getPrompt(){
		QuestionAttribute prompt = attributes.getFirst(Question.QUESTION_ATTRIBUTE.PROMPT);
		return prompt.getAttrValue();
	}
	
	private String getPictureURL(){
		QuestionAttribute url = attributes.getFirst(Question.QUESTION_ATTRIBUTE.PICTURE_URL);
		return url.getAttrValue();
	}
	
	private String getCorrectAnswer(){
		QuestionAttribute correct = attributes.getFirst(Question.QUESTION_ATTRIBUTE.CORRECT);
		return correct.getAttrValue();
	}
	
	public boolean checkAnswer(String answer){
		 return AnswerChecker.check(answer, getCorrectAnswer());
	}

	@Override
	public String renderQuizMode() {
		String HTML = "";
		HTML += "<img src=\"" + getPictureURL() + "\">" + '\n';
		HTML += "<p>" + getPrompt() + "</p><br>" + '\n';
		HTML += "Your answer: <input type=\"text\" name=\"answer\"><br>" + '\n';
		HTML += "<input type=\"submit\" value=\"Submit\">" + '\n';
		return HTML;
	}

	public static String renderCreateMode() {
		String HTML = "";
		HTML += "<input type=\"hidden\" name=\"type\" value=\"" + Question.QUESTION_TYPE.PICTURE.value + "\">" + '\n';
		HTML += "<p>Picture URL: <input type=\"text\" name=\"picture_url\"></p>" + '\n';
		HTML += "<p>Text prompt (optional):</p>" + '\n';
		HTML += "<textarea cols=\"40\" rows=\"5\" name=\"prompt\">Enter question prompt here</textarea>" + '\n';
		HTML += "<p>Correct answer:</p>" + '\n';
		HTML += "<input type=\"text\" name=\"answer\"><br>" + '\n';
		//HTML += "<input type=\"submit\" value=\"Submit\">" + '\n';
		return HTML;
	}
}
