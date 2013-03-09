package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quiz.Quiz;
import quiz.QuizConstants;
import quiz.QuizResult;
import user.Activity;
import user.PasswordHash;
import user.User;
import database.DataBaseObject;

/**
 * Servlet implementation class CurrentUserProfileServlet
 */
@WebServlet("/CurrentUserProfileServlet")
public class CurrentUserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CurrentUserProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}
	
	private void getMessages(HttpServletRequest request, Connection conn, User user) {
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM Message WHERE recipient='" + user.getName() + "' AND beenRead=" + false + ";";
			ResultSet rs = stmt.executeQuery(query);
			if (rs == null) {
				request.setAttribute("unreadMsg", 0);
			} else {
				int num = 0;
				if (rs.last()) {
					num = rs.getRow();
				}
				request.setAttribute("unreadMsg", num);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getUserRecentQuizzes(HttpServletRequest request, Connection conn, User user) {
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
	
	private void getAuthoring(HttpServletRequest request, Connection conn, User user) {
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
	
	private void getFriendActivity(HttpServletRequest request, Connection conn, User user) {
		List<Activity> activities = new ArrayList<Activity>();
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM Message inner join Friend_Request on Message.id=Friend_Request.id WHERE sender='" + user.getName() + 
			"' OR recipient='" + user.getName() + "' AND isAccepted=true;";
			ResultSet rs = stmt.executeQuery(query);
			if (rs != null) {
				int count = 0;
				while (rs.next() && count < QuizConstants.N_TOP_RATED) {
					
					String username = rs.getString("sender").equals(user.getName()) ? rs.getString("recipient") : rs.getString("sender");
					User friend = getFriend(conn, username);
					if (friend != null) {
						String[] attrs = DataBaseObject.getRow(rs, QuizConstants.QUIZ_N_COLS);
						Quiz quiz = new Quiz(attrs, conn);
						
						String msg = friend.getName();
						if (quiz.getAuthor().equals(friend.getName())) {
							msg += " recently authored " + quiz.getName();
						} else {
							msg += " recently took" + quiz.getName();
						}
						
						Activity act = new Activity(friend, msg, quiz.getID());
						activities.add(act);
						count++;
					}
				}
			}
			request.setAttribute("activities", activities);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private User getFriend(Connection conn, String username) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query = "SELECT * FROM User WHERE username='" + username + "';";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				return new User(DataBaseObject.getRow(rs, QuizConstants.USER_N_COLUMNS), conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("database");
		
		RequestDispatcher dispatch;
		User user = (User) request.getSession().getAttribute("user");
		getUserRecentQuizzes(request, conn, user);
		getMessages(request, conn, user);
		HomePageQueries.getPopQuizzes(request, conn);
		HomePageQueries.getRecentQuizzes(request, conn);
		getAuthoring(request, conn, user);
		getFriendActivity(request, conn, user);

		dispatch = request.getRequestDispatcher("user_home.jsp");
		dispatch.forward(request, response);
	}

}
