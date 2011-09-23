<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>settings</title>
	</head>
	<body>
	<h3>register a new admin</h3>
	</body>
	<% request.getSession(true).setAttribute("action", "addAdmin");%>
		<form action="../Registration" method="post">
			<label>user name</label>: <input type="text" name="username" /><br />
			<label>password</label>: <input type="password" name="password" /><br />
			<label>name</label>: <input type="text" name="name" /><br />
			<label>phone number</label>: <input type="text" name="phoneNumber" /><br />
			<input type="submit" value="Submit" />
		</form>
</html>