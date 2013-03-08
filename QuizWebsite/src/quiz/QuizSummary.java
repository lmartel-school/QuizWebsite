package quiz;

import java.sql.*;
import java.util.*;

import user.User;

public class QuizSummary {
	
	private Quiz quiz;
	private List<QuizResult> usersResults; //leave as ability to be null?
	private List<String> topPerformers;
	private List<String> recentTopPerformers;
	private List<QuizResult> recentResults;
	private double mean;
	private double mode;
	private double median;
	
	public QuizSummary(Quiz quiz, User usr, Connection conn) {
		this.quiz = quiz;
		
		getRecentResults(quiz, conn);
		
	}
	
	private void getRecentResults(Quiz quiz, Connection conn) {
		recentResults = new ArrayList<QuizResult>();
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query = "SELECT * from Quiz_Result where quiz_id=" + quiz.getID() + " order by quiz_id DESC";
			ResultSet rs = stmt.executeQuery(query);
			if (rs != null) {
				int count = 0;
				while (rs.next() && count < QuizConstants.N_TOP_SCORERS) {
					recentResults.add(new QuizResult(rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), quiz));
					count++;
				}
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
