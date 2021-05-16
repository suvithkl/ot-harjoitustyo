package sudoku.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    User user;
    Game game;

    @Before
    public void setUp() {
        user = new User("tester");
        game = new Game(user, Difficulty.NORMAL);
    }

    @Test
    public void unsolvedCheckedAsUnsolved() { assertFalse(game.checkIfSolved()); }

    // Palauttaatko game.checkIfSolved() oikean boolean-arvon, kun sudokuruudukko on ratkaistu oikein,
    // testataan testiluokan SolverTest puolella

    @Test
    public void hasGrid() { assertNotNull(game.getGrid()); }

    @Test
    public void hasDifficulty() {
        assertNotNull(game.getDifficulty());
    }

    @Test
    public void hasCorrectUser() { assertEquals(game.getUser(), user);}

    @Test
    public void setsModuleCorrectly() {
        game.setModule(2, 2, 88);
        assertEquals(88, game.getGrid().getGrid()[2][2]);
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void wontSetNumberToInvalidModule() {
        Game other = game;
        game.setModule(-8, 17, 5);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(other.getGrid().getGrid()[i][j], game.getGrid().getGrid()[i][j]);
            }
        }
    }

    @Test
    public void hasNoTimeWhenNotSet() { assertNull(game.getTime()); }

    @Test
    public void hasCorrectTimeWhenSet() {
        game.setTime("08:08");
        assertEquals("08:08", game.getTime());
    }
}