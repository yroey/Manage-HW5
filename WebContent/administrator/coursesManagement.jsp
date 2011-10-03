<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*" import="cs236369.hw5.Utils" %>
<%
  Course[] courses = Course.getAll();
  Administartor admin = (Administartor)session.getAttribute("administrator");
  String msg = Utils.getSessionMessage(session);
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title>DR - Admin - Courses</title>
    <link href='http://fonts.googleapis.com/css?family=Sansita+One|Chivo' rel='stylesheet' type='text/css' />
    <link href="../static/css/main.css" rel="stylesheet" type="text/css" />
    <link href="../static/css/admin.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
      #courses_table {width:100%; background-color:#BCDD11;}
      #courses_table td, #courses_table th {background-color:#F1FAC0; padding:8px}
    </style>
	</head>
	<body>
    <jsp:include page="header.jsp" />
    <jsp:include page="menu.jsp" />
	 <div id="main">
		<h2>Add a new Course</h2>
				<ul id="errors" <%= msg.equals("") ? "style='display:none'" : "" %>>
         		 	<li><%= msg %></li>
       		 	</ul>
				<form action="./selectSessions.jsp" method="post" >
				<dl>
						<dt><label>Name</label>:</dt><dd><input type="text" name="name" id="name"/></dd>
						<dt><label>group</label>:</dt><dd><input type="text" name="group_id" id="group_id"/></dd>
						<dt><label>Capacity</label>:</dt><dd><input type="text" name="capacity" id="capacity"/></dd>
						<dt><label>Credit Points</label>:</dt><dd><input type="text" name="credit_points" id="credit_points"/></dd>
						<dt><label>Course Description</label>:</dt><dd><textarea name="description" rows="8" cols="20" style="width:400px;" id="description" ></textarea></dd>
						<dt></dt><dd><button onclick="return validateReg()">Save</button></dd>
				</dl>
				</form>

		<h3>All Courses</h3>
		<table id="courses_table">
			  <tr>
			   	<th>Name</th>
			   	<th>Group</th>
			   	<th>Capacity</th>
			   	<th>Credit Points</th>
			   	<th>Manage</th>
			   	<th>remove course</th>
			  </tr>
			  <% for(Course c : courses) { %>
			  <tr>
			  	<td><%= c.getName() %></td>
			  	<td><%= c.getIntField("group_id") %></td>
			  	<td><%= c.getIntField("capacity") %></td>
			  	<td><%= c.getIntField("credit_points") %></td>
			  	<td><a href="viewCourseInfo.jsp?id=<%= c.getId() %>">view more info</a></td>
			  	<td>
			  	  <% if (c.getIntField("creator_id") == admin.getId()){  %>
			   			<a href="javascript:void(0)" onclick="test(<%= c.getId() %>, 'remove'); return false;">Remove Course</a>
			   	  <% }else{%>
			   	    course not managed by you
			   	  <% } %>
			   	</td>
			  </tr>
			  <% }%>
		</table>
   </div>
		<script type="text/javascript">
		function test(id, action){
			var ans=confirm("are you sure?");
			if (ans == true){
				oformElement = document.forms[1];
				oformElement.elements["course_id"].value =id;
				oformElement.elements["action"].value = action;
				document.forms[1].submit();
			}
		}
	</script>
	<form action="ManageCourses" method="post">
		<input type="hidden" name="action" value="null" />
		<input type="hidden" name="course_id" value="null"/>
	</form>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
	 <script>
   	   function validateReg() {
    	  var name = $('#name').val();
    	  var group_id = $('#group_id').val();
    	  var capacity = $('#capacity').val();
    	  var credit_points = $('#credit_points').val();
    	  var description = $('#description').val();

    	  var msgs = {'name': 'Course name should contain letters or numbers and be 1-32 charachters long',
    			          'group_id': 'Group id should  only be numbers and be 1-12 charachters long',
    			          'group_id_empty': 'Group id is a mandatory field',
    			          'capacity': 'Capacity should  only be numbers and be 1-12 charachters long',
    			          'capacity_empty': 'Capacity is a mandtaory field',
    			          'credit_points': 'Credit points should  only be numbers and be 1-12 charachters long',
    			          'credit_points_empty': 'Credit points  is a mandtaory field',
    			          'name_empty': 'Name is a mandtaory field',
    			          'name_too_long': 'Name should not be longer than 25 charachters',
    			          'name_empty': 'Coures name is a mandatory field',}
    	  var errors = [];
          if (!name) {
            errors.push(msgs['name_empty']);
          } else if (!name.match(/^[a-zA-Z0-9 ]{1,32}$/)) {
         	  alert(name);
            errors.push(msgs['name']);
          }
          if (!group_id) {
            errors.push(msgs['group_id_empty']);
          } else if (!group_id.match(/^[0-9]{1,12}$/)) {
            errors.push(msgs['group_id']);
          }
          if (!capacity) {
              errors.push(msgs['capacity_empty']);
            } else if (!capacity.match(/^[0-9]{1,12}$/)) {
              errors.push(msgs['capacity']);
            }
          if (!credit_points) {
              errors.push(msgs['credit_points_empty']);
            } else if (!credit_points.match(/^[0-9]{1,12}$/)) {
              errors.push(msgs['credit_points']);
            }
          if (name.length > 25) {
        	  errors.push(msgs['name_too_long']);
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