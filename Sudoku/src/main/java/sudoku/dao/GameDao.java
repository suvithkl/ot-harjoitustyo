package sudoku.dao;

import java.util.List;

import sudoku.domain.Game;

/**
 * Sudokupelien tallentamisen abstrahoiva DAO-rajapinta
 */
public interface GameDao {

    /**
     * Tallentaa pelin
     * @param game tallennettava peli
     * @throws Exception jos tallentaminen ei onnistu
     */
    void save(Game game) throws Exception;

    /**
     * Kaikki tallennetut pelit
     * @return kaikki tallennetut pelit
     */
    List<Game> getAll();
}