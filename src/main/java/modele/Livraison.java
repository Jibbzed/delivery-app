package modele;

import java.util.List;

public class Livraison {


    public Intersection destinationLivraison;
    // public Coursier coursierLivraison;
    public List<Troncon> parcoursLivraison; // parcours à faire pour la livraison
    public Intersection origineLivraison; // première intersection du parcours de la livraison
    public int fenetreHoraireLivr;

    public Livraison(Intersection destinationLivraison, int fenetreHoraireLivr) {
        this.destinationLivraison = destinationLivraison;
        this.fenetreHoraireLivr = fenetreHoraireLivr;
    }

    public Intersection getOrigineLivraison() {
        return origineLivraison;
    }

    public void setOrigineLivraison(Intersection origineLivraison) {
        this.origineLivraison = origineLivraison;
    }

    public List<Troncon> getParcoursLivraison() {
        return parcoursLivraison;
    }

    public void setParcoursLivraison(List<Troncon> parcoursLivraison) {
        this.parcoursLivraison = parcoursLivraison;
    }

    public int getFenetreHoraireLivr() {
        return fenetreHoraireLivr;
    }

    public void setFenetreHoraireLivr(int fenetreHoraireLivr) {
        this.fenetreHoraireLivr = fenetreHoraireLivr;
    }

    public Intersection getDestinationLivraison() {
        return destinationLivraison;
    }

    public void setDestinationLivraison(Intersection destinationLivraison) {
        this.destinationLivraison = destinationLivraison;
    }

}
