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
		String action = (String)request.getSession().getAttribute("action");
		if (action.equals("addStudent")){
			Student stud = new Student();
			stud.setField("username",  username);
			stud.setField("password", password);
			stud.setField("name", name);
			stud.setField("phone_number", Integer.parseInt(phoneNumber));
			stud.save();
		}
		else if(action.equals("updateDetails")){
			String id = request.getParameter("id");
			Administartor admin = new Administartor(Integer.parseInt(id));
			admin.setField("username", username);
			admin.setField("password", password);
			admin.setField("name", name);
			admin.setField("phone_number", Integer.parseInt(phoneNumber));
			admin.update();
		}
		else if(action.equals("addAdmin")){
			//administrator
			Administartor admin = new Administartor();
			admin.setField("username", username);
			admin.setField("password", password);
			admin.setField("name", name);
			admin.setField("phone_number", Integer.parseInt(phoneNumber));
			admin.save();			
		}
		response.sendRedirect("login.jsp");
	}
}
