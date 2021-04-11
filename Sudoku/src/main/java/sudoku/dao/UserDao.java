package sudoku.dao;

import java.sql.*;
import java.util.List;
import sudoku.domain.User;

public interface UserDao {

    User create(User user) throws SQLException;

    List<User> getAll();

    User findUser(String username);
}