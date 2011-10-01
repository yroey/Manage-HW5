<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="cs236369.hw5.Utils"	%>

<%
  String msg = Utils.getSessionMessage(session);
  String type = "admin";//pageContext.getServletConfig().getInitParameter("type");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DR - Login</title>
<link href='http://fonts.googleapis.com/css?family=Sansita+One|Chivo' rel='stylesheet' type='text/css'>
<link href="static/css/main.css" rel="stylesheet" type="text/css" />
</head>
<body>
  <jsp:include page="header.jsp" />
  <div id="login">
    <div class="green-box">
		  <form action="Authentication" method="post">
		    <% if (!"".equals(msg)) { %>
		    <div id="login_msg"><%= msg %></div>
		    <% } %>
		    <input type="hidden" name="type" value="<%= type %>">
		    <dl>
		      <dt><label for="useranme">Username</label></dt>
		      <dd><input type="text" name="username" /></dd>
		    </dl>
		    <dl>
		      <dt><label for="password">Password</label></dt>
		      <dd><input type="password" name="password" /></dd>
		    </dl>
		    <dl>
		      <dt></dt>
		      <dd><button type="submit">Login</button></dd>
		    </dl>
		  </form>
    </div>
  </div>
</body>
</html>