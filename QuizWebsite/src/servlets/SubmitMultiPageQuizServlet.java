package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.MyDB;

import quiz.InProgressQuiz;
import quiz.Quiz;
import user.*;
import java.sql.*;

/**
 * Servlet implementation class SubmitMultiPageQuizServlet
 */
@WebServlet("/SubmitMultiPageQuizServlet")
public class SubmitMultiPageQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitMultiPageQuizServlet() {
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
		HttpSession session = request.getSession();
		InProgressQuiz progress = (InProgressQuiz) session.getAttribute("in_progress_quiz");
		String userAnswer = request.getParameter("answer" + progress.getActiveQuestion().getQuestionNumber());
		progress.submitAnswer(userAnswer);
		
		boolean giveFeedback = progress.getQuiz().getType() == Quiz.PAGE_TYPE.MULTI_IMMEDIATE.value;
		if(giveFeedback){
			String feedback = progress.getFeedback();
			session.setAttribute("feedback", feedback);
		}
		
		RequestDispatcher dispatch;
		if(progress.hasNextQuestion()){
			progress.moveToNextQuestion();
			dispatch = request.getRequestDispatcher("paged_quiz.jsp");
		} else {
			progress.gradeAll();
			checkAchievements((User) session.getAttribute("user"), progress);
			dispatch = request.getRequestDispatcher("quiz_finished.jsp");
		}
			
		dispatch.forward(request, response);
	}
	
	private void checkAchievements(User user, InProgressQuiz progress) {
		Connection conn = MyDB.getConnection();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query = "SELECT count(*) from Quiz_Result WHERE username='" + user.getName() + "' GROUP BY username;";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				int count = rs.getInt(1);
				if (count == 9) {
					HomePageQueries.createAchieve(user, "Quiz Machine");
				}
			} 
			
			query = "SELECT max(score) from Quiz_Result WHERE quiz_id=" + progress.getQuiz().getID() + ";";
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				int max = rs.getInt(1);
				if (progress.getScore() > max) {
					HomePageQueries.createAchieve(user, "I am the Greatest");
				}
			} else if (progress.getScore() != 0) {
					HomePageQueries.createAchieve(user, "I am the Greatest");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
