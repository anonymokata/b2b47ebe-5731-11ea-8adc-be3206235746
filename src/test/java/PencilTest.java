import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PencilTest {
    private static final String DEFAULT_PAPER = "She sells sea shells";
    private static final String DEFAULT_TEXT = " down by the sea shore";
    private static final Integer DEFAULT_DURABILITY = 40000;
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
    public void whenPencilWritesThenitLosesDurability() {
        assertEquals(DEFAULT_PAPER + DEFAULT_TEXT, pencil.write(DEFAULT_PAPER, DEFAULT_TEXT));
        assertTrue(pencil.getDurability() < DEFAULT_DURABILITY);
    }
}
