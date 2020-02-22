import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PencilTest {
    private static final String DEFAULT_PAPER = "She sells sea shells";
    private static final String DEFAULT_PAPER_MISSING_SEA = "She sells     shells";
    private static final String WOOD_CHUCK_PAPER = "How much chuck could a woodchuck chuck if a woodchuck could chuck wood?";
    private static final String DEFAULT_TEXT = " down by the sea shore";
    private static final Integer DEFAULT_ERASER_DURABILITY = 150;
    private static final Integer DEFAULT_POINT_DURABILITY = 100;
    private static final Integer DEFAULT_LENGTH = 10;
    private Pencil pencil;
    private Pencil dullPencil;

    @Before
    public void setUp() {
        this.pencil = new Pencil(DEFAULT_POINT_DURABILITY, DEFAULT_LENGTH, DEFAULT_ERASER_DURABILITY);
        this.dullPencil = new Pencil(0, 0, 0);
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
        Pencil almostDullPencil = new Pencil(10, DEFAULT_LENGTH, DEFAULT_ERASER_DURABILITY);
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
        Pencil toBeDullPencil = new Pencil(1, DEFAULT_LENGTH, DEFAULT_ERASER_DURABILITY);
        toBeDullPencil.write(DEFAULT_PAPER, "A");
        assertEquals(toBeDullPencil.getPointDurability(), Integer.valueOf(0));
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
        String outcome = pencil.erase("", "");
        assertEquals("", outcome);
    }

    @Test
    public void whenPencilDoesNotFindTargetToEraseThenPaperRemainsUnmodified() {
        String modifiedPaper = pencil.erase(DEFAULT_PAPER, "Test");
        assertEquals(modifiedPaper, DEFAULT_PAPER);
    }

    @Test
    public void whenPencilFindsTargetToEraseInPaperItThenReplacesTargetWithSpaces() {
        String modifiedPaper = pencil.erase(DEFAULT_PAPER, "sea");
        assertEquals(DEFAULT_PAPER_MISSING_SEA, modifiedPaper);
    }

    @Test
    public void whenPencilFindsMultipleInstancesOfTargetTextItErasesLastInstanceOfTargetText() {
        String modifiedPaper = pencil.erase(WOOD_CHUCK_PAPER, "chuck");
        assertEquals(WOOD_CHUCK_PAPER.substring(0, 60) + "     " + WOOD_CHUCK_PAPER.substring(65), modifiedPaper);
    }

    @Test
    public void whenPencilIsCreatedThenGetEraserDurabilityReturnsThatEraserDurability() {
        assertEquals(Integer.valueOf(150), pencil.getEraserDurability());
    }

    @Test
    public void whenEraserIsDullThenTargetTextIsNotErased() {
        assertEquals(DEFAULT_PAPER, dullPencil.erase(DEFAULT_PAPER, "sea"));
    }

    @Test
    public void whenEraserErasesThenItLosesDurability() {
        assertEquals(DEFAULT_ERASER_DURABILITY, pencil.getEraserDurability());
        pencil.erase(DEFAULT_PAPER, "sea");
        assertTrue(pencil.getEraserDurability() < DEFAULT_ERASER_DURABILITY);
    }

    @Test
    public void whenEraserBecomesDullWhileErasingWordTheRemainingLettersOfWordAreNotErased() {
        Pencil pencilAlmostDullEraser = new Pencil(DEFAULT_POINT_DURABILITY, DEFAULT_LENGTH, 2);
        assertEquals("She sells s   shells", pencilAlmostDullEraser.erase(DEFAULT_PAPER, "sea"));
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
        String modifiedPaper = pencil.edit(DEFAULT_PAPER_MISSING_SEA, "sea");
        assertEquals(DEFAULT_PAPER, modifiedPaper);
    }

    @Test
    public void whenPencilEditsAndEditTextIsLargerThanWhiteSpaceThenItCollidesWithOriginalPaperText() {
        String modifiedPaper = pencil.edit(DEFAULT_PAPER_MISSING_SEA, "apples");
        assertEquals("She sells appl@@ells", modifiedPaper);
    }

    @Test
    public void whenPencilEditsAndEditTextLargerThanRemainderOfTextThenEditTextOverlapsWithCollisions() {
        String modifiedPaper = pencil.edit(DEFAULT_PAPER_MISSING_SEA, "vegetables and fruits");
        assertEquals("She sells vege@@@l@s and fruits", modifiedPaper);
    }
}
