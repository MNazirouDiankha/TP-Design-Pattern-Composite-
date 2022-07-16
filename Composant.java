public abstract class Composant {

    private int level;
    private String name;

    public Composant(String cname, int clevel) {
        this.name = cname;
        this.level = clevel;
    }

    public abstract void operation();

    public String getName() {
        return this.name;
    }

    public int getLevel() {
        return this.level;
    }

}