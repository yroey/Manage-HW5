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
	}

	public String getTableName() {
		return tableName;
	}
	public Student(ResultSet rs) {
		super(rs);
	}

	public Student() {
		super();
	}

	public Student(int id) {
		super(id);
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

	public void getAvailableCoursesIds() {

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
}
