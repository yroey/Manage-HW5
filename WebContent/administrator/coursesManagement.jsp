<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<%
  Course[] courses = Course.getAll();
  Administartor admin = (Administartor)session.getAttribute("administrator");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>DR - Admin - Courses</title>
    <link href='http://fonts.googleapis.com/css?family=Sansita+One|Chivo' rel='stylesheet' type='text/css' />
    <link href="../static/css/main.css" rel="stylesheet" type="text/css" />
    <link href="../static/css/admin.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
      #courses_table {width:100%; background-color:#BCDD11;}
      #courses_table td, #courses_table th {background-color:#F1FAC0; padding:8px}
    </style>
	</head>
	<body>
    <jsp:include page="header.jsp" />
    <jsp:include page="menu.jsp" />
	 <div id="main">
		<h2>Add a new Course</h2>
				<form action="./selectSessions.jsp" method="post" >
				<dl>
						<dt><label>Name</label>:</dt><dd><input type="text" name="name" /></dd>
						<dt><label>group</label>:</dt><dd><input type="text" name="group_id" /></dd>
						<dt><label>Capacity</label>:</dt><dd><input type="text" name="capacity" /></dd>
						<dt><label>Credit Points</label>:</dt><dd><input type="text" name="credit_points" /></dd>
						<dt><label>Course Description</label>:</dt><dd><textarea name="description" rows="8" cols="20" style="width:400px;" ></textarea></dd>
						<dt></dt><dd><input type="submit" value="Save" /></dd>
				</dl>
				</form>

		<h3>All Courses</h3>
		<table id="courses_table">
			  <tr>
			   	<th>Name</th>
			   	<th>Group</th>
			   	<th>Capacity</th>
			   	<th>Credit Points</th>
			   	<th>Manage</th>
			   	<th>remove course</th>
			  </tr>
			  <% for(Course c : courses) { %>
			  <tr>
			  	<td><%= c.getName() %></td>
			  	<td><%= c.getIntField("group_id") %></td>
			  	<td><%= c.getIntField("capacity") %></td>
			  	<td><%= c.getIntField("credit_points") %></td>
			  	<td><a href="viewCourseInfo.jsp?id=<%= c.getId() %>">view more info</a></td>
			  	<td>
			  	  <% if (c.getIntField("creator_id") == admin.getId()){  %>
			   			<a href="javascript:void(0)" onclick="test(<%= c.getId() %>, 'remove'); return false;">Remove Course</a>
			   	  <% }else{ System.out.println(c.getId() + " " +admin.getId());%>
			   	    course not managed by you
			   	  <% } %>
			   	</td>
			  </tr>
			  <% }%>
		</table>
   </div>
		<script type="text/javascript">
		function test(id, action){
			oformElement = document.forms[1];
			oformElement.elements["course_id"].value =id;
			oformElement.elements["action"].value = action;
			document.forms[1].submit();
		}
	</script>
	<form action="ManageCourses" method="post">
		<input type="hidden" name="action" value="null" />
		<input type="hidden" name="course_id" value="null"/>
	</form>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
    <script>
    </script>
	</body>
</html>