<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>Course Management</title>
	</head>
	<body>
		<h3>add a new course:</h3>
				<form action="ManageCourses" method="post" >
						<label>Name</label>: <input type="text" name="name" /><br />
						<label>group</label>: <input type="text" name="group_id" /><br />
						<label>Capacity</label>: <input type="text" name="capacity" /><br />
						<label>Credit Points</label>: <input type="text" name="credit points" /><br />
						<label>course description</label>: <input type="textarea" name="course description" /><br />
						<input type="submit" value="Submit" />
				</form>
		<%
			Course[] courses = Course.getAll();
		%>
		<h3>all Courses</h3>
		<table border="1">
			  <tr>
			   	<td>name</td>
			   	<td>group</td>
			   	<td>capacity</td>
			   	<td>credit points</td>
			   	<td>description</td>
			   	<td>remove course</td>
			  </tr>
			  <%for(Course c : courses){ %>
			  <tr>
			  	<td><%=c.getName() %></td>
			  	<td><%=c.getIntField("group_id") %></td>	
			  	<td><%=c.getIntField("capacity") %></td>	
			  	<td><%=c.getIntField("credit_points") %></td>
			  	<td><%=c.getStringField("course_description") %></td>
			  	<td> <%if (c.getIntField("creator_id") == ((Administartor)session.getAttribute("administrator")).getId()){%>
			   			<a href="#" onclick="test(<%=new Integer(c.getId()).toString()%>, 'remove'); return false;">removeCouerse</a> <% }else{%>none<%} %> </td>
			   			<% }%>
			  </tr>	  
		</table>
		<script type="text/javascript">
		function test(id, action){
			document.getElementById('course_id').value=id;
			document.getElementById('action').value=action;
			document.forms[1].submit();
		}
	</script>
	<form action="ManageCourses" method="post">
		<input type="hidden" name="action" value="null" />
		<input type="hidden" name="course_id" value="null"/>	
	</form>	
	</body>
</html>