<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="quiz.*, java.util.*, user.*, servlets.*, java.sql.*;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Message</title>
</head>
<body>
<h1> Compose a new message:</h1>

<form action="CreateMessageServlet" method="post">
<input type="hidden" name="sender" value="<%User u = (User)session.getAttribute("user"); out.print(u.getName()); %>">
<select name="recipient">
<% Connection conn = (Connection) application.getAttribute("con");
List<String> friendsList = HomePageQueries.getFriends(u.getName(), conn);
friendsList.add("Truman");
for (String name : friendsList){
	out.println("<option value=\"" + name + "\">" + name + "</option>");
}
%>
</select>
<br/>
<textarea rows="10" cols="30" name="text">Compose here...</textarea>
<input type="submit" value="Submit">
</form>
</body>
</html>