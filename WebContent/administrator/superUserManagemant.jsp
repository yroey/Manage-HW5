<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>settings</title>
	</head>
	<body>
		<%Administartor admin = (Administartor)session.getAttribute("administrator");
		boolean superUser = false;
		String type = "";
		if (Administartor.getSuperUserId() != admin.getId()){
			superUser = true;
			type = "DISABLED";
		}
		%>
	 <% request.getSession(true).setAttribute("action", "addAdmin");%>
	<h3>Register a new administrator</h3>	
		<form action="../Registration" method="post">
			<label>user name</label>: <input  <%=type%> type="text" name="username" /><br />
			<label>password</label>: <input  <%=type%> type="password" name="password" /><br />
			<label>name</label>: <input <%=type%> type="text" name="name" /><br />
			<label>phone number</label>: <input  <%=type%> type="text" name="phoneNumber" /><br />
			<input  type="submit" value="Register" />
		</form>
</html>