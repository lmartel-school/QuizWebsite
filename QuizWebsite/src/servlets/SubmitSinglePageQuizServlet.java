package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.MyDB;

import question.MultiAnswerMultiChoiceQuestion;
import question.Question;
import quiz.InProgressQuiz;
import quiz.QuizConstants;
import user.User;

/**
 * Servlet implementation class SubmitSinglePageQuizServlet
 */
@WebServlet("/SubmitSinglePageQuizServlet")
public class SubmitSinglePageQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitSinglePageQuizServlet() {
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
		while(true){
			Question active = progress.getActiveQuestion();
			String userAnswer;
			
			//this is a hack that assembles a textbox-like answer from the user's many answers,
			//in order to avoid changing the Question interface at the last minute.
			if(active.getType() == Question.QUESTION_TYPE.MULTI_CHOICE_MULTI_ANSWER){
				MultiAnswerMultiChoiceQuestion q = (MultiAnswerMultiChoiceQuestion) active;
				userAnswer = "";
				for(int i = 0; i < q.getChoices().size(); i++){
					String nextAnswer = request.getParameter("answer" + q.getQuestionNumber() + "_" + i);
					if(nextAnswer != null) userAnswer += nextAnswer + '\n'; //newline character allows parsing answers by line
				}
			} else {
				userAnswer = request.getParameter("answer" + active.getQuestionNumber());
			}
			
			progress.submitAnswer(userAnswer.trim());
			if(!progress.hasNextQuestion()) break;
			progress.moveToNextQuestion();
		}
		progress.gradeAll();
		checkAchievements((User) session.getAttribute("user"), progress);
		RequestDispatcher dispatch = request.getRequestDispatcher("quiz_finished.jsp");	
		dispatch.forward(request, response);
	}
	
	private void checkAchievements(User user, InProgressQuiz progress) {
		Connection conn = MyDB.getConnection();
		Statement stmt;
		List<String> achieve = HomePageQueries.existingAchieve(user.getName());

		try {
			stmt = conn.createStatement();
			String query = "SELECT count(*) from Quiz_Result WHERE username='" + user.getName() + "' GROUP BY username;";
			if (!achieve.contains("Quiz Machine")) {
				ResultSet rs = stmt.executeQuery(query);
				if (rs.next()) {
					int count = rs.getInt(1);
					if (count == 9) {
						HomePageQueries.createAchieve(user, "Quiz Machine");
					}
				} 
			}
			
			if (achieve.contains("I am the Greatest")) return;
			query = "SELECT max(score) from Quiz_Result WHERE quiz_id=" + progress.getQuiz().getID() + ";";
			ResultSet rs = stmt.executeQuery(query);
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
