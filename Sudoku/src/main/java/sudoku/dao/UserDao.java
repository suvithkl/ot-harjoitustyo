package sudoku.dao;

import java.sql.SQLException;
import java.util.List;
import sudoku.domain.User;

public interface UserDao {

    User create(User user) throws SQLException;

    List<User> getAll();

    User findUser(String username);
}