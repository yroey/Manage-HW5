<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<%
  Student student = (Student)session.getAttribute("student");
  Course[] courses = Course.getAll();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
Hi student!
<br>
<a href="time_table.jsp">time table</a>
<h2>Your courses</h2>
<% for (Course course : student.getCourses()) { %>
  <%= course.getName() %> <br />
<% } %>

<h2>All courses</h2>
<% for (Course course : courses) { %>
  <%= course.getName() %> <br />
<% } %>
</body>
</html>