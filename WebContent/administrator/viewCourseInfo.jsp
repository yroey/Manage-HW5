<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<%
  String id = request.getParameter("id");
  Course c = new Course(Integer.parseInt(id));
  int group_id = c.getIntField("group_id");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>DR - Admin - Course Info</title>
    <link href='http://fonts.googleapis.com/css?family=Sansita+One|Chivo' rel='stylesheet' type='text/css' />
    <link href="../static/css/main.css" rel="stylesheet" type="text/css" />
    <link href="../static/css/admin.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
      #session_table {width:100%; background-color:#BCDD11;}
      #session_table td, #courses_table th {background-color:#F1FAC0; padding:8px}
      label {font-weight:bold}
    </style>
	</head>
	<body>
    <jsp:include page="header.jsp" />
    <jsp:include page="menu.jsp" />
    <div id="main">
	    <h3>Course Details</h3>
			<div>
				<label for="name">Name:</label> <%= c.getName() %>
			</div>
			<div>
				<label for="academic_points">Academic Points:</label> <%= c.getIntField("credit_points") %>
			</div>
			<div>
				<label for="group">Group:</label> <%= c.getIntField("group_id") %>
			</div>
			<div><label>Description:</label><br/>
			 <%= c.getStringField("description").replace("\n", "<br/>") %>
			</div>
			<h3>Sessions</h3>
			<table id="session_table">
			  <tr>
			   	<td>Start Hour</td>
			   	<td>End Hour</td>
			   	<td>Day Of Week</td>
			   	<td>Length</td>
			  </tr>
			  <% for(Session s : c.getSessions()){ %>
			  <tr>
			  	<td><%= s.getStartHour() %></td>
			  	<td><%= s.getEndHour() %></td>
			  	<td><%= s.getDayOfWeek() %></td>
			  	<td><%= s.getIntField("length") %></td>
			   <% } %>
		    </tr>
			</table>
		</div>
	</body>
</html>