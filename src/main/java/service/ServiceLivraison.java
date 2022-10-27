package service;

import modele.Intersection;
import modele.Livraison;

import java.util.Set;

public interface ServiceLivraison {
    public void ajouterLivraison(Livraison livraison);
    public void suprimmerLivraison(Livraison livraison);
    public Set<Livraison> afficherToutLivraisons();
    public Set<Livraison> afficherLivraisonParIntersection(Intersection intersection);
}
