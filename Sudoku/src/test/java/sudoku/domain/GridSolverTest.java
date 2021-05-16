package sudoku.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class GridSolverTest {

    @Test
    public void solvedGridCheckedAsSolved() {
        int[][] grid = {{9, 1, 7, 4, 8, 3, 5, 2, 6},
                       {6, 5, 8, 7, 1, 2, 3, 9, 4},
                       {4, 2, 3, 6, 9, 5, 1, 8, 7},
                       {7, 8, 5, 9, 2, 4, 6, 3, 1},
                       {1, 4, 2, 3, 6, 8, 9, 7, 5},
                       {3, 9, 6, 1, 5, 7, 8, 4, 2},
                       {5, 7, 9, 8, 4, 1, 2, 6, 3},
                       {2, 6, 4, 5, 3, 9, 7, 1, 8},
                       {8, 3, 1, 2, 7, 6, 4, 5, 9}};
        assertTrue(GridSolver.solved(grid));
    }

    @Test
    public void solvedGridCheckAsSolved2() {
        int[][] grid = {{5, 3, 4, 6, 7, 8, 9, 1, 2},
                       {6, 7, 2, 1, 9, 5, 3, 4, 8},
                       {1, 9, 8, 3, 4, 2, 5, 6, 7},
                       {8, 5, 9, 7, 6, 1, 4, 2, 3},
                       {4, 2, 6, 8, 5, 3, 7, 9, 1},
                       {7, 1, 3, 9, 2, 4, 8, 5, 6},
                       {9, 6, 1, 5, 3, 7, 2, 8, 4},
                       {2, 8, 7, 4, 1, 9, 6, 3, 5},
                       {3, 4, 5, 2, 8, 6, 1, 7, 9}};
        assertTrue(GridSolver.solved(grid));
    }

    @Test
    public void solvedGridCheckAsSolved3() {
        int[][] grid = {{1, 2, 3, 4, 5, 6, 7, 8, 9},
                       {4, 5, 6, 7, 8, 9, 1, 2, 3},
                       {7, 8, 9, 1, 2, 3, 4, 5, 6},
                       {2, 3, 4, 5, 6, 7, 8, 9, 1},
                       {5, 6, 7, 8, 9, 1, 2, 3, 4},
                       {8, 9, 1, 2, 3, 4, 5, 6, 7},
                       {3, 4, 5, 6, 7, 8, 9, 1, 2},
                       {6, 7, 8, 9, 1, 2, 3, 4, 5},
                       {9, 1, 2, 3, 4, 5, 6, 7, 8}};
        assertTrue(GridSolver.solved(grid));
    }

    @Test
    public void unsolvedGridCheckedAsUnsolved() {
        int[][] grid = {{0, 1, 2, 3, 4, 5, 6, 7, 8},
                       {9, 5, 8, 0, 0, 2, 0, 9, 4},
                       {0, 2, 0, 0, 0, 5, 1, 8, 0},
                       {7, 8, 5, 0, 0, 4, 6, 0, 0},
                       {0, 4, 0, 3, 6, 0, 0, 7, 5},
                       {0, 0, 6, 1, 0, 7, 0, 0, 0},
                       {5, 0, 0, 8, 4, 0, 2, 0, 0},
                       {0, 0, 4, 0, 3, 0, 7, 0, 0},
                       {8, 0, 0, 2, 7, 0, 0, 0, 0}};
        assertFalse(GridSolver.solved(grid));
    }

    @Test
    public void incorrectlySolvedGridCheckedAsUnsolved() {
        int[][] grid = {{8, 1, 7, 4, 8, 3, 5, 2, 6},
                       {6, 5, 8, 7, 1, 2, 3, 9, 4},
                       {4, 2, 3, 6, 9, 5, 1, 8, 7},
                       {7, 8, 5, 9, 2, 4, 6, 3, 1},
                       {1, 4, 2, 3, 6, 8, 9, 7, 5},
                       {3, 9, 6, 1, 5, 7, 8, 4, 2},
                       {5, 7, 9, 8, 4, 1, 2, 6, 3},
                       {2, 6, 4, 5, 3, 9, 7, 1, 8},
                       {8, 3, 1, 2, 7, 6, 4, 5, 9}};
        assertFalse(GridSolver.solved(grid));
    }

    @Test
    public void incorrectlySolvedGridCheckedAsUnsolved2() {
        int[][] grid = {{1, 2, 3, 5, 4, 6, 7, 8, 9},
                       {4, 5, 6, 7, 9, 8, 1, 2, 3},
                       {7, 8, 9, 1, 2, 3, 4, 6, 5},
                       {2, 3, 4, 5, 6, 7, 8, 1, 9},
                       {5, 7, 6, 8, 9, 1, 2, 3, 4},
                       {8, 1, 9, 2, 3, 4, 5, 6, 7},
                       {3, 4, 5, 6, 8, 7, 9, 1, 2},
                       {6, 7, 8, 9, 2, 1, 3, 4, 5},
                       {9, 1, 2, 4, 3, 5, 6, 7, 8}};
        assertFalse(GridSolver.solved(grid));
    }

    @Test
    public void invalidForSubgridCheckedAsUnsolved() {
        int[][] grid = {{1, 2, 3, 0, 0, 0, 0, 0, 0},
                       {2, 1, 0, 0, 0, 0, 0, 0, 0},
                       {3, 0, 0, 0, 0, 0, 0, 0, 0},
                       {0, 0, 0, 0, 0, 0, 0, 0, 0},
                       {0, 0, 0, 0, 0, 0, 0, 0, 0},
                       {0, 0, 0, 0, 0, 0, 0, 0, 0},
                       {0, 0, 0, 0, 0, 0, 0, 0, 0},
                       {0, 0, 0, 0, 0, 0, 0, 0, 0},
                       {0, 0, 0, 0, 0, 0, 0, 0, 0}};
        assertFalse(GridSolver.solved(grid));
    }
}