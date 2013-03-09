package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.MyDB;

import user.Note;

/**
 * Servlet implementation class CreateMessageServlet
 */
@WebServlet("/CreateMessageServlet")
public class CreateMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateMessageServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		String sender = request.getParameter("sender");
		String recip = request.getParameter("recipient");
		String text = request.getParameter("text");
		Note newNote = new Note(sender, recip, text);
		
		newNote.saveToDataBase(MyDB.getConnection());
		
		RequestDispatcher disp = request.getRequestDispatcher("MessageInboxServlet");
		disp.forward(request, response);
	}

}
