<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, user.*, quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= ((User) request.getAttribute("user")).getName()%> 's Profile Page</title>
</head>
<body>

<%
	User user = (User) request.getAttribute("user");
	String name = user.getName();
	
	out.println("<h1>Welcome to " + user.getName() + "'s profile page.</h1>");
	
	int friendStatus = (Integer) request.getAttribute("friend_status");
	
	switch (friendStatus) {
	
		case 0: break;
		case 1: 
			out.println("You have a pending friend request for " + name);
			break;	
		
		case 2: %>
			<form action="FriendRequestResponse" method="post">
	
			<% out.println("<input name=confirmation type=hidden value=true />");  %>
			<input type="submit" value="Accept Friend Request"/>
			</form>
			<% break;
		case 3: %>
			<form action="SendFriendRequest" method="post">
			<input name=friend type=hidden value=<%= name %> />
			<input type="submit" value="Request as Friend"/>
			</form>
	<%		 break;
	default: out.println("You are friends with " + name); %>
			<form action="FriendRequestResponse" method="post">
	
			<% out.println("<input name=confirmation type=hidden value=false />");  %>
			<input type="submit" value="Remove From Friends"/>
			</form>
	<% break;
	}
		
	
	out.println("<h3>" + name + "'s Recently Taken Quizzes</h3>");
	List<QuizResult> usrRecents = (List<QuizResult>) request.getAttribute("userRecent");
	
	for (int i = 0; i < usrRecents.size(); i++) {
		QuizResult result = usrRecents.get(i);
		out.println("<a href=\"QuizResultServlet?id=" + result.getID() + "\">" + result.getQuiz().getName() + " Result</a>");
		
	}
	
	
	out.println("<h3>Quizzes " + name + " Authored</h3>");
	List<Quiz> authored = (List<Quiz>) request.getAttribute("authored");
	
	for (int i = 0; i < authored.size(); i++) {
		Quiz quiz = authored.get(i);
		out.println("<a href=\"QuizServlet?id=" + quiz.getID() + "\">" + quiz.getName() + "</a>");
		
	}
	
	
	out.println("<h3>Recent Activity</h3>");
	List<Activity> activities = (List<Activity>) request.getAttribute("activities");
	
	for (int i = 0; i < activities.size(); i++) {
		Activity act = activities.get(i);
		User usr = act.getUser();
		out.println(usr.getName() + ": " + act.getActivity());
		if (act.getQuizID() != -1) {
			out.println("<a href=\"QuizServlet?id=" + act.getQuizID() + "\">Go to the Quiz</a>");
		}
		out.println();
	}
	
	%>
	
	<a href=SearchServlet>Search All Users</a>
	out.println("<a href="CurrentUserProfileServlet"> Go to your Profile </a>");

</body>
</html>