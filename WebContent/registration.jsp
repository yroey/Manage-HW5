<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>registration form</title>
	</head>
	<body>
		<form action="Registration" method="post">
			<label>user name</label>: <input type="text" name="username" /><br />
			<label>password</label>: <input type="text" name="password" /><br />
			<label>name</label>: <input type="text" name="name" /><br />
			<label>phone number</label>: <input type="text" name="phoneNumber" /><br />
			<input type="radio" name="type" value="administrator" /> administrator<br />
			<input type="radio" name="type" value="student" /> student <br />
			<input type="submit" value="Submit" />
		</form>
	</body>
</html>