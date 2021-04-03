package sudoku.domain;

public class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) return false;
        User o = (User) obj;
        return this.username.equals(o.username);
    }
}