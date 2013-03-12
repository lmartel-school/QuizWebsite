package user;

import java.sql.*;

import database.*;


/**
 * Amateur Author—The user created a quiz.
	Prolific Author—The user created five quizzes.
	Prodigious Author—The user created ten quizzes.
	Quiz Machine—The user took ten quizzes.
	I am the Greatest—The user had the highest score on a quiz. Note, once earned this achievement does not go away if someone else later bests the high score.
	Practice Makes Perfect—The user took a quiz in practice mode.
 * @author jocelynneff
 *
 */

public class Achievement extends DataBaseObject {
	
	String award;
	String username;

	public Achievement(String[] attrs, Connection conn) {
		dbID = Integer.parseInt(attrs[0]);
		username = attrs[1];
		award = attrs[2];
	}
	
	public Achievement(String username, String award) {
		this.username = username;
		this.award = award;
	}
	
	public String getAward() {
		return award;
	}
	
	@Override
	public void saveToDataBase(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			if (dbID == -1) {
				generateID(conn, "Achievement");
				String query = "Insert into Achievement VALUES (" + dbID + ", '" + username + "', '" + award + "';";
				stmt.executeUpdate(query);
			} else {
				/* no way to update */
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
