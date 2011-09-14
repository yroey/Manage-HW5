/**
 *
 */
package cs236369.hw5.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Dennis
 *
 */
public class Administartor extends Base
{
	static String tableName = "administrator";

	/**
	 *
	 */
	public Administartor()
	{
		super();
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
		fieldsTypes.put("password", "int");
	}

	public int addNewCourse(int courseID, int group, String name,
			int capacity, int creditPoints,String description, int id){

		Connection connection = Utils.getConnection();

		String prepStmt = "INSERT INTO courses VALUES ( ? , ? , ? , ? , ? , ? , ? );";
		PreparedStatement ps = null;
		int rs = 0;
		try
		{
			ps = connection.prepareStatement(prepStmt);
			ps.setInt(1, courseID);
			ps.setInt(2, group);
			ps.setString(3, name);
			ps.setInt(4, capacity);
			ps.setInt(5, creditPoints);
			ps.setString(6, description);
			ps.setInt(7, id);
			System.out.println(ps.toString());
			rs = ps.executeUpdate();
			System.out.println(rs);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public static int removeCourse(int id){
		Connection connection = Utils.getConnection();
		String prepStmt = "DELETE FROM courses WHERE id=?;";
		PreparedStatement ps = null;
		int rs = 0;
		try
		{
			ps = connection.prepareStatement(prepStmt);
			ps.setInt(1, id);
			System.out.println(ps.toString());
			rs = ps.executeUpdate();
			System.out.println(rs);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public static Administartor authenticate(String username, String password)
	{
		Connection connection = Utils.getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		try {
			prepStmt = connection.prepareStatement("SELECT * FROM administrators WHERE username = ? and password = ? LIMIT 1");
			prepStmt.setString(1, username);
			prepStmt.setString(2, password);
			System.out.println(prepStmt.toString());
			rs = prepStmt.executeQuery();
			if (rs.next()) {
				return new Administartor(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
