package sudoku.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    User user;

    @Before
    public void setUp() {
        user = new User("tester");
    }

    @Test
    public void setsCorrectUsername() { assertEquals("tester", user.getUsername()); }

    @Test
    public void sameUsernameAreEqual() {
        User u2 = new User("tester");
        assertEquals(user, u2);
    }

    @Test
    public void differentUsernameNotEqual() {
        User u2 = new User("U2");
        assertNotEquals(user, u2);
    }

    @Test
    public void differentTypeNotEqual() {
        Object o = new Object();
        assertNotEquals(user, o);
    }
}