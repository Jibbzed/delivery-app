package modele;

import com.github.javafaker.Faker;

import java.util.Objects;

public class Coursier {

    private final String nom;
    private final String prenom;
    private boolean planifie;
    private boolean valide;

    public Coursier(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.planifie = false;
        this.valide = true;
    }

    public Coursier(String nom, String prenom, boolean planifie) {
        this.nom = nom;
        this.prenom = prenom;
        this.planifie = planifie;
        this.valide = true;
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

    public boolean isPlanifie() {
        return planifie;
    }

    public void setPlanifie(boolean planifie) {
        this.planifie = planifie;
    }

    /**
     * Correspond à un set de valide, mais plus explicite
     */
    public void deleteCoursier() {
        this.valide = false;
    }



    @Override
    public String toString() {
        if (this.valide) {
            return "Coursier{" +
                    "nom='" + nom + '\'' +
                    ", prenom='" + prenom + '\'' +
                    ", planifie=" + planifie +
                    '}';
        } else {
            return "Coursier supprimé.";
        }
    }
}
