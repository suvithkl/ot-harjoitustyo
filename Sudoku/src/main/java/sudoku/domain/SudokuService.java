package sudoku.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
     * Aloittaa uuden pelin
     * @param diff pelin vaikeustaso enumina
     * @see Difficulty
     * @return uusi generoitu sudokuruudukko
     */
    public Grid startGame(Difficulty diff) {
        this.beingSolved = new Game(loggedIn, diff);
        return beingSolved.getGrid();
    }

    /**
     * Tarkistaa onko parhaillaan pelattava sudoku ratkaistu oikein
     * @return true jos ratkaisu on oikea, muuten false
     */
    public boolean checkGame() {
        return beingSolved.checkIfSolved();
    }

    /**
     * Tallentaa pelituloksen
     * @param time pelatun pelin kesto muodossa '00:00'
     * @return true jos pelituloksen tallentaminen onnistui, muuten false
     */
    public boolean saveGame(String time) {
        beingSolved.setTime(time);
        try {
            gameDao.save(beingSolved);
        } catch (Exception e) {
            System.out.println("Exception in saveGame: " + e);
            return false;
        }
        return true;
    }

    /**
     * Asettaa numeron ratkaistavan pelin sudokuruudukon ruutuun
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
     * Kaikki ratkaistut sudokupelit
     * @return kaikki ratkaistut sudokupelit
     */
    public List<String> getSolved() {
        List<String> list = new ArrayList<>();
        List<Game> temp = gameDao.getAll().stream().sorted(Comparator.comparing(Game::getTime)).collect(Collectors.toList());
        for (Game g : temp) {
            String s = g.getUser().getUsername() + ";" + g.getTime() + ";" + g.getDifficulty().name();
            list.add(s);
        }
        return list;
    }

    /**
     * Kirjaa käyttäjän sisään
     * @param username käyttäjänimi
     * @return true jos käyttäjänimi on olemassa, muuten false
     */
    public boolean login(String username) {
        User user = userDao.getByUsername(username);
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
     * Kirjaa käyttäjän ulos
     */
    public void logout() {
        loggedIn = null;
    }

    /**
     * Luo uuden käyttäjän
     * @param username käyttäjänimi
     * @return true jos käyttäjän luominen onnistui, muuten false
     */
    public boolean createUser(String username) {
        if (userDao.getByUsername(username) != null) {
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