package sudoku.domain;

public class User {
//    Integer id;
    private String username;

    public User(String username) {
        this.username = username;
    }

//    LISÄÄ JOS TULEE TARVETTA
//    --------
//    public void setId(Integer id) { this.id = id; }
//    public Integer getId() { return id; }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        User o = (User) obj;
        return this.username.equals(o.username);
    }
}