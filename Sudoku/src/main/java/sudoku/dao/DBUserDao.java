package sudoku.dao;

import java.sql.*;
import java.util.List;
import sudoku.domain.User;

public class DBUserDao implements UserDao {

    private List<User> users;
    private String file;

    @Override
    public User create(User user) throws SQLException {
        users.add(user);
        // kesken
        return user;
    }

    @Override
    public User findUser(String username) throws SQLException {
        return null;
    }

    @Override
    public List<User> getAll() throws SQLException {
        return users;
    }
}