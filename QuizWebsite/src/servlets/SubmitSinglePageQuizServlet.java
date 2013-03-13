package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import question.Question;
import quiz.InProgressQuiz;

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
			Question q = progress.getActiveQuestion();
			int n = q.getQuestionNumber();
			String userAnswer = request.getParameter("answer" + n);
			progress.submitAnswer(userAnswer);
			if(!progress.hasNextQuestion()) break;
			progress.moveToNextQuestion();
		}
		progress.gradeAll();
		
		RequestDispatcher dispatch = request.getRequestDispatcher("quiz_finished.jsp");	
		dispatch.forward(request, response);
	}

}
