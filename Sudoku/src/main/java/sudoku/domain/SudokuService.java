package sudoku.domain;

import java.util.ArrayList;
import java.util.List;
import sudoku.dao.GameDao;
import sudoku.dao.UserDao;

/**
 * Sovelluslogiikasta huolehtiva luokka
 */
public class SudokuService {

    private UserDao userDao;
    private User loggedIn;
    private GameDao gameDao;
    private Game beingSolved;

    public SudokuService(UserDao userDao, GameDao gameDao) {
        this.userDao = userDao;
        this.gameDao = gameDao;
    }

    /**
     * Uuden pelin aloittaminen
     * @param diff pelin vaikeustaso enumina
     * @see Difficulty
     * @return uusi generoitu sudokuruudukko
     */
    public Grid startGame(Difficulty diff) {
        this.beingSolved = new Game(loggedIn, diff);
        return beingSolved.getGrid();
    }

    /**
     * Tarkistaminen, onko parhaillaan pelattava sudoku ratkaistu oikein
     * @return true jos ratkaisu on oikea, muuten false
     */
    public boolean checkGame() {
        return beingSolved.checkIfSolved();
    }

    /**
     * Pelituloksen tallentaminen
     * @param time pelatun pelin kesto sekunteina
     * @return true jos pelituloksen tallentaminen onnistui, muuten false
     */
    public boolean saveGame(long time) {
        beingSolved.setTime(Long.toString(time));
        try {
            gameDao.save(beingSolved);
        } catch (Exception e) {
            System.out.println("Exception in saveGame: " + e);
            return false;
        }
        return true;
    }

    /**
     * Numeron asettaminen ratkaistavan pelin sudokuruudukon ruutuun
     * @param a ruudun rivinumero
     * @param b ruudun sarakenumero
     * @param number asetettava numero
     * @return true jos oikeaan ruutuun asetettiin oikea numero, muuten false
     */
    public boolean setModule(int a, int b, int number) {
        beingSolved.setModule(a, b, number);
        return beingSolved.getGrid().getGrid()[a][b] == number;
    }

    /**
     * Ratkaistut sudokupelit
     * @return ratkaistut sudokupelit
     */
    public List<String> getSolved() {
        List<String> list = new ArrayList<>();
        for (Game g : gameDao.getAll()) {
            String s = g.getUser().getUsername() + ": " + g.getTime() + " seconds with difficulty " + g.getDifficulty().name();
            list.add(s);
        }
        return list;
    }

    /**
     * Kirjautuminen sisään
     * @param username käyttäjänimi
     * @return true jos käyttäjänimi on olemassa, muuten false
     */
    public boolean login(String username) {
        User user = userDao.findUser(username);
        if (user == null) {
            return false;
        }
        loggedIn = user;
        return true;
    }

    /**
     * Kirjautunut käyttäjä
     * @return kirjautunut käyttäjä
     */
    public User getLoggedIn() {
        return loggedIn;
    }

    /**
     * Kirjautuminen ulos
     */
    public void logout() {
        loggedIn = null;
    }

    /**
     * Käyttäjän luominen
     * @param username käyttäjänimi
     * @return true jos käyttäjän luominen onnistui, muuten false
     */
    public boolean createUser(String username) {
        if (userDao.findUser(username) != null) {
            return false;
        }
        User user = new User(username);
        try {
            userDao.create(user);
        } catch (Exception e) {
            System.out.println("Exception in createUser: " + e);
            return false;
        }
        return true;
    }
}