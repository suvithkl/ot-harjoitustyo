package sudoku.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import sudoku.domain.User;

/**
 * Tietokantaan tallentava versio UserDao-rajapinnan toteuttavasta luokasta.
 */
public class DBUserDao implements UserDao {

    private List<User> users;
    private final DatabaseHelper db;

    /**
     * Alustaa oliomuuttujat ja hakee jo luodut käyttäjät tietokannasta users-listaan.
     * @param db sovelluslogiikan käyttämä tietokanta-avustaja
     * @throws SQLException jos users-listaa ei saada alustettua tietokannasta
     */
    public DBUserDao(DatabaseHelper db) throws SQLException {
        users = new ArrayList<>();
        this.db = db;
        fillUsersList();
    }

    private void fillUsersList() throws SQLException {
        db.connect();
        ResultSet rs = db.getResultSet("SELECT * FROM " + db.getUserTable());
        if (rs == null) {
            db.closeresultSet();
            db.disconnect();
            return;
        }
        while (rs.next()) {
            User u = new User(rs.getString("name"));
            users.add(u);
        }
        db.closeresultSet();
        db.disconnect();
    }

    /**
     * Luo eli tallentaa uuden käyttäjän tietokantaan.
     * @param user tallennettava käyttäjä
     * @throws SQLException jos tallentaminen ei onnistu
     */
    @Override
    public void create(User user) throws SQLException {
        db.connect();
        db.addToDatabase("INSERT INTO " + db.getUserTable() + " (name) VALUES (?)", user.getUsername());
        db.disconnect();
        users.add(user);
    }

    /**
     * Kaikki tietokantaan luodut käyttäjät.
     * @return kaikki tietokantaan luodut käyttäjät
     */
    @Override
    public List<User> getAll() {
        return users;
    }

    /**
     * Annettua käyttäjänimeä vastaava käyttäjä.
     * @param username käyttäjänimi
     * @return annettua käyttäjänimeä vastaava käyttäjä jos käyttäjä on olemassa, muuten null
     */
    @Override
    public User getByUsername(String username) {
        return users.stream().filter(u->u.getUsername().equals(username)).findFirst().orElse(null);
    }
}