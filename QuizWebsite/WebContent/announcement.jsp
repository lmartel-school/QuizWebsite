<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Compose Announcement</title>
</head>
<body>

<%@ include file="_header.jsp" %>

<form action="CreateAnnouncement" method="post">
<textarea rows="10" cols="30" name="text">Compose here...</textarea>
<input type="submit" value="Submit">

</body>
</html>