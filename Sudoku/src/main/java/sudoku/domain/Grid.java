package sudoku.domain;

import java.util.Random;

public class Grid {

    private int[][] grid;
    private int[][] solved;
    private Solver solver;
    private int emptyModules;

    public Grid(Difficulty diff) {
        grid = new int[9][9];
        this.solver = new Solver();
        if (diff == Difficulty.NORMAL) {
            emptyModules = 50;
        }
        generate();
    }

    private void generate() {
        fill();
        makeModulesEmpty();
        if (!(solver.solveToGenerate(grid))) {
            generate();
        }
    }

    private void fill() {
        Random rnd = new Random();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = rnd.nextInt(9) + 1;
            }
        }
    }

    private void makeModulesEmpty() {

    }

    public boolean checkIfSolved() {
        if (isFull()) {
            return true;
        } else if (solver.solveToGenerate(grid)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isFull() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String st = "";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                st = st + grid[i][j] + " ";
            }
            st = st + "\n";
        }
        return st;
    }
}