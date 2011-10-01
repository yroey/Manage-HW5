package cs236369.hw5.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;

import cs236369.hw5.Logger;


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
		Connection conn = Utils.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM courses");
		Logger.log(ps.toString());
		ResultSet rs = ps.executeQuery();

		ArrayList<Course> courses = new ArrayList<Course>();
		while(rs.next()) {
			courses.add(new Course(rs));
		}
		Course[] arrayCourses = new Course[courses.size()];
		courses.toArray(arrayCourses);
		Utils.closeConnection(rs, ps, conn);
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

	public int getCredit() {
	  return getIntField("credit_points");
	}

	public static Course[] GetByIds(int[] ids) throws SQLException {
		if (ids == null || ids.length == 0){
			return new Course[0];
		}
		Connection conn = Utils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder stringIds = new StringBuilder();
		stringIds.append(ids[0]);
		for (int i = 1; i < ids.length; ++i)
			stringIds.append(", ").append(ids[i]);
		try {
			ps = conn.prepareStatement("SELECT * FROM courses WHERE id in (?)");
			ps.setString(1, stringIds.toString());
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<Course> courses = new ArrayList<Course>();
		while(rs.next()) {
			courses.add(new Course(rs));
		}
		Utils.closeConnection(rs, ps, conn);
		Course[] arrayCourses = new Course[courses.size()];
		courses.toArray(arrayCourses);
		return arrayCourses;
	}

	public Student[] getStudents() throws SQLException {
		Connection conn = Utils.getConnection();
		String query = "SELECT * FROM courses_students WHERE course_id = ?;";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, getId());
		Logger.log(ps.toString());
		ResultSet rs = ps.executeQuery();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		while(rs.next()) {
			ids.add(rs.getInt("course_id"));
		}
		int[] arrayIds = new int[ids.size()];
		for (int i=0; i < arrayIds.length; i++){
			arrayIds[i] = ids.get(i).intValue();
		}
		Utils.closeConnection(rs, ps, conn);
		return Student.GetByIds(arrayIds);
	}

	public Session[] getSessions() throws SQLException {
		Connection conn = Utils.getConnection();
		String query = "SELECT * FROM sessions WHERE course_id = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, getId());
		ResultSet rs = ps.executeQuery();
		ArrayList<Session> sessions = new ArrayList<Session>();
		while(rs.next()) {
			sessions.add(new Session(rs));
		}
		Session[] arraySessions = new Session[sessions.size()];
		sessions.toArray(arraySessions);
		Utils.closeConnection(rs, ps, conn);
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
		Connection conn = Utils.getConnection();
		String query = "SELECT count(*) FROM courses_students WHERE course_id = ?;";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, getId());
		Logger.log(ps.toString());
		ResultSet rs = ps.executeQuery();
		if (!rs.next()){
			Utils.closeConnection(rs, ps, conn);
			//ERROR
		}
		int ret = rs.getInt(1);
		Utils.closeConnection(rs, ps, conn);
		return ret;
	}

	public int getCapacity() {
		return getIntField("capacity");
	}

	public static Course[] searchCourses(String name, Integer[] ids) throws SQLException {
		Connection conn = Utils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM courses where name LIKE ?";
		if (ids.length != 0) {
			query += " and id in (";
			for (int i = 0; i < ids.length -1; ++i) {
				query += ids[i] +", ";
			}
			query += ids[ids.length - 1] + ")";
		}
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, "%" + name + "%");
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Utils.closeConnection(rs, ps, conn);
			return new Course[0];
		}
		ArrayList<Course> courses = new ArrayList<Course>();
		while(rs.next()) {
			courses.add(new Course(rs));
		}

		Utils.closeConnection(rs, ps, conn);
		Course[] arrayCourses = new Course[courses.size()];
		courses.toArray(arrayCourses);
		return arrayCourses;
	}
	public static String[] getGroups() {
		return null;
	}
}
