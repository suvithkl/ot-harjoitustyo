package sudoku.domain;

import java.util.Random;
//import org.apache.flink.annotation.VisibleForTesting;

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

    // tähän lisäksi joku transpoosijuttu randomin lisäämiseksi?
//    @VisibleForTesting
    private void generate() {
        fill();
        randomize("row");
        randomize("column");
        if (solver.solved(grid)) {
            solved = grid;
        } else {
            generate();
        }
        makeModulesEmpty();
    }

    public boolean checkIfSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] != solved[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void fill() {
        int a;
        int b = 1;
        for (int i = 0; i < 9; i++) {
            a = b;
            for (int j = 0; j < 9; j++) {
                if (a <= 9) {
                    grid[i][j] = a;
                } else {
                    a = 1;
                    grid[i][j] = a;
                }
                a++;
            }
            b = a + 3;
            if (a > 9) {
                b = 1 + 3;
            }
            if (b > 9) {
                b = (b % 9) + 1;
            }
        }
    }

    private void randomize(String type) {
        int a, b;
        int max = 2;
        int min = 0;
        Random rnd = new Random();
        for (int i = 0; i < 3; i++) {
            a = rnd.nextInt(max - min + 1) + min;
            b = rnd.nextInt(max - min + 1) + min;
            while (a == b) {
                b = rnd.nextInt(max - min + 1) + min;
            }
            max += 3;
            min += 3;
            if (type.equals("row")) {
                swapRows(a, b);
            } else if (type.equals("column")) {
                swapColumns(a, b);
            }
        }
    }

    private void swapRows(int a, int b) {
        int temp;
        for (int i = 0; i < 9; i++) {
            temp = grid[a][i];
            grid[a][i] = grid[b][i];
            grid[b][i] = temp;
        }
    }

    private void swapColumns(int a, int b) {
        int temp;
        for (int i = 0; i < 9; i++) {
            temp = grid[i][a];
            grid[i][a] = grid[i][b];
            grid[i][b] = temp;
        }
    }

    private void makeModulesEmpty() {
        Random rnd = new Random();
        for (int i = 0; i < emptyModules; i++) {
            while (true) {
                int a = rnd.nextInt(9);
                int b = rnd.nextInt(9);
                if (grid[a][b] != 0) {
                    if (canErase(a, b)) {
                        grid[a][b] = 0;
                        break;
                    }
                }
            }
        }
    }

    // tässä voitaisiin hyväksyä yksi tyhjä rivi/sarake?
    private boolean canErase(int a, int b) {
        boolean rowNotEmpty = false;
        boolean columnNotEmpty = false;
        for (int i = 0; i < 9; i++) {
            if (i != a) {
                if (grid[i][b] != 0) {
                    rowNotEmpty = true;
                }
            }
            if (i != b) {
                if (grid[a][i] != 0) {
                    columnNotEmpty = true;
                }
            }
        }
        return rowNotEmpty && columnNotEmpty;
    }

    public int[][] getGrid() {
        return grid;
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