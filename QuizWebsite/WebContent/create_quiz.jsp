<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="quiz.Quiz.*,user.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Want to create a quiz?</title>
</head>
<body>
<h1>Welcome to QUIZ CREATION.</h1>
<form action="CreateQuizServlet" method="POST">
<input type="hidden" name="author" value="<%= ((User) session.getAttribute("user")).getName() %>">
<p>Name your quiz: <input type="text" name="name" /></p>
<p>Enter a description: <textarea cols="40" rows="5" name="description"></textarea></p>
<p>What type of user experience do you want? <br>
<% 
for(PAGE_TYPE t : PAGE_TYPE.values()){
	out.println("<input type=\"radio\" name=\"type\" value=\"" + t.value + "\"> " + t.description + "<br>");
}
%>

<p>Do you want the questions to appear in...<br>
<input type="radio" name="inOrder" value="true"> the order you create them,<br>
<input type="radio" name="inOrder" value="false"> or in a randomized order?<br>
</p>

<p>Select a category for this quiz...<br>
	<select name="category">
<%
		List<String> categs = (List<String>) request.getAttribute("categories");
		for (String category : categs){
			out.println("<option value=\"" + category + "\">" + category + "</option>");
		}
%>
	</select>
</p>

<p>Add some tags to your quiz...<br>
	<textarea cols="40" rows="5" name="tags">Enter wrong answers to display here, one per line</textarea><br>
</p>


<input type="submit" value="Start creating questions!">
</form>
</body>
</html>