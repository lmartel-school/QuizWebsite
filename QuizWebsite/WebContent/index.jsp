<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Master Extraordinaire</title>
</head>
<body>

	<h1><b>Welcome to Quiz Master Extraordinaire</b></h1>
	
	Log in if you wish to accept the challenge.

	<form action="LoginServlet" method="post">
	
		<p>User Name: <input type="text" name="username" />
		<p>Password: <input type="text" name="password" />
		<input type="submit" /></p>
	
	</form>
	
		<a href="create_account.html">Create New Account</a>

</body>
</html>