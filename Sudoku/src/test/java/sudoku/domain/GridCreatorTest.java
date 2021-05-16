package sudoku.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GridCreatorTest {

    private int[][] grid;
    private int[][] baseGrid;

    @Before
    public void setUp() {
        grid = new int[9][9];
        baseGrid = new int[][]{{1, 2, 3, 4, 5, 6, 7, 8, 9},
                               {4, 5, 6, 7, 8, 9, 1, 2, 3},
                               {7, 8, 9, 1, 2, 3, 4, 5, 6},
                               {2, 3, 4, 5, 6, 7, 8, 9, 1},
                               {5, 6, 7, 8, 9, 1, 2, 3, 4},
                               {8, 9, 1, 2, 3, 4, 5, 6, 7},
                               {3, 4, 5, 6, 7, 8, 9, 1, 2},
                               {6, 7, 8, 9, 1, 2, 3, 4, 5},
                               {9, 1, 2, 3, 4, 5, 6, 7, 8}};
    }

    @Test
    public void fillsCorrectly() {
        GridCreator.fill(grid);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(grid[i][j], baseGrid[i][j]);
            }
        }
    }

    @Test
    public void randomizesRows() {
        int[][] g = {{1, 2, 3, 4, 5, 6, 7, 8, 9},
                     {4, 5, 6, 7, 8, 9, 1, 2, 3},
                     {7, 8, 9, 1, 2, 3, 4, 5, 6},
                     {2, 3, 4, 5, 6, 7, 8, 9, 1},
                     {5, 6, 7, 8, 9, 1, 2, 3, 4},
                     {8, 9, 1, 2, 3, 4, 5, 6, 7},
                     {3, 4, 5, 6, 7, 8, 9, 1, 2},
                     {6, 7, 8, 9, 1, 2, 3, 4, 5},
                     {9, 1, 2, 3, 4, 5, 6, 7, 8}};
        GridCreator.randomize("row", g);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (g[i][j] != baseGrid[i][j]) {
                    assert true;
                    return;
                }
            }
        }
        assert false;
    }

    @Test
    public void randomizesColumns() {
        int[][] g = {{1, 2, 3, 4, 5, 6, 7, 8, 9},
                     {4, 5, 6, 7, 8, 9, 1, 2, 3},
                     {7, 8, 9, 1, 2, 3, 4, 5, 6},
                     {2, 3, 4, 5, 6, 7, 8, 9, 1},
                     {5, 6, 7, 8, 9, 1, 2, 3, 4},
                     {8, 9, 1, 2, 3, 4, 5, 6, 7},
                     {3, 4, 5, 6, 7, 8, 9, 1, 2},
                     {6, 7, 8, 9, 1, 2, 3, 4, 5},
                     {9, 1, 2, 3, 4, 5, 6, 7, 8}};
        GridCreator.randomize("column", g);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (g[i][j] != baseGrid[i][j]) {
                    assert true;
                    return;
                }
            }
        }
        assert false;
    }

    @Test
    public void noRandomizationWithInvalidParameter() {
        GridCreator.fill(grid);
        GridCreator.randomize("fake", grid);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(grid[i][j], baseGrid[i][j]);
            }
        }
    }

    @Test
    public void doesTransposeCorrectly() {
        int[][] transGrid = {{1, 4, 7, 2, 5, 8, 3, 6, 9},
                             {2, 5, 8, 3, 6, 9, 4, 7, 1},
                             {3, 6, 9, 4, 7, 1, 5, 8, 2},
                             {4, 7, 1, 5, 8, 2, 6, 9, 3},
                             {5, 8, 2, 6, 9, 3, 7, 1, 4},
                             {6, 9, 3, 7, 1, 4, 8, 2, 5},
                             {7, 1, 4, 8, 2, 5, 9, 3, 6},
                             {8, 2, 5, 9, 3, 6, 1, 4, 7},
                             {9, 3, 6, 1, 4, 7, 2, 5, 8}};
        baseGrid = GridCreator.transpose(baseGrid);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(baseGrid[i][j], transGrid[i][j]);
            }
        }
    }

    @Test
    public void erasesCorrectAmountOfModules() {
        GridCreator.fill(grid);
        GridCreator.makeModulesEmpty(18, grid);
        int empty = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) empty++;
            }
        }
        assertEquals(18, empty);
    }

    @Test
    public void wontEraseWhenWouldEmptyRowsOrColumns() {
        int[][] g = {{1, 0, 0, 0, 0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0, 0, 0, 0, 0}};
        GridCreator.makeModulesEmpty(1, g);
        int notEmpty = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (g[i][j] != 0) notEmpty++;
            }
        }
        assertEquals(1, notEmpty);
    }

    @Test
    public void baseGridIsSolvable() { assertTrue(GridSolver.solved(baseGrid)); }

    @Test
    public void createdGridIsNotSolved() {
        GridCreator.fill(grid);
        GridCreator.randomize("row", grid);
        GridCreator.randomize("column", grid);
        grid = GridCreator.transpose(grid);
        if (!(GridSolver.solved(grid))) {
            createdGridIsNotSolved();
        }
        GridCreator.makeModulesEmpty(50, grid);
        assertFalse(GridSolver.solved(grid));
    }

    @Test
    public void createdGridIsSolvedBeforeEmptying() {
        GridCreator.fill(grid);
        GridCreator.randomize("row", grid);
        GridCreator.randomize("column", grid);
        grid = GridCreator.transpose(grid);
        if (!(GridSolver.solved(grid))) {
            createdGridIsSolvedBeforeEmptying();
        }
        assertTrue(GridSolver.solved(grid));
    }
}