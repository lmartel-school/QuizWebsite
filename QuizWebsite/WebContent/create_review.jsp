<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Write a Review</title>
</head>
<body>
<a href="CurrentUserProfileServlet"> Go to your Profile </a>
<a href="LogoutServlet">Logout</a>

<form action="CreateReviewServlet" method="post">

	<input type="hidden" name="quizID" value="<% request.getParameter("quizID"); %>">
	Give it a Rating...<br>
	<input type="radio" name="group1" value="1"> 1
	<input type="radio" name="group1" value="2"> 2
	<input type="radio" name="group1" value="3"> 3
	<input type="radio" name="group1" value="4"> 4
	<input type="radio" name="group1" value="5"> 5
	
	<textarea rows="10" cols="30" name="text">Write a review...</textarea>
	<input type="submit" value="Submit">
</form>
</body>
</html>