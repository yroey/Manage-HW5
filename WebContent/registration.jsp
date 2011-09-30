<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>

<%
if (request.getSession(true).getAttribute("action") == null) {
  request.getSession(true).setAttribute("action", "addStudent");
}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>DR - Registration</title>
    <link href='http://fonts.googleapis.com/css?family=Sansita+One|Chivo' rel='stylesheet' type='text/css'>
    <link href='static/css/main.css' rel='stylesheet' type='text/css' />
	</head>
	<body>
    <jsp:include page="header.jsp" />
    <div id="main">
      <div class="green-box">
		    <form action="Registration" method="post">
		      <label>user name</label>: <input type="text" name="username" /><br />
		      <label>password</label>: <input type="password" name="password" /><br />
		      <label>name</label>: <input type="text" name="name" /><br />
		      <label>phone number</label>: <input type="text" name="phoneNumber" /><br />
		      <input type="submit" value="Submit" />
		    </form>
      </div>
    </div>
	</body>
</html>