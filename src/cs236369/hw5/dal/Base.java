package cs236369.hw5.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

public abstract class Base {
  protected HashMap<String, String> fieldsTypes;
  HashMap<String, Object> fieldsValues;
  String tableName;
  Base() {
    init();
  }

  Base(int id) {
    init();
    ResultSet rs = Utils.getTableRowById(tableName, id);
    try {
      setFieldsFromResultSet(rs);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  void init() {
    initFields();
    setTableName();
  }

  abstract void setTableName();

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

  public String getStringField(String name) {
    return (String)fieldsValues.get(name);
  }

  public void save() {

  }
}
