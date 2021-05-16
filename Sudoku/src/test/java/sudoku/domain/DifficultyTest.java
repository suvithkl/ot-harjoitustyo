package sudoku.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class DifficultyTest {

    @Test
    public void representCorrectAmountsOfEmptyModules() {
        assertEquals(40, Difficulty.EASY.getEmptyModules());
        assertEquals(50, Difficulty.NORMAL.getEmptyModules());
        assertEquals(60, Difficulty.HARD.getEmptyModules());
    }

    @Test
    public void returnsCorrectDifficultyEnum() {
        assertEquals(Difficulty.convertToDifficulty("EASY"), Difficulty.EASY);
        assertEquals(Difficulty.convertToDifficulty("NORMAL"), Difficulty.NORMAL);
        assertEquals(Difficulty.convertToDifficulty("HARD"), Difficulty.HARD);
    }

    @Test
    public void doesNotConvertIncorrectStrings() { assertNull(Difficulty.convertToDifficulty("FINALBOSS")); }
}