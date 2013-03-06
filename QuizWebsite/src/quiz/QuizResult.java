package quiz;

import java.sql.Connection;

public final class QuizResult extends DataBaseObject {
	
	private String username;
	private int score;
	private String timeTaken;
	private String elapsedTime;
	private int quizID;
	
	public QuizResult(String username, int score, String timeTaken, String elapsedTime, int quizID){
		this.username = username;
		this.score = score;
		this.timeTaken = timeTaken;
		this.elapsedTime = elapsedTime;
		this.quizID = quizID;
	}

	public QuizResult(String[] attrs, Connection conn){
		super(attrs, conn);
		username = attrs[1];
		score = Integer.parseInt(attrs[2]);
		timeTaken = attrs[3];
		elapsedTime = attrs[4];
		quizID = Integer.parseInt(attrs[5]);
	}
	
	@Override
	public void saveToDataBase(Connection conn) {
		// TODO Auto-generated method stub
		
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

	public int getQuizID() {
		return quizID;
	}
	
	

}
