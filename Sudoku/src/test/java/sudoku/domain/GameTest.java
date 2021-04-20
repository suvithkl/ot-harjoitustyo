package sudoku.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    User u;
    Game g1;

    @Before
    public void setUp() {
        u = new User("test");
        g1 = new Game(u, Difficulty.NORMAL);
    }

    @Test
    public void hasGrid() {
        assertTrue(g1.getGrid() instanceof Grid);
    }

    @Test
    public void hasDifficulty() {
        assertTrue(g1.getDifficulty() instanceof Difficulty);
    }

    @Test
    public void differentTypeNotEqual() {
        Object o = new Object();
        assertFalse(g1.equals(o));
    }
}