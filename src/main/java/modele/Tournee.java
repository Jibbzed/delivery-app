package modele;

import java.util.ArrayList;
import java.util.List;

public class Tournee {
    private List<Livraison> livraisons;
    private Coursier coursier;

    public Tournee() {
        this.livraisons = new ArrayList<>();
    }

    public Tournee(List<Livraison> livraisons) {
        this.livraisons = new ArrayList<Livraison>(livraisons);
    }

    public void attribuerTournee(Coursier c){
        this.coursier = c;
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
}
