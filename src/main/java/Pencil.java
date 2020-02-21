public class Pencil {

    private Integer maxDurability;
    private Integer durability;

    public Pencil(Integer durability) {
        this.durability = durability;
        this.maxDurability = durability;
    }

    public Integer getDurability() {
        return this.durability;
    }

    public String write(String paper, String text) {
        StringBuilder updatedPaper = new StringBuilder();
        updatedPaper.append(paper);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int degradation = getDegradation(c);
            char charToWrite = ((degradation > durability) || degradation == 0) ? ' ' : c;
            updatedPaper.append(charToWrite);
            durability = Math.max(durability - degradation, 0);
        }
        return updatedPaper.toString();
    }

    public void sharpen() {
        this.durability = this.maxDurability;
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
