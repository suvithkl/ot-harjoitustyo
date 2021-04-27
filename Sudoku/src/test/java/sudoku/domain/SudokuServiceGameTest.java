package sudoku.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SudokuServiceGameTest {

    FakeUserDao userDao;
    SudokuService sudokuService;
    SudokuService startedService;

    @Before
    public void setUp() {
        userDao = new FakeUserDao();
        sudokuService = new SudokuService(userDao);
        startedService = new SudokuService(userDao);
        startedService.startGame();
    }

    @Test
    public void startGameGivesGrid() { assertTrue(sudokuService.startGame() instanceof Grid); }

    @Test
    public void unsolvedCheckedAsUnsolved() { assertFalse(startedService.checkGame()); }

    @Test
    public void setsModuleCorrectly() { assertTrue(startedService.setModule(0, 0, 88)); }
}