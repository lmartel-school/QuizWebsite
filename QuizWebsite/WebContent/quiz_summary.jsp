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
		
	QuizSummary summary = (QuizSummary) request.getAttribute("quiz_summary");
	Quiz quiz = summary.getQuiz();
	
	//out.println("<h1>" + quiz.getName() + "</h1>");
	
	//out.println(quiz.getAuthor());
	out.println("<h1> TODO: this page (quiz_summary.jsp)</h1>");
	
	
	//out.println(quiz.getDescription());
	
	
	%>

</body>
</html>