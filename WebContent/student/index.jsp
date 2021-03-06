<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>

<%
  Student student = (Student)session.getAttribute("student");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DR - Student Page</title>
<link href='http://fonts.googleapis.com/css?family=Sansita+One|Chivo' rel='stylesheet' type='text/css'>
<link href="../static/css/main.css" rel="stylesheet" type="text/css" />
<link href="../static/css/student.css" rel="stylesheet" type="text/css" />

</head>
<body>
  <jsp:include page="../header.jsp" />
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
      <li><a href="javascript:void(0)" onclick="setUrl('time_table')">Time Table</a></li>
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
	  <div id="edit_student" class="main_pane">
	  </div>
		<div id="course_search" class="main_pane">
		  <h2>Course Search</h2>
		  <div id="search_form">
		    <label for="q">Query:</label> <input type="text" name="q" id="q" style="border:1px solid #ccc; font-size:16px; width:200px;" />
		    <input type="checkbox" name="available" id="available" /> <label for="available">Only Available courses</label>
		    <input type="button" value="search" onclick="set_course_search_url()" />
		  </div>
		  <table id="courses_search_table" style="display:none" cellspacing="0">
		    <tr class="sticky">
		      <th>Course Name</th>
		      <th>Credit Points</th>
		      <th>Group</th>
		    </tr>
		  </table>
		  <table style="display:none">
		    <tr id="course_template" style="display:none" class="course sticky">
          <td class="course_link"><a  href="#course?id={{ID}}" class="course_name" onclick="return setUrl('course?id={{ID}}')"></a></td>
          <td class="course_credit"></td>
          <td class="course_group"></td>
        </tr>
      </table>
		  <div id="no_courses_msg" style="display:none">
		    No course was found that matches your query.
		  </div>
		</div>
	</div>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
  <script src="../static/javascript/jquery.cookies.2.2.0.min.js"></script>
	<script src="../static/javascript/student.js"></script>
</body>
</html>
