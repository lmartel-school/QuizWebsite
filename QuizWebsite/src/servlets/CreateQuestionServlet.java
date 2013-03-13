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
import user.*;
import java.sql.*;

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
		// do nothing
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
			String[] corrects = answer.split(QuizConstants.TEXTAREA_NEWLINE_REGEX);
			String wrong = request.getParameter("wrong");
			String[] wrongs = wrong.split(QuizConstants.TEXTAREA_NEWLINE_REGEX);
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
			checkAchievement(request);
			quiz.saveToDataBase(MyDB.getConnection());
			dispatch = request.getRequestDispatcher("QuizServlet?id=" + quiz.getID());
			request.getSession().setAttribute("quiz", null);
		} else {
			dispatch = request.getRequestDispatcher("create_question.jsp");
		}
		dispatch.forward(request, response);

	}
	
	private void checkAchievement(HttpServletRequest request) {
		User cur = (User) request.getSession().getAttribute("user");
		Statement stmt;
		try {
			stmt = MyDB.getConnection().createStatement();
			String query = "SELECT count(*) FROM Quiz WHERE author='" + cur.getName() + "';";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				int count = rs.getInt(1);
				switch (count) {
					case 0: createAchieve(cur, "Amateur Author"); break;
					case 4: createAchieve(cur, "Prolific Author"); break;
					case 9: createAchieve(cur, "Prodigious Author"); break;
					default: break;
				}
			} else {
				createAchieve(cur, "Amateur Author");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void createAchieve(User user, String title) {
		Achievement achieve = new Achievement(user.getName(), title);
		achieve.saveToDataBase(MyDB.getConnection());
	}

}
