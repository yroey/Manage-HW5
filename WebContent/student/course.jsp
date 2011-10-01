<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<%
  String[] days = {"Sunday", "Monday", "Tuesday", "Wensday", "Thursday", "Frieday", "Saturday"};
  Student student = (Student)session.getAttribute("student");
  Course course = new Course(Integer.parseInt(request.getParameter("id")));
  boolean is_registered = student.isRegisteredToCourse(course.getId());
  boolean is_available = student.isCourseAvailable(course);
  boolean can_register = is_available || is_registered;
  int num_students_left = course.getCapacity() - course.getNumStudents();
  String msg = can_register ? "" : num_students_left == 0 ? "You cannot register to this course, since "+
                                                       "it has reached its capacity" :
                                                       "You cannot register to this course, since "+
                                                       "its schedule conflicts with other courses "+
                                                       "you are registered to.";
%>

<div>
  <div id="course_registration" class="<%= is_registered ? "registered" : "" %>">
      <div class="message" id="course_msg">
    </div>
    <button onclick="unregisterById(<%= course.getId() %>)" id="registered">Unregister</button>
    <button onclick="registerById(<%= course.getId() %>)"
            id="not_registered" title="<%= !is_available ? msg : "" %>"
            <%= can_register ? "" : "disabled='disabled'" %>>Register</button>

  </div>
  <h1><%= course.getName() %></h1>
  <h5>Credit Points:</h5>
  <p><%= course.getCredit() %></p>
  <h5>Capacity:</h5>
  <p><%= course.getCapacity() %>; <span id="num_students_left"><%= num_students_left %></span> left</p>
  <h4>Course Description:</h4>
  <p><%= course.getStringField("description") %></p>

  <div id="course_sessions">
    <h4>Course Sessions</h4>
    <% for (Session course_session : course.getSessions()) { %>
      <div><%= days[course_session.getDayOfWeek()] %>, <%= course_session.getStartHour() %>-<%= course_session.getEndHour() %></div>
    <% } %>
  </div>
</div>