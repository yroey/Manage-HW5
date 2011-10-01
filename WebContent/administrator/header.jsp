<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<%
Administartor admin = (Administartor)session.getAttribute("administrator");
System.out.println(admin.getStringField("name"));
%>

<div id="header">
  <div id="logo"><a href="index.jsp">DR - Dynamic Registration - Admin</a></div>
  <div id="user">Hi
    <a href="settings.jsp"><%= admin.getStringField("name") %></a> | <a href="../Authentication?logout=1&admin=1">Logout</a>
  </div>
</div>