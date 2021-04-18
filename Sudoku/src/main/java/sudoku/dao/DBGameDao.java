package sudoku.dao;

import sudoku.domain.Game;

import java.sql.*;
import java.util.List;

public class DBGameDao implements GameDao {

    @Override
    public Game save(Game game) throws SQLException {
        return null;
    }

    @Override
    public List<Game> getAll() {
        return null;
    }
}