package sudoku.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import sudoku.domain.User;

public class DBUserDao implements UserDao {

    private List<User> users;
    private DatabaseHelper db;

    public DBUserDao(DatabaseHelper db) throws SQLException {
        users = new ArrayList<>();
        this.db = db;
        ResultSet rs = db.getResultSet("SELECT * FROM User");
        if (rs == null) return;
        while (rs.next()) {
            User u = new User(rs.getString("name"));
            u.setId(rs.getInt("id"));
            users.add(u);
        }
    }

    private void save() throws SQLException {

    }

    @Override
    public User create(User user) throws SQLException {
//        user.setId();
        save();
        users.add(user);
        return user;
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public User findUser(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) return users.get(i);
        }
        return null;
    }
}