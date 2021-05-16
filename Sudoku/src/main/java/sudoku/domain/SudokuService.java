package sudoku.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import sudoku.dao.*;

/**
 * Sovelluslogiikasta huolehtiva luokka
 */
public class SudokuService {

    private final UserDao userDao;
    private User loggedIn;
    private final GameDao gameDao;
    private Game beingSolved;

    public SudokuService(String dbUrl, String userTable, String gameTable) throws SQLException {
        DatabaseHelper dbHelper = new DatabaseHelper(dbUrl, userTable, gameTable);
        this.userDao = new DBUserDao(dbHelper);
        this.gameDao = new DBGameDao(dbHelper, userDao);
    }

    /**
     * Aloittaa uuden pelin
     * @param difficulty pelin vaikeustaso merkkijonona
     * @see Difficulty
     * @return uusi generoitu sudokuruudukko
     */
    public Grid startGame(String difficulty) {
        Difficulty diff = Difficulty.convertToDifficulty(difficulty);
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
     * @throws Exception jos pelin tallentaminen ei onnistu
     */
    public void saveGame(String time) throws Exception {
        beingSolved.setTime(time);
        gameDao.save(beingSolved);
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
     * @throws Exception jos uuden käyttäjän luominen ei onnistu tallentamisvirheen vuoksi
     */
    public boolean createUser(String username) throws Exception {
        if (userDao.getByUsername(username) != null) {
            return false;
        }
        User user = new User(username);
        userDao.create(user);
        return true;
    }
}