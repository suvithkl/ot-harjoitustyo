package sudoku.domain;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SudokuServiceUserTest {

    SudokuService sudokuService;

    @Before
    public void setUp() throws Exception {
        String dbUrl = "jdbc:sqlite:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "testingData.db";
        sudokuService = new SudokuService(dbUrl, "Test1", "Test2");
        sudokuService.createUser("tester");
    }

    @Test
    public void existingUserCanLogin() {
        assertTrue(sudokuService.login("tester"));
        assertEquals("tester", sudokuService.getLoggedIn().getUsername());
    }

    @Test
    public void nonexistentUserCantLogin() {
        assertFalse(sudokuService.login("fake"));
        assertNull(sudokuService.getLoggedIn());
    }

    @Test
    public void userCanLogout() {
        sudokuService.login("tester");
        sudokuService.logout();
        assertNull(sudokuService.getLoggedIn());
    }

    @Test
    public void canCreateOnlyUniqueUser() throws Exception { assertFalse(sudokuService.createUser("tester")); }

    @Test
    public void canCreateUniqueUser() throws Exception { assertTrue(sudokuService.createUser("anotter tester")); }

    @Test
    public void createdUserCanLogin() throws Exception {
        sudokuService.createUser("anotter tester");
        assertTrue(sudokuService.login("anotter tester"));
        assertEquals("anotter tester", sudokuService.getLoggedIn().getUsername());
    }

    @After
    public void tearDown() throws Exception{
        Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + System.getProperty("file.separator") + "testingData.db"));
    }
}