<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*, question.*, java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
InProgressQuiz progress = (InProgressQuiz) session.getAttribute("in_progress_quiz");
progress.gradeAll();
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
	else HTML += " was incorrect. Correct answer: \"" + q.getAnAnswer() + "\".";
	HTML += "</li>";
	out.println(HTML);
}
%>
</ol>
</p>
</body>
</html>