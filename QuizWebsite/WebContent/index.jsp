<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Master Extraordinaire</title>
</head>
<body>

	<%
		RequestDispatcher dispatch;
		if (session.getAttribute("user") == null) {
			dispatch = request.getRequestDispatcher("log_in.jsp");
		} else {
			dispatch = request.getRequestDispatcher("CurrentUserProfileServlet");
		}
		dispatch.forward(request, response);
	%>

</body>
</html>