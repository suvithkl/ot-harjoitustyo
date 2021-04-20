package sudoku.domain;

import sudoku.dao.GameDao;
import sudoku.dao.UserDao;

public class SudokuService {

    private UserDao userDao;
    private User loggedIn;
    private GameDao gameDao;
    private Game beingSolved;

    public SudokuService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Grid startGame() {
        this.beingSolved = new Game(loggedIn, Difficulty.NORMAL);
        return beingSolved.getGrid();
    }

    /*
     * kirjautuminen sisään
     */
    public boolean login(String username) {
        User user = userDao.findUser(username);
        if (user == null) {
            return false;
        }
        loggedIn = user;
        return true;
    }

    public User getLoggedIn() {
        return loggedIn;
    }

    /*
     * kirjautuminen ulos
     */
    public void logout() {
        loggedIn = null;
    }

    /*
     * käyttäjän luominen
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