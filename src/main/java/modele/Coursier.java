package modele;

import com.github.javafaker.Faker;

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

    /**
     * Crée une fausse base de donnée de coursiers
     * @param nombre : nombre de coursier à créer
     */
    public static Coursier[] initCoursier(int nombre) {
        Faker faker = new Faker();
        Coursier[] coursierArray = new Coursier[nombre];
        for (int i = 0 ; i < nombre ; i++) {
            coursierArray[i] = new Coursier(faker.name().lastName(), faker.name().firstName());
        }
        return coursierArray;
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
