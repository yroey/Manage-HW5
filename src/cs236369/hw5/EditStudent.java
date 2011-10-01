package cs236369.hw5;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs236369.hw5.dal.Student;

/**
 * Servlet implementation class EditStudent
 */
public class EditStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditStudent() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		Student student = (Student)request.getSession(true).getAttribute("student");
		String result;
		String msg = "";
		if ("edit_details".equals(action)) {
			String username = request.getParameter("username");
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			student.setField("username", username);
			if (student.hasDuplicate()) {
				response.getWriter().write("{\"result\":0, \"msg\": \"Username already taken\"}");
			}

			student.setField("name", name);
			student.setField("phone_number", (phone));
		} else {  // Change Password
			String password = request.getParameter("password");
			student.setField("password", password);
		}

		if (student.update()) {
			result = "1";
		} else {
			// Reverting all fields to the ones stored in the DB.
			student = new Student(student.getId());
			request.getSession().setAttribute("student", student);
			result = "0";
			msg = "The were some errors in the details you've entered.";
		}
		response.getWriter().write("{\"result\": " + result + ", \"msg\": \"" + msg + "\"}");

	}

}
