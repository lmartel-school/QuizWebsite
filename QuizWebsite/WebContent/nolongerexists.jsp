<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Profile not found</title>
</head>
<body>

<%@ include file="_header.jsp" %>
<h1>Sorry, but the profile for <%out.print(request.getParameter("username")); %> no longer exists.</h1>

</body>
</html>