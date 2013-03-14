package quiz;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import database.*;
import user.*;

public class Review extends DataBaseObject {
	
	private int quizID;
	private String reviewer;
	private int rating;
	private String review;

	public Review(int quizID, String reviewer, int rating, String review) {
		this.quizID = quizID;
		this.reviewer = reviewer;
		this.rating = rating;
		this.review = review;
	}
	
	public Review(String[] attrs, Connection conn) {
		quizID = Integer.parseInt(attrs[1]);
		reviewer = attrs[2];
		rating = Integer.parseInt(attrs[3]);
		review = attrs[4];
	}
	
	
	@Override
	public void saveToDataBase(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (dbID == -1) {
				generateID(conn, "Review");
				query = "Insert into Review VALUES (" + dbID + ", " + quizID + ", '" + 
					reviewer + "', " + rating + ", '" + review + "');";
				stmt.executeUpdate(query);
			}
			
			stmt.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public String renderReview() {
		
		String review = "<a href=\"UserProfileServlet?username=" + reviewer + "\">" + reviewer + "</a> ";
		if (review != null && !review.equals("")) {
			review +=  " says: " + review + " and"; 
		} 
		review += " gave this quiz a score of " + rating;
		
		return review;
	}

}
