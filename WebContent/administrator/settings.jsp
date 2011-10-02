<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<%
  Administartor admin = (Administartor)session.getAttribute("administrator");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>DR - Admin - Settings</title>
    <link href='http://fonts.googleapis.com/css?family=Sansita+One|Chivo' rel='stylesheet' type='text/css'>
    <link href="../static/css/main.css" rel="stylesheet" type="text/css" />
    <link href="../static/css/admin.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
    <jsp:include page="header.jsp" />
    <jsp:include page="menu.jsp" />
    <div id="main">
	  <h3>Settings</h3>
			<form action="../Registration" method="post">
			  <input type="hidden" name="action" value="updateDetails" />
					<label>Username</label>: <input type="text" name="username" value="<%=admin.getStringField("username") %>" /><br />
					<label>Password</label>: <input type="password" name="password" value="<%=admin.getStringField("password") %>" /><br />
					<label>Name</label>: <input type="text" name="name" value="<%= admin.getStringField("name") %>" /><br />
					<label>Phone Number</label>: <input type="text" name="phoneNumber" value="<%= admin.getIntField("phone_number") %>" /><br />
					<input type="submit" value="Save" />
			</form>
		</div>
  </body>
</html>