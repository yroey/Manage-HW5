<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>settings</title>
	</head>
	<body>
	<script type="text/javascript">
		function test(id, action){
			oformElement = document.forms[1];
			oformElement.elements["admin_id"].value =id;
			oformElement.elements["action"].value = action;
			document.forms[1].submit();
		}
	</script>
		<%Administartor admin = (Administartor)session.getAttribute("administrator");
		boolean superUser = false;
		String type = "";
		if (Administartor.getSuperUserId() != admin.getId()){
			superUser = true;
			type = "DISABLED";
		}
		%>
	<h3>Register a new administrator</h3>
	<%
		Administartor[] admins = Administartor.getAll();
	%>
		<form action="../Registration" method="post">
		  <input type="hidden" name="action" value="addAdmin" />
		  <label>user name</label>: <input  <%=type%> type="text" name="username" /><br />
			<label>password</label>: <input  <%=type%> type="password" name="password" /><br />
			<label>name</label>: <input <%=type%> type="text" name="name" /><br />
			<label>phone number</label>: <input  <%=type%> type="text" name="phoneNumber" /><br />
			<input  type="submit" value="Register" />
		</form>
		<h3>Administrator accounts</h3>
		<table border="1">
			  <tr>
			   	<td>Username</td>
			   	<td>Password</td>
			   	<td>Name</td>
			   	<td>Phone number</td>
			   	<td>Delete administrator</td>
			  </tr>
			  <%for(Administartor a : admins){  if (a.getId() == Administartor.getSuperUserId()) {continue;}%>
			  <tr>
			  	<td><%=a.getStringField("username") %></td>
			  	<td><%=a.getStringField("password") %></td>
			  	<td><%=a.getName() %></td>
			  	<td><%=a.getIntField("phone_number") %></td>
			  	<td><a href="javascript:void(0)" onclick="test(<%=new Integer(a.getId()).toString()%>, 'removeAdmin'); return false;">remove</a> </td>
			  </tr>
			  <%} %>
		</table>
		<form action="ManageUsers" method="post">
			<input type="hidden" name="action" value="null" />
			<input type="hidden" name="admin_id" value="null"/>
		</form>
</html>