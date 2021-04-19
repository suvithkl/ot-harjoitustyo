package sudoku.dao;

import java.sql.*;

public class DatabaseHelper {

    private Connection db;
    private String dbUrl;
    private PreparedStatement p;
    private ResultSet rs;

    public DatabaseHelper(String dbUrl, String userTable, String gameTable) {
        this.dbUrl = dbUrl;
        connect();
        createTables(userTable, gameTable);
        disconnect();
    }

    public void connect() {
        try {
            if (db == null) {
                db = DriverManager.getConnection(dbUrl);
            } else {
                disconnect();
                db = DriverManager.getConnection(dbUrl);
            }
        } catch (SQLException e) {
            System.out.println("Exception in connect: " + e);
        }
    }

    public void disconnect() {
        try {
            db.close();
            if (p != null) {
                p.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("Exception in disconnect: " + e);;
        }
    }

    private void createTables(String userTable, String gameTable) {
        try {
            Statement s = db.createStatement();
            String tableString = "CREATE TABLE IF NOT EXISTS " + userTable + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(32));" +
                    "";
            s.execute(tableString);
            tableString = "CREATE TABLE IF NOT EXISTS " + gameTable + " (id INTEGER PRIMARY KEY AUTOINCREMENT, time INTEGER," +
                    " FOREIGN KEY (id) REFERENCES User(id))";
            s.execute(tableString);
            s.close();
        } catch (SQLException e) {
            System.out.println("Exception in createTables: " + e);
        }
    }

    public ResultSet getResultSet(String statement) {
        rs = null;
        try {
            p = db.prepareStatement(statement);
            rs = p.executeQuery();
        } catch (SQLException e) {
            System.out.println("Exception in getResultSet: " + e);
        }
        return rs;
    }

    public void updateDatabase(String statement, String value) {
        try {
            p = db.prepareStatement(statement);
            p.setString(1, value);
            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception in updateDatabase: " + e);
        }
    }
}