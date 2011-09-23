<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>course managmant</title>
	</head>
	<body>
		<h3>add a new course:</h3>
				<% request.getSession(true).setAttribute("action", "Add");%>
				<form action="ManageCourse" method="post">
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
			   	<td>id</td>
			   	<td>name</td>
			   	<td>group</td>
			   	<td>capacity</td>
			   	<td>credit points</td>
			   	<td>description</td>
			   	<td>remove course</td>
			  </tr>
			  <%for(Course c : courses){ %>
			  <tr>
			  	<td><%=c.getId() %></td>
			  	<td><%=c.getName() %></td>
			  	<td><%=c.getIntField("group_id") %></td>	
			  	<td><%=c.getIntField("capacity") %></td>	
			  	<td><%=c.getIntField("credit_points") %></td>
			  	<td><%=c.getStringField("course_description") %></td>
			  	<td> <%if (c.getIntField("creator_id") == ((Administartor)session.getAttribute("administrator")).getId()){%>
			  			<% request.getSession(true).setAttribute("removeCourseId", new Integer(c.getId()).toString());%>
			  			<% request.getSession(true).setAttribute("action", "Remove");%>
			   			<a href="ManageCourse">removeCouerse</a> <% }%> </td>
			   			<% }%>
			  </tr>	  
		</table>
	</body>
</html>