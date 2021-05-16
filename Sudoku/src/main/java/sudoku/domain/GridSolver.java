package sudoku.domain;

/**
 * Sudokujen ratkaistavissa olemisen tarkistamisesta huolehtiva luokka.
 */
public class GridSolver {

    private static int currentRow;
    private static int currentColumn;

    /**
     * Tarkistaa vastaako töysi generoitu sudokuruudukko oikein ratkaistua sudokuruudukkoa.
     * @param grid täysi sudokuruudukko matriisina
     * @return true jos ruudukko vastaa oiken ratkaistua sudokuruudukkoa, muuten false
     */
    public static boolean solved(int[][] grid) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] != 0) {
                    currentRow = i;
                    currentColumn = j;
                    int number = grid[i][j];
                    if (!(isValidForRow(number, grid) && isValidForColumn(number, grid) && isValidForSubgrid(number, grid))) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    // Tutkii voiko valitulla rivillä olla parametrina annettu numero ilman sääntörikkomusta
    private static boolean isValidForRow(int number, int[][] grid) {
        for (int i = 0; i < 9; i++) {
            if (i != currentColumn) {
                if (grid[currentRow][i] == number) {
                    return false;
                }
            }
        }
        return true;
    }

    // Tutkii voiko valitussa sarakkeessa olla parametrina annettu numero ilman sääntörikkomusta
    private static boolean isValidForColumn(int number, int[][] grid) {
        for (int i = 0; i < 9; i++) {
            if (i != currentRow) {
                if (grid[i][currentColumn] == number) {
                    return false;
                }
            }
        }
        return true;
    }

    // Tutkii voiko valitussa 3x3 osaruudukossa olla parametrina annettu numero ilman sääntörikkomusta
    private static boolean isValidForSubgrid(int number, int[][] grid) {
        int firstRow = (currentRow / 3) * 3;
        int lastRow = firstRow + 3;
        int firstColumn = (currentColumn / 3) * 3;
        int lastColumn = firstColumn + 3;
        for (int i = firstRow; i < lastRow; i++) {
            for (int j = firstColumn; j < lastColumn; j++) {
                if (i != currentRow && j != currentColumn) {
                    if (grid[i][j] == number) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}