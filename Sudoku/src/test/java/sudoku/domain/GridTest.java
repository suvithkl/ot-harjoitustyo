package sudoku.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GridTest {

    Grid g1;

    @Before
    public void setUp() {
        g1 = new Grid(Difficulty.NORMAL);
    }

    @Test
    public void createsGrid() { assertTrue(g1.getGrid() instanceof int[][]); }

    @Test
    public void printsCorrectly() { assertTrue(g1.toString().length() == 171); }

    @Test
    public void test() {
    }
}