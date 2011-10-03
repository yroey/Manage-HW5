<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<%
  Administartor[] admins = Administartor.getAll();
  Administartor admin = (Administartor)session.getAttribute("administrator");
  boolean superUser = false;
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>DR - Admin - Super User</title>
    <link href='http://fonts.googleapis.com/css?family=Sansita+One|Chivo' rel='stylesheet' type='text/css' />
    <link href="../static/css/main.css" rel="stylesheet" type="text/css" />
    <link href="../static/css/admin.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
      #admin_table {width:100%; background-color:#BCDD11;}
      #admin_table td {background-color:#F1FAC0; padding:8px}
    </style>
	</head>
	<body>
    <jsp:include page="header.jsp" />
    <jsp:include page="menu.jsp" />

    <div id="main">
		  <h3>Register a new Administrator</h3>

			<form action="../Registration" method="post">
        <input type="hidden" name="action" value="addAdmin" />
			  <dl>
				  <dt><label>user name</label>:</dt><dd><input type="text" name="username" /></dd>
					<dt><label>password</label>:</dt><dd><input type="password" name="password" /></dd>
					<dt><label>name</label>:</dt><dd><input type="text" name="name" /></dd>
					<dt><label>phone number</label>:</dt><dd><input type="text" name="phoneNumber" /></dd>
					<dt></dt><dd><input type="submit" value="Register" /></dd>
			  </dl>
			</form>
			<h3>Administrators</h3>
			<table id="admin_table">
				  <tr>
				   	<td>Username</td>
				   	<td>Password</td>
				   	<td>Name</td>
				   	<td>Phone number</td>
				   	<td>Delete</td>
				  </tr>
				  <% for(Administartor a : admins){
					    if (a.getId() == Administartor.getSuperUserId()) {
					    	continue;
					    } %>
				  <tr>
				  	<td><%= a.getStringField("username") %></td>
				  	<td><%= a.getStringField("password") %></td>
				  	<td><%= a.getName() %></td>
				  	<td><%= a.getStringField("phone_number") %></td>
				  	<td><a href="javascript:void(0)" onclick="test(<%= a.getId() %>, 'removeAdmin'); return false;">remove</a> </td>
				  </tr>
				  <% } %>
			</table>
		</div>
		<form action="ManageUsers" method="post">
			<input type="hidden" name="action" value="null" />
			<input type="hidden" name="admin_id" value="null"/>
		</form>

	  <script type="text/javascript">
	    function test(id, action){
	      oformElement = document.forms[1];
	      oformElement.elements["admin_id"].value =id;
	      oformElement.elements["action"].value = action;
	      document.forms[1].submit();
	    }
    </script>
  </body>
</html>