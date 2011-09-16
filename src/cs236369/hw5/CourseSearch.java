package cs236369.hw5;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs236369.hw5.dal.Course;
import cs236369.hw5.dal.Student;

/**
 * Servlet implementation class CourseSearch
 */
@WebServlet("/CourseSearch")
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
		// TODO Auto-generated method stub
	}

}
