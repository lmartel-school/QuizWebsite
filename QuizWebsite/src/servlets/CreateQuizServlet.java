package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import user.*;
import quiz.*;

/**
 * Servlet implementation class CreateQuizServlet
 */
@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuizServlet() {
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
		User author = (User) request.getSession().getAttribute("user");
		int type = Integer.parseInt(request.getParameter("type"));
		boolean inOrder = Boolean.parseBoolean(request.getParameter("inOrder"));
		String description = request.getParameter("description");
		String name = request.getParameter("name");
		Quiz quiz = new Quiz(name, inOrder, type, author.getName(), description);
		request.getSession().setAttribute("quiz", quiz);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("create_question.jsp");
		dispatch.forward(request, response);
	}

}
