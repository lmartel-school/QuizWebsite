package question;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiChoiceQuestion extends Question {

	protected MultiChoiceQuestion(String[] attrs, Connection conn) {
		super(attrs, conn);
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
	
	public String getPrompt(){
		QuestionAttribute prompt = attributes.getFirst(Question.QUESTION_ATTRIBUTE.PROMPT);
		return prompt.getAttrValue();
	}
	
	public boolean checkAnswer(String answer){
		 return AnswerChecker.check(answer, getCorrectChoices());
	}
	 
	private List<QuestionAttribute> getCorrectChoices(){
			return attributes.get(Question.QUESTION_ATTRIBUTE.CORRECT);
	}
		
	private List<QuestionAttribute> getWrongChoices(){
		return attributes.get(Question.QUESTION_ATTRIBUTE.WRONG);
	}

	@Override
	public String render() {
		String HTML = "";
		HTML += "<p>" + getPrompt() + "</p><br>" + '\n';
		for(String choice : getChoices()){
			HTML += "<input type=\"radio\" name=\"answer\" value=\"" + choice + "\">" + choice + "<br>" + '\n';
		}
		HTML += "input type=\"submit\" value=\"Submit\"" + '\n';
		return HTML;
	}
}
