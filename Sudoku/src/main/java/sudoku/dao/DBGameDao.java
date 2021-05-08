package sudoku.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import sudoku.domain.Difficulty;
import sudoku.domain.Game;
import sudoku.domain.User;

public class DBGameDao implements GameDao {

    private List<Game> games;
    private DatabaseHelper db;

    public DBGameDao(DatabaseHelper db, UserDao users) throws SQLException {
        games = new ArrayList<>();
        this.db = db;
        fillGamesList(users);
    }

    private void fillGamesList(UserDao users) throws SQLException {
        db.connect();
        ResultSet rs = db.getResultSet("SELECT * FROM Game");
        if (rs == null) {
            db.disconnect();
            return;
        }
        while (rs.next()) {
            String username = rs.getString("name");
            User user = users.getByUsername(username);
            String diff = rs.getString("difficulty");
            Difficulty difficulty = convertToDifficulty(diff);
            Game g = new Game(user, difficulty);
            String time = rs.getString("time");
            g.setTime(time);
            games.add(g);
        }
        db.disconnect();
    }

    protected Difficulty convertToDifficulty(String diff) {
        if (diff.equals(Difficulty.EASY.name())) {
            return Difficulty.EASY;
        } else if (diff.equals(Difficulty.NORMAL.name())) {
            return Difficulty.NORMAL;
        } else if (diff.equals(Difficulty.HARD.name())) {
            return Difficulty.HARD;
        } else {
            return null;
        }
    }

    @Override
    public Game save(Game game) throws SQLException {
        db.connect();
        db.updateDatabase("INSERT INTO Game (time,difficulty,name) VALUES (?,?,?)", game.getTime(),
                game.getDifficulty().name(), game.getUser().getUsername());
        db.disconnect();
        games.add(game);
        return game;
    }

    @Override
    public List<Game> getAll() {
        return games;
    }
}