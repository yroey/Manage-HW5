package cs236369.hw5.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

public abstract class Base {
	protected LinkedHashMap<String, String> fieldsTypes;
	protected LinkedHashMap<String, Object> fieldsValues;
	protected LinkedList<String> fields;
	protected static String tableName;
	protected String key;
	static Class<?> cName;
	int id;

	Base() {
		init();
	}

	Base(int id) {
		init();
		this.id = id;
		Connection connection = Utils.getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		try {
			prepStmt = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
			prepStmt.setInt(1, id);
			rs = prepStmt.executeQuery();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

			Utils.closeConnection(rs, prepStmt, connection);
			return;
		}
		try {
			rs.next();
			setFieldsFromResultSet(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Utils.closeConnection(rs, prepStmt, connection);
	}

	public abstract String getTableName();

	Base(ResultSet rs) {
		init();
		try {
			id = rs.getInt("id");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			setFieldsFromResultSet(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void init() {
		initFields();
	}

	public String getTableNameIns() {
		return tableName;
	}


	final void initFields() {
		fieldsTypes = new LinkedHashMap<String, String>();
		fieldsValues = new LinkedHashMap<String, Object>();
		fields = new LinkedList<String>();
		setFieldTypes();
		for(Entry<String, String> entry : fieldsTypes.entrySet()) {
			String name = entry.getKey();
			if (!name.equals("id")){
				fields.add(name);
			}
			String type = entry.getValue();
			if (type.equals("string")) {
				fieldsValues.put(name, "");
			}
			if (type.equals("int")) {
				fieldsValues.put(name, 0);
			}
		}
	}

	abstract void setFieldTypes();

	public int getId() {
		return id;
	}

	void setFieldsFromResultSet(ResultSet rs) throws SQLException {
		for(Entry<String, String> entry : fieldsTypes.entrySet()) {
			String name = entry.getKey();
			String type = entry.getValue();
			Object value = null;
			if (type.equals("string")) {
				value = rs.getString(name);
			}
			if (type.equals("int")) {
				value = rs.getInt(name);
			}
			fieldsValues.put(name, value);
		}
	}

	public void setField(String name, Object value) {
		fieldsValues.put(name, value);
	}

	public int getIntField(String name) {
		return (Integer)fieldsValues.get(name);
	}

	public String getStringField(String name) {
		return (String)fieldsValues.get(name);
	}

	public boolean save() {
		Connection connection = Utils.getConnection();
		if (duplicate(this.key, (String)fieldsValues.get(this.key))){
			//TODO ERROR
			return false;
		}
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
		int rs = 0;
		try
		{
			ps = connection.prepareStatement(prepStmt);
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
			System.out.println(ps.toString());
			rs = ps.executeUpdate();
			System.out.println(rs);
			return true;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/* true if key with value newKey exists in the table*/
	public boolean duplicate(String key, String newKey){

		Connection connection = Utils.getConnection();
		String prepStmt = "SELECT * FROM " + getTableName() + " WHERE " + key + "='" + newKey + "';";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			ps = connection.prepareStatement(prepStmt);
			System.out.println(ps.toString());
			rs = ps.executeQuery();
			if (!rs.next()){
				ps.close();
				connection.close();
				return false;
			}
			//ps.close();
			//connection.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			 try {
                ps.close();
            } catch (SQLException sqlex) {
            }
            ps = null;
		}

		return true;
	}

	public boolean equal(Object other) {
		if (!(other instanceof Base)) {
			return false;
		}
		Base base = (Base)other;
		return base.getId() == getId();
	}

	public boolean delete(){
		Connection connection = Utils.getConnection();
		if (!duplicate("id", new Integer(id).toString())){
			return false;
		}
		String prepStmt = "DELETE FROM " + getTableName() + " WHERE id=" + id + ";";
		PreparedStatement ps = null;
		try
		{
			ps = connection.prepareStatement(prepStmt);
			System.out.println(ps.toString());
			ps.executeUpdate();
			//ps.close();
			//connection.close();
			return true;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			 try {
                 ps.close();
             } catch (SQLException sqlex) {
             }
             ps = null;
		}
		return false;
	}
}
