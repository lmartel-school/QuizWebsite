package question;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import quiz.Quiz;

public class MultiChoiceQuestion extends Question {

	protected MultiChoiceQuestion(String[] attrs, Connection conn) {
		super(attrs, conn);
	}
	
	public MultiChoiceQuestion(Quiz parent, int number, String prompt, List<String> correctAnswers, List<String> wrongAnswers){
		super(parent, number, Question.QUESTION_TYPE.MULTI_CHOICE);
		attributes.putNew(this, Question.QUESTION_ATTRIBUTE.PROMPT, prompt);
		for(String a : correctAnswers){
			attributes.putNew(this, Question.QUESTION_ATTRIBUTE.CORRECT, a);
		}
		for(String a : wrongAnswers){
			attributes.putNew(this, Question.QUESTION_ATTRIBUTE.WRONG, a);
		}
	}
	
	/**
	 * Defaults to ordered rather than random.
	 * @return
	 */
	public List<String> getChoices(){
		return getChoices(false);
	}
	
	/**
	 * Returns a list of the choices, as strings, for the question. Namely, every right or wrong answer.
	 * @param randomize: whether we want random ordering (true), or lexicographical ordering (false)
	 * @return
	 */
	public List<String> getChoices(boolean randomize){
		List<String> choices = new ArrayList<String>();
		for(QuestionAttribute attr : getCorrectChoices()){
			choices.add(attr.toString());
		}
		for(QuestionAttribute attr : getWrongChoices()){
			choices.add(attr.toString());
		}
		if(randomize){
			Collections.shuffle(choices);
		} else {
			Collections.sort(choices);
		}
		return choices;
	}
	
	@Override
	public boolean checkAnswer(String answer){
		 return AnswerChecker.check(answer, getCorrectChoices());
	}
	
	private String getPrompt(){
		QuestionAttribute prompt = attributes.getFirst(Question.QUESTION_ATTRIBUTE.PROMPT);
		return prompt.getAttrValue();
	}
	 
	private List<QuestionAttribute> getCorrectChoices(){
			return attributes.get(Question.QUESTION_ATTRIBUTE.CORRECT);
	}
		
	private List<QuestionAttribute> getWrongChoices(){
		return attributes.get(Question.QUESTION_ATTRIBUTE.WRONG);
	}

	@Override
	public String renderQuizMode() {
		String HTML = "";
		HTML += "<p>" + getPrompt() + "</p><br>" + '\n';
		for(String choice : getChoices()){
			HTML += "<input type=\"radio\" name=\"answer\" value=\"" + choice + "\">" + choice + "<br>" + '\n';
		}
		HTML += "<input type=\"submit\" value=\"Submit\">" + '\n';
		return HTML;
	}
	
	@Override
	public String renderCreateMode(){
		String HTML = "";
		HTML += "<input type=\"hidden\" name=\"type\" value=\"" + Question.QUESTION_TYPE.MULTI_CHOICE + "\">" + '\n';
		HTML += "<p>Prompt:</p>" + '\n';
		HTML += "<textarea cols=\"40\" rows=\"5\" name=\"prompt\">Enter question prompt here</textarea>" + '\n';
		HTML += "<p>Correct answers:</p>" + '\n';
		HTML += "<textarea cols=\"40\" rows=\"5\" name=\"correct\">Enter correct answers to display here,\n one per line</textarea>" + '\n';
		HTML += "<p>Wrong answers:</p>" + '\n';
		HTML += "<textarea cols=\"40\" rows=\"5\" name=\"wrong\">Enter wrong answers to display here,\n one per line</textarea><br>" + '\n';
		HTML += "<input type=\"submit\" value=\"Submit\">" + '\n';
		return HTML;
	}
}
