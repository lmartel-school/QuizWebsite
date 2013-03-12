<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>All Users</title>
</head>
<body>
<% 
	List<String> users = (List<String>) request.getAttribute("all_users");
	for (String name : users){
		out.println("<a href=\"UserProfileServlet?username=" + name + "\">" + name + "</a> <br>");
	}
	
	out.println("<br/> <br/> <a href=\"CurrentUserProfileServlet\"> Go to your Profile</a>");

%>
</body>
</html>