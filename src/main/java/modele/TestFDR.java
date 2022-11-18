package modele;

import java.util.*;

public class TestFDR {

    public static void main(String[] args) {

        Coursier Gwen = new Coursier("Ancel", "Gwenaëlle", true);

        Intersection depart1 = new Intersection("01", 6.2, 4.1);
        Intersection intermediaire1 = new Intersection("02", 6.9, 4.1);
        Intersection intermediaire2 = new Intersection("O3", 6.9, 5.36);
        Intersection arrivee1 = new Intersection("04", 7.5, 5.36);

        Troncon troncon1 = new Troncon("Allée des Cerisiers", 23.4, depart1, intermediaire1);
        Troncon troncon2 = new Troncon("Chemin des Poissons", 18.2, intermediaire1, intermediaire2);
        Troncon troncon3 = new Troncon("Rue des Acacia", 3.15, intermediaire2, arrivee1);

        Intersection intermediaire3 = new Intersection("O5", 9.5, 5.36);
        Intersection intermediaire4 = new Intersection("O6", 9.5, 6.21);
        Intersection arrivee2 = new Intersection("07", 10.1, 6.21);

        Troncon troncon4 = new Troncon("Rue des Acacia", 36.2, arrivee1, intermediaire3);
        Troncon troncon5 = new Troncon("Chemin de Traverse", 6.8, intermediaire3, intermediaire4);
        Troncon troncon6 = new Troncon("Boulevard des Anges", 12.3, intermediaire4, arrivee2);

        List<Troncon> mesTroncons1 = new ArrayList<Troncon>();
        mesTroncons1.add(troncon1);
        mesTroncons1.add(troncon2);
        mesTroncons1.add(troncon3);

        List<Troncon> mesTroncons2 = new ArrayList<Troncon>();
        mesTroncons2.add(troncon4);
        mesTroncons2.add(troncon5);
        mesTroncons2.add(troncon6);

        Livraison livraison1 = new Livraison(depart1,arrivee1,Gwen, 8);
        livraison1.setParcoursLivraison(mesTroncons1);

        Livraison livraison2 = new Livraison(arrivee1,arrivee2,Gwen,10);
        livraison2.setParcoursLivraison(mesTroncons2);

        //List<Livraison> mesLivraisons = new ArrayList<Livraison>();
        //mesLivraisons.add(livraison1);
        //mesLivraisons.add(livraison2);

        Tournee maTournee = new Tournee();
        maTournee.attribuerTournee(Gwen);
        maTournee.ajouterLivraison(livraison1);
        maTournee.ajouterLivraison(livraison2);

        FeuilleDeRoute maFeuilleDeRoute = new FeuilleDeRoute();
        maFeuilleDeRoute.CreerFeuille(maTournee);
    }
}