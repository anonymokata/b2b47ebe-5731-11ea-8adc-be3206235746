public class Pencil {
    private static final Character EMPTY_SPACE = ' ';

    private Integer maxPointDurability;
    private Integer currentPointDurability;
    private Integer eraserDurability;
    private Integer length;

    public Pencil(Integer currentPointDurability, Integer length, Integer eraserDurability) {
        this.currentPointDurability = currentPointDurability;
        this.maxPointDurability = currentPointDurability;
        this.length = length;
        this.eraserDurability = eraserDurability;
    }

    public Integer getPointDurability() {
        return currentPointDurability;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getEraserDurability() {
        return eraserDurability;
    }

    public String write(String paper, String text) {
        StringBuilder modifiedPaper = new StringBuilder();
        modifiedPaper.append(paper);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int degradation = getDegradation(c);
            char charToWrite = ((degradation > currentPointDurability) || degradation == 0) ? EMPTY_SPACE : c;
            modifiedPaper.append(charToWrite);
            currentPointDurability = Math.max(currentPointDurability - degradation, 0);
        }
        return modifiedPaper.toString();
    }

    public void sharpen() {
        if (length > 0 && (!maxPointDurability.equals(currentPointDurability))) {
            currentPointDurability = maxPointDurability;
            length -= 1;
        }
    }

    public String erase(String paper, String targetText) {
        String modifiedPaper;
        if (paper.contains(targetText) && eraserDurability > 0) {
            int targetTextLocation = paper.lastIndexOf(targetText);
            String sectionWithErasedContent = eraseTextFromPaper(targetText, paper.substring(targetTextLocation));
            modifiedPaper = paper.substring(0, targetTextLocation) + sectionWithErasedContent;
        } else {
            modifiedPaper = paper;
        }
        return modifiedPaper;
    }

    public String edit(String paper, String editText) {
        String modifiedPaper;
        int whiteSpaceLocation = paper.indexOf("  ");
        if (whiteSpaceLocation != -1) {
            int startOfWritingArea = whiteSpaceLocation + 1;
            String remainingOriginalText = paper.length() <= startOfWritingArea + editText.length() ? "" : paper.substring(startOfWritingArea + editText.length());
            String resultOfEdit = write(paper.substring(0, startOfWritingArea), editText) + remainingOriginalText;
            modifiedPaper = compareModifiedPaperToBasePaper(paper, resultOfEdit);
        } else {
            modifiedPaper = paper;
        }
        return modifiedPaper;
    }

    private String compareModifiedPaperToBasePaper(String paper, String modifiedPaper) {
        StringBuilder collisionText = new StringBuilder();
        for (int i = 0; i < paper.length(); i++) {
            char charToAppend = (paper.charAt(i) != EMPTY_SPACE && paper.charAt(i) != modifiedPaper.charAt(i)) ? '@' : modifiedPaper.charAt(i);
            collisionText.append(charToAppend);
        }
        //retrieve the remainder of the modified paper and add it to collisionText.
        collisionText.append(modifiedPaper.substring(paper.length()));
        return collisionText.toString();
    }

    private String eraseTextFromPaper(String targetText, String paper) {
        int textLength = targetText.length();
        String textToErase = paper.substring(0, textLength);
        int eraseIndex = getIndexWhereEraseBegins(textToErase);
        return paper.substring(0, eraseIndex) + replaceTextWithSpaces(textToErase.substring(eraseIndex)) + paper.substring(textLength);
    }

    private int getDegradation(Character c) {
        int degradation;
        if (c == EMPTY_SPACE || c == '\n') {
            degradation = 0;
        } else if (Character.isUpperCase(c)) {
            degradation = 2;
        } else {
            degradation = 1;
        }
        return degradation;
    }

    private int getIndexWhereEraseBegins(String targetText) {
        int eraseIndex = targetText.length() - 1;
        while(eraseIndex > 0 && eraserDurability > 0) {
            if (targetText.charAt(eraseIndex) != EMPTY_SPACE) {
                eraserDurability -= 1;
            }
            if (eraserDurability == 0) {
                break;
            }
            eraseIndex -= 1;
        }
        return Math.max(eraseIndex, 0);
    }

    private String replaceTextWithSpaces(String text) {
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            spaces.append(EMPTY_SPACE);
        }
        return spaces.toString();
    }
}
