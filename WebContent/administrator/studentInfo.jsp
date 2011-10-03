<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*, cs236369.hw5.Utils " %>
<%
int days_per_week = 7;
int hours_per_day = 10;

int id = Integer.parseInt(request.getParameter("id"));
Student stud = new Student(id);
String select_table = Utils.getCookie(request, "time_table_format_id");
Course[] courses = stud.getCourses();
%>
Choose format:
<select id="time_table_format_selector" onchange="loadTimeTableByFormat()">
  <option value="1">Name 1</option>
  <option value="2">Name 2</option>
</select>

<div id="time_table_content"></div>
<table id="courses_table">
			  <tr>
			   	<th>Name</th>
			   	<th>Group</th>
			   	<th>Capacity</th>
			   	<th>Credit Points</th>
			  </tr>
			  <% for(Course c : courses) { %>
			  <tr>
			  	<td><%= c.getName() %></td>
			  	<td><%= c.getIntField("group_id") %></td>
			  	<td><%= c.getIntField("capacity") %></td>
			  	<td><%= c.getIntField("credit_points") %></td>
			  </tr>
			  <% }%>
		</table>
