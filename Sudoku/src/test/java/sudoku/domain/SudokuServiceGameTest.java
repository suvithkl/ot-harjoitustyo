package sudoku.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SudokuServiceGameTest {

    FakeUserDao userDao;
    SudokuService sudokuService;

    @Before
    public void setUp() {
        userDao = new FakeUserDao();
        sudokuService = new SudokuService(userDao);
    }

    @Test
    public void startGameGivesGrid() {
        assertTrue(sudokuService.startGame() instanceof Grid);
//        assertTrue(sudokuService.startGame().length() == 171);
    }
}