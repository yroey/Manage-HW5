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
public class ManageCourse extends HttpServlet
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = (String)request.getSession().getAttribute("action");
		if (action.equals("Remove")){
			Administartor admin = (Administartor)request.getSession().getAttribute("administrator"); 
			String CourseId = (String) request.getSession().getAttribute("removeCourseId");
			Course course = new Course(Integer.parseInt(CourseId));
			if (!course.delete()){
				System.out.println("can't delete " + admin.getId());
			}
			response.sendRedirect("index.html");
		}
		else{
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Administartor admin = (Administartor)request.getSession().getAttribute("administrator");
		String action = (String)request.getSession().getAttribute("action");
		if (action.equals("Add")){
			String group_id = request.getParameter("group_id");
			String name = request.getParameter("name");
			String capacity = request.getParameter("capacity");
			String creditPoints = request.getParameter("credit points");
			String description = request.getParameter("course description");
			Course course = new Course();
			course.setField("group_id",  Integer.parseInt(group_id));
			course.setField("name", name);
			course.setField("capacity", Integer.parseInt(capacity));
			course.setField("credit_points", Integer.parseInt(creditPoints));
			course.setField("course_description", description);
			course.setField("creator_id", admin.getId());
			if (!course.save()){
				System.out.println("duplicate key " + name);
			}
		}
		else {
		}
		response.sendRedirect("index.html");
	}
}
