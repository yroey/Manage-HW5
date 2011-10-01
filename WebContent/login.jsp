<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DR - Login</title>
<link href='http://fonts.googleapis.com/css?family=Sansita+One|Chivo' rel='stylesheet' type='text/css'>
<link href="static/css/main.css" rel="stylesheet" type="text/css" />
</head>
<body>
  <jsp:include page="header.jsp" />
  <div id="main">
    <div class="green-box">
		  <form action="Authentication" method="post">
		    <label>Username</label>: <input type="text" name="username" /><br />
		    <label>Password</label>: <input type="password" name="password" /><br />
		    <input type="radio" name="type" value="administrator" /> administrator<br />
		    <input type="radio" name="type" value="student" checked="checked" /> student <br />
		    <input type="submit" value="Login" />
		  </form>
    </div>
  </div>
</body>
</html>