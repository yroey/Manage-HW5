<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" %>
<%
  Administartor admin = (Administartor)session.getAttribute("administrator");
  Xslt[] xsltFiles = Xslt.getAll();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>DR - Admin - Settings</title>
    <link href='http://fonts.googleapis.com/css?family=Sansita+One|Chivo' rel='stylesheet' type='text/css'>
    <link href="../static/css/main.css" rel="stylesheet" type="text/css" />
    <link href="../static/css/admin.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
    <jsp:include page="header.jsp" />
    <jsp:include page="menu.jsp" />
    <div id="main">
	  <h3>Settings</h3>
			<form action="../Registration" method="post">
			  <input type="hidden" name="action" value="updateDetails" />
					<label>Username</label>: <input type="text" name="username" value="<%=admin.getStringField("username") %>" /><br />
					<label>Password</label>: <input type="password" name="password" value="<%=admin.getStringField("password") %>" /><br />
					<label>Name</label>: <input type="text" name="name" value="<%= admin.getStringField("name") %>" /><br />
					<label>Phone Number</label>: <input type="text" name="phoneNumber" value="<%= admin.getStringField("phone_number") %>" /><br />
					<input type="submit" value="Save" />
			</form>
		  <h3>manage Xsl database</h3>
		  <table border="1">
			  <tr>
			   	<td>name</td>
			   	<td>remove Xslt</td>
			  </tr>
			  <%for(Xslt xslt : xsltFiles){ %>
			  <tr>
			  	<td><%=xslt.getName() %></td>	
			  	<td> <%if (xslt.getUpId() == ((Administartor)session.getAttribute("administrator")).getId()){%>
			   			<a href="javascript:void(0)" onclick="test(<%=new Integer(xslt.getId()).toString()%>, 'removeXSLT'); return false;">removeXslt</a> <% }else{%>xslt file not manged by you<%} %> </td>
			   			<% }%>
			  </tr>	  
		</table>
		  <h3>upload Xsl content for the timetable design</h3>
			<form action="TimeTableByFormat" method="post">
					<label>name</label>: <input type="text" name="name"  /><br />
					<textarea name="content" rows="15" cols="50" ></textarea><br/>
					<input type="submit" value="upload" />
			</form>
			<script type="text/javascript">
			
		function test(id, action){
			oformElement = document.forms[2];
			oformElement.elements["xslt_id"].value =id;
			oformElement.elements["action"].value = action;
			document.forms[2].submit();
		}
	</script>
	<form action="TimeTableByFormat" method="post">
		<input type="hidden" name="action" value="null" />
		<input type="hidden" name="xslt_id" value="null"/>	
	</form>	
		</div>
  </body>
</html>