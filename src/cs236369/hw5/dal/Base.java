package cs236369.hw5.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

public abstract class Base {
  protected HashMap<String, String> fieldsTypes;
  static HashMap<String, Object> fieldsValues;
  protected static String tableName;
  static Class<?> cName;
  int id;
  Base() {
    init();
  }

  Base(int id) {
    init();
    this.id = id;
    ResultSet rs = Utils.getTableRowById(getTableName(), id);
    try {
      rs.next();
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

  public abstract String getTableName();
  Base(ResultSet rs) {
    init();
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
    fieldsTypes = new HashMap<String, String>();
    fieldsValues = new HashMap<String, Object>();
    setFieldTypes();
    for(Entry<String, String> entry : fieldsTypes.entrySet()) {
      String name = entry.getKey();
      String type = entry.getValue();
      if (type == "string") {
        fieldsValues.put(name, "");
      }
      if (type == "int") {
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
      if (type == "string") {
        value = rs.getString(name);
      }
      if (type == "int") {
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

  public static String getStringField(String name) {
    return (String)fieldsValues.get(name);
  }

  public void save() {

  }
}
