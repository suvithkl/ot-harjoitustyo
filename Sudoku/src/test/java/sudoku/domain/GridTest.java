package sudoku.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GridTest {

    Grid grid;

    @Before
    public void setUp() { grid = new Grid(Difficulty.NORMAL); }

    @Test
    public void createsGrid() { assertNotNull(grid.getGrid()); }

    @Test
    public void setsCorrectAmountOfEmptyModules() {
        Grid grid_e = new Grid(Difficulty.EASY);
        Grid grid_h = new Grid(Difficulty.HARD);
        int easy = 0;
        int normal = 0;
        int hard = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid_e.getGrid()[i][j] == 0) easy++;
                if (grid.getGrid()[i][j] == 0) normal++;
                if (grid_h.getGrid()[i][j] == 0) hard++;
            }
        }
        assertEquals(40, easy);
        assertEquals(50, normal);
        assertEquals(60, hard);
    }

    @Test
    public void unsolvedCheckedAsUnsolved() { assertFalse(grid.checkIfSolved()); }

    // Palauttaatko grid.checkIfSolved() oikean boolean-arvon, kun sudokuruudukko on ratkaistu oikein,
    // testataan testiluokan SolverTest puolella

    @Test
    public void setsModuleCorrectly() {
        grid.setModule(0, 0, 88);
        assertEquals(88, grid.getGrid()[0][0]);
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void wontSetNumberToInvalidModule() {
        Grid other = grid;
        grid.setModule(13, -8, 0);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(other.getGrid()[i][j], grid.getGrid()[i][j]);
            }
        }
    }

    @Test
    public void conversesToStringCorrectly() {
        assertEquals(171, grid.toString().length());
        String s = grid.toString().replace("\n", "");
        String[] string = s.split(" ");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(grid.getGrid()[i][j], Integer.parseInt(string[9 * i + j]));
            }
        }
    }
}