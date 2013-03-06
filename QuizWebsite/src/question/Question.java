package question;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import quiz.DataBaseObject;

public abstract class Question extends DataBaseObject{
	
	private int quizID;
	private int questionNumber;
	private QUESTION_TYPE type;
	protected AttributeMap attributes;
	
	
	public static enum QUESTION_TYPE {
		MULTI_CHOICE,
		FILL_IN,
		PICTURE;
	}
	
	public static enum QUESTION_ATTRIBUTE {
		CORRECT("correct"),
		WRONG("wrong"),
		PROMPT("prompt");
		
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
		QUESTION_TYPE type = serializeType(attrs[3]);
		switch(type){
		case MULTI_CHOICE: return new MultiChoiceQuestion(attrs, conn);
		case FILL_IN: return new FillInQuestion(attrs, conn);
		case PICTURE: return new PictureQuestion(attrs, conn);
		}
		assert(false);
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
		type = serializeType(attrs[3]);
		
		attributes = new AttributeMap();
		getAttributes(conn);
		
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
				attributes.put(attrType, new QuestionAttribute(dbID, attrType, attrValue));
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	private static QUESTION_TYPE serializeType(String attr){
		int val = Integer.parseInt(attr);
		switch(val){ 
		case 0: return QUESTION_TYPE.MULTI_CHOICE; 
		case 1: return QUESTION_TYPE.FILL_IN;
		case 2: return QUESTION_TYPE.PICTURE;
		}
		return null;
	}


	@Override
	public void saveToDataBase(Connection conn) {
		List<QuestionAttribute> allAttrs = attributes.getAll();
		//TODO: save all modified attributes to attributes table somehow.
		//Possible simple approach: update (if ID found) / insert (if ID not found) all attributes
			//in map on save
		//Possible more complex approach: maintain Map of "dirty" (changed) attributes,
			//update those and clear the map on save
		
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (dbID == -1) {
				generateID(conn, "Question");
				query = "Insert into Question VALUES (" + dbID + ", " + quizID + ", " + 
					questionNumber + ", " + type + ");";
				
			} else {
				query = "UPDATE Question set question_number=" + questionNumber + ", question_type=" + 
					type + " WHERE id=" + dbID + ";";
			}
			
			stmt.executeUpdate(query);
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public int getQuizID() {
		return quizID;
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
	
	//Temporary getter for testing
	public AttributeMap getAttrs(){
		return attributes;
	}

}
