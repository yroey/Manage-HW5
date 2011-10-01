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
import cs236369.hw5.dal.Student;

/**
 * @author Dennis
 *
 */
public class ManageUsers extends HttpServlet
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = (String)request.getParameter("action");
		if (action.equals("removeStud")){ 
			String studentId = (String) request.getParameter("student_id");
			Student s = new Student(Integer.parseInt(studentId));
			if (!s.delete()){
				System.out.println("can't delete " + Integer.parseInt(studentId));
			}
			response.sendRedirect("studentManagemant.jsp");
		}
		else if (action.equals("removeAdmin")){ 
			String adminId = (String) request.getParameter("admin_id");
			Administartor s = new Administartor(Integer.parseInt(adminId));
			if (!s.delete()){
				System.out.println("can't delete " + Integer.parseInt(adminId));
			}
			response.sendRedirect("superUserManagemant.jsp");
		}
	}
}
