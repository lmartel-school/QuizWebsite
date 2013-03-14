<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="quiz.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Quiz History</title>
</head>
<body>

	<a href="CurrentUserProfileServlet"> Go to your Profile </a>
	<a href="LogoutServlet">Logout</a>

<%
	List<QuizResult> results = (ArrayList<QuizResult>) request.getAttribute("userRecent");
	for (int i = 0; i < results.size(); i++) {
		QuizResult result = results.get(i);
		Quiz quiz = result.getQuiz();
		out.println("<div id=\"result\" align=\"middle\" >");
		out.println("Quiz Result for " + quiz.getName());
		out.println("Taken at " + result.getTimeTaken());
		out.println("Total time " + result.getElapsedTime());
		out.println("Score: " + result.getScore());
		out.println("<a href=\"QuizServlet?id=" + quiz.getID() + "\">Retake the Quiz!</a>");
		out.println("</div>");
		out.println("<br /><hr /><br />");
	}
%>

</body>
</html>