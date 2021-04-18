package sudoku.dao;

import java.sql.SQLException;
import java.util.List;
import sudoku.domain.Game;

public interface GameDao {

    Game save(Game game) throws SQLException;

    List<Game> getAll();
}