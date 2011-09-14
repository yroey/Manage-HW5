package cs236369.hw5;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cs236369.hw5.dal.Utils;

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
		  String type = request.getParameter("type");
		  System.out.println("creating an account of type " + type);
		  try {
			if (type.equals("student")){
			    Utils.addStudentAccount(username, password, name, Integer.parseInt(phoneNumber));
			    response.sendRedirect("login.jsp");
			}
			else{
				//administrator
				 Utils.addAdministratorAccount(username, password, name, Integer.parseInt(phoneNumber));
				 response.sendRedirect("login.jsp");
			}
		  } catch (Exception e) {
		    e.printStackTrace();
		    response.sendRedirect("login.jsp");
		  }
	}
}
