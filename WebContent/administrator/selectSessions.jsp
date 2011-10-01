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
		
			var table = new Array(70);
			var i = 0;
    		var selected = 0;
			
			for (i=0; i<70; i++)
			{
				table[i] = false;
			}
			
			function color1(obj, highlightcolor, textcolor, flag){
				if (flag == true){
					obj.style.color = textcolor;
            		obj.style.backgroundColor = highlightcolor;
				}
			}
			
			function getIndex(row,col){
				return (row*7 +col);
			}
			
            function click1(obj, highlightcolor, textcolor, col, row, flag){
            	if (flag == true){
            	}
            	else { if (obj.style.backgroundColor == highlightcolor) {
            					table[getIndex(row,col)] = false;
            					selected = selected - 1;
                				obj.style.backgroundColor = 'white';
                	 			obj.style.color = textcolor = 'black';
                			} else{
                				//alert("setting true :" + getIndex(row,col));
                				table[getIndex(row,col)] = true;
                				selected = selected + 1;
                				obj.style.color = textcolor;
                				obj.style.backgroundColor = highlightcolor;
                			}
            	}
            }
            function testt(){
            		var j = 0;
            		var startFlag = false;
					var start = 0;      
					var error = false;
					var res = "";
	            	for (i=0 ; i<7;  i++)
	    			{
	            		startFlag = false;
	            		for(j=0 ; j < 10; j++){
	            			if (table[7*j +i] == true){
		            			if (startFlag == false){
		            				startFlag = true;
		            				start = 7*j +i;
		            				end = 7*j +i;
		            				if (j == 9){ //first and last
		            					res = res + start + " " + end + ";";
		            					startFlag = false;
		            					start = 0;
			            				end = 0;
		            				}
		            			}
		            			else if (j == 9){//not first but last
		            				end = 7*j +i;
		            				res = res + start + " " + end + ";";
	            					startFlag = false;
	            					start = 0;
		            				end = 0;		            				
		            			}
		            		}
		            		else{ //else this ends
		            			if (startFlag == true){ //if flag is up then close
		            				end = 7*(j - 1 ) + i;
		            				startFlag = false;
		            				if ((Math.floor(end/7) - Math.floor(start/7))>3){
		            					error = true;
		            				} 
		            				res = res + start + " " + end + ";";
		            				start = 0;
		            				end = 0;
		            			}		            			
		            		}
	            		}	    					
	    			}
	            	oformElement = document.forms[0];
	    			oformElement.elements["sessions"].value = res;
	    			oformElement.elements["action"].value = "addCourse";
	    			if (error == true){
	    				alert("ERROR: a sessions is no more then 4 consecutive time slots in the same day");
	    			}
	    			else {
	    				if (selected > 0){
	    					document.forms[0].submit();
	    				}
		    			else{
		    				alert("ERROR: please select atlist one course session");
		    			}
	    			}
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
		<tr >
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
				<%if (flag){%><%=flagCourse.getName()%><%flag = false;} else {%><%} %>	</td>
			<% } %>
		</tr>
		<% } %>
		<table>
		<button type="submit" title="submit" onclick="testt();">submit sessions</button>	
	<form action="ManageCourses" method="post">
			<input type="hidden" name="action" value="addCourse" />
			<input type="hidden" name="sessions" value="null" />
			<input type="hidden" name=name value=<%=request.getParameter("name") %> />	
			<input type="hidden" name="group_id" value=<%=request.getParameter("group_id") %> />	
			<input type="hidden" name="capacity" value=<%=request.getParameter("capacity")  %> />	
			<input type="hidden" name="credit_points" value=<%=request.getParameter("credit_points") %> />	
			<input type="hidden" name="description" value=<%=request.getParameter("description") %> />	
	</form>	
</body>
<style type="text/css">td.true{background-color:yellow;}</style>
</html>