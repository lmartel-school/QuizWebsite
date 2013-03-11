package servlets;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import database.DataBaseObject;
import database.MyDB;
import quiz.*;
import user.*;

public class HomePageQueries {
	
	public HomePageQueries() {
		
	}
	
	public static void getPopQuizzes(HttpServletRequest request, Connection conn) {
		List<Quiz> popular = new ArrayList<Quiz>();
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT quiz_id, count(quiz_id) " +
					"FROM Quiz_Result " +
					"GROUP by quiz_id " + 
					"ORDER by count(quiz_id) DESC;";
			ResultSet rs = stmt.executeQuery(query);
			if (rs != null) {
				int count = 0;
				while (rs.next() && count < QuizConstants.N_TOP_RATED) {
					int quizId = rs.getInt(1);
					Quiz quiz = getQuizFromId(quizId, conn);
					popular.add(quiz);
					count++;
				}
			}	
			request.setAttribute("popular", popular);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private static Quiz getQuizFromId(int id, Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM Quiz WHERE id=" + id + ";";
			ResultSet rs = stmt.executeQuery(query);
			if (rs != null) {
				if (rs.next()) {
					String[] attrs = DataBaseObject.getRow(rs, QuizConstants.QUIZ_N_COLS);
					return new Quiz(attrs, conn);
				}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	public static void getRecentQuizzes(HttpServletRequest request, Connection conn) {
		List<Quiz> recents = new ArrayList<Quiz>();
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM Quiz;";
			ResultSet rs = stmt.executeQuery(query);
			if (rs != null) {
				int count = 0;
				rs.afterLast();
				while (rs.previous() && count < QuizConstants.N_TOP_RATED) {
					String[] attrs = DataBaseObject.getRow(rs, QuizConstants.QUIZ_N_COLS);
					Quiz quiz = new Quiz(attrs, conn);
					recents.add(quiz);
					count++;
				}
			}	
			request.setAttribute("recents", recents);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<String> getFriends(String username, Connection conn) {
		List<String> friends = new ArrayList<String>();
		conn = MyDB.getConnection();
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT sender FROM Message inner join Friend_Request on Message.id=Friend_Request.id WHERE recipient='"
				+ username + "' AND isAccepted=true;";
			ResultSet rs = stmt.executeQuery(query);
			if (rs != null) {
				while (rs.next()) {
					friends.add(rs.getString(1));
				}
			}	
			
			query = "SELECT recipient FROM Message inner join Friend_Request on Message.id=Friend_Request.id WHERE sender='"
				+ username + "' AND isAccepted=true;";
			rs = stmt.executeQuery(query);
			if (rs != null) {
				while (rs.next()) {
					friends.add(rs.getString(1));
				}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return friends;
	}
	
	public static void getUserRecentQuizzes(HttpServletRequest request, Connection conn, User user) {
		List<QuizResult> recents = new ArrayList<QuizResult>();
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM Quiz_Result WHERE username='" + user.getName() + 
			"' ORDER by id desc;";
			ResultSet rs = stmt.executeQuery(query);
			if (rs != null) {
				int count = 0;
				while (rs.next() && count < QuizConstants.N_TOP_RATED) {
					String[] attrs = DataBaseObject.getRow(rs, QuizConstants.QUIZ_N_COLS);
					QuizResult quiz = new QuizResult(attrs, conn);
					recents.add(quiz);
					count++;
				}
			}
			request.setAttribute("userRecent", recents);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void getAuthoring(HttpServletRequest request, Connection conn, User user) {
		List<Quiz> authored = new ArrayList<Quiz>();
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM Quiz WHERE author='" + user.getName() + 
			"';";
			ResultSet rs = stmt.executeQuery(query);
			if (rs != null) {
				int count = 0;
				while (rs.next() && count < QuizConstants.N_TOP_RATED) {
					String[] attrs = DataBaseObject.getRow(rs, QuizConstants.QUIZ_N_COLS);
					Quiz quiz = new Quiz(attrs, conn);
					authored.add(quiz);
					count++;
				}
			}
			request.setAttribute("authored", authored);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void getAllUsers(HttpServletRequest request) {
		Connection conn = MyDB.getConnection();	
		List<String> users = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query = "SELECT username from User;";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				users.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("all_users", users);
	}
	
	public static void getAnnouncements(HttpServletRequest request) {
		Connection conn = MyDB.getConnection();	
		List<Announcement> announce = new ArrayList<Announcement>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query = "SELECT * from Announcement;";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String[] attrs = DataBaseObject.getRow(rs, QuizConstants.ANNOUNCE_N_COL);
				announce.add(new Announcement(attrs, conn));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("announcements", announce);
	}
}

