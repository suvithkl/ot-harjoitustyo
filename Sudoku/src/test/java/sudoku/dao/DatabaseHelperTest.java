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
    public void setUp() {
        String dbUrl = "jdbc:sqlite:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "testingData.db";
        db = new DatabaseHelper(dbUrl, "Test1", "Test2");
        db.connect();
    }

    @Test
    public void returnsResultSet() {
        ResultSet rs = db.getResultSet("SELECT * FROM Test1");
        assertTrue(rs instanceof ResultSet);
    }

    @Test
    public void canUpdateDatabase() throws SQLException {
        db.addToDatabase("INSERT INTO Test1 (name) VALUES (?)", "tester");
        assertEquals("tester", db.getResultSet("SELECT * FROM Test1").getString(2).toString());
    }

    @After
    public void tearDown() throws Exception{
        db.disconnect();
        Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + System.getProperty("file.separator") + "testingData.db"));
    }
}