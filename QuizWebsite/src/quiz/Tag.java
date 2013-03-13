package quiz;

import java.sql.*;

import database.*;

public class Tag extends DataBaseObject {
	
	private String tag;
	private int quizID;

	public Tag(int quizID, String text) {
		super();
		this.quizID = quizID;
		tag = text;
		
	}
	
	public Tag(String[] attrs, Connection conn) {
		super(attrs, conn);
		
	}
	
	
	@Override
	public void saveToDataBase(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (dbID == -1) {
				generateID(conn, "Tag");
				query = "Insert into Tag VALUES (" + dbID + ", " + quizID + ", '" + 
					tag + "');";
				stmt.executeUpdate(query);
			}
			
			stmt.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getTag() {
		return tag;
	}
	
	public int getQuizID() {
		return quizID;
	}

}
