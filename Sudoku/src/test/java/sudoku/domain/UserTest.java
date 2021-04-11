package sudoku.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    User u1;

    @Before
    public void setUp() {
        u1 = new User("test");
    }

    @Test
    public void sameUsernameAreEqual() {
        User u2 = new User("test");
        assertTrue(u1.equals(u2));
    }

    @Test
    public void differentUsernameNotEqual() {
        User u2 = new User("U2");
        assertFalse(u1.equals(u2));
    }

    @Test
    public void differentTypeNotEqual() {
        Object o = new Object();
        assertFalse(u1.equals(o));
    }
}