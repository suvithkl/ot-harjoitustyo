package sudoku.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SudokuServiceUserTest {

    FakeUserDao userDao;
    SudokuService sudokuService;

    @Before
    public void setUp() {
        userDao = new FakeUserDao();
        sudokuService = new SudokuService(userDao);
    }

    @Test
    public void existingUserCanLogin() {
        assertTrue(sudokuService.login("tester"));
        assertEquals("tester", sudokuService.getLoggedIn().getUsername());
    }

    @Test
    public void nonExistingUserCantLogin() {
        assertFalse(sudokuService.login("fake"));
        assertEquals(null, sudokuService.getLoggedIn());
    }

    @Test
    public void userCanLogout() {
        sudokuService.login("tester");
        sudokuService.logout();
        assertEquals(null, sudokuService.getLoggedIn());
    }

    @Test
    public void canCreateOnlyUniqueUser() {
        assertFalse(sudokuService.createUser("tester"));
    }

    @Test
    public void canCreateUniqueUser() {
        assertTrue(sudokuService.createUser("anotter tester"));
    }

    @Test
    public void createdUserCanLogin() {
        sudokuService.createUser("anotter tester");
        assertTrue(sudokuService.login("anotter tester"));
        assertEquals("anotter tester", sudokuService.getLoggedIn().getUsername());
    }
}