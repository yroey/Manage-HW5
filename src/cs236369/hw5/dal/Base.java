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
      prepStmt = connection.prepareStatement("SELECT * FROM " + getTableName() + " WHERE id = ?");
      prepStmt.setInt(1, id);
      rs = prepStmt.executeQuery();
    } catch (SQLException e1) {
      e1.printStackTrace();

      Utils.closeConnection(rs, prepStmt, connection);
      return;
    }
    try {
      rs.next();
      setFieldsFromResultSet(rs);
    } catch (SQLException e) {
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
      e1.printStackTrace();
    }
    try {
      setFieldsFromResultSet(rs);
    } catch (SQLException e) {
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

  public boolean validate(){
    return false;
  }

  public boolean save() {
    if (!validate()) {
      return false;
    }
    if (duplicate(this.key, (String)fieldsValues.get(this.key))){
      return false;
    }
    String query;
    if (getId() == 0) {
      query = "INSERT INTO " + getTableName() + " (" + fields.get(0);
      for (int i = 1 ; i < fields.size(); i++ ){
        query += " ," + fields.get(i);
      }
      query += ")";
      query += " VALUES ( ?";

      for (int i = 0 ; i < fieldsTypes.size() - 1 ; i++){
        query += ", ?";
      }
      query += ");";
    } else {
      query = "UPDAET " + getTableName() + " SET ";
      for (int i = 0 ; i < fields.size(); i++){
        query += fields.get(i) + " = ?";
        if (i < fields.size() - 1) {
          query += ", ";
        }
      }
    }

    Connection connection = Utils.getConnection();
    PreparedStatement ps = null;
    int rs = 0;
    try {
      ps = connection.prepareStatement(query);
      Collection<String> ct = fieldsTypes.values();
      Iterator<String> itrT = ct.iterator();
      Collection<Object> cv = fieldsValues.values();
      Iterator<Object> itrV = cv.iterator();
      int i = 1;
      while (itrT.hasNext()) {
        if(itrT.next().equals("string")){
          ps.setString(i,(String)itrV.next());
        }
        else{ //int
          try {
            ps.setInt(i, (Integer)itrV.next());
          } catch (Exception e) {
            ps.setNull(i, java.sql.Types.INTEGER);
          }
        }
        i++;
      }
      System.out.println(ps.toString());
      rs = ps.executeUpdate();
      System.out.println(rs);
      System.out.flush();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    } finally {
      Utils.closeConnection(null, ps, connection);
    }
  }

  /* true if key with value newKey exists in the table*/
  public boolean duplicate(String key, String newKey){

    Connection connection = Utils.getConnection();
    String prepStmt = "SELECT * FROM " + getTableName() + " WHERE " + key + "='" + newKey + "';";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = connection.prepareStatement(prepStmt);
      System.out.println(ps.toString());
      rs = ps.executeQuery();
      if (!rs.next()){
        ps.close();
        connection.close();
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      Utils.closeConnection(null, ps, connection);
    }

    return true;
  }

  public boolean equals(Object other) {
    if (!(other instanceof Base)) {
      return false;
    }
    Base base = (Base)other;
    return base.getId() == getId();
  }

  public boolean delete(){
    Connection connection = Utils.getConnection();
    String prepStmt = "DELETE FROM " + getTableName() + " WHERE id=" + id + ";";
    PreparedStatement ps = null;
    try
    {
      ps = connection.prepareStatement(prepStmt);
      ps.executeUpdate();
      return true;
    }catch (SQLException e) {
      e.printStackTrace();
    }
    finally{
      Utils.closeConnection(null, ps, connection);
    }
    return false;
  }
}
