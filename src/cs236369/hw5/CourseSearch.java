package cs236369.hw5;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
		String q = request.getParameter("q");
		boolean available = "1".equals(request.getParameter("available"));
		boolean registered = "1".equals(request.getParameter("registered"));
		Course[] courses = new Course[0];
		ArrayList<Integer> available_courses_ids = null;
		System.out.println("Course search");
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
				System.out.println("is avail: " + available);
				if (available && available_courses_ids.size() == 0) {
					courses = new Course[0];
				} else {
					courses = Course.searchCourses(parseQuery(q == null ? "" : q), arrayIds);
				}
			} catch (SQLException e) {
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

	protected HashMap<String, ArrayList<String>> parseQuery(String query) {
		String[] tagNames = {"name", "credit_points", "group_id", "description", "full_text", "capacity"};
		HashMap<String, ArrayList<String>> tag_values = new HashMap<String, ArrayList<String>>();

		// get non tagged text
		int non_tagged_end_index = 0;
		for (int i = 0; i < query.length(); ++i) {
			if (query.substring(i, i+1).equals(":")) {

				non_tagged_end_index = query.lastIndexOf(" ", i);
				if (non_tagged_end_index == -1) {
					non_tagged_end_index = 0;
				}
				break;
			}
			non_tagged_end_index = i + 1;
		}

		if (non_tagged_end_index != 0) {
			String tag = "full_text";
			String value = query.substring(0, non_tagged_end_index);
			query = query.substring(non_tagged_end_index);
			ArrayList<String> tag_value = new ArrayList<String>();
			tag_value.add(value);
			tag_values.put(tag, tag_value);
			System.out.println("non tagged: " + value + "; query: " + query);

		}

		while(!query.equals("")) {
			String[] next_tag = nextTag(query, tagNames);
			String tag = next_tag[0];
			String value = next_tag[1];
			query = next_tag[2];
			if (!isValidTag(tag, tagNames)) {
				continue;
			}

			if (tag_values.get(tag) != null) {
				tag_values.get(tag).add(value);
			} else {
				ArrayList<String> tag_value = new ArrayList<String>();
				tag_value.add(value);
				tag_values.put(tag, tag_value);
			}
			System.out.println("Tag: " + next_tag[0] + "; Value: " + next_tag[1] + "; New Query: " + next_tag[2]);
		}
		return tag_values;
	}

	private boolean isValidTag(String tag, String[] tagNames) {
		for (int i = 0; i < tagNames.length; ++i) {
			if (tag.equals(tagNames[i])) {
				return true;
			}
		}
		return false;
	}

	protected String[] nextTag(String query, String[] tagNames) {
		int tag_end_index = query.indexOf(":");
		if (tag_end_index < 0) {
			String[] return_values = new String[3];
			return_values[0] = "full_text";
			return_values[1] = query;
			return_values[2] = "";
			return return_values;
		}

		String tag = query.substring(0, tag_end_index);

		String value;
		String new_query = query.substring(tag_end_index+1);
		int next_tag_end_index = new_query.indexOf(":");
		if (next_tag_end_index == -1) {
			value = new_query;
			new_query = "";
		} else {
			int new_tag_begin_index = new_query.lastIndexOf(" ", next_tag_end_index);
			if (new_tag_begin_index == -1) {
				value = "";
			} else {
				value = new_query.substring(0, new_tag_begin_index);
				new_query = new_query.substring(new_tag_begin_index + 1);
			}
		}

		String[] return_values = new String[3];
		return_values[0] = tag.trim();
		return_values[1] = value.trim();
		return_values[2] = new_query.trim();

		return return_values;

	}

}
