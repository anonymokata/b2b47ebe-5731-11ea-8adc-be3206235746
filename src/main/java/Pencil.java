public class Pencil {

    private Integer durability;

    public Pencil(Integer durability) {
        this.durability = durability;
    }

    public Integer getDurability() {
        return this.durability;
    }

    public String write(String paper, String text) {
        StringBuilder updatedPaper = new StringBuilder();
        updatedPaper.append(paper);
        for (int i = 0; i < text.length(); i++) {
            //TODO - Refactor logic.
            char c = text.charAt(i);
            int degradation = getDurabilityLoss(c);
            if (durability > 0) {
                if ((durability - degradation) < 0) {
                    updatedPaper.append(" ");
                    durability = 0;
                } else {
                    updatedPaper.append(c);
                    durability -= degradation;
                }
            } else {
                updatedPaper.append(" ");
            }
        }
        return updatedPaper.toString();
    }

    private int getDurabilityLoss(Character c) {
        int durabilityLoss;
        if (c == ' ' || c == '\n') {
            durabilityLoss = 0;
        } else if (Character.isUpperCase(c)) {
            durabilityLoss = 2;
        } else {
            durabilityLoss = 1;
        }
        return durabilityLoss;
    }
}
