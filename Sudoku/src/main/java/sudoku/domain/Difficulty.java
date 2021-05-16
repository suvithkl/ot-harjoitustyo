package sudoku.domain;

/**
 * Pelin vaikeustasoa edustava enum.
 */
public enum Difficulty {
    EASY (40),
    NORMAL (50),
    HARD (60);

    private final int emptyModules;

    Difficulty(int empty) {
        this.emptyModules = empty;
    }

    int getEmptyModules() {
        return emptyModules;
    }

    /**
     * Muuntaa vaikeustason merkkijonosta Difficulty-enumiksi.
     * @param diff vaikeustaso merkkijonona
     * @return vaikeustaso enumina
     * @see Difficulty
     */
    public static Difficulty convertToDifficulty(String diff) {
        if (diff.equals(Difficulty.EASY.name())) {
            return Difficulty.EASY;
        } else if (diff.equals(Difficulty.NORMAL.name())) {
            return Difficulty.NORMAL;
        } else if (diff.equals(Difficulty.HARD.name())) {
            return Difficulty.HARD;
        } else {
            return null;
        }
    }
}