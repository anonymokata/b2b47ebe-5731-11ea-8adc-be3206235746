import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PencilTest {
    @Test
    public void whenPencilIsPassedAnEmptyPaperAndEmptyTextItReturnsAnEmptyString() {
        Pencil pencil = new Pencil();
        assertEquals("", pencil.write("", ""));
    }
}
