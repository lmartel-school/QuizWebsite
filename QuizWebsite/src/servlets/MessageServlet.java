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
				String join1 = "SELECT * FROM Message inner join Friend_Request on Message.id=Friend_Request.id WHERE id=" + id + ";";
				String join2 = "SELECT * FROM Message inner join Challenge WHERE id=" + id + ";";
				String join3 = "SELECT * FROM Message inner join Note where id=" + id + ";";
				
				ResultSet result = next.executeQuery(join1);
				ResultSet resultChal = next.executeQuery(join2);
				ResultSet resultNote = next.executeQuery(join3);
				
				//this is a bit of a cheat: max size if it's a challenge
				String[] attrs = new String[5];
				if (result.next()) {
					generalMessage(attrs, result);
					String acceptance = result.getString("isAccepted");
					attrs[4] = acceptance;
					if (acceptance.equals("false"))
						messages.add(new FriendRequest(attrs, conn));
				} else if (resultChal.next()) {
					generalMessage(attrs, resultChal);
					String challenge = result.getString("message_id");
					attrs[4] = challenge;
					messages.add(new Challenge(attrs, conn));
				} else {
					generalMessage(attrs, resultNote);
					String note = result.getString("msg");
					attrs[4] = note;
					messages.add(new Note(attrs, conn));
						
				}
				
			}
//			Collections.sort(messages, new Comparator<Message>() {
//				public int compare(Message one, Message two) {
//					return two.getID()-one.getID();
//				}
//			});
			request.setAttribute("messages", messages);
			RequestDispatcher dispatch = request.getRequestDispatcher("messages.jsp");
			dispatch.forward(request, response);


		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void generalMessage(String[] attrs, ResultSet rs) {
		try {
			attrs[0] = rs.getString("id");
			attrs[1] = rs.getString("sender");
			attrs[2] = rs.getString("recipient");
			attrs[3] = rs.getString("beenRead");
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
