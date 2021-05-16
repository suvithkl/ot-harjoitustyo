package sudoku.domain;

/**
 * Sudokun peliruudukkoa edustava luokka.
 */
public class Grid {

    private int[][] grid;
    private final int emptyModules;

    /**
     * Alustaa oliomuuttujat ja generoi oliomuuttujaan grid ratkaistavissa olevan sudokuruudukon.
     * @param diff sudokuruudukon vaikeustaso
     */
    public Grid(Difficulty diff) {
        grid = new int[9][9];
        emptyModules = diff.getEmptyModules();
        generate();
    }

    private void generate() {
        GridCreator.fill(grid);
        GridCreator.randomize("row", grid);
        GridCreator.randomize("column", grid);
        grid = GridCreator.transpose(grid);
        if (!(GridSolver.solved(grid))) {
            generate();
        }
        GridCreator.makeModulesEmpty(emptyModules, grid);
    }

    /**
     * Tarkistaa onko kyseinen sudokuruudukko ratkaistu oikein.
     * @return true jos ratkaisu on oikea, muuten false
     */
    public boolean checkIfSolved() {
        return GridSolver.solved(grid);
    }

    /**
     * Sudokuruudukko.
     * @return sudokuruudukko matriisina
     */
    public int[][] getGrid() {
        return grid;
    }

    /**
     * Asettaa numeron sudokuruudukon ruutuun.
     * @param a ruudun rivinumero
     * @param b ruudun sarakenumero
     * @param number asetettava numero
     */
    public void setModule(int a, int b, int number) {
        grid[a][b] = number;
    }

    /**
     * Sudokuruudukko ja sen sisältämät numerot merkkijonona.
     * @return sudokuruudukko ja sen sisältämät numerot merkkijonona
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sb.append(grid[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}