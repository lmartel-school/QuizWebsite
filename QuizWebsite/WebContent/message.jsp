<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="quiz.*, user.*;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Message Details</title>
</head>
<body>

	<a href="CurrentUserProfileServlet"> Go to your Profile </a>
	<a href="LogoutServlet">Logout</a> <br><br>

	<%
	Challenge chal = (Challenge) request.getAttribute("challenge");
	FriendRequest req = (FriendRequest) request.getAttribute("friend");
	Note note = (Note) request.getAttribute("note");
	if (chal != null) {
		out.println(chal.getSender() + " has sent you a challenge");
		out.println("<a href=\"UserProfileServlet?username=" + chal.getSender() + "\">View Challenger's Profile</a>");
		QuizResult result = chal.getChallengerResult();
		out.println("<a href=\"QuizResultServlet?id=" + result.getID() + "\">View Challenger's Result</a>");
	
	} else if (req != null) {
		out.println(req.getSender() + " has sent you a friend request");
		session.setAttribute("friend", req); //why did you make this in session?
		%>
		
		<form action="FriendRequestResponse" method="post">
		
		<input name=confirmation type=hidden value=true>
		<input name=friend type=hidden value=/>
		<input type="submit" value="Accept Friend Request"/>
		
		</form>
		<br><br>
		<form action="FriendRequestResponse" method="post">
		
		<% out.println("<input name=confirmation type=hidden value=false/>"); %>
		<input type="submit" value="Reject Friend Request"/>
		
		</form>
	<%	
	
	} else if (note != null) {
		out.println("<h2>Your note from " + note.getSender() + "</h2>");
		out.println(note.getText());
	
		out.println("<br><br>");
		out.println("<a href=\"UserProfileServlet?username=" + note.getSender() + "\">View " + note.getSender() + "'s Profile</a>");
	}
	
	
	%>

</body>
</html>