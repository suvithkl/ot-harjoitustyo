package sudoku.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import sudoku.domain.Difficulty;
import sudoku.domain.Game;
import sudoku.domain.User;

/**
 * Tietokantaan tallentava versio GameDao-rajapinnan toteuttavasta luokasta
 */
public class DBGameDao implements GameDao {

    private List<Game> games;
    private final DatabaseHelper db;

    /**
     * Alustaa oliomuuttujat ja hakee jo tallennetut pelit tietokannasta games-listaan
     */
    public DBGameDao(DatabaseHelper db, UserDao users) throws SQLException {
        games = new ArrayList<>();
        this.db = db;
        fillGamesList(users);
    }

    private void fillGamesList(UserDao users) throws SQLException {
        db.connect();
        ResultSet rs = db.getResultSet("SELECT * FROM " + db.getGameTable());
        if (rs == null) {
            db.disconnect();
            return;
        }
        while (rs.next()) {
            String username = rs.getString("name");
            User user = users.getByUsername(username);
            String diff = rs.getString("difficulty");
            Difficulty difficulty = Difficulty.convertToDifficulty(diff);
            Game g = new Game(user, difficulty);
            String time = rs.getString("time");
            g.setTime(time);
            games.add(g);
        }
        db.disconnect();
    }

    /**
     * Tallentaa pelituloksen (aika, vaikeustaso, käyttäjänimi) tietokantaan
     * @param game tallennettava peli
     * @throws SQLException jos tallentaminen ei onnistu
     */
    @Override
    public void save(Game game) throws SQLException {
        db.connect();
        db.addToDatabase("INSERT INTO " + db.getGameTable() + " (time,difficulty,name) VALUES (?,?,?)",
                game.getTime(), game.getDifficulty().name(), game.getUser().getUsername());
        db.disconnect();
        games.add(game);
    }

    /**
     * Kaikki tietokantaan tallennetut pelit
     * @return kaikki tietokantaan tallennetut pelit
     */
    @Override
    public List<Game> getAll() {
        return games;
    }
}