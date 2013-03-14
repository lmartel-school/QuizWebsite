package question;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import quiz.Quiz;

import database.DataBaseObject;


public abstract class Question extends DataBaseObject{
	//if we're pulling from the database, we just store the parent's
	//database ID to avoid having to query for it.
	private int quizID;
	//if we're making a new database object, we pass a pointer to 
	//the parent to enable lazy-setting the database ID
	private Quiz parentQuiz;
	//NOTE: access quizID only through getQuizID(), because sometimes you want quizID and sometimes you want parentQuiz
	
	protected int questionNumber;
	protected QUESTION_TYPE type;
	protected AttributeMap attributes;
	
	
	public static enum QUESTION_TYPE {
		MULTI_CHOICE(0, "Multiple Choice"),
		FILL_IN(1, "Fill-in-the-Blank"),
		PICTURE(2, "Picture Prompt"),
		SIMPLE_RESPONSE(3, "Simple text response"),
		MULTI_TEXT_ANSWER(4, "Multiple text answers");
		
		private QUESTION_TYPE(final int value, final String text){
			this.value = value;
			this.text = text;
		}
		
		private static Map<Integer, QUESTION_TYPE> serializer;
		
		static {
			serializer = new HashMap<Integer, QUESTION_TYPE>();
			for(QUESTION_TYPE t : QUESTION_TYPE.values()){
				serializer.put(t.value, t);
			}
		}
		
		public static QUESTION_TYPE serialize(int t){
			return serializer.get(t);
		}
		public final String text;
		public final int value;
	}
	
	public static enum QUESTION_ATTRIBUTE {
		CORRECT("correct"),
		WRONG("wrong"),
		PROMPT("prompt"),
		PICTURE_URL("picture_url"),
		ORDERED_ANSWERS("require_ordered_answers");
		
		private QUESTION_ATTRIBUTE(final String text) {
			this.text = text;
		}
		private final String text;
		
		public String toString() { return text; }
	}
	
	/**
	 * Factory constructor. Parses the attributes to determine the type of question
	 * we want, then calls the proper constructor and returns the result.
	 * @param attrs
	 * @param conn
	 * @return a new Question object
	 */
	public static Question build(String[] attrs, Connection conn){
		QUESTION_TYPE type = QUESTION_TYPE.serialize(Integer.parseInt(attrs[3]));
		switch(type){
		case MULTI_CHOICE: return new MultiChoiceQuestion(attrs, conn);
		case FILL_IN: return new FillInQuestion(attrs, conn);
		case PICTURE: return new PictureQuestion(attrs, conn);
		case MULTI_TEXT_ANSWER: return new MultiTextAnswerQuestion(attrs, conn);
		default: assert(false);
		}
		return null;
	}

	/**
	 * DataBaseObject constructor.
	 * Initializes fields from database, and loads attributes map from the attributes table.
	 * TODO: create constructor for creating new (ie, not yet in database) questions
	 * @param attrs
	 * @param conn
	 */
	
	protected Question(String[] attrs, Connection conn) {
		super(attrs, conn);
		quizID = Integer.parseInt(attrs[1]);
		questionNumber = Integer.parseInt(attrs[2]);
		type = QUESTION_TYPE.serialize(Integer.parseInt(attrs[3]));
		
		attributes = new AttributeMap();
		getAttributes(conn);
		
	}
	/**
	 * "New" constructor for questions not yet in database
	 * @param quizID
	 * @param questionNumber
	 */
	protected Question(Quiz parentQuiz, int questionNumber, QUESTION_TYPE type){
		super();
		this.parentQuiz = parentQuiz;
		this.questionNumber = questionNumber;
		this.type = type;
		attributes = new AttributeMap();
	}
	
	private void getAttributes(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			String query;
			query = "SELECT * FROM Question_Attribute WHERE question_id =" + dbID + ";";
			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String attrType = rs.getString("attr_type");
				String attrValue = rs.getString("attr_value");
				attributes.put(attrType, new QuestionAttribute(this, attrType, attrValue));
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void saveToDataBase(Connection conn) {
		List<QuestionAttribute> allAttrs = attributes.getAll();
		
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (dbID == -1) {
				generateID(conn, "Question");
				query = "Insert into Question VALUES (" + dbID + ", " + getQuizID() + ", " + 
					questionNumber + ", " + type.value + ");";
				
			} else {
				query = "UPDATE Question set question_number=" + questionNumber + ", question_type=" + 
					type + " WHERE id=" + dbID + ";";
			}
			
			stmt.executeUpdate(query);
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < allAttrs.size(); i++) {
			allAttrs.get(i).saveToDataBase(conn);
		}
		
	}
	
	/**
	 * These render the question into HTML: one HTML rendering for taking a quiz,
	 * and one for creating a quiz. Creates form elements assuming that render()
	 * is called from within a <form> tag. Slightly janky, but you don't know my life
	 * @return
	 */
	public abstract String renderQuizMode();
	
	/**
	 * This is an HTML rendering factory-style method.
	 * Basically a workaround/accessor for the inherited static method problem.
	 * There's an assert at the bottom to remind you to add every question type to this method.
	 * @param t
	 * @return
	 */
	public final static String renderCreateMode(QUESTION_TYPE t){
		switch(t){
		case MULTI_CHOICE: return MultiChoiceQuestion.renderCreateMode();
		case FILL_IN: return FillInQuestion.renderCreateMode();
		case PICTURE: return PictureQuestion.renderCreateMode();
		case SIMPLE_RESPONSE: return SimpleResponseQuestion.renderCreateMode();
		case MULTI_TEXT_ANSWER: return MultiTextAnswerQuestion.renderCreateMode();
		default: assert(false);
		}
		return null;
	}
	
	/**
	 * Questions must be able to check whether an answer is correct or not.
	 * @param answer
	 * @return
	 */
	public abstract boolean checkAnswer(String answer);

	public int getQuizID() {
		return parentQuiz != null ? parentQuiz.getID() : quizID;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}

	public QUESTION_TYPE getType() {
		return type;
	}

	public void setType(QUESTION_TYPE type) {
		this.type = type;
	}
	
	/**
	 * Returns a correct answer to the question.
	 * If multiple correct answers are defined, it will return one of them.
	 * Choosing behavior undefined.
	 * @return
	 */
	public String getAnAnswer(){
		return attributes.getFirst(QUESTION_ATTRIBUTE.CORRECT).getAttrValue();
	}
	
	public abstract String getCompleteAnswer();
	
	protected String getPrompt(){
		QuestionAttribute prompt = attributes.getFirst(Question.QUESTION_ATTRIBUTE.PROMPT);
		return prompt.getAttrValue();
	}
	
	//Temporary getter for testing
		public AttributeMap getAttrs(){
			return attributes;
		}

}
