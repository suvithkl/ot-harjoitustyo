package sudoku.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sudoku.dao.GameDao;

public class FakeGameDao implements GameDao {

    private List<Game> games = new ArrayList<>();

    public FakeGameDao(FakeUserDao users) { games.add(new Game(users.getAll().get(0), Difficulty.NORMAL)); }

    @Override
    public void save(Game game) throws SQLException { games.add(game); }

    @Override
    public List<Game> getAll() { return games; }
}