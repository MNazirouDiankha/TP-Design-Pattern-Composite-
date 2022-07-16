import java.util.ArrayList;

public class Dossier extends Composant {
    ArrayList<Composant> composants = new ArrayList<Composant>();

    public Dossier(String dname, int dlevel) {
        super(dname, dlevel);
    }

    public void addComposant(Composant c) {
        composants.add(c);
    }

    public void operation() {

        int level = getLevel();
        for (int i = 0; i < level; i++) {
            System.out.print("│\t");
        }
        System.out.println("├───" + getName());
        for (Composant c : composants) {
            c.operation();
        }
    }
}
