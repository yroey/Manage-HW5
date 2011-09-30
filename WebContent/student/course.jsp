<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<%
  String[] days = {"Sunday", "Monday", "Tuesday", "Wensday", "Thursday", "Frieday", "Saturday"};
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
  You cannot register to this course, since it has reached its capacity or its schedule conflicts with other courses you are registered to.
  </div>

  <div class="message" id="course_msg">

  </div>

  <div id="course_sessions">
    <h4>Course Sessions</h4>
    <% for (Session course_session : course.getSessions()) { %>
      <div><%= days[course_session.getDayOfWeek()] %>, <%= course_session.getStartHour() %>-<%= course_session.getEndHour() %></div>
    <% } %>
  </div>
</div>