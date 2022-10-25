package modele;

import com.github.javafaker.Faker;

public class Coursier {

    private final String nom;
    private final String prenom;
    private boolean planifie;

    public Coursier(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.planifie = false;
    }

    public Coursier(String nom, String prenom, boolean planifie) {
        this.nom = nom;
        this.prenom = prenom;
        this.planifie = planifie;
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
     * Réfère null à un coursier, afin de ne plus l'utiliser
     * @param c : coursier à supprimer
     */
    public void deleteCoursier(Coursier c) {
        c = null;
    }

    /**
     * Crée une fausse base de donnée de coursiers
     */
    public static void initCoursier() {
        Faker faker = new Faker();
        Coursier c1 = new Coursier(faker.name().lastName(), faker.name().firstName());
        Coursier c2 = new Coursier(faker.name().lastName(), faker.name().firstName());
        Coursier c3 = new Coursier(faker.name().lastName(), faker.name().firstName());
        Coursier c4 = new Coursier(faker.name().lastName(), faker.name().firstName());
        Coursier c5 = new Coursier(faker.name().lastName(), faker.name().firstName());
    }
}
