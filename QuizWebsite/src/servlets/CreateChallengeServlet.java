package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.MyDB;

import quiz.QuizResult;

import user.Challenge;
import user.User;

/**
 * Servlet implementation class CreateChallengeServlet
 */
@WebServlet("/CreateChallengeServlet")
public class CreateChallengeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateChallengeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String sender = request.getParameter("sender");
		String recip = request.getParameter("recipient");
		QuizResult res = (QuizResult)request.getSession().getAttribute("result");
		
		Challenge ch = new Challenge(sender, recip, res);
		ch.saveToDataBase(MyDB.getConnection());
		

		RequestDispatcher disp = request.getRequestDispatcher("index.jsp");
		disp.forward(request, response);
		
		
	}

}
