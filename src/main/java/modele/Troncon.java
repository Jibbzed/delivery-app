package modele;

import java.util.Objects;

public class Troncon {

    protected String nom;
    protected double longueur;
    protected Intersection origine;
    protected Intersection destination;

    public Troncon(String nom, double longueur, Intersection origine, Intersection destination) {
        this.nom = nom;
        this.longueur = longueur;
        this.origine = origine;
        this.destination = destination;
    }

    /*getters et setters*/

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getLongueur() {
        return longueur;
    }

    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }

    public Intersection getOrigine() {
        return origine;
    }

    public void setOrigine(Intersection origine) {
        this.origine = origine;
    }

    public Intersection getDestination() {
        return destination;
    }

    public void setDestination(Intersection destination) {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Troncon troncon = (Troncon) o;
        return origine.equals(troncon.origine) && destination.equals(troncon.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origine, destination);
    }
}
