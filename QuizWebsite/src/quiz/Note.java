package quiz;

import java.sql.Connection;

public class Note extends Message {
	String text;

	
	/**
	 * This constructor takes in the line from the Messages database, so it has
	 * to query the Notes table (using dbID) to find the text that belongs here.
	 * @param args
	 * @param conn
	 */
	
	public Note(String[] args, Connection conn) {
		super(args, conn);
		
		//TODO: set text to value retrieved by finding Note with correct dbID
	}
	
	/**
	 * Constructor of new Note, not read from database.
	 */
	public Note(String sender, String recipient, String text) {
		super(sender, recipient);
		this.text = text;
	}
	
	@Override
	public void saveToDataBase(Connection conn) {
		// TODO This save function has to update both the Notes table, and 
		// the generic messages table. 

	}
	
	public String getText() {
		return text;
	}

}
