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
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <title>DR - Admin - Student's Courses</title>
    <link href='http://fonts.googleapis.com/css?family=Sansita+One|Chivo' rel='stylesheet' type='text/css' />
    <link href="../static/css/main.css" rel="stylesheet" type="text/css" />
    <link href="../static/css/admin.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
      #courses_table {width:100%; background-color:#BCDD11; margin-top:20px;}
      #courses_table td, #courses_table th {background-color:#F1FAC0; padding:8px}
    </style>
  </head>

  <body>
    <jsp:include page="header.jsp" />
    <jsp:include page="menu.jsp" />
    <div id="main">
      <h2><%= stud.getName() %>'s courses:</h2>
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
    </div>
  </body>
</html>
