package cs236369.hw5.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Utils {
  static private boolean initialized = false;
  static private Connection connection;
  static  void Init() throws NamingException, SQLException{
    Context initCtx = new InitialContext();
    Context envCtx = (Context) initCtx.lookup("java:comp/env");
    DataSource ds = (DataSource)envCtx.lookup("jdbc/manage");
    connection = ds.getConnection();
    initialized = true;
  }

  public static synchronized Connection getConnection() {

    if (initialized == true) {
      return connection;
    }

    try {
      Init();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }

    return connection;
  }

  static ResultSet executeQuery(String query) {
    connection = getConnection();
    PreparedStatement prepStmt = null;
    ResultSet rs = null;
    try {
      prepStmt = connection.prepareStatement(query);
      rs = prepStmt.executeQuery();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return rs;
  }

  static void executeUpdate(String update) {
    connection = getConnection();
    PreparedStatement prepStmt = null;
    try {
      prepStmt = connection.prepareStatement(update);
      prepStmt.executeUpdate();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  static ResultSet getTableRowById(String tableName, int id) {
    connection = getConnection();
    PreparedStatement prepStmt = null;
    ResultSet rs = null;
    try {
      prepStmt = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
      prepStmt.setInt(1, id);
      rs = prepStmt.executeQuery();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return rs;
  }

  static ResultSet getTableRowsByIds(String tableName, int[] ids) {
    connection = getConnection();
    PreparedStatement prepStmt = null;
    ResultSet rs = null;
    StringBuilder stringIds = new StringBuilder();
    stringIds.append(ids[0]);
    for (int i = 1; i < ids.length; ++i)
      stringIds.append(", ").append(ids[i]);
    try {
      prepStmt = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id in (" + stringIds.toString() + ")");
      rs = prepStmt.executeQuery();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return rs;
  }
}
