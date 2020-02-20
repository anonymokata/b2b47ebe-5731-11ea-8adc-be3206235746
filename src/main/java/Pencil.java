public class Pencil {

    private Integer durability;

    public Pencil(Integer durability) {
        this.durability = durability;
    }

    public String write(String paper, String text) {
        return paper + text;
    }

    public Integer getDurability() {
        return this.durability;
    }
}
