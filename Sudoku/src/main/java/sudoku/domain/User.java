package sudoku.domain;

/**
 * Sovelluksen käyttäjää edustava luokka.
 */
public class User {

    private final String username;

    /**
     * Alustaa käyttäjä-olion.
     * @param username käyttäjänimi
     */
    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Vertailee ovatko kaksi käyttäjää samat.
     * @param obj toinen käyttäjä johon kyseistä käyttäjää verrataan
     * @return true jos käyttäjänimet ovat samat, muuten false
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        User o = (User) obj;
        return this.username.equals(o.username);
    }
}