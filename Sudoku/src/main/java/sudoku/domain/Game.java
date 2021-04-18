package sudoku.domain;

public class Game {

    private int id;
    private User user;
    private Grid grid;
    private difficulty difficulty;
    private int lenght;
    private boolean solved;

    public Game(User user, difficulty diff) {
        this.user = user;
        this.grid = new Grid(diff);
        this.difficulty = diff;
    }

    public enum difficulty {NORMAL}

    public Game.difficulty getDifficulty() { return difficulty; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Game)) return false;
        Game o = (Game) obj;
        return this.id == o.id;
    }
}