<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="quiz.*, user.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= ((User) session.getAttribute("user")).getName() %> 's Profile</title>
</head>
<body>

	<%
	User user = (User) session.getAttribute("user");
	
	out.println("<h1>Welcome to your profile page, " + user.getName() + "!</h1>");
	
	
	int numMsg = (Integer) request.getAttribute("unreadMsg");
	out.println("You have " + numMsg + " unread messages.");
	
	
	out.println("<a href=\"MessageInboxServlet\">View Message Inbox</a>");
	
	out.println("<h3>Announcements</h3>");
	List<Announcement> announce = (List<Announcement>) request.getAttribute("announcements");
	for (int i = 0; i < announce.size(); i++) {
		out.println(announce.get(i).getMsg());
	}
	
	out.println("<h3>Recently Made Quizzes</h3>");
	List<Quiz> recents = (List<Quiz>) request.getAttribute("recents");
	for (int i = 0; i < recents.size(); i++) {
		Quiz quiz = recents.get(i);
		out.println("<a href=\"QuizServlet?id=" + quiz.getID() + "\">" + quiz.getName() + "</a>");
	}
	
	
	out.println("<h3>Popular Quizzes</h3>");
	List<Quiz> popular = (List<Quiz>) request.getAttribute("popular");
	
	for (int i = 0; i < popular.size(); i++) {
		Quiz quiz = popular.get(i);
		out.println("<a href=\"QuizServlet?id=" + quiz.getID() + "\">" + quiz.getName() + "</a>");
	}
	
	out.println("<p> Need to sort quizzes by number of QuizResult fields referring to it: this should be complete</p>");
	
	out.println("<h3>Your Recently Taken Quizzes</h3>");
	List<QuizResult> usrRecents = (List<QuizResult>) request.getAttribute("userRecent");
	
	for (int i = 0; i < usrRecents.size(); i++) {
		QuizResult result = usrRecents.get(i);
		out.println("<a href=\"QuizResultServlet?id=" + result.getID() + "\">" + result.getQuiz().getName() + " Result</a>");
		
	}
	
	
	out.println("<h3>Quizzes You Authored</h3>");
	List<Quiz> authored = (List<Quiz>) request.getAttribute("authored");
	
	for (int i = 0; i < authored.size(); i++) {
		Quiz quiz = authored.get(i);
		out.println("<a href=\"QuizServlet?id=" + quiz.getID() + "\">" + quiz.getName() + "</a>");
		
	}
	
	
	out.println("<h3>Friend Activity</h3>");
	List<Activity> activities = (List<Activity>) request.getAttribute("activities");
	
	for (int i = 0; i < activities.size(); i++) {
		Activity act = activities.get(i);
		User usr = act.getUser();
		out.println(usr.getName() + ": " + act.getActivity());
		out.println("<a href=\"UserServlet?id=" + usr.getID() + "\">" + usr.getName() + "'s Profile Page" + "</a>");
		if (act.getQuizID() != -1) {
			out.println("<a href=\"QuizServlet?id=" + act.getQuizID() + "\">Go to the Quiz</a>");
		}
		out.println();
	}
	if (user.isAdmin()) {
		out.println("<a href=\"AdminServlet\">Administration</a>");
	}
	
	%>
	<br>
	<a href=SearchServlet>Search All Users</a>

</body>
</html>