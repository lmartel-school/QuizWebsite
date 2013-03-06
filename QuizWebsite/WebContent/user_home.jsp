<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="quiz.*, java.util.*" %>
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
	
	out.println("<a href=\"MessageServlet\">View Messages</a>");
	
	
	out.println("<h3>Your Recently Taken Quizzes</h3>");
	List<QuizResult> usrRecents = (List<QuizResult>) request.getAttribute("userRecent");
	List<Quiz> authored = (List<Quiz>) request.getAttribute("authored");
	List<Quiz> recents = (List<Quiz>) request.getAttribute("recent");
	List<Activity> activities = (List<Activity>) request.getAttribute("activities");
	
	for (int i = 0; i < usrRecents.size(); i++) {
		out.println("THIS IS RESULT" + i);
	}
	%>

</body>
</html>