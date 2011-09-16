<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*"%>
<%
Course[] courses = (Course[])request.getAttribute("courses");
%>

{
"courses": [
<% for (int i = 0; i < courses.length; ++i) { %>
  {
    "name": "<%= courses[i].getName().replace("\"", "\\\"") %>"
  }
  <% if (i+1 < courses.length) {%>
  ,
  <% } %>
<% } %>
]
}