package cs236369.hw5.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Student extends Base {

	static String tableName = "students";

	//TODO table initialization
	@Override
	void setFieldTypes() {
		fieldsTypes.put("username", "string");
	    fieldsTypes.put("password", "string");
	    fieldsTypes.put("name", "string");
	    fieldsTypes.put("phone_number", "int");
	}

	public String getTableName() {
		return tableName;
	}
	public Student(ResultSet rs) {
		super(rs);
	}

	public Student() {
		super();
		key = "username";
	}

	public Student(int id) {
		super(id);
	}

	public String getName() {
		return getStringField("name");
	}

	public static Student authenticate(String username, String password) throws Exception {
		Connection connection = Utils.getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		try {
			prepStmt = connection.prepareStatement("SELECT * FROM students WHERE username = ? and password = ? LIMIT 1");
			prepStmt.setString(1, username);
			prepStmt.setString(2, password);
			System.out.println(prepStmt.toString());
			rs = prepStmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (rs.next()) {
			return new Student(rs);
		}

		throw new Exception();
	}

	public static Student[] GetByIds(int[] ids) throws SQLException {
		ResultSet rs = Utils.getTableRowsByIds(tableName, ids);
		ArrayList<Student> students = new ArrayList<Student>();
		do {
			students.add(new Student(rs));
		} while (!rs.isLast());
		Student[] arrayStudents = new Student[students.size()];
		return arrayStudents;
	}

	public void signToACourse(int course_id) {
		String update = "INSERT INTO courses_students (course_id, student_id) VALUES (" + course_id + ", " + getId() + ")";
		Utils.executeUpdate(update);
	}

	public ArrayList<Integer> getAvailableCoursesIds() {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		Course[] courses;
		try {
			courses = Course.getAll();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			courses = new Course[0];
			e1.printStackTrace();
		}
		for (Course course : courses) {
			try {
				if (isCourseAvailable(course)) {
					System.out.println("another avail " + course.getId());
					ids.add(course.getId());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ids;
	}

	public Session[] getSessions() throws SQLException {
		Course[] courses = getCourses();
		int[] ids = new int[courses.length];
		for (int i = 0; i < ids.length; ++i) {
			ids[i] = courses[i].getId();
		}

		return Session.getByCoursesIds(ids);
	}

	public Course[][] getTimeTable(int days_per_week, int hours_per_day) throws SQLException {
		Course[][] time_table = new Course[days_per_week][hours_per_day];
		for (int day = 0; day < days_per_week; ++day) {
			for (int hour = 0; hour < hours_per_day; ++ hour) {
				time_table[day][hour] = new Course();
			}
		}
		Course[] courses = getCourses();
		HashMap<Integer, Course> idsToCourses = new HashMap<Integer, Course>();
		for (Course course : courses) {
			System.out.println("insert to map: " + course.getName());
			idsToCourses.put(course.getId(), course);
		}
		for (Session session : getSessions()) {
			System.out.println("in session");
			if (session.getEndHour() >= hours_per_day || session.getDayOfWeek() >= days_per_week) {
				continue;
			}
			System.out.println("valid session");
			for (int hour = session.getStartHour(); hour <= session.getEndHour(); ++hour) {
				time_table[session.getDayOfWeek()][hour] = idsToCourses.get(session.getCourseId());
				System.out.println("day: " + session.getDayOfWeek() + "; hour: " + hour + "; course_id: " + session.getCourseId() + "; course_name: " + idsToCourses.get(session.getCourseId()).getName());
			}
		}
		return time_table;
	}

	public boolean isCourseAvailableById(int course_id) throws SQLException {
		Course course = new Course(course_id);
		return isCourseAvailable(course);
	}

	public boolean isCourseAvailable(Course course) throws SQLException {
		if (isRegisteredToCourse(course.getId())){
			return false;
		}

		if (course.getNumStudents() >= course.getCapacity()) {
			return false;
		}

		Course[] courses = getCourses();
		for (Course registered_course : courses) {
			if (Course.doCoursesConflict(course, registered_course)) {
				return false;
			}
		}

	    return true;
	}
	public boolean registerToCourse(int course_id) throws SQLException {

		ResultSet rs = Utils.executeQuery("SELECT * FROM courses_students WHERE student_id = " + getId()); //fix question marks
		ArrayList<Integer> ids = new ArrayList<Integer>();

		while(rs.next()) {
			ids.add(rs.getInt("course_id"));
		}

		if (ids.contains(course_id)) {
			return false;
		}
		if (!isCourseAvailableById(course_id)) {
			return false;
		}

		Utils.executeUpdate("INSERT INTO courses_students (course_id, student_id) VALUES (" + course_id + ", " + getId() + ")");

		Course course = new Course(course_id);
		if(isTimeTableValid() && course.getNumStudents() <= course.getCapacity()) {
			return true;
		}

		Utils.executeUpdate("DELETE FROM courses_students where course_id = " + course_id + " and student_id = " + getId());
		return false;
	}

	private boolean isTimeTableValid() throws SQLException {
		for (Course course1 : getCourses()) {
			for (Course course2: getCourses()) {
				if (!course1.equals(course2) && Course.doCoursesConflict(course1, course2)) {
					return false;
				}
			}
		}
		return true;
	}

	public Course[] getCourses() throws SQLException {
		ResultSet rs = Utils.executeQuery("SELECT * FROM courses_students WHERE student_id = " + getId()); //fix question marks
		ArrayList<Integer> ids = new ArrayList<Integer>();
		while(rs.next()) {
			ids.add(rs.getInt("course_id"));
		}
		int[] arrayIds = new int[ids.size()];
		for (int i=0; i < arrayIds.length; i++){
			arrayIds[i] = ids.get(i).intValue();
		}
		Course[] courses =  Course.GetByIds(arrayIds);
		return courses;
	}

	public boolean isRegisteredToCourse(int course_id) {
		ResultSet rs = Utils.executeQuery("SELECT * FROM courses_students WHERE student_id = " + getId() + " and course_id = " + course_id);
		try {
			rs.last();
			return rs.getRow() == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public void unregisterFromCourse(int course_id) {
		Utils.executeUpdate("DELETE FROM courses_students where course_id = " + course_id + " and student_id = " + getId());
	}
}
