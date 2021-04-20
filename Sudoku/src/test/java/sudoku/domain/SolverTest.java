package sudoku.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SolverTest {

    Solver solver;

    @Before
    public void setUp() {
        solver = new Solver();
    }

    @Test
    public void unsolvableGridCheckedAsUnsolvable() {
        int[][] grid = {{0, 1, 2, 3, 4, 5, 6, 7, 8},
                       {9, 5, 8, 0, 0, 2, 0, 9, 4},
                       {0, 2, 0, 0, 0, 5, 1, 8, 0},
                       {7, 8, 5, 0, 0, 4, 6, 0, 0},
                       {0, 4, 0, 3, 6, 0, 0, 7, 5},
                       {0, 0, 6, 1, 0, 7, 0, 0, 0},
                       {5, 0, 0, 8, 4, 0, 2, 0, 0},
                       {0, 0, 4, 0, 3, 0, 7, 0, 0},
                       {8, 0, 0, 2, 7, 0, 0, 0, 0}};
        System.out.println(solver.solveToGenerate(grid));
        assertFalse(solver.solveToGenerate(grid));
    }
}