<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="quiz.*, question.*, java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
InProgressQuiz progress = (InProgressQuiz) session.getAttribute("in_progress_quiz");
Question question = progress.getActiveQuestion();
Quiz quiz = progress.getQuiz();
String feedback = (String) session.getAttribute("feedback");
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= quiz.getName() %></title>
</head>
<body>

<%@ include file="_header.jsp" %>
	
<% if(feedback != null){ out.println("<h3>" + feedback + "</h3>"); }%>
<form action="SubmitMultiPageQuizServlet" method="POST">
<%= question.renderQuizMode() %>
<input type="submit" value="submit">
</form>
</body>
</html>