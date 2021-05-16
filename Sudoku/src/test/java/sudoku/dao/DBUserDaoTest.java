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
    public void setUp() throws SQLException {
        String dbUrl = "jdbc:sqlite:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "testingData.db";
        db = new DatabaseHelper(dbUrl, "User", "Game");
        userDao = new DBUserDao(db);
    }

    @Test
    public void canCreateUserDao() { assertNotNull(userDao); }

    @Test
    public void canInitUsersList() throws SQLException {
        userDao.create(new User("test"));
        UserDao ud = new DBUserDao(db);
        assertEquals("test", ud.getAll().get(0).getUsername());
    }

    @Test
    public void createAddsToDb() {
        try {
            userDao.create(new User("tester"));
        } catch (SQLException e) { assert false; }
        ResultSet rs = null;
        try {
            db.connect();
            rs = db.getResultSet("SELECT * FROM User");
            assert rs != null;
        } catch (SQLException e) { assert false; }
        try {
            assertEquals("tester", rs.getString("name"));
        } catch (SQLException e) { assert false; }
    }

    @Test
    public void createAddsToList() {
        try {
            userDao.create(new User("tester"));
        } catch (SQLException e) { assert false; }
        assertEquals("tester", userDao.getAll().get(0).getUsername());
    }

    @Test
    public void returnsUsersListCorrectly() { assertNotNull(userDao.getAll()); }

    @Test
    public void findsCorrectUser() {
        try {
            userDao.create(new User("tester"));
        } catch (SQLException e) { assert false; }
        assertEquals("tester", userDao.getByUsername("tester").getUsername());
    }

    @Test
    public void wontFindNonExistentUser() { assertNull(userDao.getByUsername("tester")); }

    @After
    public void tearDown() throws Exception {
        db.disconnect();
        Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + System.getProperty("file.separator") + "testingData.db"));
    }
}