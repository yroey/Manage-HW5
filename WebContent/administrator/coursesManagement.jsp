<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>Course Management</title>
	</head>
	<body>
		<h2>add a new Course</h2>
				<form action="./selectSessions.jsp" method="post" >
						<label>Name</label>: <input type="text" name="name" /><br />
						<label>group</label>: <input type="text" name="group_id" /><br />
						<label>Capacity</label>: <input type="text" name="capacity" /><br />
						<label>Credit Points</label>: <input type="text" name="credit_points" /><br />
						<label>Course Description</label>: <TEXTAREA ROWS="3"></TEXTAREA><br/>
						<input type="submit" value="Submit new course" />
				</form>
		<%
			Course[] courses = Course.getAll();
		%>
		<h3>all Courses</h3>
		<table border="1">
			  <tr>
			   	<td>Name</td>
			   	<td>Group</td>
			   	<td>Capacity</td>
			   	<td>Credit Points</td>
			   	<td>Description</td>
			   	<td>Manage</td>
			   	<td>remove course</td>
			  </tr>
			  <%for(Course c : courses){ %>
			  <tr>
			  	<td><%=c.getName() %></td>
			  	<td><%=c.getIntField("group_id") %></td>	
			  	<td><%=c.getIntField("capacity") %></td>	
			  	<td><%=c.getIntField("credit_points") %></td>
			  	<td><%=c.getStringField("course_description") %></td>
			  	<td><a href="./viewCourseInfo.jsp?id=<%=c.getId()%>">view more info</a></td>
			  	<td> <%if (c.getIntField("creator_id") == ((Administartor)session.getAttribute("administrator")).getId()){%>
			   			<a href="javascript:void(0)" onclick="test(<%=new Integer(c.getId()).toString()%>, 'remove'); return false;">removeCouerse</a> <% }else{%>course not managed by you<%} %> </td>
			   			<% }%>
			  </tr>	  
		</table>
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
	</body>
</html>