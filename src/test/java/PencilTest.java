import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PencilTest {
    private static final String DEFAULT_PAPER = "She sells sea shells";
    private static final String WOOD_CHUCK_PAPER = "How much chuck could a woodchuck chuck if a woodchuck could chuck wood?";
    private static final String DEFAULT_TEXT = " down by the sea shore";
    private static final Integer DEFAULT_ERASER_DURABILITY = 150;
    private static final Integer DEFAULT_PAPER_LENGTH = 20;
    private static final Integer DEFAULT_POINT_DURABILITY = 100;
    private static final Integer DEFAULT_LENGTH = 10;
    private Pencil pencil;

    @Before
    public void setUp() {
        this.pencil = new Pencil(DEFAULT_POINT_DURABILITY, DEFAULT_LENGTH, DEFAULT_ERASER_DURABILITY);
    }

    @Test
    public void whenPencilIsPassedAnEmptyPaperAndEmptyTextItReturnsAnEmptyString() {
        assertEquals("", pencil.write("", "", 0));
    }

    @Test
    public void whenPencilIsPassedAnEmptyPaperAndTextItReturnsThatText() {
        assertEquals(DEFAULT_TEXT, pencil.write("", DEFAULT_TEXT, 0));
    }

    @Test
    public void whenPencilIsPassedPaperAndTextItWritesTextOnPaper() {
        assertEquals(DEFAULT_PAPER + DEFAULT_TEXT, pencil.write(DEFAULT_PAPER, DEFAULT_TEXT, DEFAULT_PAPER_LENGTH));
    }

    @Test
    public void whenPencilIsCreatedWithDurabilityThenGetCurrentDurabilityReturnsThatNumber() {
        Pencil freshPencil = new Pencil(100, DEFAULT_LENGTH, DEFAULT_ERASER_DURABILITY);
        assertEquals(Integer.valueOf(100), freshPencil.getPointDurability());
    }

    @Test
    public void whenPencilIsDullOnlySpacesAreWritten() {
        Pencil dullPencil = new Pencil(0, DEFAULT_LENGTH, DEFAULT_ERASER_DURABILITY);
        assertEquals(DEFAULT_PAPER + DEFAULT_TEXT.replaceAll(".", " "), dullPencil.write(DEFAULT_PAPER, DEFAULT_TEXT, DEFAULT_PAPER_LENGTH));
    }

    @Test
    public void whenPencilWritesItLosesDurability() {
        assertEquals(DEFAULT_PAPER + DEFAULT_TEXT, pencil.write(DEFAULT_PAPER, DEFAULT_TEXT, DEFAULT_PAPER_LENGTH));
        assertTrue(pencil.getPointDurability() < DEFAULT_POINT_DURABILITY);
    }

    @Test
    public void whenPencilGoesDullRemainingTextIsSpaces() {
        Pencil almostDullPencil = new Pencil(10, DEFAULT_LENGTH, DEFAULT_ERASER_DURABILITY);
        String writtenText = DEFAULT_TEXT.substring(0, 14);
        String remainingText = DEFAULT_TEXT.substring(14);
        String expectedText = DEFAULT_PAPER + writtenText + remainingText.replaceAll(".", " ");
        assertEquals(expectedText, almostDullPencil.write(DEFAULT_PAPER, DEFAULT_TEXT, DEFAULT_PAPER_LENGTH));
    }

    @Test
    public void whenPencilWritesLowercaseLetterItLosesOnePointDurability() {
        pencil.write("", "a", 0);
        assertEquals(pencil.getPointDurability(), Integer.valueOf(DEFAULT_POINT_DURABILITY - 1));
    }

    @Test
    public void whenPencilWritesNewLineOrSpaceItDoesNotLoseDurability() {
        pencil.write(DEFAULT_PAPER, " ", DEFAULT_PAPER_LENGTH);
        assertEquals(pencil.getPointDurability(), DEFAULT_POINT_DURABILITY);
    }

    @Test
    public void whenPencilWritesUppercaseLetterItLosesTwoPointsDurability() {
        pencil.write(DEFAULT_PAPER, "A", DEFAULT_PAPER_LENGTH);
        assertEquals(pencil.getPointDurability(), Integer.valueOf(DEFAULT_POINT_DURABILITY - 2));
    }

    @Test
    public void whenPencilHasOneDurabilityAndEncountersUppercaseItWritesASpace() {
        Pencil toBeDullPencil = new Pencil(1, DEFAULT_LENGTH, DEFAULT_ERASER_DURABILITY);
        toBeDullPencil.write(DEFAULT_PAPER, "A", DEFAULT_PAPER_LENGTH);
        assertEquals(toBeDullPencil.getPointDurability(), Integer.valueOf(0));
    }

    @Test
    public void whenPencilIsDullAndTextIsSpaceThenItWritesSpace() {
        Pencil dullPencil = new Pencil(0, DEFAULT_LENGTH, DEFAULT_ERASER_DURABILITY);
        String outcome = dullPencil.write("", " ", 0);
        assertEquals(dullPencil.getPointDurability(), Integer.valueOf(0));
        assertEquals(outcome, " ");
    }

    @Test
    public void whenPencilIsSharpenedItsDurabilityResetsToInitialValue() {
        pencil.write(DEFAULT_PAPER, DEFAULT_TEXT, DEFAULT_PAPER_LENGTH);
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
        pencil.write("", "abc", 0);
        pencil.sharpen();
        assertTrue(pencil.getLength() < DEFAULT_LENGTH);
    }

    @Test
    public void whenPencilLengthIsZeroThenSharpeningPencilDoesNotRestoreDurability() {
        Pencil almostDullPencil = new Pencil(0, 0, 0);
        assertEquals(Integer.valueOf(0), almostDullPencil.getPointDurability());
        almostDullPencil.sharpen();
        assertEquals(Integer.valueOf(0), almostDullPencil.getPointDurability());
    }

    @Test
    public void whenPencilIsAtMaximumDurabilityThenSharpeningDoesNotCausePencilToLoseLength() {
        assertEquals(DEFAULT_POINT_DURABILITY, pencil.getPointDurability());
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
        assertEquals("She sells     shells", modifiedPaper);
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
        Pencil pencilDullEraser = new Pencil(DEFAULT_POINT_DURABILITY, DEFAULT_LENGTH, 0);
        assertEquals(DEFAULT_PAPER, pencilDullEraser.erase(DEFAULT_PAPER, "sea"));
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
        String modifiedPaper = pencil.edit("She sells     shells", "sea");
        assertEquals(DEFAULT_PAPER, modifiedPaper);
    }

    @Test
    public void whenPencilSpecifiesWritingLocationItWillWriteInThatLocation() {
        String modifiedPaper = pencil.write(" this is.", "A test,", 0);
        assertEquals("A test, this is.", modifiedPaper);
    }

    @Test
    public void whenPencilSpecifiesWritingLocationGreaterThanPaperLengthThenItWritesToTheEndOfThePaper() {
        String modifiedPaper = pencil.write(DEFAULT_PAPER, DEFAULT_TEXT, 999);
        assertEquals(DEFAULT_PAPER + DEFAULT_TEXT, modifiedPaper);
    }
}
