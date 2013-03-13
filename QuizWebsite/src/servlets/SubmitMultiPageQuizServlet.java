package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import quiz.InProgressQuiz;
import quiz.Quiz;

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
			dispatch = request.getRequestDispatcher("quiz_finished.jsp");
		}
			
		dispatch.forward(request, response);
	}

}
