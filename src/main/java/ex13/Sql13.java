package ex13;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;

public class Sql13 {
  public ResultSet getUser(HttpServletRequest req)
      throws SQLException {
    String sql;
    Connection connection = getJDBCConnection();
    Statement statement;
    String userid = req.getParameter("userid");
    statement = connection.createStatement();
    sql = "SELECT first_name FROM user_data WHERE userid = '" + userid.toLowerCase() + "'";
    return statement.executeQuery(sql);
  }

  public ResultSet getUser2(HttpServletRequest req)
      throws SQLException {
    String sql;
    Connection connection = getJDBCConnection();
    Statement statement;
    String userid = "id_" + req.getParameter("userid");
    statement = connection.createStatement();
    sql = "SELECT first_name FROM user_data WHERE userid = '" + userid + "'";
    return statement.executeQuery(sql);
  }

  public ResultSet getUser3(HttpServletRequest req)
      throws SQLException {
    Statement statement = getJDBCConnection().createStatement();
    UserData userData = new UserData(req.getParameter("userid"), req.getParameter("name"));
    String sql = "SELECT first_name FROM user_data WHERE userid = '" + userData.getUserId() + "'";
    return statement.executeQuery(sql);
  }

  public ResultSet getUser4(HttpServletRequest req)
      throws SQLException {
    Statement statement = getJDBCConnection().createStatement();
    UserData userData = new UserData();
    userData.setUserId(req.getParameter("userid"));
    return statement.executeQuery("SELECT first_name FROM user_data WHERE userid = '" + userData.getUserId() + "'");
  }

  public ResultSet getUser5(HttpServletRequest req)
      throws SQLException {
    Statement statement = getJDBCConnection().createStatement();
    UserData userData = new UserData();
    userData.setUserId(req.getParameter("userid"));
    UserData userData1 = userData;
    userData1.setName("guest");
    return statement.executeQuery("SELECT first_name FROM user_data WHERE userid = '" + userData1.getUserId() + "'");
  }

  public ResultSet getUser6(HttpServletRequest req)
      throws SQLException {
    Statement statement = getJDBCConnection().createStatement();
    return statement.executeQuery("SELECT first_name FROM user_data WHERE userid = '" + createUserData().setUserId(req.getParameter("userid")) + "'");
  }

  public ResultSet getUser7(HttpServletRequest req)
      throws SQLException {
    return getJDBCConnection().createStatement().executeQuery(getQuery(req.getParameter("userid")));
  }

  private String getQuery(String userId) {
    return "SELECT first_name FROM user_data WHERE userid = '" + userId + "'";
  }

  private Connection getJDBCConnection() {
    try {
      return DriverManager.getConnection("jdbc:sqlserver://myserver.database.windows.net:1433;");
    } catch (SQLException exception) {
      return null;
    }
  }

  private UserData createUserData() {
    return new UserData();
  }

  private class UserData {
    private String userId;
    private String name;

    public UserData(String userId, String name) {
      this.userId = userId;
      this.name = name;
    }

    public UserData() {
    }

    public String getUserId() {
      return userId;
    }

    public String getName() {
      return name;
    }

    public UserData setUserId(String userId) {
      this.userId = userId;
      return this;
    }

    public UserData setName(String name) {
      this.name = name;
      return this;
    }

    public String getNormalizedUserId(String userId) {
      return "id_" + userId;
    }
  }
}

