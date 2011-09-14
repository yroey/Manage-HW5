<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<%
int days_per_week = 7;
int hours_per_day = 10;

String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednsday", "Thursday","Friday","Saturday"};

Student student = (Student)session.getAttribute("student");
Course[][] time_table = student.getTimeTable(days_per_week, hours_per_day);
%>
<table border="1">
  <tr>
    <th>Hour \ Day</th>
    <% for (int day = 0; day < days_per_week; ++day) { %>
      <th><%= dayNames[day] %></th>
    <% } %>
  </tr>
  <% for (int hour = 0; hour < hours_per_day; ++hour) { %>
  <tr>
    <td><%= hour %></td>
    <% for (int day = 0; day < days_per_week; ++day) { %>
      <td><%= time_table[day][hour].getName() %></td>
    <% } %>
  </tr>
  <% } %>
<table>