package modele;

import java.util.ArrayList;
import java.util.List;

public class Tournee {

    public List<Livraison> livraisons = new ArrayList<>();

    public Tournee() {
    }

    public Tournee(List<Livraison> livraisons) {
        this.livraisons = livraisons;
    }

    public List<Livraison> getLivraisons() {
        return livraisons;
    }

    public void ajouterLivraison(Livraison l){
        this.livraisons.add(l);
    }

    public void retirerLivraison(Livraison l){
        this.livraisons.remove(l);
    }
}
