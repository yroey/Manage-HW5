  <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<%
  Student student = (Student)session.getAttribute("student");
%>
  <div id="header">
    <div id="logo"><a href="index.jsp">DR - Dynamic Registration</a></div>
    <% if (student == null) { %>
    <div id="user">
    <a href="login.jsp">Login</a> |
    <a href="registration.jsp">Register</a></div>
    <% } else { %>
    <div id="user">Hi
      <a href="javascript:void(0)" onclick="setUrl('edit_details')"><%= student.getName() %></a> | <a href="../Authentication?logout=1">Logout</a></div>
    <% } %>
  </div>
