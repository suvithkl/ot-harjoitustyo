package sudoku.domain;

/**
 * Sudokupeliä edustava luokka
 */
public class Game {

    private User user;
    private Grid grid;
    private Difficulty difficulty;
    private String time;

    public Game(User user, Difficulty diff) {
        this.user = user;
        this.grid = new Grid(diff);
        this.difficulty = diff;
    }

    /**
     * Tarkistaa onko kyseinen sudokupeli ratkaistu oikein
     * @return true jos ratkaisu on oikea, muuten false
     */
    public boolean checkIfSolved() {
        return this.grid.checkIfSolved();
    }

    /**
     * Pelin sudokuruudukko
     * @return pelin sudokuruudukko
     */
    public Grid getGrid() {
        return this.grid;
    }

    /**
     * Pelin vaikeustaso
     * @return pelin vaikeustaso
     * @see Difficulty
     */
    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    /**
     * Peliä ratkaiseva pelaaja
     * @return peliä ratkaiseva pelaaja
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Asettaa numeron pelin sudokuruudukon ruutuun
     * @param a ruudun rivinumero
     * @param b ruudun sarakenumero
     * @param number asetettava numero
     */
    public void setModule(int a, int b, int number) {
        grid.setModule(a, b, number);
    }

    /**
     * Pelin ratkaisemiseen käytetty aika
     * @return peliaika merkkijonona
     */
    public String getTime() {
        return this.time;
    }

    /**
     * Asettaa pelin ratkaisemiseen käytetyn ajan
     * @param time peliaika merkkijonona
     */
    public void setTime(String time) {
        this.time = time;
    }
}