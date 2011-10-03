/**
 * 
 */
package cs236369.hw5.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import cs236369.hw5.Logger;

/**
 * @author Dennis
 *
 */
public class Xslt extends Base
{
	static String tableName = "xslt";

	/* (non-Javadoc)
	 * @see cs236369.hw5.dal.Base#getTableName()
	 */
	public Xslt() {
		super();
	}
	public Xslt(int id) {
		super(id);
	}
	public Xslt(ResultSet rs) {
		super(rs);
	}
	@Override
	public String getTableName() {
		return tableName;
	}

	/* (non-Javadoc)
	 * @see cs236369.hw5.dal.Base#setFieldTypes()
	 */
	@Override
	void setFieldTypes()
	{
		fieldsTypes.put("name", "string");
		fieldsTypes.put("uid", "int");
	}

	public String getName() {
		return getStringField("name");
	}

	public int getUpId() {
		return getIntField("uid");
	}
	public static Xslt[] getAll() throws SQLException{
		Connection conn = Utils.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM xslt");
		Logger.log(ps.toString());
		ResultSet rs = ps.executeQuery();
		ArrayList<Xslt> xslts = new ArrayList<Xslt>();
		while(rs.next()) {
			xslts.add(new Xslt(rs));
		}
		Xslt[] arrayCourses = new Xslt[xslts.size()];
		xslts.toArray(arrayCourses);
		Utils.closeConnection(rs, ps, conn);
		return arrayCourses;
	}
	public static boolean validate(String name) {
		
		if (!Pattern.matches("^[a-zA-Z]{1,12}$", name)) {
			return false;
		}
		return true;
	}
}
