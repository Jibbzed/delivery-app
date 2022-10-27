package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Tournee {
    private List<Livraison> livraisons;
    // TODO: change coursier to Coursier instead of optional.
    private Optional<Coursier> coursier;

    public Tournee() {
        this.livraisons = new ArrayList<>();
    }

    public Tournee(List<Livraison> livraisons) {
        this.livraisons = new ArrayList<>(livraisons);
        this.coursier = Optional.empty();
    }

    public void attribuerTournee(Coursier c){
        this.coursier = Optional.of(c);
        c.setPlanifie(true);
    }

    public List<Livraison> getLivraisons() {
        return new ArrayList<>(livraisons); // Nouvelle liste pour qu'on ne puisse pas changer la liste par un get
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
}
