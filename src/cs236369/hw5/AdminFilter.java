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

import cs236369.hw5.dal.Administartor;

/**
 * Servlet Filter implementation class adminFilter
 */
public class AdminFilter implements Filter {

    /**
     * Default constructor.
     */
    public AdminFilter() {
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
		  if (((HttpServletRequest) request).getSession().getAttribute("administrator") != null) {
			  Administartor session_admin = (Administartor)((HttpServletRequest) request).getSession().getAttribute("administrator");
			  Administartor admin = new Administartor(session_admin.getId());
			  if (admin != null) {
				  chain.doFilter(request, response); // Logged in, so just continue.
				  return;
			  }
	  		}
	  	   ((HttpServletResponse)response).sendRedirect("../admin_login.jsp");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}





