package cs236369.hw5.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
		fieldsTypes.put("description", "string");
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
		Connection connection = Utils.getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		StringBuilder stringIds = new StringBuilder();
		stringIds.append(ids[0]);
		for (int i = 1; i < ids.length; ++i)
			stringIds.append(", ").append(ids[i]);
		try {
			prepStmt = connection.prepareStatement("SELECT * FROM courses WHERE id in (" + stringIds.toString() + ")");
			rs = prepStmt.executeQuery();
			//connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<Course> courses = new ArrayList<Course>();
		while(rs.next()) {
			courses.add(new Course(rs));
		}
		Utils.closeConnection(rs, prepStmt, connection);
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
			//ERROR
		}
		int ret = rs.getInt(1);
		Utils.closeConnection(rs, ps, conn);
		return ret;
	}

	public int getCapacity() {
		return getIntField("capacity");
	}

	static protected String[] getTokens(String query) {
		ArrayList<String> tokens = new ArrayList<String>();
		query = query.trim();
		int start_token = 0;
		int index = 0;
		boolean in_quote = false;
		while(index < query.length()) {
			if (query.substring(index, index + 1).equals("\"")) {
				if (in_quote) {
					tokens.add(query.substring(start_token, index));
					index++;
					start_token = index;
					in_quote = false;
				} else {
					if (start_token != index-1) {
						tokens.add(query.substring(start_token, index));
					}
					in_quote = true;
					start_token = ++index;
				}
				continue;
			}
			if (query.substring(index, index + 1).equals(" ") && !in_quote) {
				tokens.add(query.substring(start_token, index));
				start_token = ++index;
				continue;
			}
			++index;
		}
		if (start_token < index) {
			tokens.add(query.substring(start_token, index));
		}

		String[] arrayTokens = new String[tokens.size()];
		tokens.toArray(arrayTokens);
		return arrayTokens;
	}


	protected static String fillQuery(HashMap<String, ArrayList<String>> tag_values, ArrayList<String> int_fields) {
		String query = "";
		for (String tag : tag_values.keySet()) {
			ArrayList<String> values = tag_values.get(tag);
			System.out.println("tag: " + tag);
			query += "(";
			for(int i = 0; i < values.size(); ++i) {
				if(tag.equals("full_text")){
					query +=  " (MATCH(name, description) AGAINST('" + values.get(i).replace("'", "\'") + "')) ";
					continue;
				}
				query += "(";
				String[] tokens = getTokens(values.get(i).replace("'", "\'"));
				for (int j = 0; j < tokens.length; ++ j) {
					if (int_fields.contains(tag)) {
						double value;
						try {
							value = Double.parseDouble(tokens[j]);
						} catch(Exception e) {
							continue;
						}
						query += tag + "=" + value;
					} else  {
						query += tag + " LIKE '%" + tokens[j] + "%'";
					}
					if (j + 1 != tokens.length) {
						query += " AND ";
					}
				}
				query += ")";
				if (i + 1 != values.size()) {
					query += " OR ";
				}
			}
			query += ") AND";
		}
		return query + " 1 = 1 ";
	}

	public static Course[] searchCourses(HashMap<String, ArrayList<String>> tag_values, Integer[] ids) throws SQLException {
		Connection connection = Utils.getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		ArrayList<String> full_text_values = tag_values.get("full_text");
		String relevance = "(0 ";
		if (full_text_values != null) {
			for(String value: full_text_values) {
				relevance += "+ (MATCH(name, description) AGAINST('" + value.replace("'", "\'") + "')) ";
			}
		}
		relevance += " )";
		String query = "SELECT *,  " + relevance + " as relevance FROM courses WHERE ";
		ArrayList<String> int_fields = new ArrayList<String>();
		int_fields.add("credit_points");
		int_fields.add("group_id");
		int_fields.add("capacity");
		query += fillQuery(tag_values, int_fields);

		System.out.println("SEARCH QUERY IS: " + query);
		if (ids.length != 0) {
			query += " AND id IN (";
			for (int i = 0; i < ids.length -1; ++i) {
				query += ids[i] +", ";
			}
			query += ids[ids.length - 1] + ")";
		}
		query += " ORDER BY relevance DESC";
		System.out.println("FINAL SEARCH QUERY IS: " + query);
		try {
			prepStmt = connection.prepareStatement(query);
			rs = prepStmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Utils.closeConnection(rs, prepStmt, connection);
			return new Course[0];
		}
		ArrayList<Course> courses = new ArrayList<Course>();
		while(rs.next()) {
			courses.add(new Course(rs));
		}

		Utils.closeConnection(rs, prepStmt, connection);
		Course[] arrayCourses = new Course[courses.size()];
		courses.toArray(arrayCourses);
		return arrayCourses;
	}

	public static String[] getGroups() {
		return null;
	}
}
