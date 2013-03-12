package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.MyDB;

import question.*;
import quiz.*;
import java.util.*;

/**
 * Servlet implementation class CreateQuestionServlet
 */
@WebServlet("/CreateQuestionServlet")
public class CreateQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuestionServlet() {
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
		int type = Integer.parseInt(request.getParameter("type"));
		Quiz quiz = (Quiz) request.getSession().getAttribute("quiz"); //make sure this alters the one in the session
		int number = quiz.countQuestions() + 1;
		if (type == Question.QUESTION_TYPE.MULTI_CHOICE.value) {
			String prompt = request.getParameter("prompt");
			String answer = request.getParameter("correct");
			String[] corrects = answer.split("\n");
			String wrong = request.getParameter("wrong");
			String[] wrongs = wrong.split("\n");
			quiz.addQuestion(new MultiChoiceQuestion(quiz, number, prompt, Arrays.asList(corrects), Arrays.asList(wrongs)));

		} else if (type == Question.QUESTION_TYPE.FILL_IN.value) {
			String prompt = request.getParameter("prompt");
			String answer = request.getParameter("answer");
			quiz.addQuestion(new FillInQuestion(quiz, number, prompt, answer));
			
		} else if (type == Question.QUESTION_TYPE.PICTURE.value) {
			String url = request.getParameter("picture_url");
			String answer = request.getParameter("answer");
			String prompt = request.getParameter("prompt"); //being null may break it
			PictureQuestion pQ = new PictureQuestion(quiz, number, url, prompt, answer);
			quiz.addQuestion(pQ);
		}
		
		RequestDispatcher dispatch;
		String finish = request.getParameter("finished");
		if (finish.equals("true")) {
			quiz.saveToDataBase(MyDB.getConnection());
			dispatch = request.getRequestDispatcher("QuizServlet?id=" + quiz.getID());
			request.getSession().setAttribute("quiz", null);
		} else {
			dispatch = request.getRequestDispatcher("create_question.jsp");
		}
		dispatch.forward(request, response);

	}

}
