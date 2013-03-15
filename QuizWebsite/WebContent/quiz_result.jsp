<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="servlets.*, database.*, quiz.*, question.*, java.util.List, user.*, java.util.Date, java.text.SimpleDateFormat;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Result</title>
</head>
<body>

<%@ include file="_header.jsp" %>
  
<%
QuizResult res = (QuizResult)request.getAttribute("result");
User viewer = (User)session.getAttribute("user");
String takerName = res.getUsername();
Quiz quiz = res.getQuiz();
String nameWLink = "<a href=\"UserProfileServlet?username=" + takerName + "\">" + takerName + "</a>";
String quizWLink = "<a href=\"QuizServlet?id=" + quiz.getID() + "\">" + quiz.getName() + "</a>";
out.println("<h1>" + nameWLink + " took the quiz: " + quizWLink + "</h1>");
out.println("<h2> at " + res.getTimeTakenFormatted() + "</h2>");
out.println("<h2> It took " + takerName +" "+ res.getElapsedTime()+ " to get a score of " +
res.getScore() + " out of " + quiz.getMaxScore() + "</h2>");
session.setAttribute("result", res);
%>

<h2>Send a challenge to one of your friends!</h2>
<form action="CreateChallengeServlet" method="post">
<input type="hidden" name="sender" value="<%User u = (User)session.getAttribute("user"); out.print(u.getName()); %>">
<select name="recipient">
<% 
List<String> friendsList = HomePageQueries.getFriends(u.getName(), MyDB.getConnection());
for (String name : friendsList){
	out.println("<option value=\"" + name + "\">" + name + "</option>");
}
%>
</select>
<br/>
<input type="submit" value="Challenge">
</form>
</body>
</html>