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
	<%@ include file="_header.jsp" %>
	
	<% 
		
	QuizSummary summary = (QuizSummary) request.getAttribute("summary");
	Quiz quiz = summary.getQuiz();
	
	out.println("<a href=\"BeginQuizServlet?id=" + quiz.getID() + "\">Take this Quiz!</a>");
	
	out.println("<h1>" + quiz.getName() + "</h1>");
	out.println("<h2>By: <a href=\"UserProfileServlet?username=" + quiz.getAuthor() + "\">" + quiz.getAuthor() + "</a></h2>");
	
	out.println("<p>" + quiz.getDescription() + "<p>");
	
	out.println("<h3>Quiz Statistics:</h3>");
	out.println("<h4> Max score: " + quiz.getMaxScore() + "</h4>");
	out.println("<table> <tr> <td> Mean </td> <td> Median </td> </tr> <tr> <td> " + 
				summary.getMean() + "</td> <td> "+ summary.getMedian()+ "</td> </tr> </table>");
	
	
	out.println("<h3><a href=\"BeginQuizServlet?id=" + quiz.getID() + "\">Take this quiz!</a></h3>");
	
	out.println("<h3> All time High Scores: </h3>");
	out.println("<ul>");
	for (String name : summary.getTopAll()) {
		out.println("<li>" + name + "</li>");
	}
	out.println("</ul>");
	
	out.println("<h3> Recent High Scores: </h3>");
	out.println("<ul>");
	for (String name : summary.getRecentTop()) {
		out.println("<li>" + name + "</li>");
	}
	out.println("</ul>");
	
	out.println("<h3> Most recent results: </h3>");
	out.println("<ul>");
	for (QuizResult res : summary.getRecentResults()) {
		out.println("<li><a href=\"UserProfileServlet?username=" + res.getUsername() 
					+ "\">" + res.getUsername() + "</a> got " + res.getScore() + "</li>");
	}
	out.println("</ul>");
	
	
	%>
	
</body>
</html>