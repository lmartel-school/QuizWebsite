package user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Note extends Message {
	private String text;

	
	/**
	 * This constructor takes in the line from the Messages database, so it has
	 * to query the Notes table (using dbID) to find the text that belongs here.
	 * @param args
	 * @param conn
	 */
	
	public Note(String[] args, Connection conn) {
		super(args, conn);
		text = args[4]; //1-to-1 relationship guarentees this will be ok
	}
	
	/**
	 * Constructor of new Note, not read from database.
	 */
	public Note(String sender, String recipient, String text) {
		super(sender, recipient); //set sender/recip/dbID
		this.text = text;
	}
	
	@Override
	public void saveToDataBase(Connection conn) { 
		saveToNotes(conn);
		saveToMsg(conn);

	}
	
	public String getText() {
		return text;
	}
	
//	private void setTextFromDB(String id, Connection conn) {
//		try {
//			Statement stmt = conn.createStatement();      
//			String query = "SELECT msg from Note WHERE id=" + id + ";";     
//			
//			ResultSet rs = stmt.executeQuery(query);     
//			text = rs.getString("msg");	      
//			   
//		} catch (SQLException e) {     
//			e.printStackTrace();
//		}
//	}
	
	private void saveToNotes(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (dbID == -1) {
				generateID(conn, "Message");
				query = "Insert into Note VALUES (" + dbID + ", '" + text + "');";
				stmt.executeUpdate(query);
			} else {
				query = "UPDATE Note set msg='" + text + "' WHERE id=" + dbID + ";";
			}
			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

}
