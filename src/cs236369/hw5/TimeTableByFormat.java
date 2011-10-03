package cs236369.hw5;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs236369.hw5.dal.Administartor;
import cs236369.hw5.dal.Student;
import cs236369.hw5.dal.Xslt;



/**
 * Servlet implementation class Test
 */
public class TimeTableByFormat extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimeTableByFormat() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String xsltId = request.getParameter("id");
		Student stud = (Student)request.getSession().getAttribute("student");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XSLTmanager.applyStyleSheet(stud.getId(), Integer.parseInt(xsltId), baos);
		String str = new String(baos.toByteArray());
		response.getWriter().write(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		Administartor admin = (Administartor)request.getSession().getAttribute("administrator");
		if (admin != null){
			if (action == null){
				String xsltContent = request.getParameter("content");
				String xsltName = request.getParameter("name");
				int uploaderId = ((Administartor)request.getSession().getAttribute("administrator")).getId();
				if (!Xslt.validate(xsltName)){
					Utils.setSessionMessage(request.getSession(true), "There was an error in the xslt upload form");
					response.sendRedirect("settings.jsp");
					return;
				}
				XSLTmanager.upload(xsltName, xsltContent, uploaderId);
				response.sendRedirect("settings.jsp");
				return;
			}
			else if (action.equals("removeXSLT")){
				String xsltId = request.getParameter("xslt_id");
				Xslt xslt = new Xslt(Integer.parseInt(xsltId));
				if (!xslt.delete()){
					System.out.println("can't delete " + Integer.parseInt(xsltId));
				}
				response.sendRedirect("settings.jsp");
				return;
			}
		}
		response.sendRedirect("index.jsp");
	}

}
