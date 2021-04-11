package sudoku.dao;

import java.io.File;
import java.sql.DatabaseMetaData;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.*;
import sudoku.domain.User;

public class DBUserDaoTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    File testDB;
    UserDao userDao;

    @Before
    public void setUp() throws Exception {
        testDB = testFolder.newFile("testData.db");

    }

    @After
    public void tearDown() {
        testDB.delete();
    }
}