/**
 *
 */
package cs236369.hw5;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cs236369.hw5.dal.Administartor;
import cs236369.hw5.dal.Course;
import cs236369.hw5.dal.Session;
import cs236369.hw5.dal.Utils;

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
		if (action.equals("addCourse")){
			String sessions = request.getParameter("sessions");
			String group_id = request.getParameter("group_id");
			String name = request.getParameter("name");
			String capacity = request.getParameter("capacity");
			String creditPoints = request.getParameter("credit_points");
			String description = request.getParameter("description");
			Course course = new Course();
			course.setField("group_id",  Integer.parseInt(group_id));
			course.setField("name", name);
			course.setField("capacity", Integer.parseInt(capacity));
			course.setField("credit_points", Integer.parseInt(creditPoints));
			course.setField("description", description);
			course.setField("creator_id", admin.getId());
			if (!course.save()){
				Logger.log("duplicate name: " + name);
			}
			Connection conn = Utils.getConnection();
			PreparedStatement ps;
			try
			{
				ps = conn.prepareStatement("SELECT * FROM courses WHERE name = ?");
				ps.setString(1, name);
				ResultSet rs = ps.executeQuery();
				rs.next();
				int id = rs.getInt("id");
				Utils.closeConnection(rs, ps, conn);
				course = new Course(id);				
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Session.registerSessions(sessions, course.getId(), Integer.parseInt(group_id));
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
