package sudoku.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sudoku.dao.UserDao;

public class FakeUserDao implements UserDao {

    private List<User> users = new ArrayList<>();

    public FakeUserDao() {
        users.add(new User("tester"));
    }

    @Override
    public User create(User user) throws SQLException {
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