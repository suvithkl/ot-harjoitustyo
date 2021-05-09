package sudoku.domain;

import java.util.Random;

/**
 * Sudokun peliruudukkoa edustava luokka
 */
public class Grid {

    private int[][] grid;
    private Solver solver;
    private int emptyModules;

    /**
     * Alustaa oliomuuttujat ja generoi oliomuuttujaan grid ratkaistavissa olevan sudokuruudukon
     */
    public Grid(Difficulty diff) {
        grid = new int[9][9];
        this.solver = new Solver();
        setEmptyModules(diff);
        generate();
    }

    private void generate() {
        fill();
        randomize("row");
        randomize("column");
        transpose();
        if (!(solver.solved(grid))) {
            generate();
        }
        makeModulesEmpty();
    }

    /**
     * Tarkistaa onko kyseinen sudokuruudukko ratkaistu oikein
     * @return true jos ratkaisu on oikea, muuten false
     */
    public boolean checkIfSolved() {
        if (solver.solved(grid)) {
            return true;
        } else {
            return false;
        }
    }

    private void fill() {
        int a;
        int b = 1;
        for (int i = 0; i < 9; i++) {
            a = b;
            for (int j = 0; j < 9; j++) {
                if (a > 9) {
                    a = 1;
                }
                grid[i][j] = a;
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

    private void transpose() {
        int[][] tempGrid = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tempGrid[i][j] = grid[j][i];
            }
        }
        grid = tempGrid;
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

    /**
     * Sudokuruudukko
     * @return sudokuruudukko matriisina
     */
    public int[][] getGrid() {
        return grid;
    }

    /**
     * Asettaa numeron sudokuruudukon ruutuun
     * @param a ruudun rivinumero
     * @param b ruudun sarakenumero
     * @param number asetettava numero
     */
    public void setModule(int a, int b, int number) {
        grid[a][b] = number;
    }

    private void setEmptyModules(Difficulty diff) {
        if (diff == Difficulty.EASY) {
            emptyModules = 40;
        } else if (diff == Difficulty.NORMAL) {
            emptyModules = 50;
        } else if (diff == Difficulty.HARD) {
            emptyModules = 60;
        }
    }

    /**
     * Sudokuruudukko ja sen sisältämät numerot merkkijonona
     * @return sudokuruudukko ja sen sisältämät numerot merkkijonona
     */
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