package sudoku.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SudokuServiceGameTest {

    FakeUserDao userDao;
    FakeGameDao gameDao;
    SudokuService sudokuService;
    SudokuService startedService;

    @Before
    public void setUp() {
        userDao = new FakeUserDao();
        gameDao = new FakeGameDao(userDao);
        sudokuService = new SudokuService(userDao, gameDao);
        startedService = new SudokuService(userDao, gameDao);
        startedService.startGame(Difficulty.NORMAL);
    }

    @Test
    public void startGameGivesGrid() { assertTrue(sudokuService.startGame(Difficulty.NORMAL) instanceof Grid); }

    @Test
    public void unsolvedCheckedAsUnsolved() { assertFalse(startedService.checkGame()); }

    @Test
    public void setsModuleCorrectly() { assertTrue(startedService.setModule(0, 0, 88)); }
}