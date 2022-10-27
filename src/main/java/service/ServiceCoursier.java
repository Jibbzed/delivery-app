package service;

import com.github.javafaker.Faker;
import modele.Coursier;

import java.util.ArrayList;
import java.util.List;

public class ServiceCoursier {

    private List<Coursier>  listeCoursiers = new ArrayList<>();
    private int nombreCoursiers;
    private Faker faker;

    public ServiceCoursier(int nombreCoursiers) {
        this.nombreCoursiers = nombreCoursiers;
        faker = new Faker();
        initCoursier(this.nombreCoursiers);
    }

    /**
     * Crée une fausse base de donnée de coursiers
     * @param nombre : nombre de coursier à créer
     */
    public void initCoursier(int nombre) {
        for (int i = 0 ; i < nombre ; i++) {
            ajouterCoursier();
        }
    }

    public void ajouterCoursier(){
        this.listeCoursiers.add(new Coursier(faker.name().lastName(), faker.name().firstName()));
    }
    public void ajouterCoursier(Coursier coursier){
        this.listeCoursiers.add(coursier);
    }

    public void retirerCoursier(String nom, String prenom){
        Coursier c = new Coursier(nom, prenom);
        this.listeCoursiers.remove(c);
    }

    public void retirerCoursier(Coursier coursier){
        this.listeCoursiers.remove(coursier);
    }

    public List<Coursier> getListeCoursiers() {
        return new ArrayList<Coursier>(this.listeCoursiers);
    }
}
