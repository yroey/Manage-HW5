<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<%
  Student student = (Student)session.getAttribute("student");
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
  <ul id="registered_courses_list">
	  <li id="registered_course_template" style="display:none">
	   <span class="course_name"></span>
	  </li>
	</ul>
  <a href="time_table.jsp">time table</a>
</div>
<div id="courses">
  <h2>All courses</h2>
	<div id="courses">
	 <div id="course_template" style="display:none" class="course" data-id="">
    <h3 class="course_name" onclick="displayCourse(this)"></h3>
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
		  loadRegisteredCourses();
		  $.getJSON('CourseSearch', function(data){
			  var courses = data['courses'];
			  var template = $('#course_template');
			  for (i in courses) {
				  var course = courses[i];
				  x = course;
				  var new_course = template.clone();
				  new_course.find('.course_name').html(course['name']).end()
				    .data('id', course['id'])
				    .removeAttr('id').appendTo('#courses').show(0);
			  }
		  });
	  });

	  function registrationResult(result) {
		  if (data['result'] == '0') {
			  alert('Could not register to course. ' + data['msg']);
			  return;
		  }

		  // Add the course to the list of registered courses.
		  addRegisteredCourse(data['course'], true);
	  }

	  function loadRegisteredCourses() {
		  $.getJSON('CourseSearch', {'registered' : 1}, function(data) {
			  var courses = data['courses'];
			  for (var i = 0; i < courses.length; ++i) {
				  addRegisteredCourse(courses[i], false);
			  }
		  });
	  }

	  function addRegisteredCourse(course, highlight) {
		  var course_template = $('#registered_course_template').clone();
		  course_template.find('.course_name').html(course['name']);
		  course_template.css('display', '');
		  course_template.appendTo('#registered_courses_list');
	  }

	  function register(trigger) {
		  var container = $(trigger).parents('.course');
		  if (!countainer) {
			  return;
		  }
		  var course_id = $(containr).data('id');
		  $.post('CourseSearch', {'course_id': course_id}, function(data){
			  registrationResult(data);
		  }, 'json');
	  }

	  function displayCourse(trigger) {
	    var container = $(trigger).parents('.course');
	    if (!container) {
	      return;
	    }
	    var course_id = $(container).data('id');
	    $.get('CourseSearch', {'id': course_id}, function(data){

		  });
	  }
	</script>
</body>
</html>