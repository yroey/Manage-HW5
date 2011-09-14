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

/**
 * @author Dennis
 *
 */
public class addCourse extends HttpServlet
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  Administartor admin = (Administartor)request.getSession().getAttribute("administrator");
		  String courseId = request.getParameter("id");
		  String group = request.getParameter("group");
		  String name = request.getParameter("name");
		  String capacity = request.getParameter("capacity");
		  String creditPoints = request.getParameter("credit points");
		  String description = request.getParameter("course description");
		  admin.addNewCourse(Integer.parseInt(courseId), Integer.parseInt(group), name, Integer.parseInt(capacity),
				  Integer.parseInt(creditPoints), description, admin.getId());
		  try {
		    //request.getSession(true).setAttribute("student", student);
		    response.sendRedirect("index.html");
		  } catch (Exception e) {
		    e.printStackTrace();
		    response.sendRedirect("login.jsp");
		  }
	}
}
