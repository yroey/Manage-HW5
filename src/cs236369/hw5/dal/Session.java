package cs236369.hw5.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import cs236369.hw5.Logger;


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

	public static Session[] getByCoursesIds(int[] courses_ids) throws SQLException {
		if (courses_ids.length == 0) {
			return new Session[0];
		}
		Connection conn = Utils.getConnection();
		String query = "SELECT * FROM " + table_name + " WHERE course_id in (";
		String ids = Integer.toString(courses_ids[0]);
		for (int i = 1; i < courses_ids.length; ++i) {
			ids += ", " + Integer.toString(courses_ids[i]);
		}

		query += ids + ")";
		ArrayList<Session> sessions = new ArrayList<Session>();
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				sessions.add(new Session(rs));
			}
			Utils.closeConnection(rs, ps, conn);
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
		if (session1.getDayOfWeek() != session2.getDayOfWeek()) {
			return false;
		}
		if ((session1.getStartHour() >= session2.getStartHour() &&
				session1.getStartHour() <= session2.getEndHour()) ||
				(session1.getEndHour() <= session2.getEndHour() &&
						session1.getEndHour() >= session2.getStartHour()) ||
						(session1.getStartHour() <= session2.getStartHour() &&
								session1.getEndHour() >= session2.getEndHour())) {
			return true;
		}
		return false;
	}

	public static ArrayList<Session> registerSessions(String sessions, Course course, Connection conn)
	{
		String delims = ";";
		String[] s = sessions.split(delims);
		ArrayList<Session>  allSessions = new ArrayList<Session>();
		for (String str : s){
			delims = " ";
			int startHour = Integer.parseInt(str.split(delims)[0])/7;
			int endHour = Integer.parseInt(str.split(delims)[1])/7;
			int day = Integer.parseInt(str.split(delims)[0])%7; 
			int length = endHour - startHour + 1;
			Session newSession = new Session();
			newSession.setField("start_hour", startHour);
			newSession.setField("end_hour", endHour);
			newSession.setField("day_of_week", day);
			newSession.setField("length", length);
			newSession.setField("course_id", course.getId());
			newSession.setField("group_id", course.getIntField("group_id"));
			if (!newSession.save(conn)){
				System.out.println("duplicate key " + course.getId());
			}	
			allSessions.add(newSession);
		}		
		return allSessions;
	}
	public boolean save(Connection conn) {
		
		if (!validate()) {
			return false;
		}
		if (duplicate(this.key, (String)fieldsValues.get(this.key), conn)){
			//TODO ERROR
			return false;
		}
		//Connection conn = Utils.getConnection();
		String prepStmt = "INSERT INTO " + getTableName() + " (" + fields.get(0);
		for (int i = 1 ; i < fields.size(); i++ ){
			prepStmt += " ," + fields.get(i);
		}
		prepStmt += ")";
		prepStmt += " VALUES ( ?";

		for (int i = 0 ; i < fieldsTypes.size() - 1 ; i++){
			prepStmt += ", ?";
		}
		prepStmt += ");";
		PreparedStatement ps = null;
		try
		{
			ps = conn.prepareStatement(prepStmt);
			Collection<String> ct = fieldsTypes.values();
			Iterator<String> itrT = ct.iterator();
			Collection<Object> cv = fieldsValues.values();
			Iterator<Object> itrV = cv.iterator();
			int i = 1;
			while(itrT.hasNext()){
				if(itrT.next().equals("string")){
					ps.setString(i,(String)itrV.next());
				}
				else{ //int
					ps.setInt(i, (Integer)itrV.next());
				}
				i++;
			}
			ps.executeUpdate();
			ps.close();
			//Utils.closeConnection(null, ps, conn);
			return true;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Utils.closeConnection(null, ps, conn);
		return false;
	}
	public boolean duplicate(String key, String newKey, Connection conn){
		//Connection conn = Utils.getConnection();
		String query = "SELECT * FROM " + getTableName() + " WHERE " +  key + " = ?;" ;
		ResultSet rs = null;
		try
		{
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, newKey);
			Logger.log(ps.toString());
			rs = ps.executeQuery();
			if (!rs.next()){
				ps.close();
				rs.close();
				return false;
			}
			ps.close();
			rs.close();
			return true;
			//Utils.closeConnection(rs, ps, conn);
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
