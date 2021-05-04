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
    public void setUp() {
        String dbUrl = "jdbc:sqlite:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "testingData.db";
        db = new DatabaseHelper(dbUrl, "User", "Game");
        try {
            userDao = new DBUserDao(db);
            gameDao = new DBGameDao(db, userDao);
        } catch (SQLException e) {
            assert false;
        }
    }

    @Test
    public void saveAddsToDb() {
        try {
            gameDao.save(new Game(new User("tester"), Difficulty.NORMAL));
        } catch (SQLException e) {
            assert false;
        }
        db.connect();
        ResultSet rs = db.getResultSet("SELECT * FROM Game");
        if (rs == null) assert false;
        try {
            assertTrue(rs.getString("name").equals("tester"));
        } catch (SQLException e) {
            assert false;
        }
    }

    @Test
    public void saveAddsToList() {
        try {
            gameDao.save(new Game(new User("tester"), Difficulty.NORMAL));
        } catch (SQLException e) {
            assert false;
        }
        assertTrue(gameDao.getAll().get(0).getDifficulty().equals(Difficulty.NORMAL));
    }

    @Test
    public void returnsCorrectDifficultyEnum() {
        assertTrue(gameDao.setDifficulty("EASY").equals(Difficulty.EASY));
        assertTrue(gameDao.setDifficulty("NORMAL").equals(Difficulty.NORMAL));
        assertTrue(gameDao.setDifficulty("HARD").equals(Difficulty.HARD));
    }

    @After
    public void tearDown() throws Exception {
        db.disconnect();
        Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + System.getProperty("file.separator") + "testingData.db"));
    }
}