package cs236369.hw5.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Session extends Base {

	static String table_name = "sessions";
	@Override
	public String getTableName() {
		return table_name;
	}

	public Session(int id) {
		super(id);
	}

	public Session() {
		super();
	}

	public Session(ResultSet rs) {
		super(rs);
	}

	@Override
	void setFieldTypes() {
		fieldsTypes.put("start_hour", "int");
		fieldsTypes.put("end_hour", "int");
		fieldsTypes.put("day_of_week", "int");
		fieldsTypes.put("length", "int");
		fieldsTypes.put("course_id", "int");
		fieldsTypes.put("group_id", "int");
	}

	public static Session[] getByCoursesIds(int[] courses_ids) {
		if (courses_ids.length == 0) {
			return new Session[0];
		}
		String query = "SELECT * FROM " + table_name + " WHERE course_id in (";
		String ids = Integer.toString(courses_ids[0]);
		for (int i = 1; i < courses_ids.length; ++i) {
			ids += ", " + Integer.toString(courses_ids[i]);
		}

		query += ids + ")";
		ResultSet rs = Utils.executeQuery(query);
		ArrayList<Session> sessions = new ArrayList<Session>();
		try {
			while (rs.next()) {
				sessions.add(new Session(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Session[] arraySessions = new Session[sessions.size()];
		sessions.toArray(arraySessions);
		return arraySessions;
	}

	public int getDayOfWeek() {
		return getIntField("day_of_week");
	}

	public int getStartHour() {
		return getIntField("start_hour");
	}

	public int getEndHour() {
		return getIntField("end_hour");
	}

	public int getCourseId() {
		return getIntField("course_id");
	}

	public static boolean doSessionsConflict(Session session1, Session session2) {
		if ((session1.getStartHour() > session2.getStartHour() &&
				session1.getStartHour() < session2.getEndHour()) ||
			(session1.getEndHour() < session2.getEndHour() &&
				session1.getEndHour() > session2.getStartHour()) ||
			(session1.getStartHour() < session2.getStartHour() &&
				session1.getEndHour() > session2.getEndHour())) {
			return true;
		}
		return false;
	}
}
