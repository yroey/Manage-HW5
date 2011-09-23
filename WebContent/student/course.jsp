<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<%
  Student student = (Student)session.getAttribute("student");
  Course course = (Course)request.getAttribute("course");
%>

<div>
  <h3><%= course.getName() %></h3>

</div>