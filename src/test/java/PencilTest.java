import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PencilTest {
    private static final String DEFAULT_PAPER = "She sells sea shells";
    private static final String DEFAULT_PAPER_MISSING_SEA = "She sells     shells";
    private static final String DEFAULT_TEXT = " down by the sea shore";
    private static final Integer DEFAULT_ERASER_DURABILITY = 150;
    private static final Integer DEFAULT_POINT_DURABILITY = 100;
    private static final Integer DEFAULT_LENGTH = 10;
    private static final String SEA = "sea";
    private Pencil pencil;
    private Pencil almostDullPencil;
    private Pencil dullPencil;

    @Before
    public void setUp() {
        this.pencil = new Pencil(DEFAULT_POINT_DURABILITY, DEFAULT_LENGTH, DEFAULT_ERASER_DURABILITY);
        this.dullPencil = new Pencil(0, 0, 0);
        this.almostDullPencil = new Pencil(10, 0, 2);
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
        assertEquals(Integer.valueOf(DEFAULT_POINT_DURABILITY), pencil.getPointDurability());
    }

    @Test
    public void whenPencilIsDullOnlySpacesAreWritten() {
        assertEquals(DEFAULT_PAPER + DEFAULT_TEXT.replaceAll(".", " "), dullPencil.write(DEFAULT_PAPER, DEFAULT_TEXT));
    }

    @Test
    public void whenPencilWritesItLosesDurability() {
        assertEquals(DEFAULT_PAPER + DEFAULT_TEXT, pencil.write(DEFAULT_PAPER, DEFAULT_TEXT));
        assertTrue(pencil.getPointDurability() < DEFAULT_POINT_DURABILITY);
    }

    @Test
    public void whenPencilGoesDullRemainingTextIsSpaces() {
        String expectedText = DEFAULT_PAPER + DEFAULT_TEXT.substring(0, 14) + DEFAULT_TEXT.substring(14).replaceAll(".", " ");
        assertEquals(expectedText, almostDullPencil.write(DEFAULT_PAPER, DEFAULT_TEXT));
    }

    @Test
    public void whenPencilWritesLowercaseLetterItLosesOnePointDurability() {
        pencil.write("", "a");
        assertEquals(pencil.getPointDurability(), Integer.valueOf(DEFAULT_POINT_DURABILITY - 1));
    }

    @Test
    public void whenPencilWritesNewLineOrSpaceItDoesNotLoseDurability() {
        pencil.write(DEFAULT_PAPER, " ");
        pencil.write(DEFAULT_PAPER, "\n");
        assertEquals(pencil.getPointDurability(), DEFAULT_POINT_DURABILITY);
    }

    @Test
    public void whenPencilWritesUppercaseLetterItLosesTwoPointsDurability() {
        pencil.write(DEFAULT_PAPER, "A");
        assertEquals(pencil.getPointDurability(), Integer.valueOf(DEFAULT_POINT_DURABILITY - 2));
    }

    @Test
    public void whenPencilHasOneDurabilityAndEncountersUppercaseItWritesASpace() {
        almostDullPencil.write(DEFAULT_PAPER, "aaaaaaaaaA");
        assertEquals(almostDullPencil.getPointDurability(), Integer.valueOf(0));
    }

    @Test
    public void whenPencilIsDullAndTextIsSpaceThenItWritesSpace() {
        String modifiedPaper = dullPencil.write("", " ");
        assertEquals(dullPencil.getPointDurability(), Integer.valueOf(0));
        assertEquals(modifiedPaper, " ");
    }

    @Test
    public void whenPencilIsSharpenedItsDurabilityResetsToInitialValue() {
        pencil.write(DEFAULT_PAPER, DEFAULT_TEXT);
        assertTrue(pencil.getPointDurability() < DEFAULT_POINT_DURABILITY);
        pencil.sharpen();
        assertEquals(pencil.getPointDurability(), DEFAULT_POINT_DURABILITY);
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
        assertEquals(Integer.valueOf(0), dullPencil.getPointDurability());
        dullPencil.sharpen();
        assertEquals(Integer.valueOf(0), dullPencil.getPointDurability());
    }

    @Test
    public void whenPencilIsAtMaximumDurabilityThenSharpeningDoesNotCausePencilToLoseLength() {
        assertEquals(DEFAULT_LENGTH, pencil.getLength());
        pencil.sharpen();
        assertEquals(DEFAULT_LENGTH, pencil.getLength());
    }

    @Test
    public void whenPencilErasesOnEmptyPaperThenEmptyPaperIsReturned() {
        assertEquals("", pencil.erase("", ""));
    }

    @Test
    public void whenPencilDoesNotFindTargetToEraseThenPaperRemainsUnmodified() {
        assertEquals(pencil.erase(DEFAULT_PAPER, "Test"), DEFAULT_PAPER);
    }

    @Test
    public void whenPencilFindsTargetToEraseInPaperItThenReplacesTargetWithSpaces() {
        assertEquals(DEFAULT_PAPER_MISSING_SEA, pencil.erase(DEFAULT_PAPER, SEA));
    }

    @Test
    public void whenPencilFindsMultipleInstancesOfTargetTextItErasesLastInstanceOfTargetText() {
        String fullPaper = DEFAULT_PAPER + DEFAULT_TEXT;
        int lastIndex = (fullPaper).lastIndexOf(SEA);
        assertEquals(fullPaper.substring(0, lastIndex) + "   " + fullPaper.substring(lastIndex + 3), pencil.erase(fullPaper, SEA));
    }

    @Test
    public void whenPencilIsCreatedThenGetEraserDurabilityReturnsThatEraserDurability() {
        assertEquals(Integer.valueOf(150), pencil.getEraserDurability());
    }

    @Test
    public void whenEraserIsDullThenTargetTextIsNotErased() {
        assertEquals(DEFAULT_PAPER, dullPencil.erase(DEFAULT_PAPER, SEA));
    }

    @Test
    public void whenEraserErasesThenItLosesDurability() {
        assertEquals(DEFAULT_ERASER_DURABILITY, pencil.getEraserDurability());
        pencil.erase(DEFAULT_PAPER, SEA);
        assertTrue(pencil.getEraserDurability() < DEFAULT_ERASER_DURABILITY);
    }

    @Test
    public void whenEraserBecomesDullWhileErasingWordTheRemainingLettersOfWordAreNotErased() {
        assertEquals("She sells s   shells", almostDullPencil.erase(DEFAULT_PAPER, SEA));
    }

    @Test
    public void whenEraserErasesSpacesDoNotMakeEraserLoseDurability() {
        pencil.erase(DEFAULT_PAPER, " sea ");
        assertEquals(pencil.getEraserDurability(), Integer.valueOf(DEFAULT_ERASER_DURABILITY - 3));
    }

    @Test
    public void whenPencilEditsWithEmptyEditTextThenPaperIsNotModified() {
        assertEquals(DEFAULT_PAPER, pencil.edit(DEFAULT_PAPER, ""));
    }

    @Test
    public void whenPencilEditsWithEditTextAndPaperHasNoWhiteSpaceThenPaperIsNotModified() {
        assertEquals(DEFAULT_PAPER, pencil.edit(DEFAULT_PAPER, "test"));
    }

    @Test
    public void whenPencilEditsItAddsTextToWhitespaceAreaOfPaper() {
        assertEquals(DEFAULT_PAPER, pencil.edit(DEFAULT_PAPER_MISSING_SEA, SEA));
    }

    @Test
    public void whenPencilEditsAndEditTextIsLargerThanWhiteSpaceThenItCollidesWithOriginalPaperText() {
        assertEquals("She sells appl@@ells", pencil.edit(DEFAULT_PAPER_MISSING_SEA, "apples"));
    }

    @Test
    public void whenPencilEditsAndEditTextLargerThanRemainderOfTextThenEditTextOverlapsWithCollisions() {
        assertEquals("She sells vege@@@l@s and fruits", pencil.edit(DEFAULT_PAPER_MISSING_SEA, "vegetables and fruits"));
    }
}
