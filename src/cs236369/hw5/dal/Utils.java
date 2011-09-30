package cs236369.hw5.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import cs236369.hw5.Logger;

public class Utils {
	static private boolean initialized = false;
	private static Context initCtx;
	private static Context envCtx;
	private static DataSource ds;
	private static String dbName = "manage"; //maybe insert into context file?

	public static void Init() throws NamingException, SQLException{
		initCtx = new InitialContext();
		envCtx = (Context) initCtx.lookup("java:comp/env");
		ds = (DataSource)envCtx.lookup("jdbc/manage");
	}

	public static synchronized Connection getConnection() {
		Connection conn  = null;
		try {
			if (initialized == false) {
				initialized = true;
				Init();
				initTables();
			}
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return conn;
	}

	public static ResultSet executeQuery(String query) {
		Connection connection = getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		try {
			prepStmt = connection.prepareStatement(query);
			rs = prepStmt.executeQuery();
			//connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public static void executeUpdate(String update) {
		Connection connection = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = connection.prepareStatement(update);
			prepStmt.executeUpdate();
			//connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnection(null, prepStmt, connection);
	}

	public static ResultSet getTableRowById(String tableName, int id) {
		Connection connection = getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		try {

			prepStmt = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
			prepStmt.setInt(1, id);
			rs = prepStmt.executeQuery();
			//connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public static void initTables(){
		String query = null;
		if (!tableExists("students")){
			query = "CREATE TABLE `" + dbName + "`.`students` ( "
			+ "id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, "
			+ "username VARCHAR(255) NOT NULL, "
			+ "password VARCHAR(10) NOT NULL, "
			+ "name VARCHAR(255) NOT NULL, "
			+ "phone_number INT(10) UNSIGNED NOT NULL, "
			+ "PRIMARY KEY (id)) engine=innodb";
			executeUpdate(query);
		}
		if (!tableExists("administrators")){
			query = "CREATE TABLE `" + dbName + "`.`administrators` ( "
			+ "id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, "
			+ "username VARCHAR(255) NOT NULL, "
			+ "password VARCHAR(10) NOT NULL, "
			+ "name VARCHAR(255) NOT NULL, "
			+ "phone_number INT(10) UNSIGNED NOT NULL, "
			+ "PRIMARY KEY (id)) engine=innodb;";
			executeUpdate(query);
		}
		if (!tableExists("courses")){
			query = "CREATE TABLE `" + dbName + "`.`courses` ( "
			+ "id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, "
			+ "group_id INT(10) UNSIGNED NOT NULL, "
			+ "name VARCHAR(255) NOT NULL, "
			+ "capacity INT(10) UNSIGNED NOT NULL, "
			+ "credit_points INT(10) UNSIGNED NOT NULL, "
			+ "description VARCHAR(10200) NOT NULL, "
			+ "creator_id INT(10) UNSIGNED NOT NULL, "
			+ "PRIMARY KEY (id)) engine=innodb;";
			executeUpdate(query);
		}
		if (!tableExists("courses_students")){
			query = "CREATE TABLE `" + dbName + "`.`courses_students` ( "
			+ "student_id INT(10) UNSIGNED NOT NULL, "
			+ "course_id INT(10) UNSIGNED NOT NULL) engine=innodb;";
			executeUpdate(query);
		}
		if (!tableExists("sessions")){
			query = "CREATE TABLE `" + dbName + "`.`sessions` ( "
			+ "id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, "
			+ "day_of_week INT(10) UNSIGNED NOT NULL, "
			+ "hour_slot INT(10) UNSIGNED NOT NULL, "
			+ "length INT(10) UNSIGNED NOT NULL, "
			+ "course_id INT(10) UNSIGNED NOT NULL, "
			+ "end_hour INT(10) UNSIGNED NOT NULL, "
			+ "start_hour INT(10) NULL DEFAULT NULL, "
			+ "group_id INT(10) NULL DEFAULT NULL, "
			+ "PRIMARY KEY (id)) engine=innodb;";
			executeUpdate(query);
		}
	}
	private static boolean tableExists(String tableName){
		boolean ret = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = Utils.getConnection();
			String query = "SELECT table_name "
				+ "FROM information_schema.tables "
				+ "WHERE table_schema = ? "
				+ "AND table_name = ?; ";
			ps = conn.prepareStatement(query);
			ps.setString(1,dbName);
			ps.setString(2,tableName);
			Logger.log(ps.toString());
			rs = ps.executeQuery();
			ret = rs.next();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(rs, ps, conn);
		}
		return ret;
	}

	public static void closeConnection(ResultSet rs, PreparedStatement ps, Connection conn)
	{
		try
		{
			if (rs != null) {
				rs.close();
			}
			ps.close();
			conn.close();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
