package sudoku.domain;

public class Solver {

    private int tempGrid[][];
    private int currentRow;
    private int currentColumn;

    public Solver() {
        this.tempGrid = new int[9][9];
    }

    // hyvin spesifi käyttö pitäisikö yleistää
    public boolean solved(int[][] grid) {
        tempGrid = grid;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tempGrid[i][j] != 0) {
                    currentRow = i;
                    currentColumn = j;
                    int number = tempGrid[i][j];
                    if (!(isValidForRow(number) && isValidForColumn(number) && isValidForSubgrid(number))) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    // Näistä vähän fiksummat jos aikaa

    private boolean isValidForRow(int number) {
        for (int i = 0; i < 9; i++) {
            if (i != currentColumn) {
                if (tempGrid[currentRow][i] == number) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidForColumn(int number) {
        for (int i = 0; i < 9; i++) {
            if (i != currentRow) {
                if (tempGrid[i][currentColumn] == number) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidForSubgrid(int number) {
        int firstRow = (currentRow / 3) * 3;
        int lastRow = firstRow + 3;
        int firstColumn = (currentColumn / 3) * 3;
        int lastColumn = firstColumn + 3;
        for (int i = firstRow; i < lastRow; i++) {
            for (int j = firstColumn; j < lastColumn; j++) {
                if (i != currentRow && j != currentColumn) {
                    if (tempGrid[i][j] == number) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}