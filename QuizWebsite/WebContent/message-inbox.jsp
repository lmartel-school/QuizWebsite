<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="quiz.*, java.util.*, user.*;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Message Inbox</title>
</head>
<body>

	<a href="CurrentUserProfileServlet"> Go to your Profile </a>
	<a href="LogoutServlet">Logout</a><br><br>
	
	<%
	List<Message> messages = (List<Message>) request.getAttribute("messages");
	if (messages.isEmpty()) {
		out.println("<h1> You have no messages in your inbox </h1>");
	}
	for (int i = 0; i < messages.size(); i++) {
		Message message = messages.get(i);
		out.println("<a href=\"MessageServlet?messageid=" + message.getID() + "\">"
				+message.getHTMLSummary()+"</a>");
		out.println("<hr/>");
	}
	
	out.println("<a href=\"compose_message.jsp\"> <h2> Compose a new message </h2> </a>");	
	%>



</body>
</html>