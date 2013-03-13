package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quiz.Quiz;
import quiz.QuizConstants;
import database.DataBaseObject;
import database.MyDB;

/**
 * Servlet implementation class SearchTagServlet
 */
@WebServlet("/SearchTagServlet")
public class SearchTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchTagServlet() {
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
		HomePageQueries.getAllUsers(request);
		HomePageQueries.getAllQuizzes(request);
		String tag = request.getParameter("text");
		List<Quiz> matches = new ArrayList<Quiz>();
		Connection conn = MyDB.getConnection();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query = "SELECT * from Quiz inner join Tag on Tag.quiz_id = Quiz.id WHERE Tag.tag='" + tag + "';";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String[] row = DataBaseObject.getRow(rs, QuizConstants.QUIZ_TAG_N_COL);
				matches.add(new Quiz(row, conn));
			}
			request.setAttribute("tag", tag);
			request.setAttribute("tagged", matches);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher dispatch = request.getRequestDispatcher("search.jsp");
		dispatch.forward(request, response);
	}

}
