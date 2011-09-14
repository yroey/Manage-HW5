<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>all courses list</title>
	</head>
	<body>
		<%
			Course[] courses = Course.getAll();			
		%>
		<h3>all courses available for remove</h3>
		<table border="1">
			  <tr>
			   	<td>id</td>
			   	<td>name</td>
			   	<td>group</td>
			   	<td>capacity</td>
			   	<td>credit points</td>
			   	<td>description</td>
			   	<td>delete</td>
			  </tr>
			  
			  <%for(Course c : courses){ %>
				  <%if (c.getIntField("creatorID") == ((Administartor)session.getAttribute("administrator")).getId()){%>
				  <tr>
				  	<td><%=c.getId() %></td>
				  	<td><%=c.getName() %></td>
				  	<td><%=c.getIntField("group") %></td>	
				  	<td><%=c.getIntField("capacity") %></td>	
				  	<td><%=c.getIntField("credit_points") %></td>
				  	<td><%=c.getStringField("course_description") %></td>
				  	<td><a href="#">removeCouerse</a></td>		
				  </tr>
				  <% }%>
			  <% }%> 		  
		</table>
		<script type="text/javascript">
				/* alert("course removed"); */
			</script>
	</body>
</html>