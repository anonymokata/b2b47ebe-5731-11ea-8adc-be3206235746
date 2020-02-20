import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PencilTest {
    private Pencil pencil;

    @Before
    public void setUp() {
        this.pencil = new Pencil();
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
}
