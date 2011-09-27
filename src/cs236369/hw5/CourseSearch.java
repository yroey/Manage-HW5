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
		if (request.getParameter("id") != null) {
			try {
				int id = Integer.parseInt(request.getParameter("id"));
				Course course = new Course(id);
				request.setAttribute("course", course);
				request.getRequestDispatcher("/student/course.jsp").forward(request, response);
				return;
			}  catch(Exception e) {
				return;
			}
		}
		String name = request.getParameter("name");
		boolean available = "1".equals(request.getParameter("available"));
		boolean registered = "1".equals(request.getParameter("registered"));
		Course[] courses = new Course[0];
		ArrayList<Integer> available_courses_ids = null;
		if (registered) {
			try {
				courses = student.getCourses();
			} catch (SQLException e) {
				courses = new Course[0];
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			available_courses_ids = student.getAvailableCoursesIds();
			ArrayList<Integer> ids = new ArrayList<Integer>();
			if (available) {
				ids.addAll(available_courses_ids);
			}
			Integer[] arrayIds = new Integer[ids.size()];
			ids.toArray(arrayIds);
			try {
				if (available && available_courses_ids.size() == 0) {
					courses = new Course[0];
				} else {
					courses = Course.searchCourses(name, arrayIds);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setAttribute("courses", courses);


		request.setAttribute("abailable_courses_ids", available_courses_ids );
		request.getRequestDispatcher("/course_search.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Student student = (Student)request.getSession().getAttribute("student");
		int course_id = Integer.parseInt(request.getParameter("course_id"));
		boolean unregister = "1".equals(request.getParameter("unregister"));
		if (unregister) {
			student.unregisterFromCourse(course_id);
			return;
		}
		boolean registration_success = false;
		try {
			registration_success = student.registerToCourse(course_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (registration_success) {
			Course course = new Course(course_id);
			response.getWriter().print("{\"result\":1, \"name\": \"" + course.getName() + "\", \"id\": " + course_id +"}");
		} else {
			response.getWriter().print("{\"result\":0}");
		}
	}

}
