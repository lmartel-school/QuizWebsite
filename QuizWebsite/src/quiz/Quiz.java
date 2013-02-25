package quiz;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Quiz {
	
	private int id;
	private String name;
	private boolean inOrder;
	private int type; //TODO: enumerate
	private String author;
	private String description;
	private List<Question> questions;
	
	/*
	 * Given an array of attributes from the Quiz table,
	 * constructs a quiz object.
	 */
	public Quiz(String[] attrs, Connection con){
		id = Integer.parseInt(attrs[0]);
		name = attrs[1];
		inOrder = Boolean.parseBoolean(attrs[2]);
		type = Integer.parseInt(attrs[3]);
		author = attrs[4];
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
		super(); //set id = -1
		this.name = name;
		this.inOrder = inOrder;
		this.type = type;
		this.author = author;
		this.description = description;
	}
	
	
	public void save(){
		//TODO: save to database.
		//If id == -1, it's a new entry;
		//otherwise, it's an update
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
