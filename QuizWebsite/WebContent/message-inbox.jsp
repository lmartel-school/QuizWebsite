<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="quiz.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Message Inbox</title>
</head>
<body>
	<%
	List<Message> messages = (List<Message>) request.getAttribute("messages");	
	for (int i = 0; i < messages.size(); i++) {
		Message message = messages.get(i);
		//message.getHTMLSummary();
		out.println("<a href=\"MessageServlet?messageid=" + message.getID() + "\">View Message Details</a>");
		
	}
	
	%>



</body>
</html>