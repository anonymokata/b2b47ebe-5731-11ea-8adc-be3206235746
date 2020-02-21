import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PencilTest {
    private static final String DEFAULT_PAPER = "She sells sea shells";
    private static final String DEFAULT_TEXT = " down by the sea shore";
    private static final Integer DEFAULT_DURABILITY = 100;
    private Pencil pencil;

    @Before
    public void setUp() {
        this.pencil = new Pencil(DEFAULT_DURABILITY);
    }

    @Test
    public void whenPencilIsPassedAnEmptyPaperAndEmptyTextItReturnsAnEmptyString() {
        assertEquals("", pencil.write("", ""));
    }

    @Test
    public void whenPencilIsPassedAnEmptyPaperAndTextItReturnsThatText() {
        assertEquals(DEFAULT_TEXT, pencil.write("", DEFAULT_TEXT));
    }

    @Test
    public void whenPencilIsPassedPaperAndTextItWritesTextOnPaper() {
        assertEquals(DEFAULT_PAPER + DEFAULT_TEXT, pencil.write(DEFAULT_PAPER, DEFAULT_TEXT));
    }

    @Test
    public void whenPencilIsCreatedWithDurabilityThenGetDurabilityReturnsThatNumber() {
        Pencil freshPencil = new Pencil(100);
        assertEquals(Integer.valueOf(100), freshPencil.getDurability());
    }

    @Test
    public void whenPencilIsDullOnlySpacesAreWritten() {
        Pencil dullPencil = new Pencil(0);
        assertEquals(DEFAULT_PAPER + DEFAULT_TEXT.replaceAll(".", " "), dullPencil.write(DEFAULT_PAPER, DEFAULT_TEXT));
    }

    @Test
    public void whenPencilWritesItLosesDurability() {
        assertEquals(DEFAULT_PAPER + DEFAULT_TEXT, pencil.write(DEFAULT_PAPER, DEFAULT_TEXT));
        assertTrue(pencil.getDurability() < DEFAULT_DURABILITY);
    }

    @Test
    public void whenPencilGoesDullRemainingTextIsSpaces() {
        Pencil almostDullPencil = new Pencil(10);
        String writtenText = DEFAULT_TEXT.substring(0, 14);
        String remainingText = DEFAULT_TEXT.substring(14);
        String expectedText = DEFAULT_PAPER + writtenText + remainingText.replaceAll(".", " ");
        assertEquals(expectedText, almostDullPencil.write(DEFAULT_PAPER, DEFAULT_TEXT));
    }

    @Test
    public void whenPencilWritesLowercaseLetterItLosesOnePointDurability() {
        pencil.write("", "a");
        assertEquals(pencil.getDurability(), Integer.valueOf(DEFAULT_DURABILITY - 1));
    }

    @Test
    public void whenPencilWritesNewLineOrSpaceItDoesNotLoseDurability() {
        pencil.write(DEFAULT_PAPER, " ");
        assertEquals(pencil.getDurability(), DEFAULT_DURABILITY);
    }

    @Test
    public void whenPencilWritesUppercaseLetterItLosesTwoPointsDurability() {
        pencil.write(DEFAULT_PAPER, "A");
        assertEquals(pencil.getDurability(), Integer.valueOf(DEFAULT_DURABILITY - 2));
    }

    @Test
    public void whenPencilHasOneDurabilityAndEncountersUppercaseItWritesASpace() {
        Pencil toBeDullPencil = new Pencil(1);
        toBeDullPencil.write(DEFAULT_PAPER, "A");
        assertEquals(toBeDullPencil.getDurability(), Integer.valueOf(0));
    }

    @Test
    public void whenPencilIsDullAndTextIsSpaceThenItWritesSpace() {
        Pencil dullPencil = new Pencil(0);
        String outcome = dullPencil.write("", " ");
        assertEquals(dullPencil.getDurability(), Integer.valueOf(0));
        assertEquals(outcome, " ");
    }
}
