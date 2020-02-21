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
                String remainingPaper = paper.substring(indexOfTargetText);
                remainingPaper = remainingPaper.replace(targetText, replaceTextWithSpaces(targetText));
                modifiedPaper = paper.substring(0, indexOfTargetText) + remainingPaper;
                eraserDurability -= targetText.length();
            } else {
                modifiedPaper = paper;
            }
        } else {
            modifiedPaper = paper;
        }
        return modifiedPaper;
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

    private String replaceTextWithSpaces(String text) {
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            spaces.append(" ");
        }
        return spaces.toString();
    }
}
