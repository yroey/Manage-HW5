<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*"%>
<%
Administartor admin = (Administartor)session.getAttribute("administrator");
%>
<div id="menu">
	<ul>
	  <li><a href="coursesManagement.jsp">Courses</a></li>
	  <li><a href="studentManagemant.jsp">Students</a></li>
	  <li><a href="settings.jsp">Settings</a></li>
	  <% if (admin.isSuperAdmin()) { %>
	  <li><a href="superUserManagemant.jsp">Super User</a></li>
	  <% } %>
	</ul>
</div>