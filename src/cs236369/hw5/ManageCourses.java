/**
 *
 */
package cs236369.hw5;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs236369.hw5.dal.Administartor;
import cs236369.hw5.dal.Course;

/**
 * @author Dennis
 *
 */
public class ManageCourses extends HttpServlet
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Administartor admin = (Administartor)request.getSession().getAttribute("administrator");
		if (admin != null){
			String action = request.getParameter("action");
			Logger.log("action is: " + action);
			
			if (action.equals("addCourse")){
				String sessions = request.getParameter("sessions");
				String group_id = request.getParameter("group_id");
				String name = request.getParameter("name");
				String capacity = request.getParameter("capacity");
				String creditPoints = request.getParameter("credit_points");
				String description = request.getParameter("description");
				Course course = new Course();
				try{
					course.setField("group_id",  Integer.parseInt(group_id));
					course.setField("name", name);
					course.setField("capacity", Integer.parseInt(capacity));
					course.setField("credit_points", Integer.parseInt(creditPoints));
					course.setField("description", description);
					course.setField("creator_id", admin.getId());	
				}
				catch(NumberFormatException e){
					Utils.setSessionMessage(request.getSession(true), "There was an error in the course registration form");
				}
				if (!course.save(sessions)){
					Utils.setSessionMessage(request.getSession(true), "There was an error in the course registration form");
					Logger.log("duplicate name: " + name);
				}
				response.sendRedirect("coursesManagement.jsp");
			}
			else if (action.equals("remove")){ 
				String courseId = request.getParameter("course_id");
				Course course = new Course(Integer.parseInt(courseId));
				if (!course.delete()){
					System.out.println("can't delete " + Integer.parseInt(courseId));
				}
				response.sendRedirect("coursesManagement.jsp");
			}
		}
	}
}
