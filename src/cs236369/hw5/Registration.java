package cs236369.hw5;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs236369.hw5.dal.Administartor;
import cs236369.hw5.dal.Student;

/**
 * Servlet implementation class Authentication
 */
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Registration() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String phoneNumber = request.getParameter("phoneNumber");
		String action = request.getParameter("action");
		if (action.equals("addStudent")){
			Student stud = new Student();
			stud.setField("username",  username);
			stud.setField("password", password);
			stud.setField("name", name);
			stud.setField("phone_number", phoneNumber);

			if (!stud.save()) {
				Utils.setSessionMessage(request.getSession(true), "There was an error in the registration form");
				response.sendRedirect("registration.jsp");
				return;
			}
			stud = Student.getByUsername(stud.getStringField("username"));
			if (stud == null) {
				Utils.setSessionMessage(request.getSession(true), "Some error accrued, please try again");
				response.sendRedirect("registration.jsp");
				return;
			}
			if (stud.hasDuplicate()) {
				stud.delete();
				Utils.setSessionMessage(request.getSession(true), "Username is already taken");
				response.sendRedirect("registration.jsp");
				return;
			}
			request.getSession(true).setAttribute("student", stud);
			response.sendRedirect("student/index.jsp");
		}
		else if(action.equals("updateDetails")){
			Administartor admin = (Administartor)request.getSession().getAttribute("administrator");
			if (admin != null){
				admin.setField("username", username);
				admin.setField("password", password);
				admin.setField("name", request.getParameter("updated_name"));
				admin.setField("phone_number", phoneNumber);
				if (!admin.update()){
					Utils.setSessionMessage(request.getSession(true), "problem with the update form");
					response.sendRedirect("administrator/settings.jsp");
					return;
				}
				if (admin.hasDuplicate()) {
					admin.delete();
					Utils.setSessionMessage(request.getSession(true), "Username is already taken");
					response.sendRedirect("administrator/settings.jsp");
					return;
				}
				response.sendRedirect("administrator/settings.jsp");
			}
		}
		else if(action.equals("addAdmin")){
			Administartor administrator = (Administartor)request.getSession().getAttribute("administrator");
			if (administrator != null && administrator.getId() == Administartor.getSuperUserId()){
				Administartor admin = new Administartor();
				admin.setField("username", username);
				admin.setField("password", password);
				admin.setField("name", name);
				admin.setField("phone_number", phoneNumber);
				if (!admin.save()) {
					Utils.setSessionMessage(request.getSession(true), "There was an error in the registration form");
					response.sendRedirect("administrator/superUserManagemant.jsp");
					return;
				}
				if (admin.hasDuplicate()) {
					admin.delete();
					Utils.setSessionMessage(request.getSession(true), "Username is already taken");
					response.sendRedirect("administrator/superUserManagemant.jsp");
					return;
				}
				response.sendRedirect("administrator/index.jsp");
				return;
			}
			response.sendRedirect("administrator/index.jsp");
		}
	}
}
