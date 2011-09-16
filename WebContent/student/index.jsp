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
	<div id="courses">
	 <div id="course_template" style="display:none">
    <h3 class="course_name"></h3>
	 </div>
	</div>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
	<script>
	 var x;
	  $(document).ready(function(){
		  $.getJSON('CourseSearch', function(data){
			  var courses = data['courses'];
			  var template = $('#course_template');
			  for (i in courses) {
				  var course = courses[i];
				  x = course;
				  var new_course = template.clone();
				  new_course.find('.course_name').html(course['name']).end()
				    .removeAttr('id').appendTo('#courses').show(0);
			  }
		  });
	  });
	</script>
</body>
</html>