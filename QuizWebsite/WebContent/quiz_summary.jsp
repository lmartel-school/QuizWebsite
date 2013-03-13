<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Summary</title>
</head>
<body>

	
	<% 
		
	QuizSummary summary = (QuizSummary) request.getAttribute("summary");
	Quiz quiz = summary.getQuiz();
	
	out.println("<a href=\"BeginQuizServlet\"?id=" + quiz.getID() + "\">Take this Quiz!</a>");
	
	out.println("<h1>" + quiz.getName() + "</h1>");
	out.println("<h2>By: <a href=\"UserProfileServlet?username=" + quiz.getAuthor() + "\">" + quiz.getAuthor() + "</a></h2>");
	
	out.println("<p>" + quiz.getDescription() + "<p>");
	
	out.println("<h3> All time High Scores: </h3>");
	out.println("<ul>");
	for (String name : summary.getTopAll()) {
		out.println("<li>" + name + "</li>");
	}
	out.println("</ul>");
	
	
	
	//out.println(quiz.getDescription());
	
	
	%>

</body>
</html>