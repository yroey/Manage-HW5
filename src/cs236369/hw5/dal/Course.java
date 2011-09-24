package cs236369.hw5.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Course extends Base {

	public static String tableName = "courses";

	public String getTableName() {
		return tableName;
	}

	public Course(int id) {
		super(id);
	}

	public Course() {
		super();
		key = "name";
	}

	public Course(ResultSet rs) {
		super(rs);
	}

	public static Course[] getAll() throws SQLException{
		ResultSet rs = Utils.executeQuery("SELECT * FROM courses");
		ArrayList<Course> courses = new ArrayList<Course>();
		while(rs.next()) {
			courses.add(new Course(rs));
		}
		Course[] arrayCourses = new Course[courses.size()];
		courses.toArray(arrayCourses);
		return arrayCourses;
	}

	@Override
	void setFieldTypes() {
		fieldsTypes.put("group_id", "int");
		fieldsTypes.put("name", "string");
		fieldsTypes.put("capacity", "int");
		fieldsTypes.put("credit_points", "int");
		fieldsTypes.put("course_description", "string");
		fieldsTypes.put("creator_id", "int");
	}

	public String getName() {
		return getStringField("name");
	}

	public static Course[] GetByIds(int[] ids) throws SQLException {
		ResultSet rs = Utils.getTableRowsByIds(tableName, ids);
		if (rs == null){
			return new Course[0];
		}
		ArrayList<Course> courses = new ArrayList<Course>();
		while(rs.next()) {
			courses.add(new Course(rs));
		}
		Course[] arrayCourses = new Course[courses.size()];
		courses.toArray(arrayCourses);
		return arrayCourses;
	}

	public Student[] getStudents() throws SQLException {
		ResultSet rs = Utils.executeQuery("SELECT * FROM courses_students WHERE course_id = " + getId());
		ArrayList<Integer> ids = new ArrayList<Integer>();
		while(rs.next()) {
			ids.add(rs.getInt("course_id"));
		}
		int[] arrayIds = new int[ids.size()];
		for (int i=0; i < arrayIds.length; i++){
			arrayIds[i] = ids.get(i).intValue();
		}
		return Student.GetByIds(arrayIds);
	}

	public Session[] getSessions() throws SQLException {
		ResultSet rs = Utils.executeQuery("SELECT * FROM sessions WHERE course_id = " + getId());
		ArrayList<Session> sessions = new ArrayList<Session>();
		while(rs.next()) {
			sessions.add(new Session(rs));
		}
		Session[] arraySessions = new Session[sessions.size()];
		sessions.toArray(arraySessions);
		return arraySessions;
	}

	public static Course[] search(String name, boolean available) {
		try {
			return getAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Course[0];
	}

	public static boolean doCoursesConflict(Course course1, Course course2) throws SQLException {
		for (Session session1 : course1.getSessions()) {
			for (Session session2 : course2.getSessions()) {
				if (Session.doSessionsConflict(session1, session2)) {
					return true;
				}
			}
		}
		return false;
	}

	public int getNumStudents() throws SQLException {
		ResultSet rs = Utils.executeQuery("SELECT count(*) FROM courses_students WHERE course_id = " + getId());
		rs.next();
		return rs.getInt(1);
	}

	public int getCapacity() {
		return getIntField("capacity");
	}

}
