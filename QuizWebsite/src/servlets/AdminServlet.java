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

import quiz.*;
import java.util.*;

import database.DataBaseObject;
import database.MyDB;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HomePageQueries.getAllUsers(request);
		
		Connection conn = MyDB.getConnection();	
		List<Quiz> quizzes = new ArrayList<Quiz>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query = "SELECT * from Quiz;";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String [] attrs = DataBaseObject.getRow(rs, QuizConstants.QUIZ_N_COLS);
				quizzes.add(new Quiz(attrs, conn));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("all_quizzes", quizzes);
		
		try {
			stmt = conn.createStatement();
			String query = "SELECT count(*) from Quiz_Result;";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				int numTaken = rs.getInt(1);
				request.setAttribute("num_taken", numTaken);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		HomePageQueries.getAnnouncements(request);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("admin_page.jsp");
		dispatch.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
