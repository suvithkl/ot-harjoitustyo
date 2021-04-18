package sudoku.domain;

public class Grid {

    private int[][] grid;
    private Solver solver;
    private Game.difficulty difficulty;

    public Grid(Game.difficulty diff) {
        this.grid = new int[9][9];
        this.solver = new Solver();
        this.difficulty = diff;
    }

    private void generate() {

    }

    public boolean checkIfSolved() {
        if (solver.solve(grid)) return true;
        else return false;
    }

    public void printGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}