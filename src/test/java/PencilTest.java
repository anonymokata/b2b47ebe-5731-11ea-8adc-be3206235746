import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PencilTest {
    private Pencil pencil;

    @Before
    public void setUp() {
        this.pencil = new Pencil(40000);
    }

    @Test
    public void whenPencilIsPassedAnEmptyPaperAndEmptyTextItReturnsAnEmptyString() {
        assertEquals("", pencil.write("", ""));
    }

    @Test
    public void whenPencilIsPassedAnEmptyPaperAndTextItReturnsThatText() {
        String text = " down by the sea shore";
        assertEquals(text, pencil.write("", text));
    }

    @Test
    public void whenPencilIsPassedPaperAndTextItWritesTextOnPaper() {
        String expectedText = "She sells sea shells down by the sea shore";
        assertEquals(expectedText, pencil.write("She sells sea shells", " down by the sea shore"));
    }

    @Test
    public void whenPencilIsCreatedWithDurabilityThenGetDurabilityReturnsThatNumber() {
        Pencil freshPencil = new Pencil(100);
        assertEquals(Integer.valueOf(100), freshPencil.getDurability());
    }

    @Test
    public void whenPencilIsDullOnlySpacesAreWritten() {
        String paper = "She sells sea shells";
        String text = " down by the sea shore";
        Pencil dullPencil = new Pencil(0);
        assertEquals(paper + text.replaceAll(".", " "), dullPencil.write(paper, text));
    }
}
