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

import quiz.*;
import java.util.*;

/**
 * Servlet implementation class MessageServlet
 */
@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		Connection conn = (Connection) getServletContext().getAttribute("database");

		try {
			Statement stmt = conn.createStatement();

			User user = (User) request.getSession().getAttribute("user");
			String query = "SELECT * FROM Message WHERE recipient='" + user.getName() + "';";
			ResultSet rs = stmt.executeQuery(query);
			
			List<Message> messages = new ArrayList<Message>();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				
				Statement next = conn.createStatement();
				String join = "SELECT * FROM Message natural join Challenge natural join Friend_Request natural join Note WHERE id=" + id + ";";
				ResultSet result = next.executeQuery(join);
				
				//this is a bit of a cheat: max size if it's a challenge
				
				while (result.next()) {
					String[] attrs = new String[5];
					attrs[0] = result.getString("id");
					attrs[1] = result.getString("sender");
					attrs[2] = result.getString("recipient");
					attrs[3] = result.getString("beenRead");
					
					String note = result.getString("msg");
					String challenge = result.getString("message_id");
					if (note != null) {
						attrs[4] = note;
						messages.add(new Note(attrs, conn));
						
					} else if (challenge != null) {
						attrs[4] = challenge;
						messages.add(new Challenge(attrs, conn));
						
					} else {
						String acceptance = result.getString("isAccepted");
						attrs[4] = acceptance;
						if (acceptance.equals("false"))
							messages.add(new FriendRequest(attrs, conn));
					}
				}
				
			}
			Collections.sort(messages, new Comparator<Message>() {
				public int compare(Message one, Message two) {
					return two.getID()-one.getID();
				}
			});
			request.setAttribute("messages", messages);
			RequestDispatcher dispatch = request.getRequestDispatcher("messages.jsp");
			dispatch.forward(request, response);


		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
