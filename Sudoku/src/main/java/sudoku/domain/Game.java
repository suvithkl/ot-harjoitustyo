package sudoku.domain;

public class Game {

    private int id;
    private User user;
    private Grid grid;
    private Difficulty difficulty;
    private int lenght;
    private boolean solved;

    public Game(User user, Difficulty diff) {
        this.user = user;
        this.grid = new Grid(diff);
        this.difficulty = diff;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Game)) {
            return false;
        }
        Game o = (Game) obj;
        return this.id == o.id;
    }
}