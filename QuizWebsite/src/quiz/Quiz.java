package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import database.DataBaseObject;

import question.*;

public class Quiz extends DataBaseObject {
	
	
	private String name;
	private boolean inOrder;
	private int type; 
	private String author;
	private String description;
	private List<Question> questions;
	private List<String> tags;
	private String category;
	
	/**
	 * Enumerations
	 * PAGE_TYPE: what format the quiz takes. Either all on one page, multi-page with
	 * immediate feedback, or a multi-page with feedback once it's entirely complete.
	 */
	
	public static enum PAGE_TYPE {
		SINGLE_PAGE(0, "All questions on a single page, corrected after submit"),
		MULTI_IMMEDIATE(1, "Each question on its own page, with immediate feedback for the user"),
		MULTI_PAGE(2, "Each question on its own page, with no correction until the end");
		
		private PAGE_TYPE(final int value, final String description){
			this.value = value;
			this.description = description;
		}
		
		public final String description;
		public final int value;
	}
	
	/*
	 * Constructs new Quiz object,
	 * sets id to "does not exist yet" value
	 */
	public Quiz(String name, boolean inOrder, int type, String author, String description, String categ, String[] tags){
		super(); //set dbid = -1
		this.name = name;
		this.inOrder = inOrder;
		this.type = type;
		this.author = author;
		this.description = description;
		this.questions = new ArrayList<Question>();
		category = categ;
		this.tags = Arrays.asList(tags);
	}
	
	/*
	 * Given an array of attributes from the Quiz table,
	 * constructs a quiz object.
	 */
	public Quiz(String[] attrs, Connection conn){
		super(attrs, conn);
		name = attrs[1];
		inOrder = Boolean.parseBoolean(attrs[2]);
		type = Integer.parseInt(attrs[3]);
		setAuthor(attrs[4]);
		description = attrs[5];
		questions = findQuestions(conn);
		category = attrs[6];
		getTags(conn);
	}
	
	private void getTags(Connection conn) {
		tags = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query = "SELECT tag from Tag where quiz_id=" + dbID + ";";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				tags.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private List<Question> findQuestions(Connection conn){
		List<Question> questions = new ArrayList<Question>();
		try {
			Statement stmt = conn.createStatement();      
			String query = "SELECT * from Question WHERE quiz_id=" + dbID + ";";     
			
			ResultSet rs = stmt.executeQuery(query);     
			while (rs.next()) {
				String[] attrs = new String[QuizConstants.QUESTION_N_COLS];
				for (int i = 0; i < attrs.length; i++) {
					attrs[i] = rs.getString(i + 1);
				}

				questions.add(Question.build(attrs, conn));
			}
			
			return questions;
		} catch (SQLException e) {     
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void saveToDataBase(Connection conn){
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (dbID == -1) {
				generateID(conn, "Quiz");
				saveTags(conn);
				query = "Insert into Quiz VALUES (" + dbID + ", '" + name + "', " + 
					inOrder + ", " + type + ", '" + author + "', '" + description + "', '" + category + "');";
				
			} else {
				query = "UPDATE Quiz set name='" + name + "', inOrder=" + 
					inOrder + ", type=" + type + ", author ='" + author + "', description='" + 
					description + "', category='" + category + "' WHERE id=" + dbID + ";";
			}
			
			stmt.executeUpdate(query);
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//save all associated questions
		for(Question q : getQuestions()){
			q.saveToDataBase(conn);
		}
	}
	
	private void saveTags(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT MAX(id) from Tag;";     
			ResultSet rs = stmt.executeQuery(query);   
			rs.next();
			int id = rs.getInt(1) + 1; 
			
			query = "INSERT into Tag VALUES (";
			for (int i = 0; i < tags.size(); i++) {
				String sql = query + id + ", " + dbID + ", '" + tags.get(i) + "');"; 
				stmt.executeUpdate(sql);
				id++;
			}
				
		} catch (SQLException e) {     
			e.printStackTrace();
		}
		
		
	}
	
	
	
	/* Begin setters/getters */
	
	/**
	 * Dumb way to do it but it doesn't matter and I just want this to always work
	 * @param n
	 * @return
	 */
	public Question getQuestionByNumber(int n){
		for(Question q : questions){
			if(q.getQuestionNumber() == n) return q;
		}
		return null;
	}
	
	/**
	 * If we don't want the ordered list, clone the list of questions, then shuffle and return the clone.
	 * @return
	 */
	public List<Question> getQuestions(){
		if(inOrder) return questions;
		List<Question> shuffled = new ArrayList<Question>(questions);
		Collections.shuffle(shuffled);
		return shuffled;
	}
	
	/**
	 * Adds the new question to the end of the list, then sorts the questions
	 * in ascending order of question number. Will never be called enough to be a bottleneck.
	 * @param q
	 */
	public void addQuestion(Question q){
		questions.add(q);
		Collections.sort(questions, new Comparator<Question>(){

			@Override
			public int compare(Question arg0, Question arg1) {
				return arg0.getQuestionNumber() - arg1.getQuestionNumber();
			}
			
		});
	}
	
	public int countQuestions() {
		return questions.size();
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
	
	public String getCategory() {
		return category;
	}
	
	public List<String> getTags() {
		return tags;
	}
	
}
