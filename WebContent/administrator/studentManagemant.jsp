<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<%
  Student[] students = Student.getAll();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>DR - Admin - Students</title>
		<link href='http://fonts.googleapis.com/css?family=Sansita+One|Chivo' rel='stylesheet' type='text/css' />
    <link href="../static/css/main.css" rel="stylesheet" type="text/css" />
    <link href="../static/css/admin.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
      #student_table {width:100%; background-color:#BCDD11;}
      #student_table td, #courses_table th {background-color:#F1FAC0; padding:8px}
    </style>
	</head>
	<body>
    <jsp:include page="header.jsp" />
    <jsp:include page="menu.jsp" />
	  <div id="main">
			<h3>All Students</h3>
			<table id="student_table">
			  <tr>
			   	<td>Username</td>
			   	<td>Name</td>
			   	<td>Phone Number</td>
			   	<td>Registered Courses</td>
			   	<td>Delete</td>
			  </tr>
			  <% for(Student s : students) { %>
			  <tr>
			  	<td><%=s.getStringField("username") %></td>
			  	<td><%=s.getName() %></td>
			  	<td><%=s.getStringField("phone_number") %></td>
			  	<td><a href="studentInfo.jsp?id=<%= s.getId() %>">info</a></td>
			  	<td><a href="javascript:void(0)" onclick="test(<%= s.getId() %>, 'removeStud'); return false;">remove student</a></td>
			  </tr>
			  <% } %>
			</table>
		</div>
		<form action="ManageUsers" method="post">
			<input type="hidden" name="action" value="null" />
			<input type="hidden" name="student_id" value="null"/>
		</form>
	  <script type="text/javascript">
	    function test(id, action){
	    	var ans=confirm("are you sure?");
			if (ans == true){
		      oformElement = document.forms[0];
		      oformElement.elements["student_id"].value =id;
		      oformElement.elements["action"].value = action;
		      document.forms[0].submit();
			}
	    }
	  </script>
	</body>
</html>