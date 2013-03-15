<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>All Users and Quizzes</title>
</head>
<body>

<%@ include file="_header.jsp" %>
	
<% 
	List<String> users = (List<String>) request.getAttribute("all_users");
	for (String name : users){
		out.println("<a href=\"UserProfileServlet?username=" + name + "\">" + name + "</a> <br>");
	}
	
	%> <br><br>
	
	<%
	
	
	List<Quiz> quizzes = (List<Quiz>) request.getAttribute("all_quizzes");
	String prevCateg = "";
	out.println("<h3>All Quizzes, sorted by category</h3>");
	
	for (int i = 0; i < quizzes.size(); i++) {
		Quiz quiz = quizzes.get(i);
		if (!quiz.getCategory().equals(prevCateg)) {
			prevCateg = quiz.getCategory();
			if (prevCateg == null) {
				out.println("<br>Quizzes without a Category: <br>");
			} else {
				out.println("<br>" + prevCateg + " Quizzes: <br>");
			}
			
		}
		out.println("<a href=\"QuizServlet?id=" + quiz.getID() + "\">" + quiz.getName() + "</a> <br>");
	}
	
	

	
	List<Quiz> matches = (List<Quiz>) request.getAttribute("tagged");
	if (matches == null) {
		
	} else if (matches.size() == 0) {
		%>
		There were no quizzes with that tag.
		<% 
	} else {
		%>
		<h3>Quizzes with the tag <%= request.getAttribute("tag") %></h3>
		<% 
		for (int i = 0; i < matches.size(); i++) {
			Quiz quiz = matches.get(i);
			out.println("<a href=\"QuizServlet?id=" + quiz.getID() + "\">" + quiz.getName() + "</a> <br>");
		}
		
	}
	%>
	
	<p>Search for Quizzes by Tag...<br>
	<form action=SearchTagServlet method="POST">
		<textarea rows="1" cols="10" name="text"></textarea>
		<input type="submit" value="Submit">
	</form>
	</p>
</body>
</html>