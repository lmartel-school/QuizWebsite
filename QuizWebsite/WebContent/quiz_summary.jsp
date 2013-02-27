<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Summary</title>
</head>
<body>

	<% 
		
	Quiz quiz = request.getParameter("quiz");
	
	out.println("<h1>" + quiz.getName() + "</h1>");
	
	out.println(quiz.getAuthor());
	out.println("<br>");
	
	
	out.println(quiz.getDescription());
	
	
	%>

</body>
</html>