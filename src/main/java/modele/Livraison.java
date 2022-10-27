package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Livraison {

    public Optional<Intersection> origineLivraison;
    public Intersection destinationLivraison;
    public Optional<Coursier> coursierLivraison;
    public List<Troncon> parcoursLivraison; // parcours à faire pour la livraison
    public Optional<Integer> fenetreHoraireLivr;

    public Livraison() {
    }

    public Livraison(Intersection destinationLivraison) {
        this.destinationLivraison = destinationLivraison;
        parcoursLivraison = new ArrayList<>();
    }

    public Livraison(Intersection origineLivraison, Intersection destinationLivraison, List<Troncon> parcoursLivraison) {
        this.origineLivraison = Optional.of(origineLivraison);
        this.destinationLivraison = destinationLivraison;
        coursierLivraison = Optional.empty();
        fenetreHoraireLivr = Optional.empty();
        this.parcoursLivraison = parcoursLivraison;
    }
    public Livraison(Intersection origineLivraison, Intersection destinationLivraison, Coursier coursierLivraison, int fenetreHoraireLivr ) {
        this.origineLivraison = Optional.of(origineLivraison);
        this.destinationLivraison = destinationLivraison;
        this.coursierLivraison = Optional.of(coursierLivraison);
        this.fenetreHoraireLivr = Optional.of(fenetreHoraireLivr);
        this.parcoursLivraison = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Livraison{" +
                "destinationLivraison=" + destinationLivraison +
                ", coursierLivraison=" + coursierLivraison +
                ", parcoursLivraison=" + parcoursLivraison +
                ", fenetreHoraireLivr=" + fenetreHoraireLivr +
                '}';
    }

    public Optional<Coursier> getCoursierLivraison() {
        return coursierLivraison;
    }

    public void setCoursierLivraison(Coursier coursierLivraison) {
        this.coursierLivraison = Optional.of(coursierLivraison);
    }

    public List<Troncon> getParcoursLivraison() {
        return parcoursLivraison;
    }

    public void setParcoursLivraison(List<Troncon> parcoursLivraison) {
        this.parcoursLivraison = parcoursLivraison;
    }

    public Optional<Integer> getFenetreHoraireLivr() {
        return fenetreHoraireLivr;
    }

    public void setFenetreHoraireLivr(int fenetreHoraireLivr) {
        this.fenetreHoraireLivr = Optional.of(fenetreHoraireLivr);
    }

    public Intersection getDestinationLivraison() {
        return destinationLivraison;
    }

    public void setDestinationLivraison(Intersection destinationLivraison) {
        this.destinationLivraison = destinationLivraison;
    }

    public Optional<Intersection> getOrigineLivraison() {
        return origineLivraison;
    }

    public void setOrigineLivraison(Intersection origineLivraison) {
        this.origineLivraison = Optional.of(origineLivraison);
    }
}
