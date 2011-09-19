package cs236369.hw5;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs236369.hw5.dal.Course;
import cs236369.hw5.dal.Student;

/**
 * Servlet implementation class CourseSearch
 */

public class CourseSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Student student = (Student)request.getSession().getAttribute("student");
		String name = request.getParameter("name");
		boolean available = request.getParameter("available") == "1";
		Course[] courses = Course.search(name, available);
		request.setAttribute("courses", courses);

		ArrayList<Integer> available_courses_ids = student.getAvailableCoursesIds();

		request.setAttribute("abailable_courses_ids", available_courses_ids );
		request.getRequestDispatcher("/course_search.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Student student = (Student)request.getSession().getAttribute("student");
		int course_id = Integer.parseInt(request.getParameter("course_id"));
		boolean registration_success = false;
		try {
			registration_success = student.registerToCourse(course_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (registration_success) {
			response.getWriter().print("{\"result\":1}");
		} else {
			response.getWriter().print("{\"result\":0}");
		}
	}

}
