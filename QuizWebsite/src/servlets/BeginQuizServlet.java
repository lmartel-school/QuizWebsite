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
import user.*;
import database.DataBaseObject;

/**
 * Servlet implementation class BeginQuizServlet
 */
@WebServlet("/BeginQuizServlet")
public class BeginQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BeginQuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("database");
		int quizID =  Integer.parseInt(request.getParameter("id"));
		
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM Quiz WHERE id='" + quizID +"';";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				String[] attrs = DataBaseObject.getRow(rs, QuizConstants.QUIZ_N_COLS);
				Quiz quiz = new Quiz(attrs, conn);
				InProgressQuiz progress = new InProgressQuiz(quiz, conn);
				request.getSession().setAttribute("in_progress_quiz", progress);
				RequestDispatcher dispatch;
				
				if (quiz.getType() == Quiz.PAGE_TYPE.SINGLE_PAGE.value) {
					dispatch = request.getRequestDispatcher("full_quiz.jsp");
				} else {
					dispatch = request.getRequestDispatcher("paged_quiz.jsp");
				}
				
				dispatch.forward(request, response);
				
			}
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
