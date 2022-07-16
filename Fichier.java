public class Fichier extends Composant {

    public Fichier(String fname, int flevel) {
        super(fname, flevel);
    }

    public void operation() {
        for (int i = 0; i < getLevel(); i++) {
            System.out.print("│\t");
        }
        System.out.println(getName());
    }
}
