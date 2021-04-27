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
        assertTrue(solver.solved(grid));
    }

//    @Test
//    public void solvedGridCheckedAsSolved() {
//        int[][] grid = {{0, 1, 2, 3, 4, 5, 6, 7, 8},
//                       {9, 5, 8, 0, 0, 2, 0, 9, 4},
//                       {0, 2, 0, 0, 0, 5, 1, 8, 0},
//                       {7, 8, 5, 0, 0, 4, 6, 0, 0},
//                       {0, 4, 0, 3, 6, 0, 0, 7, 5},
//                       {0, 0, 6, 1, 0, 7, 0, 0, 0},
//                       {5, 0, 0, 8, 4, 0, 2, 0, 0},
//                       {0, 0, 4, 0, 3, 0, 7, 0, 0},
//                       {8, 0, 0, 2, 7, 0, 0, 0, 0}};
//        System.out.println(solver.solved(grid));
//        assertFalse(solver.solved(grid));
//    }
}