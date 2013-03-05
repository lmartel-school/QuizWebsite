package question;

import java.sql.Connection;

import quiz.DataBaseObject;

public class QuestionAttribute extends DataBaseObject {

	private int question_id;
	private String attrType;
	private String attrValue;
	
	public QuestionAttribute(int question, String type, String value){
		super();
		question_id = question;
		attrType = type;
		attrValue = value;
	}
	
	@Override
	public String toString(){
		return attrValue;
	}
	
	/**
	 * We're (at least, for now) forbidding loading these from the database individually.
	 * The Question class is responsible for creating its Attribute objects on construction.
	 */
	public QuestionAttribute(String[] attrs, Connection conn) {
		//super(attrs, conn);
		assert(false);
	}
	
	@Override
	public void saveToDataBase(Connection conn) {
		// TODO Auto-generated method stub
		
	}

	public String getValue() {
		return attrValue;
	}

}
