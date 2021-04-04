package sudoku.domain;

import java.sql.*;
import sudoku.dao.UserDao;

public class SudokuService {

    private UserDao userDao;
    private User loggedIn;

    /*
     * kirjautuminen sisään
     */
    public boolean login(String username) throws SQLException {
        User user = userDao.findUser(username);
        if (user == null) return false;
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
    public boolean createUser(String username) throws SQLException{
        if (userDao.findUser(username) != null) return false;
        User user = new User(username);
        try {
            userDao.create(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}