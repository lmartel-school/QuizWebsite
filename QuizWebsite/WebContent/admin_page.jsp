<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, quiz.*, user.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Administrative Page</title>
</head>
<body>

<% List<String> users = (List<String>) request.getAttribute("all_users"); %>
There are <%= users.size() %> users on this site. </br>
<% int numTaken = (Integer) request.getAttribute("num_taken"); 
	out.println("There are " + numTaken + " Quiz Results.");
%>
	

<h2>Modify Users</h2>
	<form action="ModifyUser" method="post">
	<select name="user">
<%
		
		for (String name : users){
			out.println("<option value=\"" + name + "\">" + name + "</option>");
		}
%>
	</select>
		<input type="radio" name="group1" value="delete"> Delete User
		<input type="radio" name="group1" value="promote"> Make Administrator
		<input type="submit" value="Submit">
	</form>
	
	
<h2>Modify Quizzes</h2>

	<form action="ModifyQuiz" method="POST">
	<select name="quiz">
<%
		List<Quiz> quizzes = (List<Quiz>) request.getAttribute("all_quizzes");
		for (Quiz quiz : quizzes){
			out.println("<option value=\"" + quiz.getID() + "\">" + quiz.getName() + "</option>");
		}
%>
	</select>
		<input type="radio" name="group1" value="delete"> Delete Quiz
		<input type="radio" name="group1" value="history"> Clear History
		<input type="submit" value="Submit">
	</form>
	
	<br> <br>
	
<h2>Add Categories</h2>

	<form action="AddCategory" method="POST">
		<textarea rows="1" cols="15" name="text">Category name...</textarea>
		<input type="submit" value="Submit">
	</form>
	
	<br><br>
	
	<h2> All Listed Announcements: </h2><br>
	<% List<Announcement> announce = (List<Announcement>) request.getAttribute("announcements");
		for (Announcement ann : announce){
			out.println(ann.getMsg());
			out.println("<a href=\"DeleteAnnouncement?id=" + ann.getID() + "\">Delete Announcement</a><br>");
		}
		
	%>
	<a href=announcement.jsp> <h2> Compose an announcement </h2> </a>
	<a href=CurrentUserProfileServlet><h2> Go to your Profile</h2> </a>
	

</body>
</html>