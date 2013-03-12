<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="question.*, question.Question.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add a question!</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<body>
<h1>Add a question to your quiz!</h1>
<% 
for(QUESTION_TYPE t : QUESTION_TYPE.values()){
	out.println("<input type=\"radio\" id=\"radio" + t.value + "\" name=\"type\"> " + t.text + "<br>");
}
%>
<% 
for(QUESTION_TYPE t : QUESTION_TYPE.values()){
	out.println("<div id=\"form" + t.value + "\">");
	out.println("<form action=\"CreateQuestionServlet\" method=\"POST\">");
	out.println(Question.renderCreateMode(t));
	out.println("<button type=\"submit\" name=\"finished\" value=\"false\">Submit and Continue</button><br>");
	out.println("<button type=\"submit\" name=\"finished\" value=\"true\">Submit and Finish</button><br>");
	out.println("</form>");
	out.println("</div>");
}
%>

<script>
$(document).ready(function(){
	$('div').hide();
});
<%
for(QUESTION_TYPE t : QUESTION_TYPE.values()){
	int i = t.value;
	out.println("$('#radio" + i + "').change(function(){");
	out.println("if($(this).is(':checked')){");
	out.println("$('div').hide();");
	out.println("$('#form" + i + "').show();");
	out.println("}");
	out.println("});");
}
%>
</script>
</body>
</html>