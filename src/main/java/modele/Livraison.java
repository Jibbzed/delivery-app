package modele;

import java.util.List;

public class Livraison {

    public Intersection destinationLivraison;
    public Coursier coursierLivraison;
    public List<Troncon> parcoursLivraison; // parcours à faire pour la livraison
    public Intersection origineLivraison; // première intersection du parcours de la livraison
    public int fenetreHoraireLivr;

    public Livraison() {
    }

    public Livraison(Intersection destinationLivraison, Coursier coursierLivraison, int fenetreHoraireLivr) {
        this.destinationLivraison = destinationLivraison;
        this.coursierLivraison = coursierLivraison;
        this.fenetreHoraireLivr = fenetreHoraireLivr;
    }

    @Override
    public String toString() {
        return "Livraison{" +
                "destinationLivraison=" + destinationLivraison +
                ", coursierLivraison=" + coursierLivraison +
                ", parcoursLivraison=" + parcoursLivraison +
                ", origineLivraison=" + origineLivraison +
                ", fenetreHoraireLivr=" + fenetreHoraireLivr +
                '}';
    }

    public Coursier getCoursierLivraison() {
        return coursierLivraison;
    }

    public void setCoursierLivraison(Coursier coursierLivraison) {
        this.coursierLivraison = coursierLivraison;
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
