package service;

import com.github.javafaker.Faker;
import modele.Coursier;

import java.util.ArrayList;
import java.util.List;

//TODO: Make it an interface and than implement it because we could have also an other implementation based on xml.
public class ServiceCoursier {
    private static ServiceCoursier instance;
    private List<Coursier>  listeCoursiers = new ArrayList<>();
    private int nombreCoursiers;
//    private Faker faker = new Faker();

    public ServiceCoursier(int nombreCoursiers) {
        if(nombreCoursiers != 1 ) {
            throw new RuntimeException("not yet Implemented.");
        }
        this.nombreCoursiers = nombreCoursiers;
        ajouterCoursier(new Coursier("BONIFACIO", "Grinardo", false));
//        initCoursier(this.nombreCoursiers);
    }

//    /**
//     * Crée une fausse base de donnée de coursiers
//     * @param nombre : nombre de coursier à créer
//     */
//    public void initCoursier(int nombre) {
//        for (int i = 0 ; i < nombre ; i++) {
//            ajouterCoursier();
//        }
//    }

//    public void ajouterCoursier(){
//        this.listeCoursiers.add(new Coursier(faker.name().lastName(), faker.name().firstName()));
//    }
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
    public static ServiceCoursier getInstance(int nombreCoursiers) {
        if (instance == null) {
            instance = new ServiceCoursier(nombreCoursiers);
        }
        return instance;
    }
}