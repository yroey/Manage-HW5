<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<%
  Student student = (Student)session.getAttribute("student");
  Course course = (Course)request.getAttribute("course");
  boolean is_registered = student.isRegisteredToCourse(course.getId());
  boolean is_available = student.isCourseAvailable(course);
%>

<div>
  <a href="javascript:history.back()">Back</a><br/>
  <h3><%= course.getName() %></h3>
  <p><%= course.getStringField("description") %></p>
  <div id="registered" style="<%= !is_registered ? "display:none" : "" %>">
    Your are registered to this course.<br/>
    <a href="javascript:void(0)" onclick="unregisterById(<%= course.getId() %>)">Unegister</a>
  </div>
  <div id="not_registered" style="<%= is_registered || !is_available ? "display:none" : "" %>">
    <a href="javascript:void(0)" onclick="registerById(<%= course.getId() %>)">Register</a>
  </div>
  <div style="<%= is_available || is_registered ? "display:none" : "" %>">
  You cannot register to this course, since its schedule conflicts with other courses you are registered to.
  </div>
</div>