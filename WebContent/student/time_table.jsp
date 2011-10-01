<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.Student, cs236369.hw5.Utils " %>
<%
int days_per_week = 7;
int hours_per_day = 10;

Student student = (Student)session.getAttribute("student");

String select_table = Utils.getCookie(request, "time_table_format_id");
%>
Choose format:
<select id="time_table_format_selector" onchange="loadTimeTableByFormat()">
  <option value="1">Name 1</option>
  <option value="2">Name 2</option>
</select>

<div id="time_table_content"></div>