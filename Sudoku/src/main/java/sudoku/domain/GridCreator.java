package sudoku.domain;

import java.util.Random;

/**
 * Sudokun peliruudukkojen luomisesta eli täyttämisestä huolehtiva luokka.
 */
public class GridCreator {

    /**
     * Täyttää sudokuruudukon sudokun sääntöjen mukaisesti.
     * (eli sama numero ilmestyy vain kerran per rivi, sarake ja 3x3 osaruudukko)
     * Yksinkertaisin mahdollinen täyttö eli siirretään numerojärjestyksessä olevia rivejä aina hieman vaakasuunnassa
     * rivi kerrallaan (ks. test.domain.GridCreatorTest missä kyseinen ruudukko on muuttujataulukko baseGrid)
     * @param grid tyhjä sudokuruudukko matriisina
     */
    public static void fill(int[][] grid) {
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

    /**
     * Sekoittaa rivien tai sarakkeiden järjestyksen per kolmen rivin tai sarakkeen ryhmä.
     * @param type jos sekoitetaan rivit niin "row", jos kolumnit niin "column"
     * @param grid sudokuruudukko matriisina
     */
    public static void randomize(String type, int[][] grid) {
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
                swapRows(a, b, grid);
            } else if (type.equals("column")) {
                swapColumns(a, b, grid);
            }
        }
    }

    private static void swapRows(int a, int b, int[][] grid) {
        int temp;
        for (int i = 0; i < 9; i++) {
            temp = grid[a][i];
            grid[a][i] = grid[b][i];
            grid[b][i] = temp;
        }
    }

    private static void swapColumns(int a, int b, int[][] grid) {
        int temp;
        for (int i = 0; i < 9; i++) {
            temp = grid[i][a];
            grid[i][a] = grid[i][b];
            grid[i][b] = temp;
        }
    }

    /**
     * Muodostaa ruudukosta (eli matriisista) transpoosin satunnaisuuden lisäämiseksi.
     * @param grid sudokuruudukko matriisina
     * @return sudokuruudukosta tehty transpoosi
     */
    public static int[][] transpose(int[][] grid) {
        int[][] tempGrid = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tempGrid[i][j] = grid[j][i];
            }
        }
        return tempGrid;
    }

    /**
     * Tyhjentää vaikeustason määräämän määrän sudokuruudukon ruutuja.
     * @param emptyModules montako ruutua vaikeustaso määrää tyhjentämään
     * @param grid sudokuruudukko matriisina
     */
    public static void makeModulesEmpty(int emptyModules, int[][] grid) {
        Random rnd = new Random();
        for (int i = 0; i < emptyModules; i++) {
            int j = 0;
            while (true) {
                j++;
                int a = rnd.nextInt(9);
                int b = rnd.nextInt(9);
                if (grid[a][b] != 0) {
                    if (canErase(a, b, grid)) {
                        grid[a][b] = 0;
                        break;
                    }
                }
                if (j > 500) {
                    break;
                }
            }
        }
    }

    // Tutkii, voidaanko rivinumeron a ja sarakenumeron b määräämä ruutu tyhjentää
    private static boolean canErase(int a, int b, int[][] grid) {
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
}