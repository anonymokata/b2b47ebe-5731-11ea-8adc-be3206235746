import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PencilTest {
    private static final String DEFAULT_PAPER = "She sells sea shells";
    private static final String DEFAULT_TEXT = " down by the sea shore";
    private static final Integer DEFAULT_DURABILITY = 100;
    private static final Integer DEFAULT_LENGTH = 10;
    private Pencil pencil;

    @Before
    public void setUp() {
        this.pencil = new Pencil(DEFAULT_DURABILITY, DEFAULT_LENGTH);
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
    public void whenPencilIsCreatedWithDurabilityThenGetCurrentDurabilityReturnsThatNumber() {
        Pencil freshPencil = new Pencil(100, DEFAULT_LENGTH);
        assertEquals(Integer.valueOf(100), freshPencil.getCurrentDurability());
    }

    @Test
    public void whenPencilIsDullOnlySpacesAreWritten() {
        Pencil dullPencil = new Pencil(0, DEFAULT_LENGTH);
        assertEquals(DEFAULT_PAPER + DEFAULT_TEXT.replaceAll(".", " "), dullPencil.write(DEFAULT_PAPER, DEFAULT_TEXT));
    }

    @Test
    public void whenPencilWritesItLosesDurability() {
        assertEquals(DEFAULT_PAPER + DEFAULT_TEXT, pencil.write(DEFAULT_PAPER, DEFAULT_TEXT));
        assertTrue(pencil.getCurrentDurability() < DEFAULT_DURABILITY);
    }

    @Test
    public void whenPencilGoesDullRemainingTextIsSpaces() {
        Pencil almostDullPencil = new Pencil(10, DEFAULT_LENGTH);
        String writtenText = DEFAULT_TEXT.substring(0, 14);
        String remainingText = DEFAULT_TEXT.substring(14);
        String expectedText = DEFAULT_PAPER + writtenText + remainingText.replaceAll(".", " ");
        assertEquals(expectedText, almostDullPencil.write(DEFAULT_PAPER, DEFAULT_TEXT));
    }

    @Test
    public void whenPencilWritesLowercaseLetterItLosesOnePointDurability() {
        pencil.write("", "a");
        assertEquals(pencil.getCurrentDurability(), Integer.valueOf(DEFAULT_DURABILITY - 1));
    }

    @Test
    public void whenPencilWritesNewLineOrSpaceItDoesNotLoseDurability() {
        pencil.write(DEFAULT_PAPER, " ");
        assertEquals(pencil.getCurrentDurability(), DEFAULT_DURABILITY);
    }

    @Test
    public void whenPencilWritesUppercaseLetterItLosesTwoPointsDurability() {
        pencil.write(DEFAULT_PAPER, "A");
        assertEquals(pencil.getCurrentDurability(), Integer.valueOf(DEFAULT_DURABILITY - 2));
    }

    @Test
    public void whenPencilHasOneDurabilityAndEncountersUppercaseItWritesASpace() {
        Pencil toBeDullPencil = new Pencil(1, DEFAULT_LENGTH);
        toBeDullPencil.write(DEFAULT_PAPER, "A");
        assertEquals(toBeDullPencil.getCurrentDurability(), Integer.valueOf(0));
    }

    @Test
    public void whenPencilIsDullAndTextIsSpaceThenItWritesSpace() {
        Pencil dullPencil = new Pencil(0, DEFAULT_LENGTH);
        String outcome = dullPencil.write("", " ");
        assertEquals(dullPencil.getCurrentDurability(), Integer.valueOf(0));
        assertEquals(outcome, " ");
    }

    @Test
    public void whenPencilIsSharpenedItsDurabilityResetsToInitialValue() {
        pencil.write(DEFAULT_PAPER, DEFAULT_TEXT);
        assertTrue(pencil.getCurrentDurability() < DEFAULT_DURABILITY);
        pencil.sharpen();
        assertEquals(pencil.getCurrentDurability(), DEFAULT_DURABILITY);
    }

    @Test
    public void whenPencilIsCreatedWithLengthThenGetLengthReturnsThatLength() {
        assertEquals(DEFAULT_LENGTH, pencil.getLength());
    }

    @Test
    public void whenPencilIsSharpenedThenItsLengthIsReducedByOne() {
        assertEquals(DEFAULT_LENGTH, pencil.getLength());
        pencil.write("", "abc");
        pencil.sharpen();
        assertTrue(pencil.getLength() < DEFAULT_LENGTH);
    }

    @Test
    public void whenPencilLengthIsZeroThenSharpeningPencilDoesNotRestoreDurability() {
        Pencil almostDullPencil = new Pencil(0, 0);
        assertEquals(Integer.valueOf(0), almostDullPencil.getCurrentDurability());
        almostDullPencil.sharpen();
        assertEquals(Integer.valueOf(0), almostDullPencil.getCurrentDurability());
    }

    @Test
    public void whenPencilIsAtMaximumDurabilityThenSharpeningDoesNotCausePencilToLoseLength() {
        assertEquals(DEFAULT_DURABILITY, pencil.getCurrentDurability());
        assertEquals(DEFAULT_LENGTH, pencil.getLength());
        pencil.sharpen();
        assertEquals(DEFAULT_LENGTH, pencil.getLength());
    }
}
