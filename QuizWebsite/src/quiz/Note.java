package quiz;

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
	
		setTextFromDB(args[0], conn);
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
		// TODO This save function has to update both the Notes table, and 
		// the generic messages table. 
		
		saveToNotes();
		saveToMsg();

	}
	
	public String getText() {
		return text;
	}
	
	private void setTextFromDB(String id, Connection conn) {
		try {
			Statement stmt = conn.createStatement();      
			String query = "SELECT msg from Note WHERE id=" + id + ";";     
			
			ResultSet rs = stmt.executeQuery(query);     
			text = rs.getString("msg");	      
			   
		} catch (SQLException e) {     
			e.printStackTrace();
		}
	}

}
