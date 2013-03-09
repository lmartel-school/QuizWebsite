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

	<%
	Challenge chal = (Challenge) request.getAttribute("challenge");
	FriendRequest req = (FriendRequest) request.getAttribute("friend");
	Note note = (Note) request.getAttribute("note");
	if (chal != null) {
		out.println(chal.getSender() + " has sent you a challenge");
		out.println("<a href=\"UserServlet?username=" + chal.getSender() + "\">View Challenger's Profile</a>");
		QuizResult result = chal.getChallengerResult();
		out.println("<a href=\"QuizResultServlet?id=" + result.getID() + "\">View Challenger's Result</a>");
	
	} else if (req != null) {
		out.println(req.getSender() + " has sent you a friend request");
		%>
		
		<form action="AcceptFriendServlet" method="post">
		
		<% out.println("<input name=requestID type=hidden value=true/>"); %>
		<input type="submit" value="Accept Friend Request"/>
		
		</form>
		<br><br>
		<form action="DeclineFriendServlet" method="post">
		
		<% out.println("<input name=requestID type=hidden value=false/>"); %>
		<input type="submit" value="Reject Friend Request"/>
		
		</form>
	<%	
	
	} else if (note != null) {
		out.println("<h2>Your note from " + note.getSender() + "</h2>");
		out.println(note.getText());
	}
	
	%>

</body>
</html>