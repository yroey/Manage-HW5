package cs236369.hw5;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs236369.hw5.dal.Student;

/**
 * Servlet Filter implementation class StudentFilter
 */
public class StudentFilter implements Filter {

    /**
     * Default constructor.
     */
    public StudentFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	  if (((HttpServletRequest) request).getSession().getAttribute("student") != null) {
		  Student session_student = (Student)((HttpServletRequest) request).getSession().getAttribute("student");
		  Student student = new Student(session_student.getId());
		  if (student != null) {
			  chain.doFilter(request, response); // Logged in, so just continue.
			  return;
		  }
  		}
  	   ((HttpServletResponse)response).sendRedirect("../login.jsp");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
}
