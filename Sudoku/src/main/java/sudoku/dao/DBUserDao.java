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
        db.connect();
        ResultSet rs = db.getResultSet("SELECT * FROM User");
        if (rs == null) {
            return;
        }
        while (rs.next()) {
            User u = new User(rs.getString("name"));
//            u.setId(rs.getInt("id"));
            users.add(u);
        }
        db.disconnect();
    }

    @Override
    public User create(User user) throws SQLException {
        db.connect();
        db.updateDatabase("INSERT INTO User (name) VALUES (?)", user.getUsername());
//        ResultSet rs = db.getResultSet("SELECT id FROM User WHERE name = " + user.getUsername());
//        user.setId(rs.getInt("id"));
        db.disconnect();
        users.add(user);
        return user;
    }

//    ONKO TARPEELLINEN
    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public User findUser(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return users.get(i);
            }
        }
        return null;
    }
}