package sudoku.dao;

import java.util.List;
import sudoku.domain.User;

public interface UserDao {
    User create(User user) throws Exception;
    User findUser(String username);
    List<User> getAll();
}