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
            if (durability > 0) {
                updatedPaper.append(text.charAt(i));
                durability -= 1;
            } else {
                updatedPaper.append(" ");
            }
        }
        return updatedPaper.toString();
    }
}
