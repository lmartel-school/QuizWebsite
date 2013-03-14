<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Log In</title>
</head>
<body>
	<img src="http://i.imgur.com/NQmIwj9.png"><br>

	<h1><b>Welcome to TheQuizBook</b></h1>
	
	Log in if you wish to accept the challenge.

	<form action="LoginServlet" method="POST">
	
		<p>User Name: <input type="text" name="username" />
		<p>Password: <input type="password" name="password" />
		<input type="submit" /></p>
	
	</form>
	
		<a href="create_account.html">Create New Account</a>

</body>
</html>