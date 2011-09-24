<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>Students Management</title>
	</head>
	<body>
		<%
			Student[] students = Student.getAll();
		%>
		<h3>all Students</h3>
		<table border="1">
			  <tr>
			   	<td>username</td>
			   	<td>password</td>
			   	<td>name</td>
			   	<td>phone number</td>
			   	<td>registered courses and time table</td>
			   	<td>delete</td>
			  </tr>
			  <%for(Student s : students){ %>
			  <tr>
			  	<td><%=s.getStringField("username") %></td>
			  	<td><%=s.getStringField("password") %></td>	
			  	<td><%=s.getName() %></td>	
			  	<td><%=s.getIntField("phone_number") %></td>
			  	<td><a href="studentPage">link</a></td>
			  	<td><a href="#" onclick="test(<%=new Integer(s.getId()).toString()%>, 'remove'); return false;">remove student</a> </td>
			  </tr>	 
			  <%} %> 
		</table>
		<script type="text/javascript">
		function test(id, action){
			document.getElementById('student_id').value=id;
			document.getElementById('action').value=action;
			document.forms[0].submit();
		}
	</script>
		<form action="ManageStudents" method="post">
			<input type="hidden" name="action" value="null" />
			<input type="hidden" name="student_id" value="null"/>	
		</form>	
	</body>
</html>