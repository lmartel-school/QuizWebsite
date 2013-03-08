<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Account</title>
</head>
<body>

	<h1>The Name <%= request.getParameter("username") %> is Already in Use.</h1>
	
	Please enter another name and password.
	
	<form action="NewAccountServlet" method="post">
		
		<p>User Name: <input type="text" name="username" />
		<p>Password: <input type="password" name="password" />
		<input type="submit" /></p>
		
	</form>

</body>
</html>