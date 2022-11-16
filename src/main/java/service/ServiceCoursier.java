package service;

import com.github.javafaker.Faker;
import modele.Coursier;

import java.util.ArrayList;
import java.util.List;

//TODO: Make it an interface and than implement it because we could have also an other implementation based on xml.
public class ServiceCoursier {
    private static ServiceCoursier instance;
    private ArrayList<Coursier>  listeCoursiers = new ArrayList<>();

    public ServiceCoursier() {;
        initCoursier();
    }

    public void ajouterCoursier(Coursier coursier){
        this.listeCoursiers.add(coursier);
    }
    public void modifierCoursier(Coursier coursier) {
        retirerCoursier(coursier);
        ajouterCoursier(coursier);
    }
    public void retirerCoursier(String nom, String prenom){
        Coursier c = new Coursier(nom, prenom);
        this.listeCoursiers.remove(c);
    }

    public void retirerCoursier(Coursier coursier){
        listeCoursiers.remove(coursier);
    }

    public ArrayList<Coursier> getListeCoursiers() { return listeCoursiers;}
    public static ServiceCoursier getInstance() {
        if (instance == null) {
            instance = new ServiceCoursier();
        }
        return instance;
    }

    /**
     * Crée une fausse base de donnée de coursiers
     */
    public void initCoursier() {
        Coursier c = new Coursier("BONIFACIO", "Grinardo");
        ajouterCoursier(c);
        Coursier c1 = new Coursier("BOUSSIO", "Clemensso");
        ajouterCoursier(c1);
        Coursier c2 = new Coursier("LEPOLLO", "Maylina");
        ajouterCoursier(c2);
        Coursier c3 = new Coursier("ANCELO", "Gwenaella");
        ajouterCoursier(c3);
        Coursier c4 = new Coursier("LOUKHNATO", "Khalilo");
        ajouterCoursier(c4);
        Coursier c5 = new Coursier("LEPIDO", "Jibesso");
        ajouterCoursier(c5);
        Coursier c6 = new Coursier("DADAMOCIO", "Taddesso");
        ajouterCoursier(c6);
        Coursier c7 = new Coursier("BELLEMINIO", "Alessandro");
        ajouterCoursier(c7);
    }
}
