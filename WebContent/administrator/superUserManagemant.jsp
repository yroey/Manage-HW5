<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" import="cs236369.hw5.Utils" %>
<%
  Administartor[] admins = Administartor.getAll();
  Administartor admin = (Administartor)session.getAttribute("administrator");
  boolean superUser = false;
  String msg = Utils.getSessionMessage(session);
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>DR - Admin - Super User</title>
    <link href='http://fonts.googleapis.com/css?family=Sansita+One|Chivo' rel='stylesheet' type='text/css' />
    <link href="../static/css/main.css" rel="stylesheet" type="text/css" />
    <link href="../static/css/admin.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
      #admin_table {width:100%; background-color:#BCDD11;}
      #admin_table td {background-color:#F1FAC0; padding:8px}
    </style>
	</head>
	<body>
    <jsp:include page="header.jsp" />
    <jsp:include page="menu.jsp" />
	
    <div id="main">
		  <h3>Register a new Administrator</h3>
			<ul id="errors" <%= msg.equals("") ? "style='display:none'" : "" %>>
         		 <li><%= msg %></li>
       		 </ul>
			<form action="../Registration" method="post">
        <input type="hidden" name="action" value="addAdmin" />
			  <dl>
				  <dt><label>user name</label>:</dt><dd><input type="text" name="username" id="username" /></dd>
					<dt><label>password</label>:</dt><dd><input type="password" name="password" id="password" /></dd>
					<dt><label>password again</label>:</dt><dd><input type="password" name="repassword" id="repassword" /></dd>
					<dt><label>name</label>:</dt><dd><input type="text" name="name" id="name" /></dd>
					<dt><label>phone number</label>:</dt><dd><input type="text" name="phoneNumber" id="phone" /></dd>
					<dt></dt><dd><button onclick="return validateReg()">Register</button></dd>
			  </dl>
			</form>
			<h3>Administrators</h3>
			<table id="admin_table">
				  <tr>
				   	<td>Username</td>
				   	<td>Password</td>
				   	<td>Name</td>
				   	<td>Phone number</td>
				   	<td>Delete</td>
				  </tr>
				  <% for(Administartor a : admins){
					    if (a.getId() == Administartor.getSuperUserId()) {
					    	continue;
					    } %>
				  <tr>
				  	<td><%= a.getStringField("username") %></td>
				  	<td><%= a.getStringField("password") %></td>
				  	<td><%= a.getName() %></td>
				  	<td><%= a.getStringField("phone_number") %></td>
				  	<td><a href="javascript:void(0)" onclick="test(<%= a.getId() %>, 'removeAdmin'); return false;">remove</a> </td>
				  </tr>
				  <% } %>
			</table>
		</div>
		<form action="ManageUsers" method="post">
			<input type="hidden" name="action" value="null" />
			<input type="hidden" name="admin_id" value="null"/>
		</form>

	  <script type="text/javascript">
	    function test(id, action){
	    	var ans=confirm("are you sure?");
			if (ans == true){
	      		oformElement = document.forms[1];
	      		oformElement.elements["admin_id"].value =id;
	      		oformElement.elements["action"].value = action;
	      		document.forms[1].submit();
			}
	    }
    </script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
    <script>
      function validateReg() {
    	  var username = $('#username').val();
    	  var password = $('#password').val();
    	  var repassword = $('#repassword').val();
    	  var name = $('#name').val();
    	  var phone = $('#phone').val();

    	  var msgs = {'username': 'Username should contain only letter and be 5-12 charachters long',
    			          'password': 'Password should contain only letter or number and be 5-12 charachters long',
    			          'password_again': 'Passwords did not match',
    			          'username_empty': 'Username is a mandatory field',
    			          'password_empty': 'Password is a mandatory field',
    			          'name_empty': 'Name is a mandtaory field',
    			          'name_too_long': 'Name should not be longer than 25 charachters',
    			          'phone': 'Phone must be number not be longer than 25 charachters'}
    	  var errors = [];
          if (!username) {
            errors.push(msgs['username_empty']);
          } else if (!username.match(/^[a-zA-Z]{5,12}$/)) {
            errors.push(msgs['username']);
          }
          if (!password) {
            errors.push(msgs['password_empty']);
          } else if (!password.match(/^[a-zA-Z0-9]{5,12}$/)) {
            errors.push(msgs['password']);
          } else if (password != repassword) {
        	  errors.push(msgs['password_again']);
          }
          if (!name) {
        	  errors.push(msgs['name_empty']);
          }
          if (name.length > 25) {
        	  errors.push(msgs['name_too_long']);
          }

          if (!phone.match(/^[0-9\-]{0,25}$/)) {
        	  errors.push(msgs['phone']);
          }
          
          if (errors.length == 0) {
        	  return true;
          }

          $('#errors li').remove();
          for (i in errors) {
        	  var li = $('<li></li>').html(errors[i])
        	  $('#errors').append(li);
          }
          $('#errors').show(0);
          return false;
      }
    </script>
  </body>
</html>