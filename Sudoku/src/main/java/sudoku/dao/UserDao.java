package sudoku.dao;

import java.util.List;

import sudoku.domain.User;

/**
 * Sovelluksen käyttäjien tallentamisen abstrahoiva DAO-rajapinta
 */
public interface UserDao {

    /**
     * Luo eli tallentaa uuden käyttäjän
     * @param user tallennettava käyttäjä
     * @throws Exception jos tallentaminen ei onnistu
     */
    void create(User user) throws Exception;

    /**
     * Kaikki luodut käyttäjät
     * @return kaikki luodut käyttäjät
     */
    List<User> getAll();

    /**
     * Annettua käyttäjänimeä vastaava käyttäjä
     * @param username käyttäjänimi
     * @return annettua käyttäjänimeä vastaava käyttäjä
     */
    User getByUsername(String username);
}