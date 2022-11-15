package modele;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Livraison {

    protected Optional<Intersection> origineLivraison = Optional.empty();
    protected Intersection destinationLivraison;
    protected Optional<Coursier> coursierLivraison = Optional.empty();
    protected List<Troncon> parcoursLivraison; // parcours à faire pour la livraison
    protected Optional<Integer> fenetreHoraireLivr = Optional.empty();
    protected Optional<LocalTime> heurePassage = Optional.empty();

    public Livraison() {
    }

    public Livraison(Intersection destinationLivraison) {
        this.destinationLivraison = destinationLivraison;
        parcoursLivraison = new ArrayList<>();
    }

    public Livraison(Intersection origineLivraison, Intersection destinationLivraison, Coursier coursier, List<Troncon> parcoursLivraison, LocalTime heureArrivee) {
        this.origineLivraison = Optional.of(origineLivraison);
        this.destinationLivraison = destinationLivraison;
        coursierLivraison = Optional.of(coursier);
        fenetreHoraireLivr = Optional.empty();
        this.parcoursLivraison = parcoursLivraison;
        heurePassage = Optional.of(heureArrivee);
    }
    public Livraison(Intersection destinationLivraison, Coursier coursierLivraison, int fenetreHoraireLivr ) {
        this.origineLivraison = Optional.empty();
        this.destinationLivraison = destinationLivraison;
        this.coursierLivraison = Optional.of(coursierLivraison);
        this.fenetreHoraireLivr = Optional.of(fenetreHoraireLivr);
        this.parcoursLivraison = new ArrayList<>();
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

    public Optional<LocalTime> getHeurePassage() {
        return heurePassage;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livraison livraison = (Livraison) o;
        return origineLivraison.equals(livraison.origineLivraison) && destinationLivraison.equals(livraison.destinationLivraison) && parcoursLivraison.equals(livraison.parcoursLivraison);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origineLivraison, destinationLivraison, parcoursLivraison);
    }

    public String afficherIhm() {
        return "livraison sur coord: (" + destinationLivraison.getLatitude() + "," + destinationLivraison.getLongitude() + ")";
    }
}
