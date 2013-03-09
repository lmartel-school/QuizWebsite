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
import user.Challenge;
import user.FriendRequest;
import user.Note;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = (Connection) getServletContext().getAttribute("database");
		int messageid =  Integer.parseInt(request.getParameter("messageid"));
		
		try {
			
			Statement next = conn.createStatement();
			String join1 = "SELECT * FROM Message inner join Friend_Request on Message.id=Friend_Request.id WHERE Message.id='" + messageid + "';";
			Statement chal = conn.createStatement();
			String join2 = "SELECT * FROM Message inner join Challenge WHERE Message.id='" + messageid + "';";
			Statement noteStmt = conn.createStatement();
			String join3 = "SELECT * FROM Message inner join Note where Message.id='" + messageid + "';";
			
			ResultSet result = next.executeQuery(join1);
			ResultSet resultChal = chal.executeQuery(join2);
			ResultSet resultNote = noteStmt.executeQuery(join3);
			
			//this is a bit of a cheat: max size if it's a challenge
			String[] attrs = new String[5];
			if (result.next()) {
				MessageQueries.generalMessage(attrs, result);
				String acceptance = result.getString("isAccepted");
				attrs[4] = acceptance;
				FriendRequest friend = new FriendRequest(attrs, conn);
				friend.setBeenRead(conn);
				request.setAttribute("friend", friend);
			} else if (resultChal.next()) {
				MessageQueries.generalMessage(attrs, resultChal);
				String challenge = resultChal.getString("result_id");
				attrs[4] = challenge;
				Challenge chall = new Challenge(attrs, conn);
				chall.setBeenRead(conn);
				request.setAttribute("challenge", chall);
			} else if (resultNote.next()){
				MessageQueries.generalMessage(attrs, resultNote);
				String note = resultNote.getString("msg");
				attrs[4] = note;
				Note msg = new Note(attrs, conn);
				msg.setBeenRead(conn);
				request.setAttribute("note", msg);
					
			}
			RequestDispatcher dispatch = request.getRequestDispatcher("message.jsp");
			dispatch.forward(request, response);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
