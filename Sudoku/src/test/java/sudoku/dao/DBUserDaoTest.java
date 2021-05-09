package sudoku.dao;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sudoku.domain.User;

public class DBUserDaoTest {

    DBUserDao userDao;
    DatabaseHelper db;

    @Before
    public void setUp() {
        String dbUrl = "jdbc:sqlite:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "testingData.db";
        db = new DatabaseHelper(dbUrl, "User", "Game");
        try {
            userDao = new DBUserDao(db);
        } catch (SQLException e) {
            assert false;
        }
    }

    @Test
    public void createAddsToDb() {
        try {
            userDao.create(new User("tester"));
        } catch (SQLException e) {
            assert false;
        }
        db.connect();
        ResultSet rs = db.getResultSet("SELECT * FROM User");
        if (rs == null) assert false;
        try {
            assertTrue(rs.getString("name").equals("tester"));
        } catch (SQLException e) {
            assert false;
        }
    }

    @Test
    public void createAddsToList() {
        try {
            userDao.create(new User("tester"));
        } catch (SQLException e) {
            assert false;
        }
        assertTrue(userDao.getAll().get(0).getUsername().equals("tester"));
    }

    @Test
    public void findsCorrectUser() {
        try {
            userDao.create(new User("tester"));
        } catch (SQLException e) {
            assert false;
        }
        assertTrue(userDao.getByUsername("tester").getUsername().equals("tester"));
    }

    @After
    public void tearDown() throws Exception {
        db.disconnect();
        Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + System.getProperty("file.separator") + "testingData.db"));
    }
}