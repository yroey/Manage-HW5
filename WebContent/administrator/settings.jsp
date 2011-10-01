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
	<h3>Settings</h3>
	</body>
	<% request.getSession(true).setAttribute("action", "updateDetails");%>
	<form action="../Registration" method="post">
			<label>user name</label>: <input type="text" name="username" Value=<%=admin.getStringField("username") %> /><br />
			<label>password</label>: <input type="password" name="password" Value=<%=admin.getStringField("password") %> /><br />
			<label>name</label>: <input type="text" name="name" Value=<%=admin.getStringField("name") %> /><br />
			<label>phone number</label>: <input type="text" name="phoneNumber" Value=<%=admin.getIntField("phone_number") %> /><br />
			<input type="hidden" name="id" Value=<%=admin.getId() %> /><br />
			<input type="submit" value="update my details" /> 
			<input type="reset" value="Reset" /> 
		</form>
</html>