package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quiz.PasswordHash;
import quiz.User;

/**
 * Servlet implementation class NewAccountServlet
 */
@WebServlet("/NewAccountServlet")
public class NewAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewAccountServlet() {
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
			String query = "SELECT * FROM User WHERE username='" + usr + "';";
			ResultSet rs = stmt.executeQuery(query);
			
			
			if (rs.last()) {
				dispatch = request.getRequestDispatcher("account_exists.jsp");
			} else {
				
				String[] attrs = new String[User.NUM_COLUMNS];
				attrs[1] = usr;
				attrs[2] = hash;
				attrs[3] = "false"; //isAdmin
				
				User user = new User(attrs, conn); //this isn't correct: what am i supposed to call?
				user.saveToDataBase(conn);
				
				request.getSession().setAttribute("user", user);
				request.setAttribute("unreadMsg", 0);
				
				dispatch = request.getRequestDispatcher("user_home.jsp");
			}
			dispatch.forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
