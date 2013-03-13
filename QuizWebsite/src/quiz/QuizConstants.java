package quiz;

public class QuizConstants {
	
	
	private QuizConstants() {
		/* empty */
	}
	
	public static final int CHALLENGE_N_COLS = 6;
	public static final int USER_N_COLUMNS = 4;
	public static final int N_TOP_RATED = 5;
	public static final int QUIZ_N_COLS = 6;
	public static final int N_FRIEND_ACTIVITIES = 3;
	public static final int QUESTION_N_COLS = 4;
	public static final int QUIZ_RESULT_N_COLS = 6;
	public static final int QUESTION_ATTRIBUTE_N_COLS = 4;
	public static final int MSG_FRIEND_N_COLS = 5;
	public static final int ANNOUNCE_N_COL= 2;
	
	
	public static final int N_TOP_SCORERS = 5;
	public static final long RECENT_INTERVAL = 900000;
	
	public static final String FILL_IN_DELIMITER = "[_]";
	public static final String TEXTAREA_NEWLINE_REGEX = "(\\r|\\n)+"; //newlines in <textarea> boxes are handled differently in Unix, Mac, and PC; this should shut them all the fuck up


}
