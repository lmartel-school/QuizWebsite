package question;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import database.DataBaseObject;


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
	
	public void setValue(String value) {
		attrValue = value;
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
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (dbID == -1) {
				generateID(conn, "Question_Attribute");
				query = "Insert into Question_Attribute VALUES (" + dbID + ", " + question_id + ", '" + 
					attrType + "', '" + attrValue + "');";
				
			} else {
				query = "UPDATE Question_Attribute set attr_value='" + attrValue + "' WHERE id=" + dbID + ";";
			}
			
			stmt.executeUpdate(query);
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public int getQuestion_id() {
		return question_id;
	}

	public String getAttrType() {
		return attrType;
	}

	public String getAttrValue() {
		return attrValue;
	}
}
