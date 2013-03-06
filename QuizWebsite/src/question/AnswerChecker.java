package question;

import java.util.List;

/**
 * Pulled answer checking out into a utility class to allow us to change it later.
 * For now it just does String equality comparison.
 * 
 * We could also subclass off of this to allow different question types to have different
 * definitions of "correct"
 * @author Leo
 *
 */
public class AnswerChecker {
	
	//Utility class. Do not instantiate!
	private AnswerChecker(){}
	
	public static boolean check(String input, String correct){
		return input.equals(correct);
	}
	
	public static boolean check(String input, QuestionAttribute correct){
		return check(input, correct.getAttrValue());
	}
	
	public static boolean check(String input, List<QuestionAttribute> correctAnswers){
		for(QuestionAttribute answer : correctAnswers){
			if(check(input, answer)) return true;
		}
		return false;
	}
}
