<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="cs236369.hw5.dal.*"
	import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<%
int days_per_week = 7;
int hours_per_day = 10;
String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednsday", "Thursday","Friday","Saturday"};
int groupId = Integer.parseInt(request.getParameter("group_id"));
Course[] courses = Course.getAll();
ArrayList<Course> temp = new ArrayList<Course>();
for (Course c : courses){	
	if (c.getIntField("group_id") == groupId){
			temp.add(c);
	}	
}
courses = new Course[temp.size()];
temp.toArray(courses);
boolean flag = false;
Course flagCourse = null;
%>
<head>
<h3>Please select sessions for the new course!</h3>
</head>
<body >
		<script type="text/javascript">
			function color1(obj, highlightcolor, textcolor, flag){
				if (flag == true){
					obj.style.color = textcolor;
            		obj.style.backgroundColor = highlightcolor;
				}
			}
            function click1(obj, highlightcolor, textcolor, col, row, flag){
            	if (flag == true){
            	}
            	else { if (obj.style.backgroundColor == highlightcolor) {
                				obj.style.backgroundColor = 'white';
                	 			obj.style.color = textcolor = 'black';
                			} else{
                				obj.style.color = textcolor;
                				obj.style.backgroundColor = highlightcolor;
                			}
            	}
            }
            function roll(obj, highlightcolor, textcolor){
              // obj.style.borderColor = highlightcolor;
              //onmouseover="roll(this, 'blue', 'white');" onmouseout="roll(this, 'white', 'blue');" 
            }
</script>
	<table border="2" cellspcing="1" id="time_table">
		<tr>
			<th>Hour \ Day</th>
			<% for (int day = 0; day < days_per_week; ++day) { %>
			<th><%= dayNames[day] %></th>
			<% } %>
		</tr>
		<% for (int hour = 0; hour < hours_per_day; ++hour) { %>
		<tr>
			<td><%= hour %></td>
			<% for (int day = 0; day < days_per_week; ++day) { %>
			<% for(Course c : courses){
    						 Session[] sessions = c.getSessions();
    						 for (Session s : sessions){
    	 							if (s.getDayOfWeek() == day && (s.getStartHour() <= hour && s.getEndHour() >= hour)){
    	 							flag = true;
    	 							flagCourse = c;
    	 							}
    						 }
     				}%>
			<td onclick="click1(this, 'blue', 'white', <%=day%>,<%=hour %>,<%=flag %> );" >
				<script type="text/javascript">
					color1(this,'green', 'white',<%=flag%>);
				</script>
				<%if (flag){%><%=flagCourse.getName()%><%flag = false;} else {%><%} %>	</td>
			<% } %>
		</tr>
		<% } %>
		<table>
		<button type="submit" title="submit" onclick="//TODO">submit sessions</button>	
</body>
</html>