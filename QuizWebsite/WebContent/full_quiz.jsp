<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="quiz.*, question.*, java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
InProgressQuiz progress = (InProgressQuiz) session.getAttribute("in_progress_quiz");
List<Question> questions = progress.getQuestions();
Quiz quiz = progress.getQuiz();
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= quiz.getName() %></title>
</head>
<body>
<h1><%= quiz.getName() %></h1>
<h3>by <%= quiz.getAuthor() %></h3>
<p><%= quiz.getDescription() %></p>
<form action="SubmitSinglePageQuizServlet" method="POST">
<%
for(Question q : questions){
	out.println(q.renderQuizMode());
}
%>
<input type="submit" value="submit">
</form>
</body>
</html>