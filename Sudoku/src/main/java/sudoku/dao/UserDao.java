package sudoku.dao;

import java.sql.*;
import java.util.List;
import sudoku.domain.User;

public interface UserDao {
    User create(User user) throws SQLException;
    User findUser(String username) throws SQLException;
    List<User> getAll() throws SQLException;
}