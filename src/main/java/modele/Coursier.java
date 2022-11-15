package modele;

import java.util.Objects;
public class Coursier {

    private final String nom;
    private final String prenom;
    private boolean planifie; // True si le coursier est attribue à une tournee

    /**
     * Constructeur de la classe.
     *
     * @param nom    nom du coursier
     * @param prenom prénom du coursier
     */
    public Coursier(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.planifie = false;
    }

    /**
     * Constructeur de la classe avec l'information à propos de l'affectation du coursier à une <code>Livraison</code> ou une <code>Tournee</code>.
     *
     * @param nom      nom du coursier
     * @param prenom   prénom du coursier
     * @param planifie booléen représentant l'état d'affectation du coursier
     */
    public Coursier(String nom, String prenom, boolean planifie) {
        this.nom = nom;
        this.prenom = prenom;
        this.planifie = planifie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coursier coursier = (Coursier) o;
        return nom.equals(coursier.nom) && prenom.equals(coursier.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, prenom);

    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public boolean getPlanifie() {
        return planifie;
    }

    public void setPlanifie(boolean planifie) {
        this.planifie = planifie;
    }

    @Override
    public String toString() {
        return nom + " " + prenom;
    }


}
