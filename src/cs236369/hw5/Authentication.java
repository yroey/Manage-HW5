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
public class Authentication extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authentication() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean logout = "1".equals(request.getParameter("logout"));
		boolean admin = "1".equals(request.getParameter("admin"));

		if (logout) {
			if (admin) {
				request.getSession(true).removeAttribute("administrator");
			} else {
				request.getSession(true).removeAttribute("student");
			}
			response.sendRedirect("");
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String username = request.getParameter("username");
		  String password = request.getParameter("password");
		  String type = request.getParameter("type");
		  System.out.println(type);
		  try {
			if (type.equals("student")){
			    Student student = Student.authenticate(username, password);
			    if (student != null){
				    request.getSession(true).setAttribute("student", student);
				    response.sendRedirect("student/index.jsp");
				    return;
			    }
			}
			else{
				//administrator
				 Administartor admin = Administartor.authenticate(username, password);
				 if (admin != null){
					 request.getSession(true).setAttribute("administrator", admin);
					 response.sendRedirect("administrator/index.jsp");
					 return;
				 }
			}
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
      Utils.setSessionMessage(request.getSession(true), "Username or password were wrong");
      response.sendRedirect(type.equals("student") ? "login.jsp" : "admin_login.jsp");
	}
}
