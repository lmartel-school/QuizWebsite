<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="database.*, quiz.*, question.*, java.util.List, user.*, java.util.Date, java.text.SimpleDateFormat;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<a href="CurrentUserProfileServlet"> Go to your Profile </a>
<a href="LogoutServlet">Logout</a> <br><br>


<%
InProgressQuiz progress = (InProgressQuiz) session.getAttribute("in_progress_quiz");
List<Question> questions = progress.getQuestions();
Quiz quiz = progress.getQuiz();
String feedback = (String) session.getAttribute("feedback");
session.setAttribute("feedback", null); //ensures the feedback doesn't leak onto the next quiz
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= quiz.getName() %></title>
</head>
<body>
<% if(feedback != null){ out.println("<h3>" + feedback + "</h3>"); }%>
<h1>Quiz finished!</h1>
<p>Your results:<br>
<ol>
<%
for(Question q : questions){
	String HTML = "<li>\"" + progress.userAnswerForQuestion(q) + "\"";
	if(progress.checkQuestionByNumber(q.getQuestionNumber())) HTML += " was correct!";
	else HTML += " was incorrect. Correct answer: \"" + q.getCompleteAnswer() + "\".";
	HTML += "</li>";
	out.println(HTML);
}
String username = ( (User)session.getAttribute("user")).getName();
int score = progress.getScore();
long elapsed = (System.currentTimeMillis() - progress.getStartTimeMillis())/1000;
String duration = String.format("%d:%02d:%02d", elapsed/3600, (elapsed%3600)/60, (elapsed%60));

QuizResult qRes  = new QuizResult(username, progress.getScore(), 
		progress.getStartTimeFormatted(), duration, quiz);
qRes.saveToDataBase(MyDB.getConnection());
%>
</ol>
Your score was <%= progress.getScore() %> out of <%= progress.getMaxPossibleScore() %>.
</p>


<form action="CreateReviewServlet" method="post">
<input type="hidden" name="quizID" value="<%out.print(progress.getQuiz().getID()); %>">
<input type="radio" name="group1" value="1">
<input type="radio" name="group1" value="2">
<input type="radio" name="group1" value="3">
<input type="radio" name="group1" value="4">
<input type="radio" name="group1" value="5">
<input type="submit" value="Post a review?">
</form>




</body>
</html>