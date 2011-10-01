<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>Course Management</title>
	</head>
	<body>

	<%String id =request.getParameter("id"); Course c = new Course(Integer.parseInt(id)); int group_id = c.getIntField("group_id");%>
<h3>Course Details</h3>
			<form action="#" method="post">				
				<div>
					<label for="id">ID:</label>
					<input name="id" id="id" type="text" readonly="readonly" value=<%=c.getId()%> />
				</div>
				<div>
					<label for="name">Name:</label>
					<input id="name" name="name" type="text" readonly="readonly" value=<%=c.getName()%> />
				</div>
				<div>
					<label for="academic_points">Academic Points:</label>
					<input id="academic_points" name="academic_points" type="text" readonly="readonly" value=<%=c.getIntField("credit_points")%> />
				</div>
				<div>
					<label for="group">Group:</label>
					<input name="group" type="text" readonly="readonly" value=<%=c.getIntField("group_id")%> />
				</div>
				</form>
				<h3>Sessions</h3>
				<%Session[] sessions = c.getSessions();%>
				<table border="1">
			  <tr>
			   	<td>start_hour</td>
			   	<td>end_hourr</td>
			   	<td>day_of_week</td>
			   	<td>length</td>
			  </tr>
			  <%for(Session s : sessions){ %>
			  <tr>
			  	<td><%=s.getStartHour()%></td>
			  	<td><%=s.getEndHour() %></td>	
			  	<td><%=s.getDayOfWeek() %></td>	
			  	<td><%=s.getIntField("length") %></td>
			   			<% }%>
			  </tr>	  
		</table>
		<h3>Add Sessions</h3>
		<form action="ManageCourses" method="post" >
						<label>Start Hour</label>: <input type="text" name="start_hour" /><br />
						<label>End Hour</label>: <input type="text" name="end_hour" /><br />
						<label>day of week</label>: <input type="text" name="day_of_week" /><br />
						<label>length</label>: <input type="textarea" name="length" /><br />
						<input type="hidden" name="action" value='addSession' />
						<input type="hidden" name="course_id" value=<%=id%> />
						<input type="hidden" name="group_id" value=<%=group_id%> />		
						<input type="submit" value="Submit" />
		</form>						
	</body>
</html>