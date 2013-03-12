package quiz;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import database.DataBaseObject;

public final class QuizResult extends DataBaseObject {
	
	private String username;
	private int score;
	private String timeTaken;
	private String elapsedTime;
	private Quiz quiz;
	
	private long startTime;
	
	public QuizResult(String username, int score, String timeTaken, String elapsedTime, Quiz quiz){
		this.username = username;
		this.score = score;
		this.timeTaken = timeTaken;
		this.elapsedTime = elapsedTime;
		this.quiz = quiz;
	}

	public QuizResult(String[] attrs, Connection conn){
		super(attrs, conn);
		username = attrs[1];
		score = Integer.parseInt(attrs[2]);
		timeTaken = attrs[3];
		elapsedTime = attrs[4];
		quiz = getQuiz(Integer.parseInt(attrs[5]), conn);
	}
	
	
	@Override
	public void saveToDataBase(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			generateID(conn, "Quiz_Result");
			String query = "Insert into Quiz_Result values (" + dbID + ", '" + username + "', " + score + ", '" + timeTaken + "', '" + elapsedTime + "', " + quiz.getID() + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getUsername() {
		return username;
	}

	public int getScore() {
		return score;
	}

	public String getTimeTaken() {
		return timeTaken;
	}

	public String getElapsedTime() {
		return elapsedTime;
	}
	
	public String getTimeTakenFormatted() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
		return formatter.format(date);
	}

	public Quiz getQuiz() {
		return quiz;
	}
	
	private Quiz getQuiz(int id, Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM Quiz WHERE id=" + id + ";";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				String[] attrs = DataBaseObject.getRow(rs, QuizConstants.QUIZ_N_COLS);
				return new Quiz(attrs, conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setTimeTaken(long startTime) {
		this.startTime = startTime;
		Date date = new Date(startTime);
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
		timeTaken = formatter.format(date);
	}
	
	public void setElapsed(long endTime) {
		elapsedTime = "";
		long result = endTime - startTime;
		elapsedTime += result;
	}
	

}
