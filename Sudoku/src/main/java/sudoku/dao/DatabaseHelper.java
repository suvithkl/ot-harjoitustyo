package sudoku.dao;

import java.sql.*;

/**
 * Tietokannan käytöstä huolehtiva avustajaluokka.
 */
public class DatabaseHelper {

    private Connection db;
    private final String dbUrl;
    private final String userTable;
    private final String gameTable;
    private PreparedStatement p;
    private ResultSet rs;

    /**
     * Alustaa oliomuuttujat (paitsi p (PreparedStatement) ja rs (ResultSet) ),
     * sekä luo tietokannan taulut jos niitä ei ole olemassa.
     * @param dbUrl käytetyn tietokannan polku
     * @param  userTable tietokantataulun, johon tallennetaan käyttäjät, nimi
     * @param  gameTable tietokantataulun, johon tallennetaan pelit, nimi
     * @throws SQLException jos yhteyden muodostus tai taulujen alustus ei onnistu
     */
    public DatabaseHelper(String dbUrl, String userTable, String gameTable) throws SQLException {
        this.dbUrl = dbUrl;
        this.userTable = userTable;
        this.gameTable = gameTable;
        connect();
        createTables();
        disconnect();
    }

    /**
     * Luo yhteyden tietokantaan ja asettaa kyseisen yhteyden oliomuuttujaksi db.
     * @throws SQLException jos yhteyden muodostus ei onnistu
     */
    public void connect() throws SQLException {
        if (db != null) {
            disconnect();
        }
        db = DriverManager.getConnection(dbUrl);
    }

    /**
     * Katkaisee yhteyden tietokantaan, sekä tarvittaessa oliomuuttujiin p (PreparedStatement) ja rs (ResultSet).
     * @throws SQLException jos yhteyden muodostus ei onnistu
     */
    public void disconnect() throws SQLException {
        if (db != null) {
            db.close();
        }
        if (p != null) {
            p.close();
        }
        if (rs != null) {
            rs.close();
        }
    }

    private void createTables() throws SQLException {
        Statement s = db.createStatement();
        String tableString = "CREATE TABLE IF NOT EXISTS " + userTable + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(32));";
        s.execute(tableString);
        tableString = "CREATE TABLE IF NOT EXISTS " + gameTable + " (id INTEGER PRIMARY KEY AUTOINCREMENT, time VARCHAR(32)," +
                " difficulty VARCHAR(6), name VARCHAR(32))";
        s.execute(tableString);
        s.close();
    }

    /**
     * Annetulla kyselyllö tietokannan tuottama taulu.
     * @param statement kysely merkkijonona
     * @return annetulla kyselyllö tietokannan tuottama taulu
     * @throws SQLException jos kyselyn tekeminen ei onnistu
     */
    public ResultSet getResultSet(String statement) throws SQLException {
        rs = null;
        p = db.prepareStatement(statement);
        rs = p.executeQuery();
        return rs;
    }

    /**
     * Lisää tietoa tietokantaan.
     * @param statement tiedon lisäävä SQL-kielinen komento (INSERT INTO -lause) merkkijonona
     * @param value lisättävä arvo
     * @throws SQLException jos tiedon lisääminen ei onnistu
     */
    public void addToDatabase(String statement, String value) throws SQLException {
        p = db.prepareStatement(statement);
        p.setString(1, value);
        p.executeUpdate();
        p.close();
    }

    /**
     * Lisää tietoa tietokantaan.
     * @param statement tiedon lisäävä SQL-kielinen komento (INSERT INTO -lause) merkkijonona
     * @param value1 lisättävä arvo
     * @param value2 lisättävä arvo
     * @param value3 lisättävä arvo
     * @throws SQLException jos tiedon lisääminen ei onnistu
     */
    public void addToDatabase(String statement, String value1, String value2, String value3) throws SQLException {
        p = db.prepareStatement(statement);
        p.setString(1, value1);
        p.setString(2, value2);
        p.setString(3, value3);
        p.executeUpdate();
        p.close();
    }

    public String getUserTable() {
        return userTable;
    }

    public String getGameTable() {
        return gameTable;
    }
}