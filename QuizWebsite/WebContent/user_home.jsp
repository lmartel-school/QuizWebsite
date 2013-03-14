<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="quiz.*, user.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= ((User) session.getAttribute("user")).getName() %> 's Dashboard</title>
</head>
<body>

	<a href="LogoutServlet">Logout</a>

	<%
	User user = (User) session.getAttribute("user");
	
	out.println("<h1>Welcome to your Dashboard, " + user.getName() + "!</h1>");
	
	
	int numMsg = (Integer) request.getAttribute("unreadMsg");
	out.println("You have " + numMsg + " unread messages.");
	
	
	out.println("<a href=\"MessageInboxServlet\">View Message Inbox</a>");
	
	out.println("<h3>Announcements</h3>");
	List<Announcement> announce = (List<Announcement>) request.getAttribute("announcements");
	for (int i = 0; i < announce.size(); i++) {
		out.println(announce.get(i).getMsg());
		%> <br><br> <%
	}
	
	out.println("<h3>Recently Made Quizzes</h3>");
	List<Quiz> recents = (List<Quiz>) request.getAttribute("recents");
	for (int i = 0; i < recents.size(); i++) {
		Quiz quiz = recents.get(i);
		out.println("<a href=\"QuizServlet?id=" + quiz.getID() + "\">" + quiz.getName() + "</a>");
		%> <br> <%
	}
	
	
	out.println("<h3>Popular Quizzes</h3>");
	List<Quiz> popular = (List<Quiz>) request.getAttribute("popular");
	
	for (int i = 0; i < popular.size(); i++) {
		Quiz quiz = popular.get(i);
		out.println("<a href=\"QuizServlet?id=" + quiz.getID() + "\">" + quiz.getName() + "</a>");
		%> <br><br> <%
	}
		
	out.println("<h3>Popular Quizzes, as Voted by Peers</h3>");
	Map<Quiz, ArrayList<Review>> rated = new HashMap<Quiz, ArrayList<Review>>();//(Map<Quiz, ArrayList<Review>>) request.getAttribute("popular_rating");
	
	for (Quiz quiz : rated.keySet()) {
		ArrayList<Review> reviews = rated.get(quiz);
		out.println("<a href=\"QuizServlet?id=" + quiz.getID() + "\">" + quiz.getName() + "</a>");
		%> <ul> 
			<% for (int i = 0; i < reviews.size(); i++) {
				out.println("<li> " + reviews.get(i).renderReview() + " </li>");
			}  
		%> </ul> <%
	}
	
	out.println("<h3>Your Recently Taken Quizzes</h3>");
	List<QuizResult> usrRecents = (List<QuizResult>) request.getAttribute("userRecent");
	
	for (int i = 0; i < usrRecents.size(); i++) {
		QuizResult result = usrRecents.get(i);
		out.println("<a href=\"QuizResultServlet?id=" + result.getID() + "\">" + result.getQuiz().getName() + " Result</a>");
		%> <br><br> <%
	}
	out.println("<a href=\"HistoryServlet\">View Complete History</a>");
	
	out.println("<h3>Quizzes You Authored</h3>");
	out.println("<h4><a href=\"StartCreateQuizServlet\">Create a new quiz!</a></h4><br>");
	List<Quiz> authored = (List<Quiz>) request.getAttribute("authored");
	
	for (int i = 0; i < authored.size(); i++) {
		Quiz quiz = authored.get(i);
		out.println("<a href=\"QuizServlet?id=" + quiz.getID() + "\">" + quiz.getName() + "</a>");
		%> <br><br> <%
	}
	
	out.println("<h3>Your Achievements</h3><br>");
	List<Achievement> achieve = (List<Achievement>) request.getAttribute("achievements");
	for (int i = 0; i < achieve.size(); i++) {
		out.println(achieve.get(i).getAward());
		%> <br> <%
	}
	
	
	out.println("<h3>Friend Activity</h3>");
	List<Activity> activities = (List<Activity>) request.getAttribute("activities");
	
	for (int i = 0; i < activities.size(); i++) {
		Activity act = activities.get(i);
		User usr = act.getUser();
		out.println(usr.getName() + ": " + act.getActivity());
		out.println("<a href=\"UserProfileServlet?username=" + usr.getName() + "\">" + usr.getName() + "'s Profile Page" + "</a>");
		if (act.getQuizID() != -1) {
			out.println("<a href=\"QuizServlet?id=" + act.getQuizID() + "\">Go to the Quiz</a>");
			%> <br><br> <%
		}
		out.println();
	}
	if (user.isAdmin()) {
		out.println("<a href=\"AdminServlet\">Administration</a>");
	}
	
	%>
	<br>
	<a href=SearchServlet>Search All Users and Quizzes</a>

</body>
</html>