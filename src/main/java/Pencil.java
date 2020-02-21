public class Pencil {
    private Integer maxDurability;
    private Integer currentDurability;
    private Integer length;

    public Pencil(Integer currentDurability, Integer length) {
        this.currentDurability = currentDurability;
        this.maxDurability = currentDurability;
        this.length = length;
    }

    public Integer getCurrentDurability() {
        return currentDurability;
    }

    public Integer getLength() {
        return length;
    }

    public String write(String paper, String text) {
        StringBuilder updatedPaper = new StringBuilder();
        updatedPaper.append(paper);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int degradation = getDegradation(c);
            char charToWrite = ((degradation > currentDurability) || degradation == 0) ? ' ' : c;
            updatedPaper.append(charToWrite);
            currentDurability = Math.max(currentDurability - degradation, 0);
        }
        return updatedPaper.toString();
    }

    public void sharpen() {
        if (length > 0) {
            currentDurability = maxDurability;
            length -= 1;
        }
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
}
