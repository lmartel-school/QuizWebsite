package servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.MyDB;
import user.*;
import quiz.*;

/**
 * Servlet implementation class CreateReviewServlet
 */
@WebServlet("/CreateReviewServlet")
public class CreateReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateReviewServlet() {
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
		Connection conn = MyDB.getConnection();

		String text = request.getParameter("text");
		int radioVal = Integer.parseInt(request.getParameter("rating"));
		int quizID = Integer.parseInt(request.getParameter("quizID"));
		
		Review review = new Review(quizID, ((User) request.getSession().getAttribute("user")).getName(), radioVal, text);
		review.saveToDataBase(conn);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("CurrentUserProfileServlet");
		dispatch.forward(request, response);
		
	}

}
