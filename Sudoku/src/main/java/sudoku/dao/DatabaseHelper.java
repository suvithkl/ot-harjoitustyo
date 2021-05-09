package sudoku.dao;

import java.sql.*;

/**
 * Tietokannan käytöstä huolehtiva avustajaluokka
 */
public class DatabaseHelper {

    private Connection db;
    private String dbUrl;
    private String userTable;
    private String gameTable;
    private PreparedStatement p;
    private ResultSet rs;

    /**
     * Alustaa oliomuuttujat (paitsi p (PreparedStatement) ja rs (ResultSet) ),
     * sekä luo tietokannan taulut jos niitä ei ole olemassa
     */
    public DatabaseHelper(String dbUrl, String userTable, String gameTable) {
        this.dbUrl = dbUrl;
        this.userTable = userTable;
        this.gameTable = gameTable;
        connect();
        createTables();
        disconnect();
    }

    /**
     * Luo yhteyden tietokantaan ja asettaa kyseisen yhteyden oliomuuttujaksi db
     */
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

    /**
     * Katkaisee yhteyden tietokantaan, sekä tarvittaessa oliomuuttujiin p (PreparedStatement) ja rs (ResultSet)
     */
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

    private void createTables() {
        try {
            Statement s = db.createStatement();
            String tableString = "CREATE TABLE IF NOT EXISTS " + userTable + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(32));";
            s.execute(tableString);
            tableString = "CREATE TABLE IF NOT EXISTS " + gameTable + " (id INTEGER PRIMARY KEY AUTOINCREMENT, time VARCHAR(32)," +
                    " difficulty VARCHAR(6), name VARCHAR(32))";
            s.execute(tableString);
            s.close();
        } catch (SQLException e) {
            System.out.println("Exception in createTables: " + e);
        }
    }

    /**
     * Annetulla kyselyllö tietokannan tuottama taulu
     * @param statement kysely merkkijonona
     * @return annetulla kyselyllö tietokannan tuottama taulu
     */
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

    /**
     * Lisää tietoa tietokantaan
     * @param statement tiedon lisäävä SQL-kielinen komento (INSERT INTO -lause) merkkijonona
     * @param value lisättävä arvo
     */
    public void addToDatabase(String statement, String value) {
        try {
            p = db.prepareStatement(statement);
            p.setString(1, value);
            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception in updateDatabase: " + e);
        }
    }

    /**
     * Lisää tietoa tietokantaan
     * @param statement tiedon lisäävä SQL-kielinen komento (INSERT INTO -lause) merkkijonona
     * @param value1 lisättävä arvo
     * @param value2 lisättävä arvo
     * @param value3 lisättävä arvo
     */
    public void addToDatabase(String statement, String value1, String value2, String value3) {
        try {
            p = db.prepareStatement(statement);
            p.setString(1, value1);
            p.setString(2, value2);
            p.setString(3, value3);
            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception in updateDatabase: " + e);
        }
    }

    public String getUserTable() { return userTable; }

    public String getGameTable() { return gameTable; }
}