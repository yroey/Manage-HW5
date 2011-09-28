<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" import="cs236369.hw5.XmlGenerator" %>
<%
  Student student = (Student)session.getAttribute("student");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="../static/css/student.css" rel="stylesheet" type="text/css" />

</head>
<body>
  <div id="header">
    <div id="logo">DR - Dynamic registration</div>
	  <div id="user">Hi <%= student.getName() %> | <a href="../Authentication?logout=1">logout</a></div>
	</div>
	<img src="../static/images/ajax-loader.gif" id="ajax-loader" style="display:none" />
	<div id="registered_courses">
	  <ul>
      <li>
		    <p>Your courses<p>
		    <ul id="registered_courses_list">
		      <li id="registered_course_template" style="display:none" class="course">
		       <a  href="#course?id={{ID}}" class="course_name" onclick="return setUrl('course?id={{ID}}')"></a>
		      </li>
		    </ul>
      </li>
      <li><a href="javascript:void(0)" onclick="setUrl('time_table')">time table</a></li>
      <li><a href="javascript:void(0)" onclick="setUrl('show_course_search')">Course Search</a></li>
	  </ul>
	</div>
	<div id="main">
	  <div id="welcome-pane" class="main_pane">
	  Welcome!
	  </div>
	  <div id="course" class="main_pane">
	  </div>
	  <div id="time_table" class="main_pane">
	  </div>
		<div id="course_search" class="main_pane">
		  <h2>Course Search</h2>
		  <div id="search_form">
		    <label for="course_name">Course Name</label><input type="text" name="course_name" id="course_name" />
		    <input type="checkbox" name="available" id="available" /> <label for="available">Only Available courses</label>
		    <input type="button" value="search" onclick="set_course_search_url()" />
		  </div>
			<div id="courses">
			 <div id="course_template" style="display:none" class="course">
		    <a  href="#course?id={{ID}}" class="course_name" onclick="return setUrl('course?id={{ID}}')"></a>
			 </div>
			</div>
		</div>
	</div>
<<<<<<< HEAD
</div>
<div>
	<h2>XSLT time table</h2>
	<%XmlGenerator.generate(2, ".//timetable.xml", ".//newResult.html"); %>
</div>
=======
>>>>>>> origin/master
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
	<script src="../static/javascript/student.js"></script>
</body>
</html>