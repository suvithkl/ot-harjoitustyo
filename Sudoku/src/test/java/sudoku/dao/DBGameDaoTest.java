package sudoku.dao;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import sudoku.domain.Difficulty;
import sudoku.domain.Game;
import sudoku.domain.User;

public class DBGameDaoTest {

    DBGameDao gameDao;
    DBUserDao userDao;
    DatabaseHelper db;

    @Before
    public void setUp() throws SQLException {
        String dbUrl = "jdbc:sqlite:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "testingData.db";
        db = new DatabaseHelper(dbUrl, "User", "Game");
        userDao = new DBUserDao(db);
        gameDao = new DBGameDao(db, userDao);
    }

    @Test
    public void canCreateUserDao() { assertNotNull(userDao); }

    @Test
    public void canCreateGameDao() { assertNotNull(gameDao); }

    @Test
    public void canInitGamesList() throws SQLException {
        Game g = new Game(new User("tester"), Difficulty.NORMAL);
        g.setTime("08:08");
        gameDao.save(g);
        GameDao gd = new DBGameDao(db, userDao);
        assertEquals(gd.getAll().get(0).getDifficulty(), Difficulty.NORMAL);
        assertEquals("08:08", gd.getAll().get(0).getTime());
    }

    @Test
    public void saveAddsToDb() {
        try {
            gameDao.save(new Game(new User("tester"), Difficulty.NORMAL));
        } catch (SQLException e) { assert false; }
        ResultSet rs = null;
        try {
            db.connect();
            rs = db.getResultSet("SELECT * FROM Game");
            assert rs != null;
        } catch (SQLException e) { assert false; }
        try {
            assertEquals("tester", rs.getString("name"));
        } catch (SQLException e) { assert false; }
    }

    @Test
    public void saveAddsToList() {
        try {
            gameDao.save(new Game(new User("tester"), Difficulty.NORMAL));
        } catch (SQLException e) { assert false; }
        assertEquals(gameDao.getAll().get(0).getDifficulty(), Difficulty.NORMAL);
    }

    @Test
    public void returnsGamesList() { assertNotNull(gameDao.getAll()); }

    @After
    public void tearDown() throws Exception {
        db.disconnect();
        Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + System.getProperty("file.separator") + "testingData.db"));
    }
}