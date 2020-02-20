public class Pencil {

    private Integer durability;

    public Pencil(Integer durability) {
        this.durability = durability;
    }

    public Integer getDurability() {
        return this.durability;
    }

    public String write(String paper, String text) {
        String updatedPaper = paper;
        if (durability > text.length()) {
            updatedPaper += text;
        } else {
            updatedPaper += text.replaceAll(".", " ");
        }
        return updatedPaper;
    }
}
