package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Tournee {
    private List<Livraison> livraisons;
    private Optional<Coursier> coursier;

    /**
     * Constructeur par défaut de la classe.
     */
    public Tournee() {
        this.livraisons = new ArrayList<>();
    }

    /**
     * Constructeur de la classe à partir d'une liste de livraisons et d'un coursier.
     *
     * @param livraisons liste des <code>Livraison</code> composant la tournée
     * @param coursier   <code>Coursier</code> attitré à la tournée
     *
     * @see Livraison
     * @see Coursier
     */
    public Tournee(List<Livraison> livraisons, Coursier coursier) {
        this.livraisons = new ArrayList<>(livraisons);
        this.coursier = Optional.of(coursier);
    }

    public void attribuerTournee(Coursier c){
        this.coursier = Optional.of(c);
        c.setPlanifie(true);
    }

    public List<Livraison> getLivraisons() {
        return new ArrayList<>(livraisons); // Nouvelle liste pour qu'on ne puisse pas changer la liste par un get
    }

    public Optional<Coursier> getCoursier() {
        return coursier;
    }

    public void ajouterLivraison(Livraison l){
        this.livraisons.add(l);
    }

    public void retirerLivraison(Livraison l){
        this.livraisons.remove(l);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tournee tournee = (Tournee) o;
        return livraisons.equals(tournee.livraisons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(livraisons);
    }

    public String toString(Plan plan) {
        String s = "Tournee : ";
        for(Livraison l : livraisons) {
            s += l.toString(plan) + " ";
        }
        return s;
    }
}
