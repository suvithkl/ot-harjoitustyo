package sudoku.domain;

public class Solver {

    private int tempGrid[][];
    private int currentRow;
    private int currentColumn;

    public Solver() {
        this.tempGrid = new int[9][9];
    }

    public boolean solve(int[][] grid) {
        tempGrid = grid;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tempGrid[i][j] == 0) {
                    currentRow = i;
                    currentColumn = j;
                    for (int k = 1; k < 10; k++) {
                        tempGrid[i][j] = k;
                        if (isValidForRow(k) && isValidForColumn(k) && isValidForSubgrid(k) && solve(tempGrid)) {
                            return true;
                        }
                        tempGrid[i][j] = 0;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // Näistä vähän fiksummat kun ehtii

    private boolean isValidForRow(int number) {
        for (int i = 0; i < 9; i++) {
            if (tempGrid[currentRow][i] == number) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidForColumn(int number) {
        for (int i = 0; i < 9; i++) {
            if (tempGrid[i][currentColumn] == number) {
                return false;
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
                if (tempGrid[i][j] == number) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkArrayIsValid(boolean[] array) {
        if (tempGrid[currentRow][currentColumn] != 0) {
            if (!(array[tempGrid[currentRow][currentColumn] - 1])) {
                array[tempGrid[currentRow][currentColumn] - 1] = true;
            } else {
                return false;
            }
        }
        return true;
    }
}