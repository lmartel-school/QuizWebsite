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
	List<Quiz> recents = (List<Quiz>) request.getAttribute("userRecent");
	
	//for (int i = 0; i < recents.size(); i++) {
	//	out.println(recents.get(i).getName());
	//}
	%>

</body>
</html>