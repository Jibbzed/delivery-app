package modele;

import java.util.List;

public class Livraison {

    public Intersection lieuLivraison;
    // public Coursier coursierLivraison;
    public List<Troncon> tronconLiesLivraison ; // liste de troncons menant à cette livraison, nulle au début puis remplie ??
    public int fenetreHoraireLivr;

    public Livraison(Intersection lieuLivraison, int fenetreHoraireLivr) {
        this.lieuLivraison = lieuLivraison;
        this.fenetreHoraireLivr = fenetreHoraireLivr;
    }
}
