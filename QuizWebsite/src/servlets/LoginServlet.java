package servlets;

import java.io.IOException;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import quiz.*;

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
			String query = "SELECT * FROM User WHERE username='" + usr + "' AND password='" + hash + "';";
			ResultSet rs = stmt.executeQuery(query);
			if (rs == null) {
				dispatch = request.getRequestDispatcher("invalid_login.html");
			} else {
				
				String[] attrs = new String[User.NUM_COLUMNS];
				for (int i = 0; i < attrs.length; i++) {
					attrs[i] = rs.getNString(i);
				}
				User user = new User(attrs, conn);
				
				request.getSession().setAttribute("user", user);
				
				getMessages(request, conn, user);
				
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

}
