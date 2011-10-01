<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<%
  Student student = (Student)session.getAttribute("student");
%>

  <div class="green-box" id="edit_details">
    <h2>Edit Your Details</h2>
    <p>(* mandatory fields)</p>
    <ul id="errors" style="display:none">
    </ul>
    <form>
      <input type="hidden" name="action" value="edit_details" />
      <dl>
        <dt><label for="username">user name*</label></dt>
        <dd><input type="text" name="username" id="username" value="<%= student.getUsername() %>" /></dd>

        <dt><label>name*</label></dt>
        <dd><input type="text" name="name" id="name" value="<%= student.getName() %>" /></dd>
        <dt><label>phone number</label></dt>
        <dd><input type="text" name="phoneNumber" id="phone" value="<%= student.getPhoneNumber() %>"/></dd>
        <dt></dt>
        <dd><button onclick="return validateEdit()">Save</button></dd>
      </dl>
    </form>
  </div>
  <div class="green-box" style="margin-top:20px" id="change_password">
    <h2>Change your password</h2>
    <p>(* mandatory fields)</p>
    <ul class="errors" style="display:none">
    </ul>
    <form>
    <input type="hidden" name="action" value="change_password" />
    <dl>
      <dt><label>News password*</label></dt>
      <dd><input type="password" name="password" id="password" /></dd>
      <dt><label>Password gain*</label></dt>
      <dd><input type="password" name="repassword" id="repassword" /></dd>
      <dt><span class="saved">Saved!</span></dt>
      <dd><button onclick="return validatePassword()">Save</button></dd>
    </dl>
    </form>
  </div>