public class Pencil {
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
        StringBuilder updatedPaper = new StringBuilder();
        updatedPaper.append(paper);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int degradation = getDegradation(c);
            char charToWrite = ((degradation > currentPointDurability) || degradation == 0) ? ' ' : c;
            updatedPaper.append(charToWrite);
            currentPointDurability = Math.max(currentPointDurability - degradation, 0);
        }
        return updatedPaper.toString();
    }

    public void sharpen() {
        if (length > 0 && (!maxPointDurability.equals(currentPointDurability))) {
            currentPointDurability = maxPointDurability;
            length -= 1;
        }
    }

    public String erase(String paper, String targetText) {
        String modifiedPaper;
        if (paper.contains(targetText)) {
            if (eraserDurability > 0) {
                int indexOfTargetText = paper.lastIndexOf(targetText);
                String remainingPaper = modifyRemainingPaper(targetText, paper.substring(indexOfTargetText));
                modifiedPaper = paper.substring(0, indexOfTargetText) + remainingPaper;
            } else {
                modifiedPaper = paper;
            }
        } else {
            modifiedPaper = paper;
        }
        return modifiedPaper;
    }

    public String edit(String paper, String editText) {
        String modifiedPaper;
        int indexOfWhiteSpace = paper.indexOf("  ");
        if (indexOfWhiteSpace != -1) {
            int startOfEditArea = indexOfWhiteSpace + 1;
            String resultOfWrite = write(paper.substring(0, startOfEditArea), editText) + paper.substring(startOfEditArea + editText.length());
            modifiedPaper = formatCollisionText(paper, resultOfWrite);
        } else {
            modifiedPaper = paper;
        }
        return modifiedPaper;
    }

    private String formatCollisionText(String paper, String modifiedPaper) {
        StringBuilder formattedText = new StringBuilder();
        int index = 0;
        while (index < paper.length()) {
            if (paper.charAt(index) != ' ' && paper.charAt(index) != modifiedPaper.charAt(index)) {
                formattedText.append('@');
            } else {
                formattedText.append(modifiedPaper.charAt(index));
            }
            index += 1;
        }
        formattedText.append(modifiedPaper.substring(index));
        return formattedText.toString();
    }

    private String modifyRemainingPaper(String eraseText, String remainingPaper) {
        int textLength = eraseText.length();
        String textToErase = remainingPaper.substring(0, textLength);
        int eraseIndex = getEraseIndex(textToErase);
        return textToErase.substring(0, eraseIndex) + replaceTextWithSpaces(textToErase.substring(eraseIndex)) + remainingPaper.substring(textLength);
    }

    private int getDegradation(Character c) {
        int degradation;
        if (c == ' ' || c == '\n') {
            degradation = 0;
        } else if (Character.isUpperCase(c)) {
            degradation = 2;
        } else {
            degradation = 1;
        }
        return degradation;
    }

    private int getEraseIndex(String targetText) {
        int eraseIndex = targetText.length() - 1;
        while(eraseIndex > 0 && eraserDurability > 0) {
            if (targetText.charAt(eraseIndex) != ' ') {
                eraserDurability -= 1;
                if (eraserDurability == 0) {
                    break;
                }
            }
            eraseIndex -= 1;
        }
        return Math.max(eraseIndex, 0);
    }

    private String replaceTextWithSpaces(String text) {
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            spaces.append(" ");
        }
        return spaces.toString();
    }
}
