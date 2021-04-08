package sudoku.dao;

import java.sql.*;

public class DatabaseHelper {

    private Connection db;
    private String dbUrl;

    public DatabaseHelper(String dbUrl, String userTable, String gameTable) {
        this.dbUrl = dbUrl;
        try {
            this.db = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            System.out.println("Could not connect to database.");
        }
        createTables(userTable, gameTable);
        try {
            db.close();
        } catch (SQLException e) {
            System.out.println("Could not close database connection.");
        }
    }

    public void connect() throws SQLException {
        if (db == null) db = DriverManager.getConnection(dbUrl);
        else {
            disconnect();
            db = DriverManager.getConnection(dbUrl);
        }
    }

    public void disconnect() throws SQLException { db.close(); }

    private void createTables(String userTable, String gameTable) {
        try {
            Statement s = db.createStatement();
            String tableString = "CREATE TABLE IF NOT EXISTS " + userTable + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(32))";
            s.execute(tableString);
            tableString = "CREATE TABLE IF NOT EXISTS " + gameTable + " (id INTEGER PRIMARY KEY AUTOINCREMENT, time INTEGER, FOREIGN KEY (id) REFERENCES User(id))";
            s.close();
            s.execute(tableString);
        } catch (SQLException e) {
            System.out.println("Could not create database tables.");
        }
    }

    public ResultSet getResultSet(String statement) {
        PreparedStatement p = null;
        ResultSet rs = null;
        try {
            p = db.prepareStatement(statement);
            rs = p.executeQuery();
        } catch (SQLException e) {
            System.out.println("Could not execute query.");
        }
        return rs;
    }
}
