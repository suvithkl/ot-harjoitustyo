package sudoku.domain;

public class User {
    Integer id;
    private String username;

    public User(String username) {
        this.id = 0; // TIETOKANNAN SEURAAVA KÄYTTÄMÄTÖN NUMERO
        this.username = username;
    }

    public Integer getId() {
        return id;
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