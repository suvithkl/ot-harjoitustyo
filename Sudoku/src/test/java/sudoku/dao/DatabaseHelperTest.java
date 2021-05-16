package sudoku.dao;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseHelperTest {

    DatabaseHelper db;

    @Before
    public void setUp() throws SQLException {
        String dbUrl = "jdbc:sqlite:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "testingData.db";
        db = new DatabaseHelper(dbUrl, "Test1", "Test2");
        db.connect();
    }

    @Test
    public void connects() {
        try {
            db.connect();
            assert true;
        } catch (SQLException e) { assert false; }
    }

    @Test
    public void disconnects() {
        try {
            db.disconnect();
            assert true;
        } catch (SQLException e) { assert false; }
    }

    @Test
    public void disconnectsAndClosesPreparedStatement() {
        try {
            db.addToDatabase("INSERT INTO Test1 (name) VALUES (?)", "test");
        } catch (SQLException e) { assert false; }
        try {
            db.disconnect();
            assert true;
        } catch (SQLException e) { assert false; }
    }

    @Test
    public void disconnectsAndClosesResultSet() {
        ResultSet rs = null;
        try {
            rs = db.getResultSet("SELECT * FROM Test1");
            assertNotNull(rs);
        } catch (SQLException e) { assert false; }
        try {
            db.disconnect();
            assertTrue(rs.isClosed());
            assert true;
        } catch (SQLException e) { assert false; }
    }

    @Test
    public void returnsResultSet() throws SQLException {
        ResultSet rs = db.getResultSet("SELECT * FROM Test1");
        assertNotNull(rs);
    }

    @Test (expected = SQLException.class)
    public void invalidStatementWontQueryTable1() throws SQLException{
        db.getResultSet("SELECT time FROM Test1");
    }
    @Test (expected = SQLException.class)
    public void invalidStatementWontQueryTable2() throws SQLException {
        db.getResultSet("SELECT fake FROM Test2");
    }

    @Test
    public void canUpdateDatabaseTable1() throws SQLException {
        db.addToDatabase("INSERT INTO Test1 (name) VALUES (?)", "tester");
        assertEquals("tester", db.getResultSet("SELECT * FROM Test1").getString(2));
    }

    @Test
    public void canUpdateDatabase2Table2() throws SQLException {
        db.addToDatabase("INSERT INTO Test2 (time,difficulty,name) VALUES (?,?,?)",
                "08:08", "NORMAL", "tester");
        ResultSet rs = db.getResultSet("SELECT * FROM Test2");
        assertEquals("08:08", rs.getString(2));
        assertEquals("NORMAL", rs.getString(3));
        assertEquals("tester", rs.getString(4));
    }

    @Test (expected = SQLException.class)
    public void invalidStatementWontUpdateTable1() throws SQLException {
        db.addToDatabase("INSERT INTO Test1 (time) VALUES (?)", "08:08");
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void invalidStatementWontUpdateTable2() throws SQLException {
        db.addToDatabase("INSERT INTO Test2 (name) VALUES (?)", "08:08", "NORMAL", "tester");
    }

    @Test
    public void hasUserTable() { assertEquals("Test1", db.getUserTable()); }

    @Test
    public void hasGameTable() { assertEquals("Test2", db.getGameTable()); }

    @After
    public void tearDown() throws Exception{
        db.disconnect();
        Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + System.getProperty("file.separator") + "testingData.db"));
    }
}