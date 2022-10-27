package modele;

import java.util.ArrayList;
import java.util.List;

public class Tournee {
    public List<Livraison> livraisons;

    public Tournee() {
        this.livraisons = new ArrayList<>();
    }

    public Tournee(List<Livraison> livraisons) {
        this.livraisons = livraisons;
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
