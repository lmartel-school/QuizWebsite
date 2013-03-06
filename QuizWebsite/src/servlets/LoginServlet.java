package servlets;

import java.io.IOException;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import quiz.*;
import java.util.*;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("database");
	
		String usr = request.getParameter("username");
		String pass = request.getParameter("password");
		String hash = PasswordHash.generationMode(pass);
		
		RequestDispatcher dispatch;
		
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM User WHERE username='" + usr + "' AND password_hash='" + hash + "';";
			ResultSet rs = stmt.executeQuery(query);
			
			
			if (!rs.last()) {
				dispatch = request.getRequestDispatcher("invalid_login.html");
			} else {
				String[] attrs = DataBaseObject.getRow(rs, QuizConstants.USER_N_COLUMNS);
				User user = new User(attrs, conn);
				request.getSession().setAttribute("user", user);
				getUserRecentQuizzes(request, conn, user);
				getMessages(request, conn, user);
				HomePageQueries.getPopQuizzes(request, conn);
				HomePageQueries.getRecentQuizzes(request, conn);
				getAuthoring(request, conn, user);
				getFriendActivity(request, conn, user);
				
				dispatch = request.getRequestDispatcher("user_home.jsp");
			}
			dispatch.forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
			String query = "SELECT * FROM Quiz WHERE quthor='" + user.getName() + 
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
					
					
					
					String[] attrs = DataBaseObject.getRow(rs, QuizConstants.QUIZ_N_COLS);
					Quiz quiz = new Quiz(attrs, conn);
					
					count++;
				}
			}
			request.setAttribute("activities", activities);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private User getFriend(Connection conn, String username) {
		return null;
	}

}
