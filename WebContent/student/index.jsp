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
<div>Hi <%= student.getName() %>!</div>
<div id="registered_courses" style="width:200px; float:left">
<h2>Your courses</h2>
	<% for (Course course : student.getCourses()) { %>
	  <%= course.getName() %> <br />
	<% } %>
	<a href="time_table.jsp">time table</a>
</div>
<div id="courses">
  <h2>All courses</h2>
	<div id="courses">
	 <div id="course_template" style="display:none" class="course">
	  <div style="display:none" class="course_id"></div>
    <h3 class="course_name"></h3>
    <a href="javascript:void(0)" onclick="register(this)">register</a>
	 </div>
	</div>
</div>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
	<script>
	 var x;
	 function register(trigger) {
		  var container = trigger;
		  while(!$(container).hasClass('course')) {
			  container = container.parentNode;
		  }
		  var id = $(container).find('.id').html();
		  alert(id);
	 }
	  $(document).ready(function(){
		  $.getJSON('CourseSearch', function(data){
			  var courses = data['courses'];
			  var template = $('#course_template');
			  for (i in courses) {
				  var course = courses[i];
				  x = course;
				  var new_course = template.clone();
				  new_course.find('.course_name').html(course['name']).end()
				    .find('.course_id').html(course['id']).end()
				    .removeAttr('id').appendTo('#courses').show(0);
			  }
		  });
	  });

	  function registrationResult(result) {
		  alert(result);
	  }

	  function registerToCourse(course_id) {
		  $.post('CourseSearch', {'course_id': course_id}, function(data){
			  registrationResult(data['result']);
		  }, 'json');
	  }
	</script>
</body>
</html>