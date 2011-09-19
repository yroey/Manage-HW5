<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>courses Management</title>
	</head>
	<body>
		<form action="addCourse" method="post">
			<label>ID</label>: <input type="text" name="id" /><br />
			<label>group</label>: <input type="text" name="group_id" /><br />
			<label>Name</label>: <input type="text" name="name" /><br />
			<label>Capacity</label>: <input type="text" name="capacity" /><br />
			<label>Credit Points</label>: <input type="text" name="credit points" /><br />
			<label>course description</label>: <input type="text" name="course description" /><br />
			<input type="submit" value="Submit" />
		</form>
	</body>
</html>