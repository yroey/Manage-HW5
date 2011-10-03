<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.Student, cs236369.hw5.dal.Xslt , cs236369.hw5.Utils " %>
<%
int days_per_week = 7;
int hours_per_day = 10;

Student student = (Student)session.getAttribute("student");

String select_table = Utils.getCookie(request, "time_table_format_id");

Xslt[] xslts = Xslt.getAll();
%>
Choose format:
<select id="time_table_format_selector" onchange="loadTimeTableByFormat()">
  <% for (Xslt xslt : xslts) { %>
    <option value="<%= xslt.getId() %>" <%= Integer.toString(xslt.getId()).equals(select_table) ? "selected='selected'" : "" %>><%= xslt.getName() %></option>
  <% } %>
</select>

<iframe id="time_table_iframe" style="width:100%; border:0; height:500px" frameborder="0"></iframe>