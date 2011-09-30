/**
 *
 */
package cs236369.hw5;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cs236369.hw5.dal.Administartor;
import cs236369.hw5.dal.Course;
import cs236369.hw5.dal.Session;

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
		String action = request.getParameter("action");
		Logger.log("action is: " + action);
		if (action == null){
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
			String startHour = request.getParameter("start_hour");
			String endHour = request.getParameter("end_hour");
			String day = request.getParameter("day_of_week");
			String length = request.getParameter("length");
		}
		else if (action.equals("addSession")){
			String startHour = request.getParameter("start_hour");
			String endHour = request.getParameter("end_hour");
			String day = request.getParameter("day_of_week");
			String length = request.getParameter("length");
			String courseId = request.getParameter("course_id");
			String groupId = request.getParameter("group_id");
			Course c = new Course(Integer.parseInt(courseId));
			Session[] sessions = null;
			try
			{
				sessions = c.getSessions();
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Session newSession = new Session();
			newSession.setField("start_hour", Integer.parseInt(startHour));
			newSession.setField("end_hour", Integer.parseInt(endHour));
			newSession.setField("day_of_week", Integer.parseInt(day));
			newSession.setField("length", Integer.parseInt(length));
			newSession.setField("course_id", Integer.parseInt(courseId));
			newSession.setField("group_id", Integer.parseInt(groupId));
			boolean conflict = false;
			for (Session s: sessions){
				conflict = Session.doSessionsConflict(newSession, s);
			}
			if (!conflict){
				if (!newSession.save()){
					System.out.println("duplicate key " + courseId);
				}
				
			}
			
			Logger.log(startHour + " " + endHour+ " " +day+ " " +length+ " " +courseId+ " " +groupId);
		}		
		else if (action.equals("remove")){ 
			String courseId = request.getParameter("course_id");
			Course course = new Course(Integer.parseInt(courseId));
			if (!course.delete()){
				System.out.println("can't delete " + Integer.parseInt(courseId));
			}
			//TODO remove registration for all students	
		}
		response.sendRedirect("coursesManagement.jsp");
	}
}
