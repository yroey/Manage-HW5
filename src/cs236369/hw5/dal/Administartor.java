/**
 *
 */
package cs236369.hw5.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cs236369.hw5.Logger;

/**
 * @author Dennis
 *
 */
public class Administartor extends Base
{
	public static String tableName = "administrators";
	public static int superAdminId = 0;
	/**
	 *
	 */
	public Administartor()
	{
		super();
	    key = "username";
	}

	/**
	 * @param id
	 */
	public Administartor(int id)
	{
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param rs
	 */
	public Administartor(ResultSet rs)
	{
		super(rs);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cs236369.hw5.dal.Base#getTableName()
	 */
	@Override
	public String getTableName()
	{
		return tableName;
	}

	/* (non-Javadoc)
	 * @see cs236369.hw5.dal.Base#setFieldTypes()
	 */
	@Override
	void setFieldTypes()
	{
	    fieldsTypes.put("username", "string");
	    fieldsTypes.put("password", "string");
	    fieldsTypes.put("name", "string");
	    fieldsTypes.put("phone_number", "int");
	}

	public static int removeCourse(int id){
		Connection conn = Utils.getConnection();
		String query = "DELETE FROM courses WHERE id=?;";
		PreparedStatement ps = null;
		try
		{
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			Logger.log(ps.toString());
			ps.executeUpdate();
			Utils.closeConnection(null, ps, conn);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}

	public static Administartor authenticate(String username, String password)
	{
		try {
			Connection conn = Utils.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM administrators WHERE username = ? and password = ? LIMIT 1");
			ps.setString(1, username);
			ps.setString(2, password);
			Logger.log(ps.toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Administartor admin = new Administartor(rs);
				Utils.closeConnection(rs, ps, conn);
				return admin;
			}
			Utils.closeConnection(rs, ps, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static int getSuperUserId(){
		return superAdminId;
	}
	
	public static Administartor[] getAll() throws SQLException{
		Connection conn = Utils.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM administrators");
		Logger.log(ps.toString());
		ResultSet rs = ps.executeQuery();
		ArrayList<Administartor> admins = new ArrayList<Administartor>();
		while(rs.next()) {
			admins.add(new Administartor(rs));
		}
		Administartor[] arrayCourses = new Administartor[admins.size()];
		admins.toArray(arrayCourses);
		Utils.closeConnection(rs, ps, conn);
		return arrayCourses;
	}
	public String getName() {
		return getStringField("name");
	}

}
