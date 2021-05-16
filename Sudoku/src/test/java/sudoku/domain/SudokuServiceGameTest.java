package sudoku.domain;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SudokuServiceGameTest {

    SudokuService sudokuService;
    SudokuService startedService;

    @Before
    public void setUp() throws Exception {
        String dbUrl = "jdbc:sqlite:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "testingData.db";
        sudokuService = new SudokuService(dbUrl, "Test1", "Test2");
        startedService = new SudokuService(dbUrl, "Test1", "Test2");
        startedService.createUser("tester");
        startedService.login("tester");
        startedService.startGame("NORMAL");
    }

    @Test
    public void startGameGivesGrid() { assertNotNull(sudokuService.startGame("NORMAL")); }

    @Test
    public void unsolvedCheckedAsUnsolved() { assertFalse(startedService.checkGame()); }

    // Palauttaatko startedService.checkGame() oikean boolean-arvon, kun sudokuruudukko on ratkaistu oikein,
    // testataan testiluokan SolverTest puolella

    @Test (expected = NullPointerException.class)
    public void cantCheckNotStartedGame() { assertFalse(sudokuService.checkGame()); }

    @Test (expected = NullPointerException.class)
    public void cantSaveNotStartedGame() throws Exception { sudokuService.saveGame("08:08"); }

    @Test
    public void canSaveGame() throws Exception {
        startedService.saveGame("08:08");
        for (String s : startedService.getSolved()) {
            assertEquals("tester;08:08;NORMAL", s);
        }
    }

    @Test
    public void setsModuleCorrectly() {
        assertTrue(startedService.setModule(0, 0, 88));
        assertEquals(true, startedService.setModule(0, 0, -1));
        assertTrue(startedService.setModule(0, 0, 8));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void wontSetNumberToInvalidModule() {
        startedService.setModule(-8, -8, 3);
    }

    @Test (expected = NullPointerException.class)
    public void cantSetModuleToNotStartedGame() { sudokuService.setModule(1, 1, 8); }

    @Test
    public void returnsListOfSolvedGames() {
        assertNotNull(sudokuService.getSolved());
        assertNotNull(startedService.getSolved());
    }

    @Test
    public void returnsSolvedListInCorrectOrder() throws Exception {
        startedService.saveGame("08:08");
        startedService.logout();
        startedService.createUser("anotter");
        startedService.login("anotter");
        startedService.startGame("EASY");
        startedService.saveGame("03:24");
        int i = 1;
        for (String s : startedService.getSolved()) {
            if (i == 1) assertEquals("anotter;03:24;EASY", s);
            if (i == 2) assertEquals("tester;08:08;NORMAL", s);
            i++;
        }
    }

    @After
    public void tearDown() throws Exception{
        Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + System.getProperty("file.separator") + "testingData.db"));
    }
}