package cs236369.hw5.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Utils {
  static private boolean initialized = false;
  static private Connection connection;
  static  void Init() throws ClassNotFoundException {

    Statement stmt  = null;
    String password = "";
    String userName = "";
    String dbURL = "";
    String driverName = "";

    String[] dbURLParts = dbURL.split("/");
    String dbName = dbURLParts[dbURLParts.length - 1];
    Class.forName(driverName);
    try {
        connection = DriverManager.getConnection (dbURL, userName, password);
        stmt = connection.createStatement();
        stmt.executeUpdate("USE " + dbName);
    } catch (SQLException e) {
        System.out.println("Could not set Database.");
    }
    initialized = true;
  }

  static synchronized Connection getConnection() {
    if (initialized == true) {
      return connection;
    }

    try {
      Init();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }

    return connection;
  }

  static ResultSet getTableRowById(String tableName, int id) {
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
}
