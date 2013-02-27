package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Quiz extends DataBaseObject {
	
	private String name;
	private boolean inOrder;
	private int type; 
	private String author;
	private String description;
	private List<Question> questions;
	
	/**
	 * Enumerations
	 * PAGE_TYPE: what format the quiz takes. Either all on one page, multi-page with
	 * immediate feedback, or a multi-page with feedback once it's entirely complete.
	 */
	
	public static enum PAGE_TYPE {
		SINGLE_PAGE,
		MULTI_IMMEDIATE,
		MULTI_PAGE;
	}
	
	/*
	 * Given an array of attributes from the Quiz table,
	 * constructs a quiz object.
	 */
	public Quiz(String[] attrs, Connection con){
		super(attrs, con);
		name = attrs[1];
		inOrder = Boolean.parseBoolean(attrs[2]);
		type = Integer.parseInt(attrs[3]);
		setAuthor(attrs[4]);
		description = attrs[5];
		questions = findQuestions();
	}
	
	
	private List<Question> findQuestions(){
		//TODO: find all questions from database whose quiz_id = this.id
		//Use the Question class' database constructor, add them to
		//an arraylist, and return it
		return null;
	}
	
	/*
	 * Constructs new Quiz object,
	 * sets id to "does not exist yet" value
	 */
	public Quiz(String name, boolean inOrder, int type, String author, String description){
		super(); //set dbid = -1
		this.name = name;
		this.inOrder = inOrder;
		this.type = type;
		this.setAuthor(author);
		this.description = description;
	}
	
	
	public void saveToDataBase(Connection conn){
		//TODO: save to database.
		//If dbid == -1, it's a new entry;
		//otherwise, it's an update
		
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (dbID == -1) {
				generateID(conn, "Quiz");
				query = "Insert into Quiz VALUES (" + dbID + "'" + name + "', " + inOrder + ", " + type + ", '" + author + "', '" + description + "');";
				
			} else {
				query = "UPDATE Quiz set name='" + name + "', inOrder=" + inOrder + ", type=" + type + ", author ='" + author + "', description='" + description + "' WHERE id=" + dbID;
			}
			
			stmt.executeUpdate(query);
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/* Begin setters/getters */
	
	public List<Question> getQuestions(){
		return questions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isInOrder() {
		return inOrder;
	}

	public void setInOrder(boolean inOrder) {
		this.inOrder = inOrder;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	

	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
