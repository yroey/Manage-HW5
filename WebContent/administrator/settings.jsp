<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*"  import="cs236369.hw5.Utils"  %>
<%
  Administartor admin = (Administartor)session.getAttribute("administrator");
  Xslt[] xsltFiles = Xslt.getAll();
  String msg = Utils.getSessionMessage(session);
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>DR - Admin - Settings</title>
    <link href='http://fonts.googleapis.com/css?family=Sansita+One|Chivo' rel='stylesheet' type='text/css'>
    <link href="../static/css/main.css" rel="stylesheet" type="text/css" />
    <link href="../static/css/admin.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
      #xslt_table {width:100%; background-color:#BCDD11;}
      #xslt_table td {background-color:#F1FAC0; padding:8px}
    </style>
	</head>
	<body>
    <jsp:include page="header.jsp" />
    <jsp:include page="menu.jsp" />
    <div id="main">
	  <h3>Settings</h3>
			<form action="../Registration" method="post">
			  <input type="hidden" name="action" value="updateDetails" />
			   <ul id="errors" <%= msg.equals("") ? "style='display:none'" : "" %>>
         		 <li><%= msg %></li>
       		 </ul>
			  <dl>
					<dt><label>Username</label>:</dt><dd><input type="text" name="username" value="<%=admin.getStringField("username") %>" /></dd>
					<dt><label>Password</label>:</dt><dd><input type="password" name="password" value="<%=admin.getStringField("password") %>" /></dd>
					<dt><label>Name</label>:</dt><dd><input type="text" name="name" value="<%= admin.getStringField("name") %>" /></dd>
					<dt><label>Phone Number</label>:</dt><dd><input type="text" name="phoneNumber" value="<%= admin.getStringField("phone_number") %>" /></dd>
					<dt></dt><dd><input type="submit" value="Save" /></dd>
			  </dl>
			</form>
		  <h3 style="margin-top:20px">Manage Time Table XSLTs</h3>
		  <table id="xslt_table">
			  <tr>
			   	<td>Name</td>
			   	<td>Remove Xslt</td>
			  </tr>
			  <%for(Xslt xslt : xsltFiles){ %>
			  <tr>
			  	<td><%=xslt.getName() %></td>
			  	<td>
			  	  <%if (xslt.getUpId() == admin.getId()){%>
			   		  <a href="javascript:void(0)" onclick="test(<%= xslt.getId() %>, 'removeXSLT'); return false;">removeXslt</a> <% }else{ %>xslt file not manged by you<% } %>
			   	</td>
			  </tr>
        <% } %>
		</table>
		  <h3 style="margin-top:20px">Upload a new XSLT</h3>
		  <ul id="errors" <%= msg.equals("") ? "style='display:none'" : "" %>>
         		 <li><%= msg %></li>
       		 </ul>
       		 
			<form action="TimeTableByFormat" method="post">
					<label>name</label>: <input type="text" name="name" id="name"  /><br />
					<textarea name="content" rows="15" cols="50" ></textarea><br/>
					<dt></dt><dd><button onclick="return validateReg()">Upload</button></dd>
			</form>
			<script type="text/javascript">

		function test(id, action){
			var ans=confirm("are you sure?");
			if (ans == true){
				oformElement = document.forms[2];
				oformElement.elements["xslt_id"].value =id;
				oformElement.elements["action"].value = action;
				document.forms[2].submit();
			}
		}
	</script>
	<form action="TimeTableByFormat" method="post">
		<input type="hidden" name="action" value="null" />
		<input type="hidden" name="xslt_id" value="null"/>
	</form>
		</div>
			<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
	 <script>
   	   function validateReg() {
    	  var name = $('#name').val();
    	
    	  var msgs = {'name': 'Name should contain letters and be 1-12 charachters long'}
    	  var errors = [];
          if (!name) {
            errors.push(msgs['name']);
          } else if (!name.match(/^[a-zA-Z]{1,12}$/)) {
            errors.push(msgs['name']);
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